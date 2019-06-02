package com.riversoft.weixin.common.media;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.riversoft.weixin.common.util.DateDeserializer;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * Created by exizhai on 11/21/2015.
 */
@Setter
@Getter
public class Media {

    private MediaType type;

    @JsonProperty("media_id")
    private String mediaId;

    @JsonDeserialize(using = DateDeserializer.class)
    @JsonProperty("created_at")
    private Date createdAt;

}
