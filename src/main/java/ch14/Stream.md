# 2. 스트림(stream)
## 2.1 스트림이란? 
* 많은 수의 데이터를 다루기 위해 컬렉션이나 배열만을 이용할 때의 불편
  * 원하는 결과를 얻기 위해 for문과 Iterator 이용해 코드를 작성
    * 너무 길고 알아보기 어려움, 코드의 재사용성도 떨어짐
    * 데이터 소스마다 다른 방식으로 다뤄야 함
      * (Collection이나 Iterator같은 인터페이스를 사용해 컬렉션 다루는 방법을 표준화하기는 하였으나) 각 컬렉션 클래스에는 같은 기능의 메서드들이 중복되어 있음
      * 예) List를 정렬할 때는 Collections.sort()사용, 배열을 정렬할 때는 Arrays.sort() 사용
  * 이러한 문제점을 해결하기 위해 스트림(Stream)이 만들어짐
* **스트림의 특징 및 장점**
  * 데이터소스를 추상화
    * 데이터소스가 무엇이든간에 같은 방식으로 다룰수 있게 함
    * 코드의 재사용성이 높아짐
  * 데이터를 다루는데 자주 사용되는 메서드들을 정의
    * 배열, 컬렉션, 파일에 저장된 데이터 등을 모두 같은 방식으로 다룰 수 있음
  * 스트림을 사용한 코드는 간결하고 이해하기 쉬우며 재사용성도 높음
  * 예)
    ```java
    String[] strArr = {"aaa", "ddd", "ccc"};
    List<String> strList = Arrays.asList(strArr);
    //위의 두 데이터소스를 기반으로 스트림 생성하기
    Stream<String> strStream1 = Arrays.stream(strArr); //스트림 생성
    Stream<String> strStream2 = strList.stream(); //스트림 생성
    //스트림으로 데이터소스의 데이터를 읽어 정렬하고 화면에 출력하기
    strStream1.sorted().forEach(System.out::println);
    strStream2.sorted().forEach(Sysrem.out::println); //데이터소스는 서로 다르지만, 정렬하고 출력하는 방법은 완전히 동일

### (1) 스트림은 데이터소스를 변경하지 않는다.
* 스트림은 데이터소스로부터 데이터를 읽기만 할 뿐, 데이터 소스를 변경하지 않음
* 필요한 경우 정렬된 결과를 컬렉션이나 배열에 담아 반환할 수 있음
  * 예)
    ```java
    //정렬된 결과를 새로운 List에 담아 반환
    List<String> sortedList = strStream1.sorted().collect(Collectors.toList());

### (2) 스트림은 일회용이다.
* 스트림은 한 번 사용하면 닫혀서 다시 사용할 수 없다.
* 필요하다면 스트림을 다시 생성해야 한다.
  * 예)
    ```java
    strStream1.sorted().forEach(System.out::println); 
    int numOfStr = strStream1.count(); //에러! 스트림이 이미 닫혔음.

### (3) 스트림은 작업을 내부 반복으로 처리한다.
* 스트림을 이용한 작업이 간결한 것의 비밀은 반복문을 메서드의 내부에 숨기기 때문이다.
  * 예)
    * forEach()는 스트림에 정의된 메서드 중의 하나로, 매개변수에 대입된 람다식을 데이터 소스의 모든 요소에 적용함
        ```java
        for(String str : strList)
          System.out.println(str);
        //스트림 적용
        stream.forEach(System.out:println);

### (4) 스트림의 연산
* 연산(operation)
  * 스트림에 정의된 메서드 중에서 데이터 소스를 다루는 작업을 수행하는 것을 의미
  * 스트림이 제공하는 다양한 연산을 이용해 복잡한 작업을 간단히 처리할 수 있음
    * DB에 SELECT문으로 질의(쿼리)하는 것과 비슷한 느낌
* 스트림이 제공하는 연산의 종류
  * 중간 연산
    * 연산 결과가 스트림인 연산
    * 스트림에 연속해서 중간 연산할 수 있음
  * 최종 연산
    * 연산 결과가 스트림이 아닌 연산
    * 스트림의 요소를 소모하므로 단 한 번만 가능
  * 예) 
    ```java
    stream.distinct().limit(5).sorted().forEach(System.out::println);
    //distinct(), limit(), sorted()는 중간연산. forEach()는 최종연산.

#### 스트림의 중간 연산 목록
* 중복을 제거 
  * Stream<T> distinct()
* 조건에 안 맞는 요소 제거
  * Stream<T> filter(Predicate<T> predicate)
* 스트림의 일부를 잘라냄
  * Stream<T> limit(long maxSize)
* 스트림의 일부를 건너뜀
  * Stream<T> skip(ling n)
* 스트림의 요소에 작업 수행
  * Stream<T> peek(Consumer<T> action)
* 스트림의 요소를 정렬
  * Stream<T> sorted()
  * Stream<T> sorted(Comparator<T> comparator)
* 스트림의 요소를 변환
  * Stream<R> <span style="color:yellow"><u>**map**</u></span>(Function<T, R> mapper)
  * DoubleStream mapToDouble(ToDoubleFunction<T> mapper)
  * IntStream mapToInt(ToIntFunction<T> mapper)
  * LongStream mapToLong(ToLongFunction<T> mapper)
  * Stream<R> <span style="color:yellow"><u>**flatMap**</u></span>(Function<T, Stream<R>> mapper)
  * DoubleStream flatMapToDouble(Function<T, DoubleStream> m)
  * IntStream flatMapToInt(Function<T, IntStream> m)
  * LongStream flatMapToLong(Function<T, LongStream> m)

#### 스트림의 최종 연산 목록
* 각 요소에 지정된 작업 수행
  * void forEach(Consumer<? super T> action)
  * void forEachORdered(Consumer<? super T> action)
* 스트림의 요소의 개수 반환
  * long count()
* 스트림의 최대값/최소값을 반환
  * Optional<T> max(Comparator<? super T> comparator)
  * Optional<T> min(Comparator<? super T> comparator)
* 스트림의 요소 하나를 반환
  * Optional<T> findAny() //아무거나 하나
  * Optional<T> findFirst() //첫번째 요소
* 주어진 조건을 모든 요소가 만족시키는지, 만족시키지 않는지 확인
  * boolean allMatch(Predicate<T> p) //모두 만족하는지
  * boolean anyMatch(Predicate<T> p) //하나라도 만족하는지
  * boolean noneMatch(Predicate<> p) //모두 만족하지 않는지
* 스트림의 모든 요소를 배열로 반환
  * Object[] toArray()
  * A[] toArray(IntFunction<A[]> generator)
* 스트림의 요소를 하나씩 줄여가면서(리듀싱) 계산
  * Optional<T> <span style="color:yellow"><u>**reduce**</u></span>(BinaryOperator<T> accumulator)
  * T reduce(T identity, BinaryOperator<T> accumulator)
  * X reduce(X identity, BiFunction<X, T, X> accumulator, BinaryOperator<X> combiner)
* 스트림의 요소를 수집. 주로 요소를 그룹화하거나 분할한 결과를 컬렉션에 담아 반환하는데 사용
  * R <span style="color:yellow"><u>**collect**</u></span>(Collector<T, A, R> collector)
  * R collect(Supplier<R> supplier, BiConsumer<R, T> accumulator, BiConsumer<R, R> combiner)

### (5) 지연된 연산
* 최종 연산이 수행되기 전까지는 중간 연산이 수행되지 않는다.
  * 스트림에 대해 distinct(), sort() 같은 중간 연산을 호출해도 즉각적으로 연산이 수행되는 것이 아니다.
  * 최종연산이 수행되어야 비로스 스트림의 요소들이 중간 연산을 거쳐 최종 연산에 소모된다. 

### (6) Stream<Integer>와 IntStream
* 데이터소스의 요소를 기본형으로 다루는 스트림이 제공됨
  * IntStream, LongStream, DoubleStream
  * 요소의 타입이 T인 스트림은 기본적으로 Stream<T>이지만, 오토박싱&언박싱으로 인한 비효율을 줄이기 위해
    * Stream<Integer>대신 IntStream을 사용하는 것이 더 효율적

### (7) 병렬 스트림
* 스트림으로 데이터를 처리하면 병렬 처리가 쉬움
  * 병렬 스트림은 내부적으로 fork&join 프레임웍을 이용해 자동적으로 연산을 병렬로 수행함
  * 병렬 처리가 항상 더 빠른 결과를 얻게 해주는 것이 아님
  * parallel()
    * 병렬로 연산을 수행하도록 함
  * sequential()
    * parallel()을 호출한 것을 취소함. 병렬로 처리되지 않게 함.
  * 예)
    ```java
    int sum = strStream.parallel() //strStream을 병렬 스트림으로 전환
                       .mapToInt(s -> s.length())
                       .sum();

## 2.2 스트림 만들기
* 스트림의 소스가 될 수 있는 대상
  * 배열, 컬렉션, 임의의 수 등...

### (1) 컬렉션을 소스로 하는 스트림 만들기
* 컬렉션의 최고 조상인 Collection에 stream()이 정의됨
  * List와 Set을 구현한 컬렉션 클래스는 모두 stream()으로 스트림 생성 가능
  * stream()
    * 해당 컬렉션을 소스로 하는 스트림 반환
    * 예)
      ```java
      List<Integer> list = Arrays.asList(1, 2, 3, 4, 5); //가변인자
      Stream<Integer> intStream = list.stream(); //list를 소스로 하는 컬렉션 생성
      intStream.forEach(System.out::println); //스트림의 모든 요소 출력
      intStream.forEach(System.out::println); //에러! 스트림이 이미 닫힘
      //forEach()는 스트림의 요소를 소모하면서 작업을 수행하므로, 같은 스트림에 forEach()를 두번 호출할 수 없다.
      //한번 더 출력하고싶다면 같은 소스로부터 한 번 더 스트림을 생성해야 한다.

### (2) 배열을 소스로 하는 스트림 만들기
* Stream과 Arrays에 배열 소스로 스트림을 생성하는 static 메서드가 정의됨
  * ```java
    Stream<T> Stream.of(T... values) //가변인자
    Stream<T> Stream.of(T[])
    Stream<T> Arrays.stream(T{})
    Stream<T> Arrays.stream(T[] array, int startInclusive, int endExclusive)
  * 예) 문자열 스트림의 생성
    * ```java
      Stream<String> strStream = Stream.of("a", "b", "c"); //가변인자
      Stream<String> strStream = Stream.of(new String[] {"a", "b", "c"});
      Stream<String> strStream = Arrays.stream(new String[]{"a", "b", "c"});
      Stream<String> strStream = Arrays.stream(new String[]{"a", "b", "c"}, 0. 3); 
* int, long, double과 같은 기본형 배열을 소스로 하는 스트림의 생성
  * ```java
    IntStream IntStream.of(int... values) //IntStream임에 유의!
    IntStream IntStream.of(int[])
    IntStream Arrays.stream(int[])
    IntStream Arrays.stream(int[] array, int startInclusive, int endExclusive)

### (3) 특정 범위의 정수를 스트림으로 생성하기
* 지정된 범위의 연속된 정수를 스트림으로 생성
  * ```java
    IntStream IntStream.range(int begin, int end) //경계의 끝인 end가 포함X
    IntStream IntStream.rangeClosed(int begin, int end) //end가 포함O

### (4) 임의의 수로 이루어진 스트림 생성하기
* Random클래스에 다음과 같은 인스턴스 메서드 포함됨
  * 해당 타입의 난수로 이루어진 스트림 반환
  * 무한 난수 스트림 반환 -> limit()을 사용해 스트림의 크기를 제한해주어야 함
    * IntStream ints()
    * LongStream longs()
    * DoubleStream doubles()
  * 유한 난수 스트림 반환
    * IntStream ints(long streamSize)
    * LongStream longs(long streamSize)
    * DoubleStream doubles(long streamSize)
  * 지정된 범위 난수 스트림 반환
    * IntStream ints(int begin, int end)
    * LongStream longs(long begin, long end)
    * DoubleStream doubles(double begin, double end)
    * IntStream ints(long streamSize, int begin, int end)
    * LongStream logs(long streamSize, long begin, long end)
    * DoubleStream doubles(long streamSize, double begin, double end)

### (5) 람다식에 의해 계산된 값들을 요소로 하는 스트림 생성하기 - iterate(), generate()
* Stream클래스의 iterate(), generate()는 람다식을 매개변수로 받아, 이 람다식에 의해 계산되는 값들을 요소로 하는 무한스트림을 생성함
  * ```java
    static <T> Stream<T> iterate(T seed, UnaryOperator<T> f)
    static <T> Stream<T> generate(Supplier<T> s)
  * static <T> Stream<T> iterate(T seed, UnaryOperator<T> f)
    * 씨앗값(seed)으로 지정된 값부터 시작해, 람다식 f에 의해 계산된 결과를 다시 seed값으로 해서 계산을 계속함
    * 예)
      ```java
      Stream<Integer> evenStream = Stream.iterate(0, n -> n+2); //0, 2, 4, 6, ...
  * static <T> Stream<T> generate(Supplier<T> s)
    * iterate()처럼 람다식에 의해 계산되는 값을 요소로 하는 무한 스트림을 생성해 반환함. 
    * 그러나 iterate()와 달리 이전 결과를 이용해 다음 요소를 계산하지는 않음
    * 예)
      ```java
      Stream<Double> randomStream = Stream.generate(Math::random);
      Stream<Integer> oneStream = Stream.generate(() -> 1);
    * generate()에 정의된 매개변수의 타입은 Supplier<T>이므로, 매개변수가 없는 람다식만 허용됨
  * 주의
    * iterate(), generate()에 의해 생성된 스트림은 기본형 스트림 타입의 참조변수로 다룰 수 없음. 굳이 필요하다면 mapToInt()와 같은 메서드로 변환해야 함
    * 예)
      ```java
      IntStream evenStream = Stream.iterate(0, n -> n+2); //에러!!!
      DoubleStream randomStream = Stream.iterate(Math::random); //에러!!
      //maptToInt(), boxed()
      IntStream evenStream = Stream.iterate(0, n -> n+2).mapToInt(Integer::valueOf);
      Stream<integer> stream = evenSTream.boxed(); //IntStream -> Stream<Integer>
 
### (6) 파일의 목록을 소스로 하는 스트림 생성하기
* java.nio.file.Files의 list()
  * 지정된 디렉토리(dir)에 있는 파일의 목록을 소스로 하는 스트림을 생성해 반환
  * ```java
    Stream<Path> Files.list(Path dir)

### (7) 빈 스트림 생성하기
* 요소가 하나도 없는 빈 스트림을 생성할 수도 있음
  * 스트림에 연산을 수행한 결과가 하나도 없을 때 null보다 빈 스트림을 반환하는 것이 나음
  * ```java
    Stream emptyStream = Stream.empty(); //empty()는 빈 스트림을 생성해 반환
    long count = emptyStream.count(); //0

### (8) 두 스트림의 연결하기
* Stream의 static 메서드 concat() 사용시 같은 타입의 두 스트림을 하나로 연결 가능
* 예)
  ```java
    String[] str1 = {"123", "456", "789"};
    String[] str2 = {"ABC", "abc", "DEF"};
    Stream<String> strs1 = Stream.of(str1);
    Stream<String> strs2 = Stream.of(str2);
    Stream<String> strs3 = Stream.concat(strs1, strs2); //두 스트림을 하나로 연결

## 2.3 스트림의 중간연산
### (1) 스트림 자르기 - skip(), limit()
* ```java
  Stream<T> skip(long n)
  Stream<T> limit(long maxSize)
  IntStream skip(long n)
  IntStream limit(long maxSize)
* 예)
  ```java
  IntStream.intStream = IntStream.rangeClosed(1, 10); //1~10의 요소를 가진 스트림
  IntStream.skip(3).limit(5).forEach(System.out::print); //45678
    
### (2) 스트림의 요소 걸러내기 - filter(), distinct()
#### filter()
* 주어진 조건(Predicate)에 맞지 않는 요소를 걸러냄
  * ```java
    Stream<T> filter(Predicate<? super T> predicate)
  * 예)
  ```java
  IntStream intStream = IntStream.rangeClosed(1, 10); // 1~10
  intStream.filter(i -> i % 2 == 0).forEach(System.out::print); //246810
* 다른 조건으로 여러 번 쓸 수 있음
  * 예)
  ```java
  //아래의 두 문장은 동일한 결과를 얻음
  intStream.filter(i -> i%2 == 0 && i%3 !=0).forEach(System.out::print); //157
  intStream.filter(i -> i%2 == 0).filter(i -> i%3 != 0).forEach(System.out::print); //157

#### distinct()
* 스트림에서 중복된 요소들을 제거함
  * ```java
    STream<T> distinct()
  * 예)
  ```java
  IntStream intStream = IntStream.of(1, 2, 2, 3, 3, 3, 4, 5, 5, 6);
  IntSTream.distinct().forEach(System.out::print); //123456  

### (3) 스트림의 정렬 - sorted()
* sorted() 
  * ```java
    Stream<T> sorted()
    Stream<T> sorted(Comparator<? super T> comparator)
  * 정렬 기준
    * 지정된 Comparator
      * 만일 Comparator를 지정하지 않으면
        * 스트림 요소의 기본 정렬기준(Comparable)르 정렬함
        * 단, 스트림의 요소가 Comparable을 구현한 클래스가 아닐 경우 예외 발생
    * Comparator 대신 int값을 반환하는 람다식을 사용하는 것도 가능
  * 예)
  ```java
  Stream<String> strStream = Stream.of("dd", "aaa", "CC", "cc", "b");
  strStream.sorted().forEach(System.out::print); //CCAAAbccdd

#### 문자열 스트림을 정렬하는 다양한 방법 (보다 정확한 메서드 선언을 보려면 JavaAPI 문서 참조)
* 대소문자 구분, 기본정렬(AABBCCaabbcc)
  * strStream.sorted() //기본 정렬
  * strStream.sorted(Comparator.naturalOrder()) //기본 정렬
  * strStream.sorted((s1, s2) -> s1.compareTo(s2)); //람다식도 가능
  * strStream.sorted(String::compareTo); //위의 문장과 동일
* 대소문자 구분, 기본정렬의 역순(ccbbaaCCBBAA)
  * strStream.sorted(Comparator.reverseOrder())
  * strStream.sorted(Comparator.<String>naturalOrder().reversed())
* 대소문자 구분 없음(AAaaBBbbCCcc)
  * strStream.sorted(String.CASE_INSENSITIVE_ORDER)
* 대소문자 구분 없이 역순(ccCCbbBBaaAA)
  * strStream.sorted(String.CASE_INSENSITIVE_ORDER.reversed())
* 길이 순 정렬
  * strStream.sorted(Comparator.comparing(String::length))
  * strStream.sorted(Comparator.comparingInt(String::length)) //no오토박싱
* 길이 순 정렬 역순
  * strStream.sorted(Comparator.comparing(String::length).reversed())

#### Comparator의 default 메서드
#### Comparator의 static 메서드

### (4) 스트림의 변환 - map()
* 스트림에 저장된 값 중 원하는 필드만 뽑아내거나 특정 형태로 변환해야 할 때 map() 사용
  * ```java
    Stream<R> map(Function<? super T, ? extends R> mapper)
    //매개변수로 T타입을 R타입으로 변환해서 반환하는 함수를 지정해야 함
  * 예) File의 스트림에서 파일의 이름만 뽑아 출력
    * ```java
      Stream<File> fileStream = Stream.of(new File("Ex1.java"), new File("Ex1.bak"), new File("Ex2.java"), new File("Ex1.txt"));
      //map()으로 Stream<File>을 Stream<String>으로 변환
      Stream<String> filenameStream = fileStream.map(File::getName);
      filenameStream.forEach(System.out::println); //스트림의 모든 파일 이름들을 출력
  * map()도 filter()처럼 하나의 스트림에 여러 번 적용 가능
    * 예) File의 스트림에서 파일의 확장자만 뽑아 중복 제거해서 출력
      * ```java
        fileStream.map(File::getName) //Stream<File> -> Stream<String>
                  .filter(s -> s.indexOf('.') != -1) //확장자가 없는 것은 제외
                  .map(s -> s.subString(s.indexOf('.')+1)) //Stream<String> -> Stream<String>
                  .map(String::toUpperCase) //모두 대문자로 변환
                  .distinct() //중복 제거
                  .forEach(System.out::print);

### (5) 스트림의 조회 - peek()
* 스트림의 연산과 연산 사이에 올바르게 처리되었는지 확인하고 싶을 때 peek() 사용
  * (forEach()와 달리)스트림의 요소를 소모하지 않으므로 연산 사이에 여러번 끼워 넣어도 됨
  * 예)
    * ```java
      fileStream.map(File::getName) //Stream<File> -> Stream<String>
                .filter(s -> s.indexOf('.') != -1) //확장자가 없는 것은 제외
                .peek(s -> System.out.printf("filename = %s%n", s)) //파일명을 출력한다.
                .map(s -> s.subString(s.indexOf('.') + 1)) //확장자만 추출
                .peek(s -> System.out.printf("filename = %s%n", s)) //파일명을 출력한다.
                .forEach(System.out::println); 
      //peek()은 filter()나 map()의 결과를 확인할 때 유용함

### (6) mapToInt(), mapToLong(), mapToDouble()
* map()
  * 연산의 결과로 Stream<T>타입 스트림 반환
  * --> 스트림의 요소를 기본형 타입으로 변환할 경우 기본형 스트림으로 변환하는 것이 더 유용할 수 있음
* mapToInt(), mapToLong(), mapToDouble()
  * ```java
    DoubleStream mapToDouble(ToDoubleFunction<? super T> mapper)
    IntStream mapToInt(ToIntFunction<? super T> mapper)
    LongStream mapToLong(ToLongFunction<? super T> mapper)
  * Stream<Integer>과 IntStream 비교
    * ```java
      //스트림에 포함된 모든 학생들의 성적을 합산해야 하는 상황

      //map()으로 학생의 총점을 뽑아 Stream<Integer> 타입 스트림 생성
      Stream<Integer> studentScoreStream1 = studentStream.map(Student::getTotalScore);

      //mapToInt()로 IntStream타입의 스트림 생성
      IntStream studentScroreStream2 = studentStream.mapToInt(Student::getTotalScore);
      int allTotalScore = strudentScoreStream.sum(); //int sum();
        //더 효율적. 성적을 더할 때 Integer를 int로 변환할 필요가 없기 때문!
  * 기본형 스트림이 제공하는 메서드
    * Stream<T>에서 지원하는 숫자를 다루는 목적의 메서드들과 비교하면...
      * Stream<T>는 count()만 지원함
      * Stream<T>도 max(), min()을 정의하고있지만 매개변수로 Comparator를 지정해야만 함
    * 기본형 스트림(IntStream, LongStream, DoubleStream)이 제공하는 숫자를 다루는데 편리한 메서드들
      * int sum()
        * 스트림의 모든 요소의 총합
        * 스트림의 요소가 하나도 없을 때 0을 반환
      * OptionalDouble average()
        * sum() / (double)count()
      * OptionalInt max()
        * 스트림의 요소 중 제일 큰 값
      * OptionalInt min()
        * 스트림의 요소 중 제일 작은 값
      * 참고
        * OptioanlInt, OptionalDouble 타입
          * 일종의 래퍼클래스. 각각 int값과 Double값을 내부적으로 가지고 있음.
          * 기본형스트림 클래스가 제공하는 메서드 average() 반환타입이 Optional***인 이유
            * 스트림의 요소가 하나도 없어 0인 것과, 여러 요소들을 합한 평균이 0인 상황을 구분하기 위해
      * 특징
        * 최종연산이기 때문에 호출 후에 스트림이 닫힘
          * 예) 하나의 스트림에 sum()과 average()를 연속해 호출 불가
            * ```java
              IntStream scoreStream = studentStream.mapToInt(Student::getTotlaScore);
              long totalScore = scoreStream.sum(); //sum()은 최종연산이므로 호출 후 스트림이 닫힘.
              OptionalDouble average = scoreStream.average(); //에러! 스트림이 이미 닫혀있음.
              double d = average.getAsDouble(); //OptionalDouble에 저장된 값을 꺼내서 d에 저장
            * sum()과 average()를 모두 호출해야 할 때 스트림을 또 생성해야 함 --> 불편 --> IntSummaryStatistics가 제공하는 다양한 메서드 사용 권장
* IntSummaryStatistics
  * IntSummaryStatistics가 제공하는 메서드
  * ```java
    IntSummaryStatistics stat = scoreStream.summaryStatistics();
    long totalCount = stat.getCount();
    long totalScore = stat.getSum();
    double avgScore = stat.getAverage();
    int minScore = stat.getMin();
    int maxScore = stat.getMax(); 
* 기본형 스트림을 Stream<T>로 변환하는 법
  * mapToObj() 이용
    * 예) IntStream을 Stream<String>으로 변환
      * ```java
        IntStream intStream = new Random().ints(1, 46); //1~45 사이의 정수(46 포함x) 스트림 생성
        Stream<String> lottoStream = intStream.distinct().limit(6).sorted()
                                              .mapToObj(i -> i+ ", "); //정수를 문자열로 변환
        lottoStream.forEach(System.out::print);
* CharSequence
  * chars()
    * String이나 StringBuffer에 저장된 문자들을 IntStream으로 다룰 수 있게 해줌
    * 예)
      * ```java
        IntStream charStream = "12345".chars(); //default IntStream chars()
        int charSum = charStream.map(ch -> ch-'0').sum(); //charSum = 15
* 스트림간의 변환 정리 (자바의정석 p.864 참고)
  * Stream<String> -> IntStream으로 변환할 때
    * mapToInt(Integer::parseInt)
  * Stream<Integer> -> IntStream으로 변환할 때
    * mapToInt(Integer::intValue)

### (7) flatMap()
* flatMap()
  * Stream<T[]>를 Stream<T>로 변환할 때 사용
    * 예1) <u>스트림의 요소가 배열이거나 map()의 연산결과가 배열인 경우</u>(스트림의 타입이 Stream<T[]>)
      * ```java
        Stream<String[]> studentArrStream = Stream.of(
          new String[]{"김자바", "박자바", "안자바"},
          new String[]{"이자바", "전자바", "홍자바"}
        );
        //각 요소의 문자열들을 합쳐 문자열이 요소인 스트림 Stream<String>으로 만드는 방법
        //1. map()을 써서 스트림의 요소를 변환
        //2. Arrays.stream(T[])을 써서 배열을 스트림으로 생성
        //그러나 map()을 쓰면...
        Stream<Stream<String>> stringStreamStream = studentArrStream.map(Arrays::stream);
        //따라서 flatMap()을 써야...
        Stream<String> stringStream = studentArrStream.flatMap(Arrays::stream);
      * map()과 flatMap() 비교
        * Stream<String[]> ---> map(Arrays::stream) ---> Stream<Stream<String>>
        * Stream<String[]> ---> flatMap(Arrays::stream) ---> Stream<String>
    * 예2) 여러 문장을 요소로 하는 스트림이 있을 때, 이 문장들을 split()로 나눠 요소가 단어인 스트링스트림으로 만들고 싶은 상황
      * ```java
         String[] lineArr = {
          "Believe or not It is true",
          "Do or not There is no try"
         }

         Stream<String> lineStream = Arrays.stream(lineArr);
         //map()을 쓰면...
         Stream<Stream<String>> stringArrStream = lineStream.map(line -> Stream.of(line.split(" +")));
         //flatMap()을 쓰면...
         Stream<String> stringStream = lineStream.flatMap(line -> Stream.of(line.split(" +")));
      * map()과 flatMap() 비교
        * Stream<String> ---> map(s -> Stream.of(s.split(" +"))) ---> Stream<Stream<String>>
        * Stream<String> ---> flatMap(s -> Stream.of(s.split(" +"))) ---> Stream<String>

## 2.4 Optional<T>와 OptionalInt
* Optional<T>
  * 지네릭 클래스. 'T'타입의 객체를 감싸는 래퍼클래스.
  * Optional타입의 객체에는 모든 타입의 참조변수를 담을 수 있음.
  * java.util.Optional은 JDK 1.8부터 추가되었음.
  * ```java
    public final class Optional<T> {
      private final T value; //T타입의 참조변수
        ...
    }
  * 최종연산의 결과를 그냥 반환하지 않고 Optional객체에 담아서 반환하면, 반환된 결과가 null인지 if문으로 체크하는 대신 Optional에 정의된 메서드를 통해 간단히 처리할 수 있음
    * null체크를 위한 if문 없이도 NullPointerException이 발생하지 않는 보다 간결하고 안전한 코드 작성이 가능해짐
    
### Optional 객체 생성하기
* of(), ofNullable()
  * Optional 객체를 생성할 때 사용
    * 만일 참조변수의 값이 null일 가능성이 있다면 ---> ofNullable()사용
    * Optional<T>타입의 참조변수를 기본값으로 초기화할 때는 ---> empty()사용
  * 예)
    * ```java
      String str = "abc";
      Optional<String> optVal1 = Optional.of(str);
      Optional<String> optVal2 = Optional.of("abc");
      Optional<String> optVal3 = Optional.of(new String("abc"));
      
      //만일 참조변수의 값이 null일 가능성이 있다면 ofNullable()사용
      Optional<String> optVal4 = Optional.of(null); //에러! NullPointerException 발생
      Optional<String> optVal5 = Optional.ofNullable(null); //ok
      
      //Optional<T>타입의 참조변수를 기본값으로 초기화할 때는 empty() 사용
      Optional<String> optVal6 = null; //null로 초기화
      Optional<String> optVal7 = Optional.<String>empty(); //빈 객체로 초기화

### Optional 객체의 값 가져오기
#### get(), orElse(), orElseGet(), orElseThrow()
* get()
  * Optional객체에 저장된 값을 가져올 때 사용
  * 값이 null일 때 NoSuchElementException이 발생할 수 있음 
* orElse()    
  * 값이 null일 때 대체할 값 지정
* orElseGet()
  * null을 대체할 값을 반환하는 람다식 지정
* orElseThrow()
  * null일 때 지정된 예외를 발생시킴
* 예)
  * ```java
    Optional<String> optVal = Optioanl.of("abc");
    String str1 = optVal.get(); //optVal에 저장된 값을 반환. null이면 예외 발생.
    String str2 = optVal.orElse(""); //optVal에 저장된 값이 null일 때는 ""를 반환
    String str3 = optVal.orElseGet(String::new); // () -> new String()와 동일
    String str4 = optVal.orElseThrow(NullPointerException::new); //널이면 예외 발생

#### filter(), map(), flatMap()
  * Optional 객체에도 filter(), map(), flatMap() 사용 가능
    * map()의 연산결과가 Optional<Optional<T>>일 때, flatMap()을 사용하면 Optional<T>를 결과로 얻음
    * Optional객체의 값이 null이면, 이 메서드들은 아무 일도 하지 않음
    * 예)
      * ```java
        int result = Optional.of("123")
                            .filter(x -> x.length() > 0)
                            .map(Integer::parseInt).orElse(-1); //result=123
        result = Optional.of("")
                        .filter(x -> x.length() > 0)
                        .map(Interger::parseInt).orElse(-1); //result=-1;

#### isPresent()
* isPresent()는 Optional객체의 값이 null이면 false를, 아니면 true를 반환함

#### ifPresent()
* ifPresent(Consumer<T> block)은 값이 있으면 주어진 람다식을 실행하고, 없으면 아무일도 하지 않음
* 예)
  * ```java
    if(str != null) {
      System.out.println(str);
    }
    //위의 조건문을 isPresent()를 사용해 다음과 같이 작성 가능
    if(Optional.ofNullable(str).isPresent()) {
      System.out.println(str);
    }
    //ifPresent()로 더 간결히 작성 가능
    Optional.ofNullable(str).ifPresent(System.out::println);
    
* Stream클래스에 정의된 메서드 중 Optional<T>를 반환하는 메서드
  * Optional<T> findAny()
  * Optional<T> findFirst()
  * Optional<T> max(Comparator<? super T> comparator)
  * Optional<T> min(Comparator<? super T> comparator)
  * Optional<T> reduce(BinaryOperator<T> accumulator)

### OptionalInt, OptionalLong, OptionalDouble

## 2.5 스트림의 최종연산
## 2.6 collect()
## 2.7 Collector 구현하기 
## 2.8 스트림의 변환