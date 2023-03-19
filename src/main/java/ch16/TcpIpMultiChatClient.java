package ch16;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ConnectException;
import java.net.Socket;
import java.util.Scanner;

public class TcpIpMultiChatClient {
    public static void main(String[] args) {
        if(args.length != 1) {
            System.out.println("USAGE: java TcpIpMultiChatClient 대화명");
            System.exit(0);
        }

        try {
            String serverIp = "127.0.0.1";

            //소켓을 생성하여 연결을 요청한다.
            Socket socket = new Socket(serverIp, 7777);
            System.out.println("서버에 연결되었습니다.");

            Thread sender = new Thread(new ClientSender(socket, args[0]));
            Thread receiver = new Thread(new ClientReceiver(socket));

            sender.start();
            receiver.start();
        } catch (ConnectException ce) {
            ce.printStackTrace();
        } catch (Exception e) {}
    }

    static class ClientSender extends Thread {
        Socket socket;
        DataOutputStream dataOutputStream;
        String name;

        ClientSender(Socket socket, String name) {
            this.socket = socket;
            try {
                dataOutputStream = new DataOutputStream(socket.getOutputStream());
                this.name = name;
            } catch (Exception e) {}
        }

        @Override
        public void run() {
            Scanner scanner = new Scanner(System.in);
            try {
                if(dataOutputStream != null) {
                    dataOutputStream.writeUTF(name);
                }

                while (dataOutputStream != null) {
                    dataOutputStream.writeUTF("[" + name + "]" + scanner.nextLine());
                }
            } catch (Exception e) {}
        }
    }

    static class ClientReceiver extends Thread {
        Socket socket;
        DataInputStream dataInputStream;

        ClientReceiver(Socket socket) {
            this.socket = socket;
            try {
                dataInputStream = new DataInputStream(socket.getInputStream());
            } catch (IOException e) {}
        }

        @Override
        public void run() {
            while(dataInputStream != null) {
                try {
                    System.out.println(dataInputStream.readUTF());
                } catch (IOException e) {}
            }
        }
    }
}
