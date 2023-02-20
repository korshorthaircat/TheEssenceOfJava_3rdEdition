package ch13;

class ThreadEx3 {
    public static void main(String[] args) {
        ThreadEx2_1 thread = new ThreadEx2_1();
        thread.run();
    }
}
    //실행결과
//    java.lang.Exception
//    at ch13.ThreadEx2_1.throwException(ThreadEx2.java:26)
//    at ch13.ThreadEx2_1.run(ThreadEx2.java:21)
//    at ch13.ThreadEx3.main(ThreadEx3.java:6)


    //호출스택의 첫번쨰 메서드가 main()임.
    //-> run()를 호출했을 때는 쓰레드가 새로 생성되지 않음.(그저 ThreadEx3_1클래스의 run()이 호출되었을 뿐임.)

class ThreadEx3_1 extends Thread {
    @Override
    public void run() {
        throwException();
    }

    public void throwException() {
        try {
            throw new Exception();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}


