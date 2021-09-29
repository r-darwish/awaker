package com.github.rdarwish;

import com.github.rdarwish.data.MacAddress;
import jakarta.inject.Singleton;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
import reactor.netty.udp.UdpClient;

@Singleton
@Slf4j
public class WakeOnLanImpl implements WakeOnLan {
    public static final int PORT = 9;

    @Override
    public Mono<Void> wake(MacAddress macAddress, String ipAddress) {
        var macBytes = macAddress.getBytes();
        byte[] bytes = new byte[6 + 16 * macBytes.length];
        for (int i = 0; i < 6; i++) {
            bytes[i] = (byte) 0xff;
        }
        for (int i = 6; i < bytes.length; i += macBytes.length) {
            System.arraycopy(macBytes, 0, bytes, i, macBytes.length);
        }

        return UdpClient.create()
            .doOnConnect(ignored -> log.info("Sending WOL packet to {} for address {}", ipAddress, macAddress))
            .host(ipAddress)
            .port(PORT)
            .connect()
            .flatMap(connection -> Mono.from(connection.outbound().sendByteArray(Mono.just(bytes))))
            .then();
    }


}
