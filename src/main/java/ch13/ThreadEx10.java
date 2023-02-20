package ch13;

public class ThreadEx10 implements Runnable {

    static boolean autoSave = false;
    public static void main(String[] args) {

        Thread thread = new Thread(new ThreadEx10());
        thread.setDaemon(true); //이 부분이 없으면 종료되지 않는다.
        thread.start();

        for (int i = 1; i <= 10; i++) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
            System.out.println(i);

            if(i == 5) {
                autoSave = true;
            }
        }

        System.out.println("프로그램을 종료합니다.");
    }

    @Override
    public void run() {
        while(true) {
            try {
                Thread.sleep(3 * 1000); //3초마다
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            //autoSave의 값이 true이면 autoSave()를 호출함
            if(autoSave) {
                autoSave();
            }
        }

    }

    public void autoSave() {
        System.out.println("작업파일이 자동 저장되었습니다.");
    }
}
