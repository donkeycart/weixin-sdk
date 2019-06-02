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
public class ScanEvent extends EventRequest {

    @JsonProperty("EventKey")
    @JacksonXmlCData
    private String eventKey;

    @JsonProperty("ScanCodeInfo")
    private ScanCodeInfo scanCodeInfo;


    @Getter
    @Setter
    public static class ScanCodeInfo {

        @JsonProperty("ScanType")
        @JacksonXmlCData
        private String scanType;

        @JsonProperty("ScanResult")
        @JacksonXmlCData
        private String scanResult;

    }

}
