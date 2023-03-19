package ch16;

import java.io.IOException;
import java.net.*;

public class UdpClient {
    public void start() throws IOException {
        DatagramSocket datagramSocket = new DatagramSocket();
        InetAddress serverAddress = InetAddress.getByName("127.0.0.1");

        //데이터가 저장될 공간으로 byte 배열을 생성한다.
        byte[] message = new byte[100];

        DatagramPacket outPacket = new DatagramPacket(message, 1, serverAddress, 7777);
        DatagramPacket inPacket = new DatagramPacket(message, message.length);

        datagramSocket.send(outPacket); //DatagramPacket을 전송한다.
        datagramSocket.receive(inPacket); //DatagramPacket을 수신한다.

        System.out.println("current server time: " + new String(inPacket.getData()));

        datagramSocket.close();
    }

    public static void main(String[] args) {
        try {
            new UdpClient().start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
