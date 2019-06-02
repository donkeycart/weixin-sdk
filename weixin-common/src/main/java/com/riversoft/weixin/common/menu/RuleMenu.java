package com.riversoft.weixin.common.menu;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 个性化菜单
 *
 * @borball on 6/7/2016.
 */
@Setter
@Getter
public class RuleMenu extends Menu {

    @JsonProperty("matchrule")
    private Rule rule;

    @JsonProperty("menuid")
    private String id;

    public RuleMenu rule(Rule rule) {
        this.setRule(rule);
        return this;
    }
}
