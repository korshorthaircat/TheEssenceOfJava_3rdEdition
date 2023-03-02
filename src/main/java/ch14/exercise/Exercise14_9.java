package ch14.exercise;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;

public class Exercise14_9 {
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



        Map<Integer, List<Student>> studentByHakAndBan = Stream.of(studentArr)
                .collect(groupingBy(Student::getHak));

        Map<Integer, Map<Integer, Long>> totalScoreByHakAndBan = Stream.of(studentArr)
                .collect(
                        groupingBy(
                                Student::getHak,
                                groupingBy(Student::getBan, summingLong(Student::getScore)
                                )
                        )
                );

        for(Object e : totalScoreByHakAndBan.entrySet()) {
            System.out.println(e);
        }
        //실행결과
//        1={1=750, 2=300, 3=450}
//        2={1=750, 2=300, 3=450}
    }
}
