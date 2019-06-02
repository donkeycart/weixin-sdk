package com.riversoft.weixin.common.jsapi;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by exizhai on 1/28/2016.
 */
@Setter
@Getter
public class JsAPISignature {

    private String appId;
    private long timestamp;
    private String nonce;
    private String signature;
    private String url;

    /**
     * 可选
     */
    private String groupId;
}
