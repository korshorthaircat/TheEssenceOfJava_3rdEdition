package ch15;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

public class SerialEx2 {
    public static void main(String[] args) {
        try {
            String fileName = "UserInfo.ser";
            FileInputStream fileInputStream = new FileInputStream(fileName);
            BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
            ObjectInputStream objectInputStream = new ObjectInputStream(bufferedInputStream);

            //객체를 읽어올 때는 출력한 순서와 일치해야 한다...
            //그래서 직렬화할 객체가 많을 때는 각 객체를 개별적으로 직렬화하는 것보다 ArrayList같은 컬렉션에 저장해 직렬화하는 것이 좋다.(역직렬화할때 순서를 고려하지 않아도 되기 때문)
            UserInfo userInfo1 = (UserInfo) objectInputStream.readObject();
            UserInfo userInfo2 = (UserInfo) objectInputStream.readObject();
            ArrayList userInfoList = (ArrayList) objectInputStream.readObject();

            System.out.println("userInfo1 = " + userInfo1);
            System.out.println("userInfo2 = " + userInfo2);
            System.out.println("userInfoList = " + userInfoList);

            objectInputStream.close();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
