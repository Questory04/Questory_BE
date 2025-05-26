package com.ssafy.questory.dto.response.route;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class RouteResponseDto {
    private Integer attractionId;
    private int planId;
    private int day;
    private int sequence;
    private String name;    //place_name
    private String address; // place_address
    private Double latitude;
    private Double longitude;
    private String type;    // place_type
    private String categoryName; // categoryName
    private String phone;   // phone
}
