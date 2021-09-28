package com.github.rdarwish.data;

import io.micronaut.core.annotation.Introspected;

@Introspected
public class MacAddress {
    private final byte[] bytes;

    public MacAddress(String address) throws MalformedMacAddress {
        bytes = new byte[6];
        String[] hex = address.split("([:\\-])");
        if (hex.length != 6) {
            throw new MalformedMacAddress();
        }
        try {
            for (int i = 0; i < 6; i++) {
                bytes[i] = (byte) Integer.parseInt(hex[i], 16);
            }
        } catch (NumberFormatException e) {
            throw new MalformedMacAddress();
        }
    }

    public byte[] getBytes() {
        return bytes;
    }

    @Override
    public String toString() {
        return String.format("%02x:%02x:%02x:%02x:%02x:%02x",
            bytes[0], bytes[1], bytes[2], bytes[3], bytes[4], bytes[5]);
    }
}
