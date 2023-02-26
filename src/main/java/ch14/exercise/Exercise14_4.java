package ch14.exercise;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Exercise14_4 {
    //두개의 주사위를 굴려서 나온 눈의 합의 6인 경우를 모두 출력하시오.
    //힌트: 배열 사용
    public static void main(String[] args) {
        List<int[]> dicePairList = new ArrayList<>();
        for (int i = 1; i < 6; i++) {
            dicePairList.add(new int[]{i, 6 - i});
        }

        dicePairList.forEach(arr -> System.out.println(Arrays.toString(arr)));

        //해설
        Stream<Integer> diceStream = IntStream.rangeClosed(1, 6).boxed();
        diceStream
                .flatMap(i -> Stream.of(1,2,3,4,5,6).map(i2 -> new int[] {i, i2}))
                .filter(iArr -> iArr[0] + iArr[1] == 6)
                .forEach(iArr -> System.out.println(Arrays.toString(iArr)));
    }
}
