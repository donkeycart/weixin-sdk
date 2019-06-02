package com.riversoft.weixin.common.message;

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
public class MpNews implements Serializable {

    private List<MpArticle> articles = new ArrayList<>();

    public void add(MpArticle mpArticle) {
        this.getArticles().add(mpArticle);
    }

    public MpNews article(MpArticle article) {
        this.getArticles().add(article);
        return this;
    }
}
