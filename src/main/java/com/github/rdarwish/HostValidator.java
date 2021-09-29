package com.github.rdarwish;

import com.github.rdarwish.data.Host;
import reactor.core.publisher.Mono;

public interface HostValidator {
    Mono<Void> validate(Host host);
}
