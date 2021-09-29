package com.github.rdarwish;

import com.github.rdarwish.data.Host;
import jakarta.inject.Singleton;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
import reactor.netty.tcp.TcpClient;

@Singleton
@Slf4j
public class TcpPortValidator implements HostValidator {
    @Override
    public Mono<Void> validate(Host host) {
        return TcpClient.create()
            .doOnConnect(ignored -> log.info("Connecting to {}:{}", host.getIpAddress(), host.getValidationPort()))
            .doOnConnected(ignored -> log.info("Connected successfully to {}:{}", host.getIpAddress(), host.getValidationPort()))
            .host(host.getIpAddress())
            .port(host.getValidationPort())
            .connect()
            .then();
    }
}
