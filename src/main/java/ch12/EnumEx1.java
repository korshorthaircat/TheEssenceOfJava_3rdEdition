package ch12;

enum Direction {EAST, SOUTH, WEST, NORTH}

class EnumEx1 {
    public static void main(String[] args) {

        Direction direction1 = Direction.EAST;
        Direction direction2 = Direction.valueOf("WEST"); //valueOf() - 열거형 상수의 이름으로 문자열 상수에 대한 참조를 얻을 수 있게 해줌
        Direction direction3 = Enum.valueOf(Direction.class, "EAST"); //T valueOf(Class<T> enumType, String name) - 지정된 열거형에서 name과 일치하는 열거형 상수 반환

        System.out.println("direction1 = " + direction1);
        System.out.println("direction2 = " + direction2);
        System.out.println("direction3 = " + direction3);

        //열거형간 대소비교시 == 사용 가능
        System.out.println("direction1==direction2 = " + (direction1 == direction2)); //false
        System.out.println("direction1==direction3 = " + (direction1==direction3)); //true

        //열거형간 대소비교시 compareTo() 사용 가능
        System.out.println("direction1.compareTo(direction3) = " + direction1.compareTo(direction3)); //0
        System.out.println("direction1.compareTo(direction2) = " + direction1.compareTo(direction2)); //-2

        //열거형간 대소비교시 >, < 사용 불가
//        System.out.println("(direction1 > direction3) = " + (direction1 > direction3)); //에러. Operator '>' cannot be applied to 'ch12.Direction', 'ch12.Direction'

        switch(direction1) {
//            case Direction.EAST: //An enum switch case label must be the unqualified name of an enumeration constant
            case EAST:
                System.out.println("The direction is EAST"); break;
            case WEST:
                System.out.println("The direction is WEST"); break;
            case SOUTH:
                System.out.println("The direction is SOUTH"); break;
            case NORTH:
                System.out.println("The direction is NORTH"); break;
        }

        Direction[] directionArr = Direction.values(); //values() - 열거형의 모든 상수를 배열에 담아 반환
        for (Direction direction : directionArr) {
            System.out.println("direction = " + direction);
        }
    }
}
