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
import reactor.core.scheduler.Schedulers;

import java.io.IOException;
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
    public Mono<String> wake(@Parameter MacAddress macAddress) {
        return Mono.just(macAddress)
            .publishOn(Schedulers.boundedElastic())
            .handle((addr, sink) ->
                {
                    try {
                        wakeOnLan.wake(addr, broadcastAddress);
                        sink.next("");
                    } catch (IOException e) {
                        sink.error(e);
                    }
                }
            );
    }

    @Get
    public List<Host> getHosts() {
        return hosts;
    }
}
