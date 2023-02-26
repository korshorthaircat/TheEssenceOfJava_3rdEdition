package ch14.exercise;

import java.util.Comparator;
import java.util.OptionalInt;
import java.util.stream.Stream;

public class Exercise14_6 {
    //문자열 배열 strArr의 문자열 중 가장 긴 것의 길이를 출력하시오.
    public static void main(String[] args) {
        String[] strArr = {"aaa", "bb", "c", "dddd"};

        Stream.of(strArr)
                .sorted(Comparator.comparing(String::length).reversed())
                .limit(1)
                .forEach(System.out::println);

        OptionalInt max = Stream.of(strArr)
                .mapToInt(String::length)
                .max();
        System.out.println("max = " + max);

        //해설
        //문자의 길이대로 역순정렬하여 첫번째 요소 출력하기
        Stream.of(strArr)
                .map(String::length)
                .sorted(Comparator.reverseOrder())
                .limit(1)
                .forEach(System.out::println);

    }
}
