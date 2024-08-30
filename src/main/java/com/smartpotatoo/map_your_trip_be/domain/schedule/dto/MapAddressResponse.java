package com.smartpotatoo.map_your_trip_be.domain.schedule.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MapAddressResponse {
    public String ctprvnNm;
    public String sggNm;
    public String dtlAdres;
    public String jibunAddress;
    public String englishAddress;
    //경도
    public String x;
    //위도
    public String y;

}