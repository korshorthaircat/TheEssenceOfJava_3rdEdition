package ch16;

import java.net.URL;
import java.net.URLConnection;

public class NetworkEx3 {
    public static void main(String[] args) {
        URL url = null;
        String address = "http://www.codechobo.com/sample/hello.html";
        
        try {
            url = new URL(address);
            URLConnection urlConnection = url.openConnection();
            /*
            URLConnection생성 후 get메서드를 통해 관련 정보 추출하여 출력
             */
            System.out.println("urlConnection = " + urlConnection);
            System.out.println("urlConnection.getAllowUserInteraction() = " + urlConnection.getAllowUserInteraction());
            System.out.println("urlConnection.getConnectTimeout() = " + urlConnection.getConnectTimeout());
            System.out.println("urlConnection.getContent() = " + urlConnection.getContent());
            System.out.println("urlConnection.getContentEncoding() = " + urlConnection.getContentEncoding());
            System.out.println("urlConnection.getDate() = " + urlConnection.getDate());
            System.out.println("urlConnection.getDefaultUseCaches() = " + urlConnection.getDefaultUseCaches());
            System.out.println("urlConnection.getDoInput() = " + urlConnection.getDoInput());
            System.out.println("urlConnection.getDoOutput() = " + urlConnection.getDoOutput());
            System.out.println("urlConnection.getExpiration() = " + urlConnection.getExpiration());
            System.out.println("urlConnection.getHeaderFields() = " + urlConnection.getHeaderFields());
            System.out.println("urlConnection.getIfModifiedSince() = " + urlConnection.getIfModifiedSince());
            System.out.println("urlConnection.getLastModified() = " + urlConnection.getLastModified());
            System.out.println("urlConnection.getReadTimeout() = " + urlConnection.getReadTimeout());
            System.out.println("urlConnection.getURL() = " + urlConnection.getURL());
            System.out.println("urlConnection.getUseCaches() = " + urlConnection.getUseCaches());

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
