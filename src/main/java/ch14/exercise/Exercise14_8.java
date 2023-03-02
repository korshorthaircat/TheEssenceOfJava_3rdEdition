package ch14.exercise;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;

public class Exercise14_8 {
    //다음은 불합격(150점 미만)인 학생의 수를 남자와 여자로 구별하여 출력하는 프로그램이다. 알맞은 코드를 넣어 완성하라.
    public static void main(String[] args) {
        Student[] studentArr = {
                new Student("나자바", true, 1, 1, 300),
                new Student("김지미", false, 1, 1, 250),
                new Student("김자바", true, 1, 1, 200),
                new Student("이지미", false, 1, 2, 150),
                new Student("남자바", true, 1, 2, 100),
                new Student("안지미", false, 1, 2, 50),
                new Student("황지미", false, 1, 3, 100),
                new Student("강지미", false, 1, 3, 150),
                new Student("이자바", true, 1, 3, 200),
                new Student("나자바", true, 2, 1, 300),
                new Student("김지미", false, 2, 1, 250),
                new Student("김자바", true, 2, 1, 200),
                new Student("이지미", false, 2, 2, 150),
                new Student("남자바", true, 2, 2, 100),
                new Student("안지미", false, 2, 2, 50),
                new Student("황지미", false, 2, 3, 100),
                new Student("강지미", false, 2, 3, 150),
                new Student("이자바", true, 2, 3, 200)
        };

        Map<Boolean, Map<Boolean, Long>> failedStudentBySex = Stream.of(studentArr)
                .collect(groupingBy(
                                Student::isMale,
                                partitioningBy(student -> student.getScore() < 150, counting())
                        )
                );

        long failedMaleStudentNum = failedStudentBySex.get(true).get(true);
        long failedFemaleStudentNum = failedStudentBySex.get(false).get(true);

        System.out.println("불합격[여자]: " + failedFemaleStudentNum + "명");
        System.out.println("불합격[남자]: " + failedMaleStudentNum + "명");
    }
}

class Student {
    String name;
    boolean isMale;
    int hak;
    int ban;
    int score;

    public Student(String name, boolean isMale, int hak, int ban, int score) {
        this.name = name;
        this.isMale = isMale;
        this.hak = hak;
        this.ban = ban;
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public boolean isMale() {
        return isMale;
    }

    public int getHak() {
        return hak;
    }

    public int getBan() {
        return ban;
    }

    public int getScore() {
        return score;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", isMale=" + isMale +
                ", hak=" + hak +
                ", ban=" + ban +
                ", score=" + score +
                '}';
    }

    //groupingBy()에서 사용
    enum Level {HIGH, MID, LOW}
}