package com.github.rdarwish;

import com.github.rdarwish.data.MacAddress;

import java.io.IOException;

public interface WakeOnLan {
    void wake(MacAddress macAddress, String ipAddress) throws IOException;
}
