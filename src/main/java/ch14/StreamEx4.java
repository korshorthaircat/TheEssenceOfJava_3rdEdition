package ch14;

import java.util.*;
import java.util.stream.*;

public class StreamEx4 {
    public static void main(String[] args) {

        Stream<String[]> strArrStream = Stream.of(
          new String[] {"abc", "def", "ghi", "jkl"},
          new String[] {"ABC", "DEF", "GHI", "JKL"}
        );

        //map()
        //Stream<Stream<String>> strStreamStream = strArrStream.map(Arrays::stream);
        //flatMap()
        Stream<String> strStream = strArrStream.flatMap(Arrays::stream);

        strStream.map(String::toLowerCase) //모든 단어를 소문자로 변경
                .distinct() //중복제거
                .sorted() //사전순 정렬
                .forEach(System.out::println);
        System.out.println();

        String[] lineArr = {
          "Believe or not It is true",
          "Do or do not There is no try"
        };

        Stream<String> lineStream = Arrays.stream(lineArr);
        lineStream.flatMap(line -> Stream.of(line.split(" +")))
                .map(String::toLowerCase)
                .distinct()
                .sorted()
                .forEach(System.out::println);

        Stream<String> strStream1 = Stream.of("AAA", "ABC", "DFD", "Ewe");
        Stream<String> strStream2 = Stream.of("BBB", "asdf", "efs", "dfdf");

        Stream<Stream<String>> strStreamStream = Stream.of(strStream1, strStream2);
        Stream<String> strStrm = strStreamStream.map(s -> s.toArray(String[]::new))
                .flatMap(Arrays::stream);
        strStrm.map(String::toLowerCase)
                .distinct()
                .forEach(System.out::println);
    }
}
