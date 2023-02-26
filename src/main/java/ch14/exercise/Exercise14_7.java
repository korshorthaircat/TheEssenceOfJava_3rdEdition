package ch14.exercise;

import java.util.Arrays;
import java.util.Random;

public class Exercise14_7 {
    //임의의 로또번호를 정렬해서 출력하시오. (1~45 수 중 랜덤한 숫자 6개를 정렬하여 출력하기)
    public static void main(String[] args) {
        int[] arr = new int[6];
        Arrays.setAll(arr, (x) -> (int)(Math.random() * 45) + 1);
        Arrays.stream(arr).sorted().forEach(System.out::println); // <-중복제거 빼먹음

        //해설
        new Random()
                .ints(1, 46) //1~45사이의 정수(46은 포함 안됨)
                .distinct() //중복제거
                .limit(6)
                .sorted()
                .forEach(System.out::println);
        //Random클래스에는 해당 타입의 난수들로 이루어진 스트림을 반환하는 인스턴스메서드들이 포함되어 있다. (ints(), longs(0, doubles())
        //이 메서드들은 무한스트림이므로 Limit()도 같이 사용하여 스트림의 크기를 제한해주어야 한다.

        new Random()
                .ints().forEach(System.out::print);

    }
}
