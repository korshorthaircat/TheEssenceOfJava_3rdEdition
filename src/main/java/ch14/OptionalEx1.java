package ch14;

import java.util.*;

public class OptionalEx1 {

    static int optionalStringToInt(Optional<String> optStr, int defaultValue) {
        try {
            return optStr.map(Integer::parseInt).get();
        } catch (Exception e) {
            return defaultValue;
        }
    }

    public static void main(String[] args) {
        Optional<String> optionalString = Optional.of("abcde");
        Optional<Integer> optionalInt = optionalString.map(String::length);

        System.out.println("optionalString = " + optionalString.get());
        System.out.println("optionalInt = " + optionalInt.get());

        int result1 = Optional.of("123")
                            .filter(x -> x.length() > 0)
                            .map(Integer::parseInt).get();
        int result2 = Optional.of("")
                            .filter(x -> x.length() > 0)
                            .map(Integer::parseInt).orElse(-1);

        System.out.println("result1 = " + result1);
        System.out.println("result2 = " + result2);

        Optional.of("456").map(Integer::parseInt)
                            .ifPresent(x -> System.out.printf("result3 = %d, %n", x));

        OptionalInt optionalInt1 = OptionalInt.of(0); //0을 저장
        OptionalInt optionalInt2 = OptionalInt.empty(); //빈 객체를 저장

        System.out.println(optionalInt1.isPresent()); //true
        System.out.println(optionalInt2.isPresent()); //false

        System.out.println(optionalInt1.getAsInt()); //0
        //System.out.println(optionalInt2.getAsInt()); //NoSuchElementException
        System.out.println("optionalInt1 = " + optionalInt1);
        System.out.println("optionalInt2 = " + optionalInt2);
        System.out.println("optionalInt1.equals(optionalInt2) ? " + optionalInt1.equals(optionalInt2));

        Optional<String> opt1 = Optional.ofNullable(null); //null을 저장
        Optional<String> opt2 = Optional.empty();

        System.out.println("opt1 = " + opt1);
        System.out.println("opt2 = " + opt2);
        System.out.println("opt1.equals(opt2) ? " + opt1.equals(opt2)); //true
        
        int result3 = optionalStringToInt(Optional.of("123"), 0);
        int result4 = optionalStringToInt(Optional.of(""), 0);

        System.out.println("result3 = " + result3);
        System.out.println("result4 = " + result4);
    }
}
