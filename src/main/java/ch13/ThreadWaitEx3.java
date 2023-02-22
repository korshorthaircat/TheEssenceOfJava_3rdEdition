package ch13;

import java.util.ArrayList;

class ThreadWaitEx3 {
    public static void main(String[] args) throws Exception {
        Table3 table = new Table3(); //여러 쓰레드가 공유하는 객체

        new Thread(new Cook3(table), "COOK1").start();
        new Thread(new Customer3(table, "donut"), "CUSTOMER1").start();
        new Thread(new Customer3(table, "burger"), "CUSTOMER2").start();

        Thread.sleep(2000); //0.1초(100밀리세컨드) 후 강제 종료시킨다.
        System.exit(0); //프로그램 전체를 종료함. (모든 쓰레드가 종료된다.)
    }
}

class Customer3 implements Runnable {
    private Table3 table;
    private String food;

    Customer3(Table3 table, String food) {
        this.table = table;
        this.food = food;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {}

            String name = Thread.currentThread().getName();

            if (eatFood()) {
                System.out.println(name + " ate a " + food);
            } else {
                System.out.println(name + " failed to eat. :");
            }
        }
    }

    boolean eatFood() {
        return table.remove(food);
    }
}

class Cook3 implements Runnable {
    private Table3 table;

    Cook3(Table3 table) {
        this.table = table;
    }

    @Override
    public void run() {
        while(true) {
            //임의의 요리를 하나 선택해서 table에 추가한다.
            int idx = (int)(Math.random() * table.dishNum());
            table.add(table.dishNames[idx]);

            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
            }
        }
    }
}

class Table3 {
    String[] dishNames = {"donut", "donut", "burger"}; //donut이 더 자주 나온다.
    final int MAX_FOOD = 6; //테이블에 놓을 수 있는 최대 음식의 개수

    private ArrayList<String> dishes = new ArrayList<>();

    public synchronized void add(String dish) {
        while(dishes.size() > MAX_FOOD) {
            String name = Thread.currentThread().getName();
            System.out.println(name + " is waiting.");
            try {
                wait(); //COOK쓰레드를 기다리게 한다.
                Thread.sleep(500);
            } catch (InterruptedException e) {}
        }
        dishes.add(dish);
        notify(); //기다리고있는 CUSTOMER 쓰레드를 깨운다.
        System.out.println("Dishes: " + dishes.toString());
    }

    public boolean remove(String dishName) {
        synchronized (this) {
            String name = Thread.currentThread().getName();

            while(dishes.size() == 0) {
                System.out.println(name + " is waiting.");
                try {
                    wait(); //CUSTOMER 쓰레드를 기다리게 한다.
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                }
            }

            while (true) {
                for (int i = 0; i < dishes.size(); i++) {
                    if (dishName.equals(dishes.get(i))) {
                        dishes.remove(i);
                        notify(); //잠자고 있는 COOK를 깨운다.
                        return true;
                    }
                }

                try {
                    System.out.println(name + " is waiting.");
                    wait(); //원하는 음식이 없는 CUSTOMER쓰레드를 기다리게 한다.
                    Thread.sleep(500);
                } catch (InterruptedException e) {}
            }
        }
    }

    public int dishNum() {
        return dishNames.length;
    }
}


