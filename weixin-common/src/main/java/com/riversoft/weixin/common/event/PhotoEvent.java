package com.riversoft.weixin.common.event;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlCData;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Created by exizhai on 11/14/2015.
 */
@Getter
@Setter
public class PhotoEvent extends EventRequest {

    @JsonProperty("EventKey")
    @JacksonXmlCData
    private String eventKey;

    @JsonProperty("SendPicsInfo")
    private SendPicsInfo sendPicsInfo;

    @Getter
    @Setter
    public static class SendPicsInfo {

        @JsonProperty("Count")
        private int count;
        @JsonProperty("PicList")
        private List<Item> items;

        public SendPicsInfo() {
        }

        @Getter
        @Setter
        public static class Item {

            @JsonProperty("PicMd5Sum")
            @JacksonXmlCData
            private String picMd5Sum;

            public Item() {
            }

        }
    }

}
