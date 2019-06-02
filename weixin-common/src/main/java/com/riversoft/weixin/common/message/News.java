package com.riversoft.weixin.common.message;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by exizhai on 9/26/2015.
 */
@Setter
@Getter
public class News implements Serializable {

    @JacksonXmlProperty(localName = "item")
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<Article> articles = new ArrayList<>();

    public void add(Article article) {
        this.getArticles().add(article);
    }

    public News article(Article article) {
        this.getArticles().add(article);
        return this;
    }

}
