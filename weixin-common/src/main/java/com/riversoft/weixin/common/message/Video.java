package com.riversoft.weixin.common.message;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlCData;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Created by exizhai on 9/25/2015.
 */
@Setter
@Getter
public class Video implements Serializable {

    @JsonProperty("media_id")
    @JacksonXmlProperty(localName = "MediaId")
    @JacksonXmlCData
    private String mediaId;

    @JacksonXmlProperty(localName = "Title")
    @JacksonXmlCData
    private String title;

    @JacksonXmlProperty(localName = "Description")
    @JacksonXmlCData
    private String description;

    public Video mediaId(String mediaId) {
        this.mediaId = mediaId;
        return this;
    }

    public Video title(String title) {
        this.title = title;
        return this;
    }

    public Video description(String description) {
        this.description = description;
        return this;
    }
}
