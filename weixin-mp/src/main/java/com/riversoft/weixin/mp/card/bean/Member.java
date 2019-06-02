package com.riversoft.weixin.mp.card.bean;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 会员卡
 * Created by exizhai on 11/27/2015.
 */
@Getter
@Setter
public class Member extends AbstractCard {

    /**
     * 可选
     * 商家自定义会员卡背景图，须
     * 先调用上传图片接口将背景图上传至CDN，否则报错，
     * 卡面设计请遵循微信会员卡自定义背景设计规范  ,像素大小控制在
     * 1000像素*600像素以下
     */
    @JsonProperty("background_pic_url")
    private String backgroundPic;

    /**
     * 必填，会员卡特权说明
     */
    @JsonProperty("prerogative")
    private String prerogative;

    /**
     * 可选，设置为true时用户领取会员卡后系统自动将其激活，无需调用激活接口
     */
    @JsonProperty("auto_activate")
    private boolean autoActivate;

    /**
     * 可选，设置为true时会员卡支持一键开卡，不允许同时传入activate_url字段，否则设置wx_activate失效。填入该字段后仍需调用接口设置开卡项方可生效
     */
    @JsonProperty("wx_activate")
    private boolean wxActivate;

    /**
     * 必填，显示积分，填写true或false，如填写true，积分相关字段均为必填
     */
    @JsonProperty("supply_bonus")
    private boolean supplyBonus;

    /**
     * 可选。设置跳转外链查看积分详情。仅适用于积分无法通过激活接口同步的情况下使用该字段
     */
    @JsonProperty("bonus_url")
    private String bonusUrl;

    /**
     * 必填，是否支持储值，填写true或false。如填写true，储值相关字段均为必填
     */
    @JsonProperty("supply_balance")
    private boolean supplyBalance;

    /**
     * 可选：设置跳转外链查看余额详情。仅适用于余额无法通过激活接口同步的情况下使用该字段
     */
    @JsonProperty("balance_url")
    private String balanceUrl;

    /**
     * 自定义会员信息类目，会员卡激活后显示,包含name_type和url字段
     */
    @JsonProperty("custom_field1")
    private CustomField customField1;

    /**
     * 自定义会员信息类目，会员卡激活后显示，包含name_type和url字段
     */
    @JsonProperty("custom_field2")
    private CustomField customField2;

    /**
     * 自定义会员信息类目，会员卡激活后显示，包含name_type和url字段
     */
    @JsonProperty("custom_field3")
    private CustomField customField3;

    /**
     * 可选 积分清零规则
     */
    @JsonProperty("bonus_cleared")
    private String bonusCleared;

    /**
     * 可选，积分规则
     */
    @JsonProperty("bonus_rules")
    private String bonusRules;

    /**
     * 可选，储值说明
     */
    @JsonProperty("balance_rules")
    private String balanceRules;

    /**
     * 必填，激活会员卡的url
     */
    @JsonProperty("activate_url")
    private String activateUrl;

    /**
     * 可选，自定义会员信息类目，会员卡激活后显示
     */
    @JsonProperty("custom_cell1")
    private CustomCell customCell1;

    @JsonProperty("bonus_rule")
    private BonusRule bonusRule;

    @JsonProperty("discount")
    private int discount;


    @Getter
    @Setter
    public static class CustomField {

        /**
         * 会员信息类目名称。
         * FIELD_NAME_TYPE_LEVEL         等级
         * FIELD_NAME_TYPE_COUPON        优惠券
         * FIELD_NAME_TYPE_STAMP         印花
         * FIELD_NAME_TYPE_DISCOUNT      折扣
         * FIELD_NAME_TYPE_ACHIEVEMEN    成就
         * FIELD_NAME_TYPE_MILEAGE       里程
         */
        @JsonProperty("name_type")
        private String name;

        private String url;
    }


    @Getter
    @Setter
    public static class CustomCell {

        /**
         * 入口名称
         */
        private String name;

        /**
         * 入口右侧提示语，6个汉字内
         */
        private String tips;

        /**
         * 入口跳转链接
         */
        private String url;

    }

    @Getter
    @Setter
    public static class BonusRule {

        @JsonProperty("cost_money_unit")
        private int costMoneyUnit;

        @JsonProperty("increase_bonus")
        private int increaseBonus;

        @JsonProperty("max_increase_bonus")
        private int maxIncreaseBonus;

        @JsonProperty("init_increase_bonus")
        private int initIncreaseBonus;

        @JsonProperty("cost_bonus_unit")
        private int costBonusUnit;

        @JsonProperty("reduce_money")
        private int reduceMoney;

        @JsonProperty("least_money_to_use_bonus")
        private int leastMoney2UseBonus;

        @JsonProperty("least_money_to_use_bonus")
        private int max_reduce_bonus;
    }
}
