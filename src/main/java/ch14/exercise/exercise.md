## 14-1. 메서드를 람다식으로 변환하여 아래의 표를 완성하시오.
* ```
  int max(int a, int b) {
    return a > b ? a : b;
  }
  
  (int a, int b) -> a > b? a : b
* ```
  int printVar(String name, int i) {
    System.out.println(name + "=" + i);
  }
  
  (name, i) -> System.out.println(name + "=" + i)
* ```
  int square(int x) {
    return x * x;
  }

  x -> x * x
* ```
  int roll() {
    return (int)(Math.random() * 6);
  }
  
  () -> (int)(Math.random() * 6)
* ```
  int sumArr(int[] arr) {
    int sum = 0;
    for (int i : arr)
        sum += i;
    return sum;
  }
  
  (int[] arr) -> {
    int sum = 0;
    for (int i: arr)
        sum += i;
    return sum;
  }
* ```
  int[] emptyArr() {
    return new int[]{};
  }
  
  () -> new int[]{}

## 14-2. 람다식을 메서드 참조로 변환하여 표를 완성하시오. (변환이 불가능한 경우, '변환불가'라 명시)
* (String s) -> s.length()
  * String::length
* () -> new int[]{}
  * int[]::new
* arr -> Arrays.stream(arr)
  * Arrays::stream
* (string str1, String str2) -> str1.equals(str2)
  * String::equals
* (a, b) -> Integer.compare(a, b)
  * Integer::compare
* (String kind, int num) -> new Card(kind, num)
  * Card::new
* (x) -> System.out.println(x)
  * System.out::println
* ()-> Math.random()
  * Math::random
* (str) -> str.toUpperCase()
  * String::toUpperCase
* () -> new NullPointerException()
  * NullPointerException::new
* (Optional opt) -> opt.get()
  * Optional::get
* (StringBuffer sb, String s) -> sb.append(s)
  * StringBuffer::append
* (String s) -> System.out.println(s)
  * System.out::println

## 14-3. 아래의 괄호 안에 알맞은 함수형 인터페이스는?
( ) f; //함수형인터페이스 타입의 참조변수f를 선언.
f = (int a, int b) -> a > b ? a : b;
-> IntBinaryOperator
