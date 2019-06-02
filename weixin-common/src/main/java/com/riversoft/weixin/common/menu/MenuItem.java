package com.riversoft.weixin.common.menu;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by exizhai on 9/25/2015.
 */
@Setter
@Getter
public class MenuItem implements Serializable {

    private MenuType type;
    private String name;
    private String key;

    //view, miniprogram 必须
    private String url;

    //media_id,view_limited 必须
    @JsonProperty("media_id")
    private String mediaId;

    //小程序必须
    @JsonProperty("appid")
    private String appId;

    @JsonProperty("pagepath")
    private String pagePath;

    @JsonProperty("sub_button")
    private List<MenuItem> subItems = new ArrayList<MenuItem>();

    public MenuItem name(String name) {
        this.name = name;
        return this;
    }

    public MenuItem key(String key) {
        this.key = key;
        return this;
    }

    public MenuItem url(String url) {
        this.setUrl(url);
        return this;
    }

    public MenuItem type(MenuType type) {
        this.type = type;
        return this;
    }

    public MenuItem add(MenuItem menuItem) {
        this.subItems.add(menuItem);
        return this;
    }
}
