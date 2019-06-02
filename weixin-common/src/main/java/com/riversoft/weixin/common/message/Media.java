package com.riversoft.weixin.common.message;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Created by exizhai on 9/26/2015.
 */
@Setter
@Getter
public class Media implements Serializable {

    @JsonProperty("media_id")
    @JacksonXmlProperty(localName = "MediaId")
    private String mediaId;

    public Media(String mediaId) {
        this.mediaId = mediaId;
    }

}
