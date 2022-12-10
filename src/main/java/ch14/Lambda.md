# 1. 람다식
* 자바의 새로운 변화
    * JDK1.5 - 지네릭스
    * JDK1.8 - 람다식
        * 람다식의 도입으로 인해 자바는 객체지향언어인 동시에 함수형 언어가 되었음
        * 기존의 자바를 거의 변경하지 않고도 함수형 언어의 장점을 잘 접목시킴
      
## 1.1 람다식이란?
* 람다식(Lambda Expression)
  * 메서드를 하나의 식expression으로 표현한 것
  * '익명함수(anonymous function)'이라고도 함
    * 메서드의 이름과 반환값이 없어지기 때문
  * 예)
    ```java
    int[] arr = new int[5];
    Arrays.setAll(arr, (i) -> (int)(Math.random() * 5) + 1);
* 메서드와 비교했을 때의 장점
  * 간결하며 이해하기 쉬움
  * 모든 메서드는 클래스에 포함되어야 하므로, 메서드의 경우 클래스도 새로 만들어야 하고, 객체도 생성해야만 비로소 그 메서드를 호출할 수 있음.
    * --> 하지만 람다식은 이러한 과정 없이 오직 람다식 자체만으로도 메서드의 역할을 대체할 수 있음
  * 메서드를 변수처럼 다루는 것이 가능해짐
    * 메서드의 매개변수로 전달되어지는 것이 가능함
    * 메서드의 결과로 반환될 수도 있음
* 메서드와 함수의 차이
  * 메서드는 함수와 같은 의미이지만, 특정 클래스에 반드시 속해야 한다는 제약이 있으므로 객체지향개념에서는 '함수'와는 다른 용어를 선택해 사용함
  * 그러나 이제 다시 람다식을 통해 메서드가 하나의 독립적 기능을 하게 되었으므로, '함수'라는 용어를 사용함

## 1.2 람다식 작성하기
    (매개변수 선언) -> { 문장들 }
* 메서드와 람다식의 형식 비교
  ```java
  //메서드
  int max(int a, int b) {
    return a > b ? a : b;
  }
  //람다식
  (int a, int b) -> {
    return a > b ? a : b;
  }
* 람다식 작성법
  * 반환값이 있는 메서드의 경우
    * --> return문 대신 식으로 표현할 수 있음(식 연산결과가 자동적으로 반환됨)
    * 이 때는 문장statement이 아니라 '식'이므로 끝에 세미콜론을 붙이지 않음
    ```java
    (int a, int b) -> a > b ? a : b
  * 람다식에 선언된 매개변수의 타입을 추론 가능한 경우(대부분의 경우)
    * --> 매개변수타입 생략 가능
    ```java
    (a, b) -> a > b ? a : b
  * 선언된 매개변수가 하나뿐인 경우
    * --> () 괄호 생략 **가능**
    ```java
    a -> a * a //Ok
  * 선언된 매개변수가 하나뿐이고 **타입이 있는 경우**
    * --> () 괄호 생략 **불가**
    ```java
    (int a) -> a * a
  * 괄호 {}안의 문장이 하나인 경우
    * --> {} 괄호 생략 **가능**
    ```java
    (String name, int i) -> System.out.println(name + "=" + i)
  * 괄호 {} 안의 문장이 하나이지만 **return문인 경우**
    * --> {} 괄호 생략 **불가**
    ```java
    (int a, int b) -> { return a > b ? a : b; }

## 1.3 함수형 인터페이스(Functional Interface)
* <u>람다식은 실제로는 **익명 객체**이다.</u>
    ```java
  //람다식은
  (int a, int b) -> a > b ? a : b 
  //익명 클래스의 객체와
  new Object() { int max(int a, int b) {return a > b ? a : b;}} 
  //동등하다. 
* 람다식으로 정의된 익명객체의 메서드를 어떻게 호출할 수 있는가?  
  * ```java
    //익명 객체의 주소를 f라는 참조변수로 저장
    타입 f = (int a, int b) -> a > b ? a : b;
    //참조변수 f의 타입은 무엇으로 해야하는가?
    // --> 클래스 또는 인터페이스가 가능하다. 단, 람다식과 동등한 메서드가 정의되어 있어야 한다. (그래야 호출 가능하니까...)
  * 
      ```java
    //메서드가 정의된 인터페이스
    interface MyFunction { public abstract int max(int a, int b); }
    //위의 인터페이스를 구현한 익명 클래스의 객체
    MyFunction f = new MyFunction() {
        public int max(int a, int b) {
            return a > b ? a : b;
        }
    };
    //익명 객체의 메서드를 호출
    int big = f.max(5, 3);
  *
    ```java
    //익명객체를 람다식으로 대체
    MyFunction f = (int a, int b) -> a > b ? a : b;
    //익명 객체의 메서드를 호출
    int big = f.max(5,3);
* 함수형 인터페이스(Functional Interface)
  * 하나의 메서드가 선언된 인터페이스를 정의해 람다식을 다루는 것은 기존의 자바 규칙들을 어기지 않으면서도 자연스럽다.
  * 그래서 인터페이스를 통해 람다식을 다루기로 결정되었으며,
  * 람다식을 다루기 위한 인터페이스를 '함수형 인터페이스'라고 부른다.
  *     
    ```java
    @FunctionalInterface //이 어노테이션을 붙이면, 컴파일러가 항상 함수형 인터페이스를 올바르게 정의했는지 확인해준다. 
    interface MyFunction { //함수형 인터페이스 MyFunction을 정의 
        public abstract int max(int a, int b);
    } 
  * 함수형 인터페이스의 제약
    * <u>함수형 인터페이스에는 오직 하나의 추상메서드만 정의</u>되어 있어야 한다.
      * 그래야 람다식과 인터페이스의 메서드가 1:1로 연결될 수 있기 때문
    * static메서드와 default메서드의 개수에는 제약이 없다. 

### 함수형 인터페이스 타입의 매개변수와 반환타입
1. 메서드의 <u>매개변수가 함수형 인터페이스 타입</u>일 때
   *  ```java
      //함수형 인터페이스가 다음과 같이 정의되어 있을 때, 
      @FunctionalInterface
      interface MyFunction { 
        void myMethod(); //추상메서드
      }
      //메서드의 매개변수 MyFunction타입이면,
      //이 메서드를 호출할 때 람다식을 참조하는 참조변수를 매개변수로 지정해야한다는 뜻!
      void aMethod(MyFunction f) { //매개변수 타입이 함수형 인터페이스
        f.myMethd(); //MyFunction에 정의된 메서드 호출
      }
      ...
      MyFunction f = () -> System.out.println("myMethod()");
      aMethod(f);
      //혹은 참조변수 없이 직접 람다식을 매개변수로 지정하는 것도 가능하다.
      aMethod(() -> System.out.println("myMethod()")); //람다식을 매개변수로 지정
   
2. 메서드의 <u>반환타입이 함수형 인터페이스 타입</u>일 때
   * 함수형 인터페이스의 추상메서드와 동등한 람다식을 가리키는 참조변수를 반환하거나, 람다식을 직접 반환할 수 있다.
   * ```java
     MyFunction myMethod() {
      MyFunction f = () -> {};
      return f; //이 줄과 윗 줄을 한 줄로 줄이면, return () -> {};
     }
3. 람다식을 참조변수로 다룰 수 있다는 것의 의미
  * 메서드를 통해 람다식을 주고받을 수 있다!
  * 변수처럼 메서드를 주고받는 일이 가능하다!
    * 사실상 메서드가 아니라 (익명)객체를 주고받는 것이라 근본적으로 달라진 것은 없지만, 람다식 덕분에 코드가 간결하고 이해하기 쉬워진다. 

### 람다식의 타입과 형변환
* 람다식의 타입
  * 함수형 인터페이스로 람다식을 참조할 수는 있지만, 람다식의 타입이 함수형 인터페이스의 타입과 일치하는 것은 아니다!
  * 람다식은 익명객체이고, 익명객체는 타입이 없다.
    * 정확히 말하자면, 타입은 있지만 컴파일러가 이름을 임의로 정하기 때문에 알 수 없다.
    * 따라서 대입 연산자의 양변의 타입을 일치시키기 위해 형변환이 필요하다.
      * 람다식은 이름이 없을 뿐, 분명히 객체이지만 Object타입으로 형변환 불가능하다.
      * 오직 함수형 인터페이스로만 형변환 가능하다.
    * ```java
      MyFunction f = (MyFunction)(() -> {}); //양변의 타입이 다르므로 형변환 필요 
      //이하는 에러
      Object obj = (Object)(() -> {}); //에러!

### 외부변수를 참조하는 람다식 
* 람다식 내에서 외부에 선언된 변수에 접근하기
  * 람다식 내에서 참조하는 지역변수는 final이 붙지 않아도 상수로 간주된다.
  * LambdaEx3.java 참고

## 1.4 java.util.function 패키지
* 함수형 인터페이스를 정의할 때, <u>java.util.function패키지의 인터페이스를 활용</u>하는 것이 좋다.
  * 왜냐하면 대부분의 메서드는 타입이 비슷하기 때문이다.
    * 매개변수가 없거나 한 개, 또는 두 개, 반환값은 없거나 한 개.
    * 지네릭메서드로 정의하면 매개변수나 반환타입이 달라도 문제가 되지 않음
  * java.util.function패키지의 인터페이스를 활용하면 함수형 인터페이스에 정의된 메서드 이름도 통일되고, 재사용성이나 유지보수 측면에서도 좋기 때문이다.

### 기본적인 함수형 인터페이스
  * **java.lang.Runnable**
    * void **run**()
    * 매개변수도 없고 반환값도 없음
  * **Supplier<T>**
    * T **get**()
    * 매개변수는 없고 반환값만 있음
  * **Consumer<T>**
    * void **accept**(T t)
    * 매개변수만 있고 반환값이 없음
  * **Function<T, R>**
    * R **apply**(T t)
    * 일반적인 함수. 하나의 매개변수를 받아 결과를 반환함
  * **Predicate<T>**
    * boolean **test**(T t)
    * 조건식을 표현하는 데 사용됨. 매개변수는 하나, 반환타입은 boolean

### 조건식의 표현에 사용되는 함수형 인터페이스 Predicate
* Predicate<T>
  * Function의 변형. 반환타입이 boolean이라는 것만 다음.
  * 조건식을 람다식으로 표현하는데 사용됨.
  * 예) 
    ```java
    Predicate<String> isEmptyStr = s -> s.lengt() == 0;
    String s = "";
    if(isEmptyStr.test(s)){ //if(s.length() == 0)
      System.out.println("This is empty String");
    }
  
### 매개변수가 두 개인 함수형 인터페이스 Bi~
* 매개변수의 개수가 2개인 함수형 인터페이스는 이름 앞에 접두사 'Bi'가 붙음
  * 매개변수의 타입으로 보통 'T'를 사용하므로, 알파벳에서 'T'의 다움 문자인 'U', 'V', 'W'를 매개변수의 타입으로 사용하는 것일뿐 별다른 의미는 없음
* 매개변수가 두 개인 함수형 인터페이스
  * **BiConsumer<T, U>**
    * void **accept**(T t, U u)
    * 두 개의 매개변수만 있고 반환값이 없음
  * **BiPredicate<T, U>**
    * boolean **test**(T t, U u)
    * 조건식을 표현하는 데 사용함. 매개변수는 둘, 반환값은 boolean
  * **BiFunction<T, U>**
    * R **apply**(T t, U u)
    * 두 개의 매개변수를 받아 하나의 결과를 리턴함
  * Supplier는 매개변수가 없이 반환값만 존재하므로 BiSupplier가 존재할 수 없음
* 매개변수의 개수가 3개인 함수형 인터페이스
  * 직접 정의해서 사용해야함
  * 예)
    ```java
    @FuntionalInterface
    interface TriFunction<T, U, V, R> {
      R applay(T t, U u, V v);
    }

### UnaryOperator와 BinaryOperator
* 매개변수의 타입과 반환타입의 타입이 모두 일치하는 함수형 인터페이스
  * **UnaryOperator<T>**
    * T **apply**(T t)
    * Function의 자손. Function과 달리 매개변수와 결과의 타입이 같음. 
  * **BinaryOperator<T>**
    * T **apply**(T t, T t)
    * BiFunction의 자손. BiFunction과 달리 매개변수와 결과의 타입이 같음.
    
### 컬렉션 프레임웍과 함수형 인터페이스
* 컬렉션 프레임웍의 인터페이스의 다수의 디폴트 메서드가 추가됨. 그 중 일부는 함수형 인터페이스를 사용함.
* 컬렉션 프레임웍의 함수형 인터페이스를 사용하는 메서드
  * Collection 인터페이스
    * boolean **removeif**(Predicate<E> filter)
      * 조건에 맞는 요소를 삭제
  * List 인터페이스
    * void **replaceAll**(UnaryOperator<E> operator)
      * 모든 요소를 변환하여 대체
  * Iterable 인터페이스
    * void **forEach**(Consumer<T> action)
      * 모든 요소에 작업 action을 수행
  * Map 인터페이스
    * V **compute**(K key, BiFunction<K, V, V> f)
      * 지정된 키의 값에 작업 f를 수행
    * V **computeIfAbsent**(K key, Function<K, V> f)
      * 지정된 키가 없으면, 작업 f를 수행 후 추가
    * V **computeIfPresent**(K key, BiFunction<K, V, V> f)
      * 지정된 키가 있을 때 작업 f를 수행
    * V **merge**(K key, V value, BiFunction<V, V, V> f)
      * 모든 요소에 병합작업 f를 수행
    * void **forEach**(BiConsumer<K, V> action)
      * 모든 요소에 작업 action을 수행
    * void **replaceAll**(BiFunction<K, V, V> f)
      * 모든 요소에 치환작업 f를 수행
      
### 기본형을 사용하는 함수형 인터페이스
* 기본형 타입의 값을 처리할 때 래퍼클래스를 사용하면 비효율적임. 
* 따라서 기본형 타입의 값을 효율적으로 처리하기 위해 기본형 함수형 인터페이스 필요
  * **DoubleToIntFunction**
    * int applyAsInt(double d)
  * **ToIntFunction<T>**
    * int applyAsInt(T value)
  * **IntFunction<R>**
    * R apply(T t, U u)
  * **ObjIntConsumer<T>**
    * void accept(T t, U u)
* LambdaEx6.java 예제 참고
  
## 1.5 Function의 합성과 Predicate의 결합
* 함수형 인터페이스 Function의 메서드
  * default <V> Function<T, V> **andThen**(Function<? super R, ? extends V> after)
  * default <V> Function<V, R> **compose**(Function<? super V, ? extends T> before)
  * static <T> Function<T, T> **identity**()
* 함수형 인터페이스 Predicate의 메서드
  * default Predicate<T> **and**(Predicate<? super T> other)
  * default Predicate<T> **or**(Predicate<? super T> other)
  * default Predicate<T> **negate**()
  * static <T> Predicate<T> **isEqual**(Object targetRef)

### Function의 합성
* 수학에서 두 함수를 합성해 새로운 함수를 만들어내기 가능 --> 두 람다식도 함성해 새로운 람다식을 만들어내기 가능
  * f.andThen(g)
    * 함수 f를 먼저 적용하고, 그 다음에 함수 g를 적용함
  * f.composer(g)
    * 함수 g를 먼저 적용하고, 그 다음에 함수 f를 적용함
  * identity()
    * 함수를 적용하기 이전과 이후가 동일한 '항등 함수'가 필요할 때 사용 (x -> x)
    * map()으로 변환작업할 때, 변환없이 그대로 처리하고자 할 때 사용됨
  * 예)
      ```java
    //문자열을 숫자로 변환하는 함수 f
    Function<String, Integer> f = (s) -> Integer.parseInt(s, 16);
    //숫자를 2진 문자열로 변환하는 함수 g
    Function<Integer, String> g = (i) -> Interger.toBinaryString(i);
    //f와 g의 합성 - 예를 들어 h에 문자열 "FF"를 입력하면, 결과로 "1111111"을 얻음
    Function<String, String> h = f.andThen(g);
    //f와 g의 합성(2) - 예를들어 함수 j에 숫자 2를 입력하면 결과로 16을 얻음
    Function<Integer, Integer> j = f.compose(g);

### Predicate의 결합
* 여러 조건식을 논리 연산자인 $$, ||, !으로 연결해 하나의 식을 구성하기 가능 --> 여러 Predicate를 <u>and(), or(), negate()</u>로 연결해 하나의 새로운 Predicate로 결합할 수 있음
  * and(), or()
    * 예)
      ```java
        Predicate<Integer> p = i -> i < 100;
        Predicate<Integer> q = i -> i < 200;
        Predicate<Integer> r = i -> i%2 == 0;
        Predicate<Integer> notP = p.negate(); // i>= 100
        //100 <= i && (i < 200 || i%2 == 0)
        Predicate<Integer> all = notP.and(q.or(r));
        Predicate<Integer> all2 = notP.and(i -> i < 200).or(i -> i%2 == 0);
        System.out.println(all.test(150)); //true
  * negate()
    * Predicate의 끝에 negate()를 붙이면 조건식 전체가 부정이 됨
  * isEqual()
    * 두 대상을 비교하는 Predicate를 만들 때 사용함
    * isEqual()의 매개변수로 비교대상을 하나 지정하고, 또 다른 비교대상은 test()의 매개변수로 지정함
    * 예)
      ```java
      Predicate<String> p = Predicate.isEqual(str1);
      boolean result = p.test(str2); //str1과 str2가 같은지 비교하여 결과를 반환
      //위의 두 문장을 합칠 경우
      boolean result = Predicate.isEqual(str1).test(str2); //str1과 str2가 같은지 비교

## 1.6 메서드 참조
* 메서드 참조(method reference)
  * 람다식이 하나의 메서드만 호출하는 경우 메서드 참조를 통해 람다식을 더욱 간결하게 표현 가능
    * 예)
      ```java
      //문자열을 정수로 변환하는 함수
      Function<String, Integer> f = (String s) -> Integer.parseInt(s);
      //위의 람다식을 메서드로 표현하면
      Integer meaninglessName(String s) {
        return Integer.parseInt(s);
      } //값을 받아서 Inter.parseInt()에 넘겨주기만 하는 거추장스러운 메서드
      Function<String, Integer> f2 = Integer::parseInt; //f1과 동일함. 메서드 참조.
      //람다식의 일부를 생략했지만, 컴파일러는 생략된 부분을 우변의 parsInt메서드의 선언부 혹은 좌변의 Function인터페이스에 지정된 지네릭 타입으로부터 쉽게 알아낼 수 있음
    * 예2)
      ```java
      Bifunction<String, String, Boolean> f = (s1, s2) -> s1.equals(s2);
      //람다식을 메서드 참조로 변경
      BiFunction<String, String, Boolean> f2 = String::equals;
  * 이미 생성된 객체의 메서드를 람다식에서 사용한 경우에는 클래스 이름 대신 그 객체의 참조변수를 적어줘야 함.
    * 예)
      ```java
      MyClass obj = new MyClass();
      Function<String, Boolean> f = (x) -> obj.equals(x); //람다식
      Function<String, Boolean> g2 = obj::equals; //메서드 참조
    
### 람다식을 메서드 참조로 변환하는 방법
* 하나의 메서드만 호출하는 람다식은 <u>**'클래스이름::메서드이름'**</u> 또는 <u>**'참조변수::메서드이름'**</u>으로 바꿀 수 있다.
* static메서드 참조
  * 람다
    * (x) -> ClassName.method(x)
  * 메서드 참조
    * className::method
* 인스턴스메서드 참조
  * 람다
    * (obj, x) -> obj.method(x)
  * 메서드 참조
    * ClassName::method
* 특정 객체 인스턴스 메서드 참조
  * 람다
    * (x) -> obj.method(x)
  * 메서드 참조
    * obj::method

#### 생성자의 메서드 참조
* 생성자를 호출하는 람다식도 메서드 참조로 변환 가능
  * 매개변수가 없는 생성자의 경우
  * 예)
    ```java
    Supplier<MyClass> s = () -> new MyClass(); //람다식
    Supplier<MyClass> s2 = MyClass::new; //생성자의 메서드 참조
  * 매개변수가 있는 생성자의 경우
    * 매개변수의 개수에 따라 알맞은 함수형 인터페이스를 사용해야 함
    * 필요한 경우 함수형 인터페이스를 새로 정의해야 함
    * 예)
      ```java
      Function<Integer, MyClass> f = (i) -> new MyClass(i); //람다식
      Function<Integer, MyClass> f2 = MyClass::new; //메서드 참조
      BiFunction<Integer, String, MyClass> bf = (i, s) -> new MyClass(i, s);
      BiFunction<Integer, String, MyClass> bg2 = MyClass:new; //메서드 참조
  * 배열을 생성하는 경우
    * 예)
      ```java
      Function<Integer, int[]> f = x -> new int[x]; //람다식
      Function<Integer, int[]> f2 = int[]::new; //메서드 참조