package ch16;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TcpIpServer3 {
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
                System.out.println(getTime() + "연결요청을 기다립니다.");

                /*
                setSoTimeout()을 사용하여 서버소켓의 요청 대기시간을 설정한다 (파라미터에 0 입력시 제한시간 없이 대기한다.)
                대기시간동안 접속 요청이 없으면 SocketTimeoutException이 발생한다.
                 */
                serverSocket.setSoTimeout(5 * 1000); //연결요청을 5초간 기다린다.
                Socket socket = serverSocket.accept();
                System.out.println(getTime() + socket.getInetAddress() + "로부터 연결요청이 들어왔습니다.");

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
            } catch (SocketTimeoutException ste) {
                System.out.println("지정된 시간동안 접속요청이 없어 서버를 종료합니다.");
                System.exit(0);
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
