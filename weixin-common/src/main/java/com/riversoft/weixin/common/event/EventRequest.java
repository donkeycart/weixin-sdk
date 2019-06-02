package com.riversoft.weixin.common.event;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlCData;
import com.riversoft.weixin.common.message.XmlMessageHeader;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by exizhai on 9/30/2015.
 */
@Setter
@Getter
public class EventRequest extends XmlMessageHeader {

    @JsonProperty("Event")
    @JacksonXmlCData
    private EventType eventType;

}
