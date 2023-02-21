package ch13;

class ThreadEx22 {
    public static void main(String[] args) {
        RunnableEx22 runnableEx22 = new RunnableEx22();
        new Thread(runnableEx22).start(); //ThreadGroup에 의해 참조되므로, gc대상이 아니다.
        new Thread(runnableEx22).start(); //ThreadGroup에 의해 참조되므로, gc대상이 아니다.

        //실행결과
//        balance: 700
//        balance: 500
//        balance: 300
//        balance: 200
//        balance: 100
//        balance: 100
//        balance: 0
//        balance: 0

        //잔액 음수가 출력되지 않음

    }
}

class Account2 {
    private int balance = 1000; //private으로 해야 동기화가 의미 있음.
    //만일 private가 아니면, 외부에서 직접 접근할 수 있기 때문에 아무리 동기화를 해도 값의 변경을 막을 수 없음.
    //synchronized를 이용한 동기화는 지정된 영역의 코드를 한번에 하나의 쓰레드가 수행하는 것을 보장하는 것일 뿐이기 때문

    public int getBalance() {
        return balance;
    }

    public synchronized void withdraw(int money) { //synchronized로 메서드를 동기화
        if(balance >= money) {
            try {
                Thread.sleep(1000); //일부러 Thread.sleep(1000)을 써서 if문을 통과하자마자 다른 쓰레드에게 제어권을 넘기도록 함
            } catch (InterruptedException e) {}
            balance -= money;
        }
    }
}

class RunnableEx22 implements Runnable {
    Account2 account = new Account2();
    @Override
    public void run() {
        while (account.getBalance() > 0) {
            int money = (int) (Math.random() * 3 + 1) * 100;
            account.withdraw(money);//100, 200, 300 중 한 값을 임의로 선택해 출금(withdraw)
            System.out.println("balance: " + account.getBalance());
        }

    }
}