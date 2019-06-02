package com.riversoft.weixin.mp.url;

import com.riversoft.weixin.common.WxClient;
import com.riversoft.weixin.common.exception.WxRuntimeException;
import com.riversoft.weixin.common.util.JsonMapper;
import com.riversoft.weixin.mp.MpWxClientFactory;
import com.riversoft.weixin.mp.base.AppSetting;
import com.riversoft.weixin.mp.base.WxEndpoint;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * Created by exizhai on 12/1/2015.
 */
@Slf4j
public class Urls {

    private WxClient wxClient;

    public static Urls defaultUrls() {
        return with(AppSetting.defaultSettings());
    }

    public static Urls with(AppSetting appSetting) {
        Urls urls = new Urls();
        urls.setWxClient(MpWxClientFactory.getInstance().with(appSetting));
        return urls;
    }

    public void setWxClient(WxClient wxClient) {
        this.wxClient = wxClient;
    }

    /**
     * 长链接转换成短链接
     *
     * @param longUrl
     * @return
     */
    public String url2short(String longUrl) {
        String url = WxEndpoint.get("url.url.toshort");
        String json = String.format("{\"action\":\"long2short\",\"long_url\":\"%s\"}", longUrl);

        log.debug("long url to short: {}", json);
        String response = wxClient.post(url, json);

        Map<String, Object> result = JsonMapper.defaultMapper().json2Map(response);
        if (result.containsKey("short_url")) {
            return result.get("short_url").toString();
        } else {
            throw new WxRuntimeException(999, "long url to short failed.");
        }
    }
}
