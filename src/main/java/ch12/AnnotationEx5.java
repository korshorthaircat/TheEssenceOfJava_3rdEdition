package ch12;
import java.lang.annotation.*;

//애너테이션을 직접 정의하고, 애너테이션의 요소의 값을 출력하는 법을 보여주는 예제
@Deprecated
@SuppressWarnings("1111") // 유효하지 않은 애너테이션은 무시된다.
@TestInfo(testedBy="yeonghyeon", testDate=@DateTime(yymmdd="221204", hhmmss="142700"))
class AnnotationEx5 {
    public static void main(String args[]) {
        // AnnotaionEx5의 Class객체를 얻는다.
        Class<AnnotationEx5> cls = AnnotationEx5.class;
        //AnnotationEx5.class는 클래스객체를 의미하는 리터럴임.
        //모든 클래스 파일은 클래스로더에 의해 메모리에 올라갈 때, 클래스에 대한 정보가 담긴 객체를 생성하는데 이 객체를 클래스 객체라고 함.
        //이 객체를 참조할때는 '클래스이름.class'의 형식을 사용함
        //클래스 객체는 해당 클래스에 대한 모든 정보를 가지고 있으므로, 애너테이션의 정보도 가지고있음.
        TestInfo anno = (TestInfo)cls.getAnnotation(TestInfo.class);

        System.out.println("anno.testedBy()="+anno.testedBy());
        System.out.println("anno.testDate().yymmdd()="+anno.testDate().yymmdd());
        System.out.println("anno.testDate().hhmmss()="+anno.testDate().hhmmss());

        for(String str : anno.testTools())
            System.out.println("testTools="+str);

        System.out.println();

        // AnnotationEx5에 적용된 모든 애너테이션을 배열로 가져온다.
        Annotation[] annoArr = cls.getAnnotations();

        for(Annotation a : annoArr)
            System.out.println(a);
    }
}

@Retention(RetentionPolicy.RUNTIME)  // 실행 시에 사용가능하도록 지정
@interface TestInfo {
    int       count()	    default 1;
    String    testedBy();
    String[]  testTools()   default "JUnit";
    TestType  testType()    default TestType.FIRST;
    DateTime  testDate();
}

@Retention(RetentionPolicy.RUNTIME)  // 실행 시에 사용가능하도록 지정
@interface DateTime {
    String yymmdd();
    String hhmmss();
}

enum TestType { FIRST, FINAL }