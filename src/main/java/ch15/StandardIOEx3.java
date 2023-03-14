package ch15;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

/**
 * 표준 입출력의 대상 변경 연습
 *
 * setOut() - System.out의 출력을 지정된 PrintStream으로 변경
 * setErr() - System.err의 출력을 지정된 PrintStream으로 변경
 * setIn() - System.in의 입력을 지정한 InputStream으로 변경
 */
public class StandardIOEx3 {
    public static void main(String[] args) {
        PrintStream printStream = null;
        FileOutputStream fileOutputStream = null;

        try {
            fileOutputStream = new FileOutputStream("test.txt");
            printStream = new PrintStream(fileOutputStream);
            System.setOut(printStream); //System.out의 출력대상을 test.txt파일로 변경
        } catch (IOException e) {
            System.err.println("File not found.");
        }

        System.out.println("Hello by System.out");
        System.err.println("Hello by System.err");

    }
}
