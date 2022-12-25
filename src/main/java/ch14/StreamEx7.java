package ch14;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.*;

public class StreamEx7 {
    public static void main (String[] args) {
        StudentEx7[] studentEx7Arr = {
                new StudentEx7("김자바", true, 1, 1, 100),
                new StudentEx7("박자바", false, 2, 2, 200),
                new StudentEx7("안자바", true, 3, 2, 290),
                new StudentEx7("류자바", false, 1, 2, 150),
                new StudentEx7("최자바", true, 2, 3, 240),
                new StudentEx7("김자바", false, 3, 3, 230),
                new StudentEx7("이자바", true, 1, 3, 210),
                new StudentEx7("강자바", false, 2, 1, 300),
                new StudentEx7("전자바", true, 2, 1, 220),
                new StudentEx7("오자바", false, 3, 2, 110)
        };

        //단순 분할(성별로 분할)
        System.out.println("\n1. 단순분할(성별로 분할)\n");

        Map<Boolean, List<StudentEx7>> studentBySex = Stream.of(studentEx7Arr)
                .collect(Collectors.partitioningBy(StudentEx7::getIsMale)); //학생들을 성별로 분할
        List<StudentEx7> maleStudentEx7 = studentBySex.get(true); //Map에서 남학생 목록을 얻음
        List<StudentEx7> femaleStudentEx7 = studentBySex.get(false); //Map에서 여학생 목록을 얻음

        for(StudentEx7 s : maleStudentEx7) System.out.println(s);
        for(StudentEx7 s : femaleStudentEx7) System.out.println(s);

        //단순 분할 + 통계(성별 학생수)
        System.out.println("\n2. 단순분할 + 통계(성별 학생수)\n");
        Map<Boolean, Long> studentCountBySex = Stream.of(studentEx7Arr)
                .collect(Collectors.partitioningBy(StudentEx7::getIsMale, Collectors.counting()));

        System.out.println("남학생 수: " + studentCountBySex.get(true));
        System.out.println("여학생 수: " + studentCountBySex.get(false));

        Map<Boolean, Long> studentTotalScoreBySex = Stream.of(studentEx7Arr)
                .collect(Collectors.partitioningBy(StudentEx7::getIsMale, Collectors.summingLong(StudentEx7::getScore)));

        System.out.println("남학생 총점: " + studentTotalScoreBySex.get(true));
        System.out.println("여학생 총점: " + studentTotalScoreBySex.get(false));

        //단순 분할 + 통계(성별 1등)
        System.out.println("\n3. 단순분할 + 통계(성별 1등)\n");
        Map<Boolean, Optional<StudentEx7>> studentTopBySex = Stream.of(studentEx7Arr)
                .collect(Collectors.partitioningBy(StudentEx7::getIsMale, Collectors.maxBy(Comparator.comparingInt(StudentEx7::getScore))));
            //maxBy()는 반환타입이 Optional<T>이다.

        System.out.println("남학생 1등: " + studentTopBySex.get(true));
        System.out.println("여학생 1등: " + studentTopBySex.get(false));

        Map<Boolean, StudentEx7> studentTopBySex2 = Stream.of(studentEx7Arr)
                .collect(Collectors.partitioningBy(StudentEx7::getIsMale,
                        Collectors.collectingAndThen(
                                Collectors.maxBy(Comparator.comparingInt(StudentEx7::getScore)), Optional::get
                        )
                ));
            //collectingAndThen()과 Optional::get을 함께 사용하면 Optional이 아닌 객체로 반환받을 수 있다.

        System.out.println("남학생 1등: " + studentTopBySex2.get(true));
        System.out.println("여학생 1등: " + studentTopBySex2.get(false));

        //다중 분할(성별 불합격자, 100점 이하)
        System.out.println("\n4. 다중분할(성별 불합격자, 100점 이하)\n");

        Map<Boolean, Map<Boolean, List<StudentEx7>>> failedStudentBySex = Stream.of(studentEx7Arr)
                .collect(Collectors.partitioningBy(StudentEx7::getIsMale,
                        Collectors.partitioningBy(s -> s.getScore() <= 100))
                );
        List<StudentEx7> failedMaleStudentEx7 = failedStudentBySex.get(true).get(true);
        List<StudentEx7> unfailedMaleStudentEx7 = failedStudentBySex.get(true).get(false);
        List<StudentEx7> failedFemaleStudentEx7 = failedStudentBySex.get(false).get(true);
        List<StudentEx7> unfailedFemaleStudentEx7 = failedStudentBySex.get(false).get(false);

        System.out.println("불합격한 남학생: ");
        for(StudentEx7 s : failedMaleStudentEx7) System.out.println(s);
        System.out.println("합격한 남학생: ");
        for(StudentEx7 s : unfailedMaleStudentEx7) System.out.println(s);
        System.out.println("불합격한 여학생: ");
        for(StudentEx7 s : failedFemaleStudentEx7) System.out.println(s);
        System.out.println("합격한 여학생: ");
        for(StudentEx7 s : unfailedFemaleStudentEx7) System.out.println(s);
    }
}

class StudentEx7 {
    String name;
    boolean isMale; //성별
    int hak; //학년
    int ban; //반
    int score; //점수

    //groupingBy()에서 사용
    enum Level { HIGH, MID, LOW } //성적을 상중하 세 단계로 분류함

    @Override
    public String toString() {
        return String.format("[%s, %s, %d학년 %d반, %3d점]", name, isMale ? "남" : "여", hak, ban, score);
    }

    public StudentEx7(String name, boolean isMale, int hak, int ban, int score) {
        this.name = name;
        this.isMale = isMale;
        this.hak = hak;
        this.ban = ban;
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public boolean getIsMale() {
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
}
