package ch16;

import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.*;
import java.util.Scanner;

public class TcpIpServer5 {
    public static void main(String[] args) {
        ServerSocket serverSocket = null;
        Socket socket = null;

        try {
            serverSocket = new ServerSocket(7777);
            System.out.println("서버가 준비되었습니다.");

            socket = serverSocket.accept();

            /*
            소켓으로 데이터를 송신하는 작업과, 소켓으로 데이터를 수신하는 작업을
            별도의 쓰레드 Sender, Receiver가 처리하도록 하여
            송신과 수신이 동시에 이루어지도록 함 -> 1:1채팅이 가능
             */
            Sender sender = new Sender(socket);
            Receiver receiver = new Receiver(socket);

            sender.start();
            receiver.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

class Sender extends Thread {
    Socket socket;
    DataOutputStream dataOutputStream;
    String name;

    Sender(Socket socket) {
        this.socket = socket;
        try{
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            name = "[" + socket.getInetAddress() + " : " + socket.getPort() + "]";
        } catch (Exception e) {}
    }

    @Override
    public void run() {
        Scanner scanner = new Scanner(System.in);
        while (dataOutputStream != null) {
            try {
                dataOutputStream.writeUTF(name + scanner.nextLine());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}

class Receiver extends Thread {
    Socket socket;
    DataInputStream dataInputStream;

    Receiver(Socket socket) {
        this.socket = socket;
        try {
            dataInputStream = new DataInputStream(socket.getInputStream());
        } catch (Exception e) {}
    }

    @Override
    public void run() {
        while (dataInputStream != null) {
            try {
                System.out.println(dataInputStream.readUTF());
            } catch (IOException e) {}
        }
    }
}

