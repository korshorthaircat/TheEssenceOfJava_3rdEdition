package ch15;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

class SuperUserInfo {
    String name;
    String password;

    public SuperUserInfo() {
        this("Unknown", "0000");
    }

    public SuperUserInfo(String name, String password) {
        this.name = name;
        this.password = password;
    }
}

/**
 * 조상으로부터 상속받은 인스턴스변수에 대한 직렬화
 */
public class UserInfo2 extends SuperUserInfo implements Serializable {
    int age;

    public UserInfo2() {
     this("Unknown", "0000", 0);
    }

    public UserInfo2(String name, String password, int age) {
        super(name, password);
        this.age = age;
    }

    @Override
    public String toString() {
        return "UserInfo2{" +
                "age=" + age +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    /**
     * 조상으로부터 상속받은 인스턴스변수에 대한 직렬화 작업시 호출됨
     * @param objectOutputStream
     * @throws IOException
     */
    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.writeUTF(name);
        objectOutputStream.writeUTF(password);
        objectOutputStream.defaultWriteObject();
    }

    /**
     * 조상으로부터 상속받은 인스턴스변수에 대한 역직렬화 작업시 호출됨
     * @param objectInputStream
     * @throws IOException
     * @throws ClassNotFoundException
     */
    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        name = objectInputStream.readUTF();
        password =  objectInputStream.readUTF();
        objectInputStream.defaultReadObject();
    }

}
