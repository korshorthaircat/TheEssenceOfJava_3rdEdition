package ch14;

import java.util.*;
import java.util.stream.*;

public class StreamEx8 {
    public static void main(String[] args) {
        Student[] stuArr = {
                new Student("가자바", false, 1, 1, 100),
                new Student("나자바", false, 1, 1, 169),
                new Student("다자바", true, 1, 2, 234),
                new Student("라자바", true, 1, 2, 122),
                new Student("마자바", false, 1, 3, 123),
                new Student("바자바", true, 1, 3, 109),
                new Student("사자바", false, 1, 2, 234),
                new Student("아자바", false, 1, 3, 222),
                new Student("자자바", true, 1, 1, 100),
                new Student("차자바", false, 1, 1, 300),

                new Student("카자바", false, 2, 1, 290),
                new Student("타자바", true, 2, 1, 122),
                new Student("하자바", false, 2, 1, 212),
                new Student("강자바", true, 2, 1, 90),
                new Student("낭자바", false, 2, 2, 222),
                new Student("당자바", false, 2, 2, 100),
                new Student("랑자바", false, 2, 1, 122),
                new Student("망자바", true, 2, 3, 122),
                new Student("방자바", false, 2, 3, 221),
                new Student("상자바", true, 2, 1, 129),
        };

        //1. 단순그룹화(반별로 그룹화)
        System.out.println("\n1. 단순그룹화(반별로 그룹화)\n");

        Map<Integer, List<Student>> studentByBan = Stream.of(stuArr)
                .collect(Collectors.groupingBy(Student::getBan, Collectors.toList()));
            //groupingBy()로 그룹화하면 기본적으로 List<T>에 담음
            //따라서 위의 문장에서 Collectors.toList() 생략 가능
            //toList() 대신 toSet()이나 toCollection(HashSet::new)을 사용할 수도 있음.(단, Map의 지네릭 타입도 이 경우 적절히 바뀌어야아 함)

        for(List<Student> ban : studentByBan.values()) {
            for(Student s: ban) {
                System.out.println(s);
            }
        }

        //2. 단순그룹화(성적별로 그룹화)
        //모든 학생들을 성적별 세 그룹으로 분류하여 집계
        System.out.println("\n2. 단순그룹화(성적별로 그룹화)\n");

        Map<Student.Level, List<Student>> studentByLevel = Stream.of(stuArr)
                .collect(Collectors.groupingBy(s -> {
                    if(s.getScore() >= 200) return Student.Level.HIGH;
                    else if(s.getScore() >= 100) return Student.Level.MID;
                    else return Student.Level.LOW;
                }));
        TreeSet<Student.Level> keySet = new TreeSet<>(studentByLevel.keySet());

        for(Student.Level key : keySet) {
            System.out.println("[" + key + "]");

            for(Student s : studentByLevel.get(key)) {
                System.out.println(s);
            }
            System.out.println();

        }

        //3. 단순그룹화 + 통계(성적별 학생수)
        System.out.println("\n3. 단순그룹화 + 통계(성적별 학생수)\n");

        Map<Student.Level, Long> studentCountByLevel = Stream.of(stuArr)
                .collect(Collectors.groupingBy(s -> {
                    if(s.getScore() >= 200) return Student.Level.HIGH;
                    else if(s.getScore() >= 100) return Student.Level.MID;
                    else return Student.Level.LOW;
                }, Collectors.counting()));

        for(Student.Level key : studentCountByLevel.keySet())
            System.out.printf("[%s] - %d명, ", key, studentCountByLevel.get(key));

        for(List<Student> level : studentByLevel.values()) {
            System.out.println();
            for(Student s : level) {
                System.out.println(s);
            }
        }

        //4. 다중그룹화(학년별, 반별)
        System.out.println("\n4. 다중그룹화(학년별, 반별)\n");

        Map<Integer, Map<Integer, List<Student>>> studentByHakAndBan = Stream.of(stuArr)
                .collect(Collectors.groupingBy(Student::getHak,
                        Collectors.groupingBy(Student::getBan))
                );
            //groupingBy()를 여러번 사용하면 다수준 그룹화 가능. 학년별로 그룹화한 뒤에 다시 반별로 그룹화함.

        for(Map<Integer, List<Student>> hak : studentByHakAndBan.values()) {
            for(List<Student> ban: hak.values()) {
                System.out.println();
                for(Student s : ban) {
                    System.out.println(s);
                }
            }
        }

        //5. 다중그룹화 + 통계(학년별, 반별 1등)
        System.out.println("\n5. 다중그룹화 + 통계(학년별, 반별 1등)\n");

        Map<Integer, Map<Integer, Student>> topStudentByHakAndBan = Stream.of(stuArr)
                .collect(Collectors.groupingBy(Student::getHak,
                        Collectors.groupingBy(Student::getBan,
                                Collectors.collectingAndThen(
                                        Collectors.maxBy(Comparator.comparingInt(Student::getScore))
                                        , Optional::get
                                )
                        )
                ));

        for(Map<Integer, Student> ban : topStudentByHakAndBan.values()) {
            for(Student s : ban.values()) {
                System.out.println(s);
            }
        }

        //6. 다중그룹화 + 통계(학년별, 반별 성적그룹)
        System.out.println("\n6. 다중그룹화 + 통계(학년별, 반별 성적그룹)\n");

        Map<String, Set<Student.Level>> studentByScoreGroup = Stream.of(stuArr)
                .collect(Collectors.groupingBy(s -> s.getHak() + "-" + s.getBan(),
                        Collectors.mapping(s -> {
                            if (s.getScore() >= 200) return Student.Level.HIGH;
                            else if (s.getScore() >= 100) return Student.Level.MID;
                            else return Student.Level.LOW;
                        }, Collectors.toSet())
                ));

        Set<String> keySet2 = studentByScoreGroup.keySet();

        for(String key: keySet2) {
            System.out.println("[" + key + "]" + studentByScoreGroup.get(key));
        }
    }
}

class Student {
    String name;
    boolean isMale;
    int hak; //학년
    int ban; //반
    int score;

    @Override
    public String toString() {
        return String.format("[%s, %s, %d학년 %d반, %3d점]", name, isMale? "남" : "여", hak, ban, score);
    }

    enum Level {
        HIGH, MID, LOW
    }

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
}