package com.riversoft.weixin.common.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlCData;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.riversoft.weixin.common.message.XmlMessageHeader;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by exizhai on 9/29/2015.
 */
@Setter
@Getter
@JacksonXmlRootElement(localName = "xml")
public class ShortVideoRequest extends XmlMessageHeader {

    @JsonProperty("MsgId")
    private String msgId;

    @JsonProperty("MediaId")
    @JacksonXmlCData
    private String mediaId;

    @JsonProperty("ThumbMediaId")
    @JacksonXmlCData
    private String thumbMediaId;
}
