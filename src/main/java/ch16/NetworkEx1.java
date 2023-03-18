package ch16;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;

public class NetworkEx1 {
    public static void main(String[] args) {
        /*
        자바에서는 IP주소를 다루기 위한 클래스로 InetAddress 제공
         */
        InetAddress inetAddress = null;
        InetAddress[] inetAddressArr = null;

        try {
            inetAddress = InetAddress.getByName("www.naver.com");
            /*
            getByName()으로 도메인명(host)을 통해 IP주소를 얻는다.
             */
            System.out.println("inetAddress.getHostName() = " + inetAddress.getHostName());
            System.out.println("inetAddress.getHostAddress() = " + inetAddress.getHostAddress());
            System.out.println("inetAddress.toString() = " + inetAddress.toString());

            byte[] ipAddress = inetAddress.getAddress();
            System.out.println("getAddress() = " + Arrays.toString(ipAddress));

            String result = "";
            for (int i = 0; i < ipAddress.length; i++) {
                result += (ipAddress[i] < 0) ? ipAddress[i] + 256 : ipAddress[i];
                result += ".";
            }
            System.out.println("getAddress() + 256 = " + result);
            System.out.println();

        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        try {
            inetAddress = InetAddress.getLocalHost();
            /*
            getLocalHost()를 통해 호스트명과 IP주소를 알아낼 수 있음
             */
            System.out.println("inetAddress.getHostName() = " + inetAddress.getHostName());
            System.out.println("inetAddress.getHostAddress() = " + inetAddress.getHostAddress());
            System.out.println();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        try {
            inetAddressArr = InetAddress.getAllByName("www.naver.com");
            /*
            하나의 도메인 명(www.naver.com)에 여러 IP주소가 맵핑될 수도 있고, 또 그 반대의 경우도 가능하다.
            전자의 경우 getAllByName()을 통해 모든 IP주소를 얻을 수 있음
             */
            for (int i = 0; i < inetAddressArr.length; i++) {
                System.out.println("inetAddressArr[" + i + "] :" + inetAddressArr[i]);

            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }
}
