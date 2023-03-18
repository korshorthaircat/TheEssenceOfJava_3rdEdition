package ch16;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TcpIpServer4 implements Runnable {
    ServerSocket serverSocket;
    Thread[] threadArr;

    public static void main(String[] args) {
        /*
        여러개의 쓰레드를 생성해 클라이언트의 요청을 동시에 처리하도록 함
        서버에 접속하는 클라이언트의 수가 많을 때는 쓰레드를 이용해 클라이언트의 요청을 병렬적으로 처리하는 것이 좋다.
        그렇지 않으면 서버가 접속을 요청한 순서대로 처리하므로 늦게 접속요청한 클라이언트는 오랜 시간을 기다려야 하기 때문이다.
         */
        TcpIpServer4 server = new TcpIpServer4(5); //5개의 쓰레드를 생성하는 서버를 생성한다.
        server.start();
    }

    public TcpIpServer4(int num) {
        try {
            serverSocket = new ServerSocket(7777);
            System.out.println(getTimeAndName() + "서버가 준비되었습니다.");

            threadArr = new Thread[num];
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void start() {
        for (int i = 0; i < threadArr.length; i++) {
            threadArr[i] = new Thread(this);
            threadArr[i].start();
        }
    }

    @Override
    public void run() {
        while (true) {

            try {
                System.out.println(getTimeAndName() + "가 연결요청을 기다립니다.");
                Socket socket = serverSocket.accept();
                System.out.println(getTimeAndName() + socket.getInetAddress() + "로부터 연결요청이 들어왔습니다.");

                /*
                소켓의 출력 스트림을 얻는다.
                 */
                OutputStream outputStream = socket.getOutputStream();
                DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
                dataOutputStream.writeUTF("[Notice] Test Message1 from Server");
                System.out.println(getTimeAndName() + "데이터를 전송했습니다.");

                /*
                스트림과 소켓을 닫아준다.
                 */
                dataOutputStream.close();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    static String getTimeAndName() {
        String name = Thread.currentThread().getName();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("[hh:mm:ss]");
        return simpleDateFormat.format(new Date()) + name;
    }
}
