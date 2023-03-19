package ch16;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UdpServer {
    public void start() throws IOException {
        DatagramSocket datagramSocket = new DatagramSocket(7777); //포트 7777번을 사용하는 소켓을 생성한다.
        DatagramPacket inPacket;
        DatagramPacket outPacket;

        byte[] inMessage = new byte[10];
        byte[] outMessage;

        while (true) {
            //데이터를 수신하기 위한 패킷을 생성한다.
            inPacket = new DatagramPacket(inMessage, inMessage.length);

            //패킷을 통해 데이터를 수신(receive)한다.
            datagramSocket.receive(inPacket);

            //수신한 패킷으로부터 client의 IP주소와 Port를 얻는다.
            InetAddress address = inPacket.getAddress();
            int port = inPacket.getPort();

            //서버의 현재 시간을 시분초 형태로([hh:mm:ss])로 반환한다.
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("[hh:mm:ss]");
            String time = simpleDateFormat.format(new Date());
            outMessage = time.getBytes(); //time을 byte배열로 변환한다.

            //패킷을 생성해서 client에게 전송한다.
            outPacket = new DatagramPacket(outMessage, outMessage.length, address, port);
            datagramSocket.send(outPacket);
        }
    }

    public static void main(String[] args) {
        try {
            new UdpServer().start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
