package com.riversoft.weixin.common;

import com.riversoft.weixin.common.exception.WxError;
import com.riversoft.weixin.common.exception.WxRuntimeException;
import com.riversoft.weixin.common.util.CollectionUtils;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by exizhai on 9/26/2015.
 */
@Slf4j
public class WxClient {
    static final String MEDIA_TYPE_JSON = "application/json; charset=utf-8";

    protected OkHttpClient httpClient = null;
    private String clientId;
    private String clientSecret;
    private AccessTokenHolder accessTokenHolder;

    public WxClient() {
        httpClient = new OkHttpClient();
    }

    public WxClient(String tokenUrl, String clientId, String clientSecret) {
        this(clientId, clientSecret, new DefaultAccessTokenHolder(tokenUrl, clientId, clientSecret));
    }

    public WxClient(String clientId, String clientSecret, AccessTokenHolder accessTokenHolder) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.accessTokenHolder = accessTokenHolder;
        httpClient = new OkHttpClient();
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public String get(String url) {
        return get(url, true);
    }

    public String get(String url, boolean needToken) {
        if (needToken) {
            try {
                return httpGet(appendAccessToken(url));
            } catch (Exception e) {
                if (e instanceof WxRuntimeException) {
                    WxRuntimeException wxRuntimeException = (WxRuntimeException) e;
                    if (invalidToken(wxRuntimeException.getCode())) {
                        log.warn("token invalid: {}, will refresh.", wxRuntimeException.getCode());
                        refreshToken();
                        return httpGet(appendAccessToken(url));
                    }
                }
                throw e;
            }
        } else {
            return httpGet(url);
        }
    }

    public byte[] getBinary(String url, boolean needToken) {
        if (needToken) {
            try {
                return httpGetBinary(appendAccessToken(url));
            } catch (Exception e) {
                if (e instanceof WxRuntimeException) {
                    WxRuntimeException wxRuntimeException = (WxRuntimeException) e;
                    if (invalidToken(wxRuntimeException.getCode())) {
                        log.warn("token invalid: {}, will refresh.", wxRuntimeException.getCode());
                        refreshToken();
                        return httpGetBinary(appendAccessToken(url));
                    }
                }
                throw e;
            }
        } else {
            return httpGetBinary(url);
        }
    }

    private byte[] httpGetBinary(String url) {

        Request request = new Request.Builder().url(url).get().build();
        Call call = httpClient.newCall(request);
        try (Response response = call.execute()) {
            if (!response.isSuccessful()) {
                throw new WxRuntimeException(response.code(), response.message());
            }

            String contentTypeHeader = response.header("Content-Type");
            if (StringUtils.isNotBlank(contentTypeHeader)) {
                WxError wxError = WxError.fromJson(response.body().string());
                if (wxError.getErrorCode() != 0) {
                    throw new WxRuntimeException(wxError);
                }
            }
            return response.body().bytes();
        } catch (IOException ex) {
            log.error("http get: {} failed.", url, ex);
            throw new WxRuntimeException(999, ex.getMessage());
        }
    }

    private String httpGet(String url) {
        Request request = new Request.Builder().url(url).get().build();
        Call call = httpClient.newCall(request);

        try (Response response = call.execute()) {
            if (!response.isSuccessful()) {
                throw new WxRuntimeException(response.code(), response.message());
            }
            WxError wxError = WxError.fromJson(response.body().toString());
            if (wxError.getErrorCode() != 0) {
                throw new WxRuntimeException(wxError);
            }
            return response.body().string();
        } catch (IOException ex) {
            log.error("http get: {} failed.", url, ex);
            throw new WxRuntimeException(999, ex.getMessage());
        }
    }

    public File download(String url) {
        try {
            return httpDownload(appendAccessToken(url));
        } catch (Exception e) {
            if (e instanceof WxRuntimeException) {
                WxRuntimeException wxRuntimeException = (WxRuntimeException) e;
                if (invalidToken(wxRuntimeException.getCode())) {
                    log.warn("token invalid: {}, will refresh.", wxRuntimeException.getCode());
                    refreshToken();
                    return httpDownload(appendAccessToken(url));
                }
            }
            throw e;
        }
    }

    public InputStream copyStream(String url, String post) {
        try {
            return httpCopyFromStream(appendAccessToken(url), post);
        } catch (Exception e) {
            if (e instanceof WxRuntimeException) {
                WxRuntimeException wxRuntimeException = (WxRuntimeException) e;
                if (invalidToken(wxRuntimeException.getCode())) {
                    log.warn("token invalid: {}, will refresh.", wxRuntimeException.getCode());
                    refreshToken();
                    return httpCopyFromStream(appendAccessToken(url), post);
                }
            }
            throw e;
        }
    }

    /**
     * 永久素材下载使用,奇葩的下载方式
     *
     * @param url
     * @param post
     * @return
     */
    private InputStream httpCopyFromStream(String url, String post) {

        RequestBody requestBody = FormBody.create(MediaType.parse(MEDIA_TYPE_JSON), post);
        Request request = new Request.Builder().url(url).post(requestBody).build();
        Call call = httpClient.newCall(request);

        try (Response response = call.execute()) {

            if (!response.isSuccessful()) {
                throw new WxRuntimeException(response.code(), response.message());
            }
            InputStream inputStream = response.body().byteStream();
            byte[] binaryContent = IOUtils.toByteArray(inputStream);
            String content = new String(binaryContent, "UTF-8");
            if (content.contains("errcode")) {
                WxError wxError = WxError.fromJson(content);
                throw new WxRuntimeException(wxError);
            } else {
                return new ByteArrayInputStream(binaryContent);
            }

        } catch (IOException e) {
            log.error("http download: {} failed.", url, e);
            throw new WxRuntimeException(999, e.getMessage());
        }
    }

    private File httpDownload(String url) {
        Request request = new Request.Builder().url(url).get().build();
        Call call = httpClient.newCall(request);

        try (Response response = call.execute()) {
            if (!response.isSuccessful()) {
                throw new WxRuntimeException(response.code(), response.message());
            }
            String dispositionHeaders = response.header("Content-disposition");

            if (StringUtils.isNotBlank(dispositionHeaders)) {
                String fileName = extractFileName(dispositionHeaders);
                if (StringUtils.isNotBlank(fileName)) {
                    log.warn("Cannot get filename from Content-disposition");
                    fileName = UUID.randomUUID().toString();
                }
                InputStream inputStream = response.body().byteStream();
                File tempFile = new File(FileUtils.getTempDirectory(), fileName);
                FileUtils.copyInputStreamToFile(inputStream, tempFile);
                return tempFile;
            }

            String errors = response.body().string();
            log.warn("download file : {} failed: {}", url, errors);
            if (errors.contains("errcode")) {
                WxError wxError = WxError.fromJson(errors);
                throw new WxRuntimeException(wxError);
            } else {
                throw new WxRuntimeException(999, errors);
            }
        } catch (IOException e) {
            log.error("http download: {} failed.", url, e);
            throw new WxRuntimeException(999, e.getMessage());
        }
    }

    private String extractFileName(String headerValue) {
        String fileName = null;
        Pattern regex = Pattern.compile("(?<=filename=\").*?(?=\")");
        Matcher regexMatcher = regex.matcher(headerValue);
        if (regexMatcher.find()) {
            fileName = regexMatcher.group();
        }

        return fileName;
    }

    public String post(String url, String content) {
        try {
            return httpPost(appendAccessToken(url), content);
        } catch (Exception e) {
            if (e instanceof WxRuntimeException) {
                WxRuntimeException wxRuntimeException = (WxRuntimeException) e;
                if (invalidToken(wxRuntimeException.getCode())) {
                    log.warn("token invalid: {}, will refresh.", wxRuntimeException.getCode());
                    refreshToken();
                    return httpPost(appendAccessToken(url), content);
                }
            }
            throw e;
        }
    }

    public String post(String url, InputStream inputStream, String fileName, Map<String, String> form) {
        File tempFile = new File(FileUtils.getTempDirectory(), fileName);

        try {
            FileUtils.copyInputStreamToFile(inputStream, tempFile);
        } catch (IOException e) {
            log.error("http post: {} failed", url, e);
            throw new WxRuntimeException(999, "Copy stream to file failed:" + e.getMessage());
        }

        try {
            return httpPost(appendAccessToken(url), tempFile, form);
        } catch (Exception e) {
            if (e instanceof WxRuntimeException) {
                WxRuntimeException wxRuntimeException = (WxRuntimeException) e;
                if (invalidToken(wxRuntimeException.getCode())) {
                    log.warn("token invalid: {}, will refresh.", wxRuntimeException.getCode());
                    refreshToken();
                    return httpPost(appendAccessToken(url), tempFile, form);
                }
            }
            throw e;
        } finally {
            FileUtils.deleteQuietly(tempFile);
        }
    }

    public String post(String url, InputStream inputStream, String fileName) {
        return post(url, inputStream, fileName, null);
    }

    /**
     * @param url
     * @param content
     * @return
     */
    private String httpPost(String url, String content) {
        RequestBody requestBody = FormBody.create(MediaType.parse("application/json; charset=utf-8"), content);
        Request request = new Request.Builder().url(url).post(requestBody).build();
        Call call = httpClient.newCall(request);


        try (Response response = call.execute()) {
            if (!response.isSuccessful()) {
                throw new WxRuntimeException(response.code(), response.message());
            }
            WxError wxError = WxError.fromJson(response.body().toString());
            if (wxError.getErrorCode() != 0) {
                throw new WxRuntimeException(wxError);
            }
            return response.body().toString();
        } catch (IOException ex) {
            log.error("http post: {} failed", url, ex);
            throw new WxRuntimeException(999, ex.getMessage());
        }
    }

    /**
     * @param url
     * @param file
     * @return
     */
    private String httpPost(String url, File file) {
        return httpPost(url, file, null);
    }

    private String httpPost(String url, File file, Map<String, String> form) {

        RequestBody fileBody = RequestBody.create(MediaType.parse("application/octet-stream"), file);

        MultipartBody.Builder multipartBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("media", file.getName(), fileBody);

        if (CollectionUtils.isNotEmpty(form)) {
            for (String key : form.keySet()) {
                multipartBody.addFormDataPart(key, form.get(key));
            }
        }

        Request request = new Request.Builder().url(url).post(multipartBody.build()).build();
        Call call = httpClient.newCall(request);

        try (Response response = call.execute()) {
            if (!response.isSuccessful()) {
                throw new WxRuntimeException(response.code(), response.message());
            }
            return response.body().string();
        } catch (IOException ex) {
            log.error("http post: {} failed", url, ex);
            throw new WxRuntimeException(999, ex.getMessage());
        }
    }

    private String appendAccessToken(String url) {
        String token = accessTokenHolder.getAccessToken().getAccessToken();
        log.debug("[{}]:access token: {}", clientId, token);
        return url + (url.indexOf('?') == -1 ? "?access_token=" + token : "&access_token=" + token);
    }

    public void refreshToken() {
        accessTokenHolder.refreshToken();
    }

    public AccessToken getAccessToken() {
        return accessTokenHolder.getAccessToken();
    }

    private boolean invalidToken(int code) {
        boolean result = code == 42001 || code == 40001 || code == 40014;
        if (result) {
            accessTokenHolder.expireToken();//强制设置为无效
        }
        return result;
    }

}
