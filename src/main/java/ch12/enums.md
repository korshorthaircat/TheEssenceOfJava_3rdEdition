# 2. 열거형(enums)
## 2.1 열거형이란?
* 서로 관련된 상수를 편리하게 선언하기 위함
* JDK 1.5부터 새로 추가됨
* '타입에 안전한 열거형(typesafe enum)'
  * 열거형이 갖는 값뿐만 아니라 타입도 관리함
  * 실제 값이 같아도 타입이 다르면 컴파일 에러가 발생함

## 2.2 열거형의 정의와 사용
* 정의
    ```
    enum 열거형이름 { 상수명1, 상수명2, ... }
* 예)
  ```
    enum Direction { EAST, SOUTH, WEST, NORTH }
    class Unit {
        int x, y; //유닛의 좌표
        Direction direction; //열거형을 인스턴스 변수로 선언
        void init() {
            direction = Direction.EAST; //유닛의 방향을 EAST로 초기화
        }
    }
* 사용
  * 열거형 상수간 비교시에는 '==' 비교 가능
  * 비교연산자 >, < 사용 불가
  * compareTo() 사용 가능(비교대상이 같으면 0, 왼쪽이 크면 양수, 오른쪽이 크면 음수 반환)
  * 스위치문의 조건식에 열거형 사용 가능
    * case문에 열거형의 이름은 적지 않고 상수의 이름만 적어야 하는 점 유의

### 모든 열거형의 조상 - java.lang.Enum
* 모든 열거형에 대하여 컴파일러가 자동으로 추가해주는 메서드
  * `values()`
    * 열거형의 모든 상수를 배열에 담아 반환
  * `valueOf()`
    * 열거형 상수의 이름으로 문자열 상수에 대한 참조를 얻을 수 있게 해줌
    * 예)
      * ```
        Direction direction = Direction.valueOf("WEST");
        System.out.pritnln(Direction.WEST == Direction.valueOf("WEST")); //true
* Enum클래스에 정의된 메서드
  * `Class<E> getDeclaringClass()`
    * 열거형의 Class 객체를 반환
  * `String name()`
    * 열거형 상수의 이름을 문자열로 반환
  * `int ordinal()`
    * 열거형 상수가 정의된 순서를 반환(0부터 시작)
  * `T valueOf(Class<T> enumType, String name)`
    * 지정된 열거형에서 name과 일치하는 열거형 상수 반환

## 2.3 열거형에 멤버 추가하기
* Enum클래스에 정의된 ordinal()이 열거형상수가 정의된 순서를 반환하지만, 이 값을 열거형 상수의 값으로 사용하지 않는 것이 좋다.
  * ordinal()이 반환하는 값(정의된 순서)는 내부적 용도로만 사용되기 위한 것이기 때문
* 열거형상수의 값이 불연속적인 경우 -> 열거형에 멤버 추가 가능
  * 열거형상수의 이름 옆에 원하는 값을 괄호와 함께 적어줌
  * 지정된 값을 저장할 수 있는 인스턴스변수와 생성자를 새로 추가해주어야 함
  * 주의)
    * 열거형상수를 모두 정의한 뒤 다른 멤버를 추가해야 함
    * 열거형 상수의 마지막에 세미콜론; 붙이기
  * 예)
  ```
  emun Direction {
    EAST(1), SOUTH(5), WEST(-1), NORTH(10); //세미콜론 잊지 말기
    private final int value; //정수를 저장할 필드 추가
    Direction(int value)) { this.value = value; } //생성자 추가. 열거형의 생성자는 묵시적으로 제어자가 private이므로, 외부에서 열거형 객체 생성 불가
    public int getValue() { return value; }
  }
* 열거형 상수에 여러 값 지정하기
  * 주의)
    * 인스턴스 변수, 생성자 추가할 것
  * 예)
  ```
  enum Direction {
    EAST(1, ">"), SOUTH(2, "V"), WEST(3, "<"), NORTH(4, "^");
    private final int value;
    private final String symbol;
    Direction(int value, String symbol) {
      this.symbol = symbol;
      this.value = value;
    }
    public int getValue() {return value;}
    public String getSymbol() {return symbol;}
  }

* 열거형에 추상 메서드 추가하기
  * 각 열거형 상수가 이 추상메서드를 반드시 구현하게 함
  * 예)
  ```
  enum Transportation {
    BUS(100) { int fare(int distance) {return distance * BASIC_FARE;} },
    TRAIN(150) { int fare(int distance) {return distance * BASIC_FARE;} },
    SHIP(100) { int fare(int distance) {return distance * BASIC_FARE;} },
    AIRPLANE(300) { int fare(int distance) {return distance * BASIC_FARE;} };
  
    abstract int fare(int distance); //거리에 따른 요금을 계산하는 추상메서드 -> 각 열거형상수가 이 추상메서드를 반드시 구현해야 함
  
    protected final int BASIC_FARE; //접근제어자를 protected로 해야 각 상수에서 이 필드에 접근 가능하다.
    
    Transportation(int basicFare) {
      BASIC_FARE = basicFare;
    }
  
    public int getBasicFare() { return BASIC_FARE; }
  }

## 2.4 열거형의 이해
* 예)
  ```
  enum Direction { EAST, SOUTH, WEST, NORTH }
  //여기서 열거형 상수 하나하나는 Direction객체이다. 
  
  //이를 클래스로 정의하면, 아래와 같다.
  class Direction {
    static final Direction EAST = new Direction("EAST");
    static final Direction WEST = new Direction("WEST");
    static final Direction SOUTH = new Direction("SOUTH");
    static final Direction NORTH = new Direction("NORTH");
  
    private String name;
    private Direction(String name) {
      this.name = name;
    }
  }
