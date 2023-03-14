package ch15;

import java.io.IOException;
import java.io.RandomAccessFile;


public class RandomAccessFileEx1 {
    public static void main(String[] args) {
        /**
         * 파일에 출력 작업 수행되었을 때 파일포인터 위치가 어떻게 달라지는지 확인
         *
         * int가 4바이트이므로 -> writeInt() 호출 후 파일포인터 위치가 0에서 4로 변함
         * longdl 8바이트이므로 -> wirteLong() 호출 후 파일포인터 위치가 4에서 12로 변함
         */
        try {
            RandomAccessFile randomAccessFile = new RandomAccessFile("test.dat", "rw");
            System.out.println("파일 포인터의 위치: " + randomAccessFile.getFilePointer());
            randomAccessFile.writeInt(100);
            System.out.println("파일 포인터의 위치: " + randomAccessFile.getFilePointer());
            randomAccessFile.writeLong(100L);
            System.out.println("파일 포인터의 위치: " + randomAccessFile.getFilePointer());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
