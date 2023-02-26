package ch14.exercise;

import java.util.stream.*;

public class Exercise14_5 {
    //문자열 배열 strArr의 모든 문자열의 길이를 더한 결과를 출력하시오.
    public static void main(String[] args) {
        String[] strArr = {"aaa", "bb", "c", "dddd"};

        int result = Stream.of(strArr).mapToInt(String::length).peek(length -> System.out.printf(length + ", ")).sum();

        System.out.println("result = " + result);

        //해설
        //1) map()을 이용해 Stream<String>을 이용해 Stream<Integer>로 변환 후 각 요소를 더하는 방법
        Stream<Integer> integerStream = Stream.of(strArr).map(str -> str.length());
        Integer sum1 = integerStream.reduce(0, (a, b) -> Integer.sum(a, b));
        System.out.println("sum1 = " + sum1);

        //2) mapToInt()를 이용해 Stream<String>을 IntStream으로 변환 후 각 요소를 더하는 방법
        //IntStream에는 sum(), max(), min()과 같은 메서드가 있어 편리하다.
        int sum2 = Stream.of(strArr).mapToInt(String::length).sum();
        System.out.println("sum2 = " + sum2);
    }
}
