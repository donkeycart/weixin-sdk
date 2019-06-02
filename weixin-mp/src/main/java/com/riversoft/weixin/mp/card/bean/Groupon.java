package com.riversoft.weixin.mp.card.bean;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 优惠券
 * Created by exizhai on 11/28/2015.
 */
@Getter
@Setter
public class Groupon extends AbstractCard {

    /**
     * 团购券专用，团购详情。
     */
    @JsonProperty("default_detail")
    private String detail;

}
