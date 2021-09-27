package com.github.rdarwish;

import com.github.rdarwish.data.MacAddress;
import jakarta.inject.Singleton;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

@Singleton
public class WakeOnLanImpl implements WakeOnLan {
    public static final int PORT = 9;

    @Override
    public void wake(MacAddress macAddress, String ipAddress) throws IOException {
        var macBytes = macAddress.getBytes();
        byte[] bytes = new byte[6 + 16 * macBytes.length];
        for (int i = 0; i < 6; i++) {
            bytes[i] = (byte) 0xff;
        }
        for (int i = 6; i < bytes.length; i += macBytes.length) {
            System.arraycopy(macBytes, 0, bytes, i, macBytes.length);
        }

        InetAddress address = InetAddress.getByName(ipAddress);
        DatagramPacket packet = new DatagramPacket(bytes, bytes.length, address, PORT);
        DatagramSocket socket = new DatagramSocket();
        socket.send(packet);
        socket.close();
    }


}
