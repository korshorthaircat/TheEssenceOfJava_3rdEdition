package ch15;

import java.io.EOFException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class RandomAccessFileEx2 {
    public static void main(String[] args) {

        int[] score = {//번호, 국어, 영어, 수학
                        1, 100,  90,  90,
                        2,  70,  90, 100,
                        3, 100, 100, 100,
                        4,  70,  60,  80,
                        5,  70,  90, 100
        };

        try {
            RandomAccessFile randomAccessFile = new RandomAccessFile("score2.dat", "rw");

            for (int i = 0; i < score.length; i++) {
                randomAccessFile.writeInt(score[i]);
            }

            randomAccessFile.seek(0); //이 구절이 없으면 readInt()가 아무것도 읽지 못한다.

            while (true) {
                System.out.println(randomAccessFile.readInt());
            }
        } catch (EOFException eofException) {
            //readInt()를 호출했을 때 더이상 읽을 내용이 없으면 EOFException이 발생함
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }
}
