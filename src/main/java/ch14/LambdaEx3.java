package ch14;

@FunctionalInterface
interface MyFunction3{
    void myMethod();
}

class Outer{
    int val = 10; //Outer.this.val

    class Inner {
        int val = 20; //this.val

        void method(int i) { //void method(final int i) {
            int val = 30; //final int val = 30;
//            i = 10; //에러. 상수의 값을 변경할 수 없다.

            //람다식 내에서 외부에 선언된 변수에 접근하기
            //람다식 내에서 참조하는 지역변수는 final이 붙지 않아도 상수로 간주된다.
            //람다식 내에서 지역변수 i와 val을 참조하고 있으므로, 람다식 내에서나 다른 어느곳에서나 이 변수들의 값을 변경하는것은 허용되지 않는다.
            //반면 Inner클래스와 Outer클래스의 인스턴스 변수인 this.val과 Outer.this.val은 상수로 간주되지 않으므로 값을 변경할 수 있다.
            MyFunction3 f = () -> {
                System.out.println("             i:" + i);
                System.out.println("           val:" + val);
                System.out.println("      this.val:" + ++this.val);
                System.out.println("outer.this.val:" + ++Outer.this.val);
            };
            f.myMethod();
        }
    } //Inner클래스의 끝
}//Outer클래스의 끝

class LambdaEx3 {
    public static void main(String[] args) {
        Outer outer = new Outer();
        Outer.Inner inner = outer.new Inner();
        inner.method(100);
    }

}
