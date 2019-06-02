package com.riversoft.weixin.common;

import com.riversoft.weixin.common.exception.WxRuntimeException;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;

import javax.net.ssl.SSLContext;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.util.concurrent.TimeUnit;

/**
 * Created by exizhai on 12/1/2015.
 */
@Slf4j
public class WxSslClient {


    protected OkHttpClient httpClient = null;

    public WxSslClient(String certPath, String certPassword) {
        KeyStore keyStore = null;
        SSLContext sslcontext = null;
        try {
            keyStore = KeyStore.getInstance("PKCS12");
            FileInputStream inputStream = new FileInputStream(new File(certPath));
            keyStore.load(inputStream, certPassword.toCharArray());
//            sslcontext = SSLContext.getInstance().custom().loadKeyMaterial(keyStore, certPassword.toCharArray()).build();
        } catch (Exception e) {
            log.error("initializing WxHttpsClient failed.", e);
            throw new WxRuntimeException(999, e.getMessage());
        }

        // Allow TLSv1 protocol only
//        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext, new String[]{"TLSv1"}, null, SSLConnectionSocketFactory.getDefaultHostnameVerifier());

        OkHttpClient.Builder client = new OkHttpClient.Builder();
        //设置超时
        httpClient = client.connectTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS).build();
    }

    public String get(String url) {
        Request request = new Request.Builder().url(url).get().build();
        Call call = httpClient.newCall(request);
        try (Response response = call.execute()) {
            return parseResponse(response);
        } catch (IOException ex) {
            log.error("http get: {} failed.", url, ex);
            throw new WxRuntimeException(999, ex.getMessage());
        }
    }

    public String parseResponse(Response response) throws IOException {
        if (!response.isSuccessful()) {
            throw new WxRuntimeException(response.code(), response.message());
        }
        return response.body().string();

    }

    public String post(String url, String content) {

        RequestBody requestBody = FormBody.create(MediaType.parse("text/xml"), content);
        Request request = new Request.Builder().url(url).post(requestBody).build();
        Call call = httpClient.newCall(request);

        try (Response response = call.execute()) {
            return parseResponse(response);
        } catch (IOException ex) {
            log.error("http post: {} failed", url, ex);
            throw new WxRuntimeException(999, ex.getMessage());
        }
    }
}