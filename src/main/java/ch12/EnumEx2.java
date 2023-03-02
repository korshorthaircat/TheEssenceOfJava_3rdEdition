package ch12;

enum DirectionEx2 {
    EAST(1, ">"),
    SOUTH(2, "V"),
    WEST(3, "<"),
    NORTH(4, "^");

    private final int value;
    private final String symbol;

    private static final DirectionEx2[] directionArr = DirectionEx2.values();

    public static DirectionEx2 of(int direction) {
        if (direction < 1 || direction > 4) {
            throw new IllegalArgumentException("Invalid value: " + direction);
        }
        return directionArr[direction -1];
    }

    /**
     * 방향을 회전시키는 메서드
     * num의 값만큼 90도씩 시계방향으로 회전한다.
     * @param num
     * @return
     */
    public DirectionEx2 rotate(int num) {
        num = num % 4;
        if (num < 0) {
            num += 4; //num이 음수일 때는 시계 반대방향으로 회전한다.
        }
        return directionArr[(value -1 + num) % 4];
    }

    DirectionEx2(int value, String symbol) { //접근제어자에 private 생략됨
        this.value = value;
        this.symbol = symbol;
    }

    public int getValue() {
        return value;
    }

    public String getSymbol() {
        return symbol;
    }

    @Override
    public String toString() {
        return name() + getSymbol();
    }
}

public class EnumEx2 {
    public static void main(String[] args) {

        for (DirectionEx2 direction : DirectionEx2.values()) {
            System.out.println("direction.name() + direction.getValue() = " + direction.name() + direction.getValue());
        }

        DirectionEx2 direction1 = DirectionEx2.EAST;
        DirectionEx2 direction2 = DirectionEx2.of(1);

        System.out.println("direction1 = " + direction1);
        System.out.println("direction2 = " + direction2);

        System.out.println("--회전--");
        System.out.println("DirectionEx2.EAST.rotate(1) = " + DirectionEx2.EAST.rotate(1));
        System.out.println("DirectionEx2.EAST.rotate(2) = " + DirectionEx2.EAST.rotate(2));
        System.out.println("DirectionEx2.EAST.rotate(-1) = " + DirectionEx2.EAST.rotate(-1));
        System.out.println("DirectionEx2.EAST.rotate(-2) = " + DirectionEx2.EAST.rotate(-2));

    }
}
