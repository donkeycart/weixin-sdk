package com.riversoft.weixin.common;

import com.riversoft.weixin.common.exception.WxError;
import com.riversoft.weixin.common.exception.WxRuntimeException;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * AccessToken holder
 *
 * @borball on 8/14/2016.
 */
public abstract class AccessTokenHolder {

    private static Logger logger = LoggerFactory.getLogger(AccessTokenHolder.class);

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
        logger.debug("[{}]:fetching a new access token.", clientId);

        String url = String.format(this.tokenUrl, this.clientId, this.clientSecret);


        Request request = new Request.Builder().url(url).method("GET", null).build();
        Call call = httpClient.newCall(request);

        try (Response response = call.execute()) {
            if (response.isSuccessful()) {
                WxError wxError = WxError.fromJson(response.body().toString());
                if (wxError.getErrorCode() != 0) {
                    throw new WxRuntimeException(wxError);
                }
                return response.body().toString();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

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
