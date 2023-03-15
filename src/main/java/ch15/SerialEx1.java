package ch15;

import java.io.*;
import java.util.ArrayList;

public class SerialEx1 {
    public static void main(String[] args) {
        try {

            UserInfo userInfo1 = new UserInfo("JavaMan", "1234", 30);
            UserInfo userInfo2 = new UserInfo("JavaWoman", "asdf", 30);
            ArrayList<UserInfo> userInfoList = new ArrayList<>();
            userInfoList.add(userInfo1);
            userInfoList.add(userInfo2); //객체 생성 및 저장

            /**
             * 생성한 객체를 직렬화하여 파일(UserInfo.ser)에 저장하는 예제
             */
            String fileName = "UserInfo.ser"; //확장자를 serialization의 약자인 ser로 하는 것이 보통이지만, 이에 대한 제약은 없다.
            FileOutputStream fileOutputStream = new FileOutputStream(fileName);
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(bufferedOutputStream);

            //객체를 직렬화한다.
            objectOutputStream.writeObject(userInfo1);
            objectOutputStream.writeObject(userInfo2);
            objectOutputStream.writeObject(userInfoList); //ArrayList에 저장된 모든 객체들과 각 객체의 인스턴스 변수가 참조하고있는 객체들까지 모두 직렬화됨
            objectOutputStream.close();
            System.out.println("직렬화가 잘 끝났습니다.");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

