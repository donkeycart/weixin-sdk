package com.riversoft.weixin.common.event;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlCData;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by exizhai on 11/14/2015.
 */
@Getter
@Setter
public class LocationSelectEvent extends EventRequest {

    @JsonProperty("EventKey")
    @JacksonXmlCData
    private String eventKey;

    @JsonProperty("SendLocationInfo")
    private SendLocationInfo sendLocationInfo;

    @Getter
    @Setter
    public static class SendLocationInfo {

        @JsonProperty("Location_X")
        @JacksonXmlCData
        private String x;

        @JsonProperty("Location_Y")
        @JacksonXmlCData
        private String y;

        @JsonProperty("Scale")
        @JacksonXmlCData
        private String scale;

        @JsonProperty("Label")
        @JacksonXmlCData
        private String label;

        @JsonProperty("Poiname")
        @JacksonXmlCData
        private String poiName;

    }
}
