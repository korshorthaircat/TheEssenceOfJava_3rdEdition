package ch13;

class ThreadEx1 {
    public static void main(String[] args) {
        ThreadEx1_1 thread1 = new ThreadEx1_1();

        Runnable target = new ThreadEx1_2(); //Runnable을 구현한 클래스의 인스턴스 생성
        Thread thread2 = new Thread(target); //생성자 Thread(Runnable target)
        //Thread thread2 = new Thread(new ThreadEx1_2());

        //쓰레드의 이름을 지정하지 않으면 'Thread-번호'의 형식으로 이름이 정해짐
        //쓰레드의 이름은 생성자나 메서드를 통해 지정 또는 변경할 수 있음.
        Thread thread3 = new Thread(new ThreadEx1_2(), "hello");
        Thread thread4 = new Thread(new ThreadEx1_2());
        thread4.setName("hello2");

        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();

    }
}

class ThreadEx1_1 extends Thread {
    @Override
    public void run() {
        for(int i = 0; i < 5; i++) {
            System.out.println(getName()); //조상인 Thread의 getName()을 호출
        }
    }

}

class ThreadEx1_2 implements Runnable {
    @Override
    public void run() {
        for(int i = 0; i < 5; i++) {
            Thread thread = Thread.currentThread(); //현재 실행중인 Thread를 반환한다.
            System.out.println(thread.getName()); //현재 실행중인 Thread의 getName()을 호출
        }
    }
}