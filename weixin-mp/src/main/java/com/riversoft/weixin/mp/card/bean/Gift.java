package com.riversoft.weixin.mp.card.bean;

import lombok.Getter;
import lombok.Setter;

/**
 * 礼品券
 * Created by exizhai on 11/28/2015.
 */
@Getter
@Setter
public class Gift extends AbstractCard {

    /**
     * 礼品券专用，填写礼品的名称
     */
    private String gift;
}
