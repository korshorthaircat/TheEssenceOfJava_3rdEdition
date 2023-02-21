package ch13;

class ThreadEx21 {
    public static void main(String[] args) {
        RunnableEx21 runnableEx21 = new RunnableEx21();
        new Thread(runnableEx21).start(); //ThreadGroup에 의해 참조되므로, gc대상이 아니다.
        new Thread(runnableEx21).start(); //ThreadGroup에 의해 참조되므로, gc대상이 아니다.

        //실행결과
//        balance: 700
//        balance: 900
//        balance: 600
//        balance: 400
//        balance: 300
//        balance: 0
//        balance: -200

        //잔액 음수가 출력됨.
        //-> 한 쓰레드가 if문의 조건식을 통과하고, 출금하기 바로 직전 다른 쓰레드가 끼어들어 출금을 먼저했기 때문
            //(예제에서는 일부러 if문을 통과하자마자 sleep()를 호출해 다른 쓰레드에게 제어권을 넘기도록 했으나, 굳이 이렇게 하지 않더라도 쓰레드의 작업이 다른 쓰레드에 의해 영향을 받는 일이 발생할 수 있음)
        //잔고를 확인하는 if문과 출금하는 문장은 하나의 임계영역(critical section)으로 묶여져야 한다. 동기화가 필요하다.

    }
}

class Account {
    private int balance = 1000;

    public int getBalance() {
        return balance;
    }

    public void withdraw(int money) {
        if(balance >= money) {
            try {
                Thread.sleep(1000); //일부러 Thread.sleep(1000)을 써서 if문을 통과하자마자 다른 쓰레드에게 제어권을 넘기도록 함
            } catch (InterruptedException e) {}
            balance -= money;
        }
    }
}

class RunnableEx21 implements Runnable {
    Account account = new Account();
    @Override
    public void run() {
        while (account.getBalance() > 0) {
            int money = (int) (Math.random() * 3 + 1) * 100;
            account.withdraw(money);//100, 200, 300 중 한 값을 임의로 선택해 출금(withdraw)
            System.out.println("balance: " + account.getBalance());
        }

    }
}