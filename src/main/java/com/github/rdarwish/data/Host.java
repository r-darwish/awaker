package com.github.rdarwish.data;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.micronaut.core.annotation.Introspected;
import lombok.Data;

@Data
@Introspected
public class Host {
    String name;
    @JsonSerialize(using = ToStringSerializer.class)
    MacAddress macAddress;
    String ipAddress;
    Short validationPort;
}
