package ch12;

class NewClass{
    int newField;
    int getNewField() {return newField;}

    @Deprecated
    int oldField;
    @Deprecated
    int getOldField() {return oldField;}
}

public class AnnotationEx2 {
    public static void main(String[] args) {
        NewClass nc = new NewClass();

        nc.oldField = 10;
        System.out.println(nc.getOldField());
    }
//    javac AnnotationEx2.java로 컴파일 할 경우 '-Xlint:deprecation'옵션을 사용하여 컴파일하면 자세한 내용을 알 수 있음
//    Note: AnnotationEx2.java uses or overrides a deprecated API.
//            Note: Recompile with -Xlint:deprecation for details.

//    javac -Xlint:deprecation AnnotationEx2.java로 컴파일 할 경우
//    AnnotationEx2.java:17: warning: [deprecation] oldField in NewClass has been deprecated
//    nc.oldField = 10;
//          ^
//    AnnotationEx2.java:18: warning: [deprecation] getOldField() in NewClass has been deprecated
//        System.out.println(nc.getOldField());
//                             ^
//                                     2 warnings

}
