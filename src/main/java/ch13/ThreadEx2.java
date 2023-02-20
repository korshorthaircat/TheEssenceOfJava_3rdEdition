package ch13;

class ThreadEx2 {
    public static void main(String[] args) {
        ThreadEx2_1 thread = new ThreadEx2_1();
        thread.start();
    }

    //실행결과
//    java.lang.Exception
//    at ch13.ThreadEx2_1.throwException(ThreadEx2.java:18)
//    at ch13.ThreadEx2_1.run(ThreadEx2.java:13)

    //호출스택의 첫번쨰 메서드가 main()이 아니라 run()임을 확인할 수 있음
    //-> start()를 호출했을 때는 main쓰레드의 호출스택이 없음. main쓰레드가 이미 종료되었기 때문.
}

class ThreadEx2_1 extends Thread {
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


