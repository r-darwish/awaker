package com.github.rdarwish.controllers;

import com.github.rdarwish.WakeOnLan;
import com.github.rdarwish.data.Host;
import com.github.rdarwish.data.MacAddress;
import io.micronaut.context.annotation.Parameter;
import io.micronaut.context.annotation.Property;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.List;

@Controller("/wake")
@RequiredArgsConstructor
public class Wake {
    private final WakeOnLan wakeOnLan;

    @Property(name = "awaker.broadcastAddress")
    protected String broadcastAddress;

    @Property(name = "awaker.clients")
    protected List<Host> hosts;

    @Post
    public Mono<Void> wake(@Parameter MacAddress macAddress) {
        return wakeOnLan.wake(macAddress, broadcastAddress);
    }

    @Get
    public List<Host> getHosts() {
        return hosts;
    }
}
