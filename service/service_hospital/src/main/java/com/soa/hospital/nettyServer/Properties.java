package com.soa.hospital.nettyServer;

import lombok.Data;

@Data
public class Properties {
    private String ip="http://127.0.0.1";

    private Integer hospitalPort=18080;

    private Integer GovernmentPort=18081;
}
