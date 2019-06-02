package com.riversoft.weixin.common;

import com.riversoft.weixin.common.exception.WxError;
import com.riversoft.weixin.common.exception.WxRuntimeException;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * AccessToken holder
 *
 * @borball on 8/14/2016.
 */
@Slf4j
public abstract class AccessTokenHolder {

    private String clientId;
    private String clientSecret;
    private String tokenUrl;
    private OkHttpClient httpClient = null;

    public AccessTokenHolder(String tokenUrl, String clientId, String clientSecret) {
        this.tokenUrl = tokenUrl;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.httpClient = new OkHttpClient();
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public void setTokenUrl(String tokenUrl) {
        this.tokenUrl = tokenUrl;
    }

    protected String fetchAccessToken() {
        log.debug("[{}]:fetching a new access token.", clientId);

        String url = String.format(this.tokenUrl, this.clientId, this.clientSecret);


        Request request = new Request.Builder().url(url).method("GET", null).build();
        Call call = httpClient.newCall(request);

        try (Response response = call.execute()) {
            if (response.isSuccessful()) {

                String result = response.body().string();

                WxError wxError = WxError.fromJson(result);
                if (wxError.getErrorCode() != 0) {
                    throw new WxRuntimeException(wxError);
                }
                return result;
            }
            throw new WxRuntimeException(999, response.toString());
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            throw new WxRuntimeException(999, e.getMessage());
        }
    }

    /**
     * 获取access token
     *
     * @return
     */
    public abstract AccessToken getAccessToken();

    /**
     * 强制刷新
     */
    public abstract void refreshToken();

    /**
     * 强制设置token过期
     */
    public abstract void expireToken();
}
