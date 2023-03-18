package ch16;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TcpIpServer2 {
    public static void main(String[] args) {
        ServerSocket serverSocket = null;

        try {
            /*
            서버소켓을 생성하여 7777번 포트와 결합(bind)한다.
             */
            serverSocket = new ServerSocket(7777);
            System.out.println(getTime() + "서버가 준비되었습니다.");
        } catch (IOException e) {
            e.printStackTrace();
        }

        while(true) {
            try {
                /*
                Socket클래스에 정의된 getPort()와 getLocalPort()를 통해
                TCP/IP통신에서 소켓이 사용하고 있는 포트정보를 알아낼 수 있다.
                 */
                System.out.println(getTime() + "연결요청을 기다립니다.");
                Socket socket = serverSocket.accept();
                System.out.println(getTime() + socket.getInetAddress() + "로부터 연결요청이 들어왔습니다.");
                System.out.println("socket.getPort() : " + socket.getPort()); //상대편 소켓(원격소켓)이 사용하는 포트(클라이언트 프로그램의 소켓이 사용하는 포트는 사용가능한 임의의 포트가 선택된다.)
                System.out.println("socket.getLocalPort() : " + socket.getLocalPort()); //소켓 자신이 사용하는 포트

                /*
                소켓의 출력스트림을 얻는다.
                 */
                OutputStream outputStream = socket.getOutputStream();
                DataOutputStream dataOutputStream = new DataOutputStream(outputStream);

                /*
                원격소켓(remote socket)에 데이터를 보낸다.
                 */
                dataOutputStream.writeUTF("[Notice] Test Message1 from Server.");
                System.out.println(getTime() + "데이터를 전송했습니다.");

                /*
                스트림과 소켓을 닫는다.
                 */
                dataOutputStream.close();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    static String getTime() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("[hh:mm:ss]");
        return simpleDateFormat.format(new Date());
    }
}
