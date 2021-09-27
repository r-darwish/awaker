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
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.io.IOException;
import java.util.List;

@Controller("/wake")
@RequiredArgsConstructor
public class Wake {
    private final WakeOnLan wakeOnLan;

    @Property(name = "awaker.clients")
    protected String broadcastAddress;

    @Property(name = "awaker.clients")
    protected List<Host> hosts;

    @Post
    public Flux<String> wake(@Parameter MacAddress macAddress) {
        return Flux.just(macAddress)
            .publishOn(Schedulers.boundedElastic())
            .handle((addr, sink) ->
                {
                    try {
                        wakeOnLan.wake(addr, broadcastAddress);
                        sink.next("SUCCESS");
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
