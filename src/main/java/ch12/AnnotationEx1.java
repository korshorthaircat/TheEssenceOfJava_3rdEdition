package ch12;

class Parent {
    void parentMethod() {}
}

class Child extends Parent {
    @Override
    void parentMethod() {} //조상 메서드의 이름을 잘못 적을 경우 컴파일 오류가 확인됨
//    error: method does not override or implement a method from a supertype
//    @Override
}

