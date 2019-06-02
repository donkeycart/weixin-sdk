package com.riversoft.weixin.common.exception;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Created by exizhai on 9/26/2015.
 */
@Getter
@Setter
public class WxError implements Serializable {

    @JSONField(name = "errcode")
    private int errorCode;

    @JSONField(name = "errmsg")
    private String errorMsg;
    private String json;

    public static WxError fromJson(String json) {
        WxError wxError = JSONObject.parseObject(json, WxError.class);
        wxError.setJson(json);
        return wxError;
    }

}
