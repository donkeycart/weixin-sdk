package com.riversoft.weixin.common.event;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlCData;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by exizhai on 9/30/2015.
 */
@Getter
@Setter
public class ViewEvent extends EventRequest {

    @JsonProperty("EventKey")
    @JacksonXmlCData
    private String url;

}
