package ch12;

import java.util.ArrayList;

class NewClass2 {
    int newField;
    int getNewField() {
        return newField;
    }

    @Deprecated
    int oldField;
    @Deprecated
    int getOldField() {
        return oldField;
    }
}
public class AnnotationEx3 {
    //@SuppressWarnings({"derpecation", "unchecked"}) //두 경고메세지를 한번에 모두 억제하도록 할 수도 있다.
    //그러나 이 애너테이션은 개별적인 대상에 붙여 경고의 억제범위는 최소화하는 편이 바람직하다. 나중에 추가될 코드에서 발생하는 경고까지 억제될 수 있기 때문이다.
    @SuppressWarnings("deprecation") //deprecation 관련 경고를 억제
    public static void main(String[] args) {
        NewClass2 nc2 = new NewClass2();
        nc2.oldField = 10; //@Deprecated가 붙은 대상을 사용
        System.out.println(nc2.getOldField()); //@Deprecated가 붙은 대상을 사용

        @SuppressWarnings("unchecked") //지네릭스 관련 경고를 억제
        ArrayList<NewClass2> list = new ArrayList<>(); //타입을 지정하지 않음
        list.add(nc2);
    }
}
