package ch12;

enum Transportation {
    BUS(100) { int fare(int distance) {return distance * BASIC_FARE;} },
    TRAIN(150) { int fare(int distance) {return distance * BASIC_FARE;} },
    SHIP(100) { int fare(int distance) {return distance * BASIC_FARE;} },
    AIRPLANE(300) { int fare(int distance) {return distance * BASIC_FARE;} };

    abstract int fare(int distance); //거리에 따른 요금을 계산하는 추상메서드 -> 각 열거형상수가 이 추상메서드를 반드시 구현해야 함

    protected final int BASIC_FARE; //접근제어자를 protected로 해야 각 상수에서 이 필드에 접근 가능하다.

    Transportation(int basicFare) { //private Transportation(int basicFare)
        BASIC_FARE = basicFare;
    }

    public int getBasicFare() { return BASIC_FARE; }
}

class EnumEx3 {
    public static void main(String[] args) {
        System.out.println("Transportation.BUS.fare(100) = " + Transportation.BUS.fare(100));
        System.out.println("Transportation.TRAIN.fare(100) = " + Transportation.TRAIN.fare(100));
        System.out.println("Transportation.SHIP.fare(100) = " + Transportation.SHIP.fare(100));
        System.out.println("Transportation.AIRPLANE.fare(100) = " + Transportation.AIRPLANE.fare(100));

    }
}
