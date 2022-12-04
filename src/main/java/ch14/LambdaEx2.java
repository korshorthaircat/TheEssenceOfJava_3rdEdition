package ch14;

@FunctionalInterface
interface MyFunction2 {
    void MyMethod(); //public abstract void myMethod();
}

class LambdaEx2 {
    public static void main(String[] args) {
        MyFunction2 f = () -> {};
        //MyFunction2 f = (MyFunction2)(() -> {});
        Object obj = (MyFunction2)(() -> {}); //Object타입으로 형변환이 생략됨
        String str = ((Object)(MyFunction2)(() -> {})).toString();

        System.out.println(f);
        System.out.println(obj);
        System.out.println(str);

//        > Task :LambdaEx2.main()
//        ch14.LambdaEx2$$Lambda$1/0x0000000800060840@7b3300e5
//        ch14.LambdaEx2$$Lambda$2/0x0000000800061040@3caeaf62
//        ch14.LambdaEx2$$Lambda$3/0x0000000800061440@6bc168e5

        //컴파일러는 람다식의 타입을 어떠한 형식으로 만드는지 확인 가능
        //일반적인 익명 객체라면, 객체의 타입이 '외부클래스이름$번호'와 같은 형식으로 타입이 결정되나,
        //람다식의 타입은 '외부클래스이름$Lambda$번호'와 같은 형식으로 되어있음
    }
}
