package ch16;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

public class NetworkEx4 {
    public static void main(String[] args) {
        URL url = null;
        BufferedReader input = null;
        String address = "http://www.codechobo.com/sample/hello.html";
        String line = "";

        try {
            url = new URL(address);
            input = new BufferedReader(new InputStreamReader(url.openStream()));
            /*
            URL에 연결 후 내용을 읽어 옴
            읽어올 데이터가 문자데이터이므로 BufferedReader 사용
            openStream() 호출해 URL의 InputStream을 얻음
            openStream()은 openConnection()을 호출해 URLConnection을 얻은 다음 여기에 다시 getInputStream()을 호출한 것 과 같음
            즉 (1)과 (2)는 같다.

            (1)
            InputStream inputStream = url.openStream();

            (2)
            URLConnection urlConnection = url.openConnection();
            InputStream inputStream = urlConnection.getInputStream();
             */

            while ( (line = input.readLine()) != null ) {
                System.out.println(line);
            }
            input.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
