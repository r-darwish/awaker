package com.github.rdarwish.data;

import com.fasterxml.jackson.annotation.JsonGetter;
import lombok.Data;

@Data
public class Host {
    String name;
    MacAddress macAddress;
    String ipAddress;

    @JsonGetter
    public String macAddress() {
        return macAddress.toString();
    }
}
