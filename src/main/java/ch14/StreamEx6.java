package ch14;

import java.util.*;
import java.util.stream.*;

public class StreamEx6 {
    public static void main (String[] args) {

        StudentEx6[] stuArr = {
                new StudentEx6("이자바", 3, 300),
                new StudentEx6("김자바", 1, 200),
                new StudentEx6("안자바", 2, 100),
                new StudentEx6("박자바", 2, 150),
                new StudentEx6("소자바", 1, 200),
                new StudentEx6("나자바", 3, 290),
                new StudentEx6("류자바", 3, 180)
        };

        //학생 이름만 뽑아서 List<String>에 저장
        List<String> stuNames = Stream.of(stuArr).map(StudentEx6::getName).collect(Collectors.toList());
        System.out.println(stuNames);

        //스트림을 배열로 변환
        StudentEx6[] stuArr2 = Stream.of(stuArr).toArray(StudentEx6[]::new);
        System.out.println(Arrays.toString(stuArr2));

        //스트림을 Map<String, Student>로 변환. 학생이름이 Key.
//        Map<String, Student> stuMap = Stream.of(stuArr).collect(Collectors.toMap(s -> s.getName(), p -> p));
        Map<String, StudentEx6> stuMap = Stream.of(stuArr).collect(Collectors.toMap(StudentEx6::getName, p -> p));

        for(String name : stuMap.keySet())
            System.out.println(name +"-"+stuMap.get(name));

        //Collectors.counting()
        long count = Stream.of(stuArr).collect(Collectors.counting());
        System.out.println("count: " + count);

        //Collectors.summingInt()
        long totalScore1 = Stream.of(stuArr).collect(Collectors.summingInt(StudentEx6::getTotalScore));
        System.out.println("totalScore1: " + totalScore1);

        //Collectors.reducing()
        long totalScore2 = Stream.of(stuArr).collect(Collectors.reducing(0, StudentEx6::getTotalScore, Integer::sum));
        System.out.println("totalScore2: " + totalScore2);

        //Collectors.comparingInt()
        Optional<StudentEx6> topStudent = Stream.of(stuArr).collect(Collectors.maxBy(Comparator.comparingInt(StudentEx6::getTotalScore)));
        System.out.println("topStudent: " + topStudent.get());

        //Collectors.summarizingInt()
        IntSummaryStatistics statistics = Stream.of(stuArr).collect(Collectors.summarizingInt(StudentEx6::getTotalScore));
        System.out.println(statistics);

        //Collectors.joining()
        String studentNames = Stream.of(stuArr).map(StudentEx6::getName).collect(Collectors.joining(",", "<<<", ">>>"));
        System.out.println(studentNames);
    }
}

class StudentEx6 implements Comparable<StudentEx6> {
    String name;
    int ban;
    int totalScore;

    StudentEx6(String name, int ban, int totalScore) {
        this.name = name;
        this.ban = ban;
        this.totalScore = totalScore;
    }

    public String toString() {
        return String.format("[%s, %d, %d]", name, ban, totalScore).toString();
    }

    @Override
    public int compareTo(StudentEx6 s) {
        return s.totalScore - this.totalScore ;
    }

    public String getName() {
        return name;
    }

    public int getBan() {
        return ban;
    }

    public int getTotalScore() {
        return totalScore;
    }
}