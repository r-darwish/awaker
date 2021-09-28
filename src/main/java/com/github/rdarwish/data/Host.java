package com.github.rdarwish.data;

import com.fasterxml.jackson.annotation.JsonGetter;
import io.micronaut.core.annotation.Introspected;
import lombok.Data;

@Data
@Introspected
public class Host {
    String name;
    MacAddress macAddress;
    String ipAddress;

    @JsonGetter
    public String macAddress() {
        return macAddress.toString();
    }
}
