package com.riversoft.weixin.mp.poi.bean;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.riversoft.weixin.common.util.BooleanDeserializer;
import com.riversoft.weixin.common.util.BooleanSerializer;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Created by exizhai on 11/30/2015.
 */
@Setter
@Getter
public class Business {

    private String sid;

    @JsonProperty("business_name")
    private String businessName;

    @JsonProperty("branch_name")
    private String branchName;
    private String province;
    private String city;
    private String district;
    private String address;
    private String telephone;
    private String[] categories;

    @JsonProperty("offset_type")
    private int offsetType;
    private double longitude;
    private double latitude;

    @JsonProperty("photo_list")
    private List<Photo> photoList;
    private String recommend;
    private String special;
    private String introduction;

    @JsonProperty("open_time")
    private String openTime;

    @JsonProperty("avg_price")
    private int avgPrice;

    @JsonProperty("poi_id")
    private String poiId;

    //以下字段查询的时候会返回，新增或者修改的时候不用提供

    /**
     * 门店是否可用状态。1 表示系统错误、2 表示审核中、3 审核通过、4 审核驳回。当该字段为1、2、4 状态时，poi_id 为空
     */
    @JsonProperty("available_state")
    @JsonDeserialize(using = StateDeserializer.class)
    private AvailableState availableState;

    /**
     * 扩展字段是否正在更新中。1 表示扩展字段正在更新中，尚未生效，不允许再次更新； 0 表示扩展字段没有在更新中或更新已生效，可以再次更新
     */
    @JsonProperty("update_status")
    @JsonDeserialize(using = BooleanDeserializer.class)
    @JsonSerialize(using = BooleanSerializer.class)
    private boolean updated;


    @Setter
    @Getter
    public static class Photo {

        @JsonProperty("photo_url")
        private String url;

        public Photo() {
        }

        public Photo(String url) {
            this.url = url;
        }
    }
}
