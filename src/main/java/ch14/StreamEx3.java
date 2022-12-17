package ch14;

import java.util.*;
import java.util.stream.*;

public class StreamEx3 {
    public static void main(String[] args) {
        StudentEx3[] studentArr = {
                new StudentEx3("김자바", 3, 300),
                new StudentEx3("이자바", 1, 200),
                new StudentEx3("박자바", 2, 150),
                new StudentEx3("안자바", 2, 180),
                new StudentEx3("소자바", 1, 190),
                new StudentEx3("황자바", 3, 210),
                new StudentEx3("전자바", 2, 230),
                new StudentEx3("최자바", 1, 120)
        };

        Stream<StudentEx3> studentStream = Stream.of(studentArr);

        studentStream.sorted(Comparator.comparing(StudentEx3::getBan) //반별정렬
                                       .thenComparing(Comparator.naturalOrder())) //성적순정렬(StudentEx3클래스에 정의한 compareTo()를 기준으로)
                     .forEach(System.out::println);

        //스트림을 다시 생성한다.
        studentStream = Stream.of(studentArr);
        IntStream studentScoreStream = studentStream.mapToInt(StudentEx3::getTotalScore);

        //IntSummaryStatistics가 제공하는 메서드들 확인
        IntSummaryStatistics stat = studentScoreStream.summaryStatistics();
        System.out.println("count: " + stat.getCount());
        System.out.println("sum: " + stat.getSum());
        System.out.println("average: " + stat.getAverage());
        System.out.println("min: " + stat.getMin());
        System.out.println("max: " + stat.getMax());
    }
}

class StudentEx3 implements Comparable<StudentEx3> {
    String name;
    int ban;
    int totalScore;

    StudentEx3(String name, int ban, int totalScore) {
        this.name = name;
        this.ban = ban;
        this.totalScore = totalScore;
    }

    String getName() { return name;}
    int getBan() { return ban; }
    int getTotalScore() { return totalScore;}

    public String toString() {
        return String.format("[%s, %d, %d]", name, ban, totalScore).toString();
    }

    @Override
    public int compareTo(StudentEx3 s) {
        return s.totalScore - this.totalScore;
    }
}