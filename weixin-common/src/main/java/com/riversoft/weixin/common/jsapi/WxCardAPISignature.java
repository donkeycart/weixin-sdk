package com.riversoft.weixin.common.jsapi;

import lombok.Getter;
import lombok.Setter;

/**
 * @borball on 2/28/2017.
 */
@Getter
@Setter
public class WxCardAPISignature {

    /**
     * 是否是拉起卡券接口，
     * 两种API可以支持：拉起卡券和添加到卡包
     * <p>
     * 拉起卡券接口appID会参与签名
     */
    private boolean chooseCard;

    private long timestamp;
    private String nonce;

    /**
     * 必填
     */
    private String cardId;

    /**
     * 拉起卡券
     */
    private String locationId;

    /**
     * 拉起卡券
     */
    private String cardType;

    /**
     * 添加到卡包:自定义code
     */
    private String code;

    /**
     * 添加到卡包:指定用户领取
     */
    private String openId;

    /**
     * 添加到卡包:红包类型卡券
     */
    private String balance;

    private String signature;

}
