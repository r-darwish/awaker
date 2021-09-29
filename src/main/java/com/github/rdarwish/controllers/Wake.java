package com.github.rdarwish.controllers;

import com.github.rdarwish.HostValidator;
import com.github.rdarwish.WakeOnLan;
import com.github.rdarwish.data.Host;
import io.micronaut.context.annotation.Parameter;
import io.micronaut.context.annotation.Property;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;

@Controller("/wake")
@RequiredArgsConstructor
@Slf4j
public class Wake {
    private final WakeOnLan wakeOnLan;
    private final HostValidator hostValidator;

    @Property(name = "awaker.broadcastAddress")
    protected String broadcastAddress;

    @Property(name = "awaker.validationDelay")
    protected Duration validationDelay;

    @Property(name = "awaker.retries")
    protected Long validationRetries;

    @Property(name = "awaker.clients")
    protected List<Host> hosts;

    @Post
    public Mono<Void> wake(@Parameter Host host) {
        return wakeOnLan.wake(host.getMacAddress(), broadcastAddress)
            .then(Mono.delay(validationDelay))
            .then(hostValidator.validate(host))
            .doOnError(e -> log.info("Error connecting to {}: ", host.getName(), e))
            .doOnSuccess(v -> log.info("Woke up {}", host.getName()))
            .retry(validationRetries);
    }

    @Get
    public List<Host> getHosts() {
        return hosts;
    }
}
