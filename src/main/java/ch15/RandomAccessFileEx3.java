package ch15;

import java.io.EOFException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class RandomAccessFileEx3 {
    public static void main(String[] args) {
        int sum = 0;

        try {
            RandomAccessFile randomAccessFile = new RandomAccessFile("score2.dat", "r");
            int i = 4;
            //socre2.dat파일에서 4번과목의 점수를 합산하고자 함

            while(true) {
                randomAccessFile.seek(i);
                sum += randomAccessFile.readInt();
                i += 16; //파일포인터의 값을 16씩 증가시켜가며 readInt() 호출
            }
        } catch (EOFException eofException) {
            System.out.println("sum = " + sum);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }
}
