package com.riversoft.weixin.common.menu;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by exizhai on 9/25/2015.
 */
@SuppressWarnings("serial")
@Getter
@Setter
public class Menu implements Serializable {

    @JsonProperty("button")
    private List<MenuItem> menus = new ArrayList<>();

    public Menu add(MenuItem menuItem) {
        this.menus.add(menuItem);
        return this;
    }

}
