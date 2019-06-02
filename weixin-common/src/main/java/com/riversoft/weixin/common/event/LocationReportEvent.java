package com.riversoft.weixin.common.event;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by exizhai on 9/30/2015.
 */
@Getter
@Setter
public class LocationReportEvent extends EventRequest {

    @JsonProperty("Latitude")
    private String latitude;

    @JsonProperty("Longitude")
    private String longitude;

    @JsonProperty("Precision")
    private String precision;
}
