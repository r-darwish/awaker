package com.github.rdarwish;

import com.github.rdarwish.data.MacAddress;
import reactor.core.publisher.Mono;

public interface WakeOnLan {
    Mono<Void> wake(MacAddress macAddress, String ipAddress);
}
