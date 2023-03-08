# 2. 스트림(stream)

## 2.5 스트림의 최종연산
* 최종연산
  * 스트림의 요소를 소모해 결과를 만들어냄
  * 최종연산 후에는 스트림이 닫히게 되고 더 이상 사용할 수 없음
  * 최종연산의 결과는 스트림 요소의 합과 같은 단일 값이거나, 스트림의 요소가 담긴 배열 또는 컬렉션일 수 있음

### <mark>forEach()</mark>
* ```java
    void forEach(Consumer<? super T> action)
* peek()과 달리 스트림의 요소를 소모함
* 반환타입이 void이므로 스트림의 요소를 출력하는 용도로 많이 사용됨

### <mark>조건검사 - allMatch(), anyMatch(), noneMatch(), findFirst(), findAny()</mark>
* ```java
    boolean allMatch (Predicate<? super T> predicate) //모든 요소가 일치하면 참
    boolean anyMatch (Predicate<? super T> predicate) //하나의 요소라도 일치하면 참
    boolean noneMatch (Predicate<? super T> predicate) //모든 요소가 불일치하면 참
  
    Optional<T> findFirst() //조건에 일치하는 첫번째 요소를 반환
    Optional<T> findAny() //조건에 일치하는 요소를 하나 반환 
    //주로 filter()와 함꼐 사용되어 조건에 맞는 스트림 요소가 있는지 확인하는데 사용
    //병렬 스트림의 경우 findFirst()대신 findAny()를 써야 함
* 예) 
  * ```java
    //학생들의 성적 정보 스트림 stuStream에서 총점이 낙제점(총점 100 이하)인 학생이 있는지 확인하기
    boolean noFailed = stuStream.anyMatch(s -> s.getTotalScore() <= 100)
    
    Optional<Student> stu = stuStream.filter(s -> s.getTotalScore() <= 100).findFirst();
    Optional<Student> stu2 = parallelStream.filter(s -> s.getTotalScore() <= 100).findAny();


### <mark>통계 - count(), sum(), average(), max(), min()</mark>
* ```java
  long count()
  Optional<T> max(Comparator<? super T> comparator)
  Optional<T> min(Comparator<? super T> comparator)
  //참고) 기본형스트림(IntStream, LongStream, ...)의 min(), max()와 달리
  //매개변수로 Comparator를 필요로 함 
* 기본형 스트림이 아닌 경우에는 통계와 관련된 메서드가 3개 뿐임
* 대부분의 경우 위의 메서드를 사용하기보다는 기본형 스트림으로 변환하거나, reduce(), collect()를 이용해 통게정보를 얻음

### <mark>리듀싱 - reduce()</mark>
* ```java
  Optional<T> reduce(BinaryOperator<T> accumulator) {
    T a = identity;
  
    for(T b : stream)
      a = accumulator.apply(b, a);
      
    return a;
  }
* 스트림의 요소를 줄여나가면서 연산을 수행하고 최종결과를 반환
  * 따라서 매개변수의 타입이 BinaryOperator<T>인 것. 처음 두 요소를 가지고 연산한 결과를 가지고 그 다음 요소와 연산함.
  * 이 과정에서 스트림의 요소를 하나씩 소모
  * 스트림의 모든 요소를 소모하면 결과를 반환한다.
* 연산의 초기 값을 갖는 reduce()
  ```java
  T reduce(T identity, BinaryOperator<T> accumulator) //초기값과 스트림의 첫번째 요소로 연산을 시작함
  //스트림의 요소가 하나도 없는 경우 초기값이 반환되므로 반환타입이 T이다.
  U reduce(U identity, BiFunction<U, T, U> accumulator, BinaryOperator<U> combiner)
  //combiner는 병렬스트림에 의해 처리된 결과를 합칠 때 사용하기 위한 것
* 최종연산 count(), sum()은 내부적으로 모두 reduce()를 이용해 작성된 것임
  * ```java
    int count = intStream.reduce(0, (a, b) -> a + 1);
    int sum = intStream.reduce(0, (a, b) -> a + b);
    int max = intStream.reduce(Integer.MIN_VALUE, (a, b) -> a > b ? a : b);
    int min = intStream.reduce(Integer.MAX_VALUE, (a, b) -> a < b ? a : b);
    //OptionalInt max = intStream.reduce(Integer::max); // int max(int a, int b)
    //OptionalInt min = intStream.reduce(Integer::min); // int min(int a, int b)
    //int maxValue = max.getAsInt();

## 2.6 <mark>collect()</mark>
> * collect()
>   * 스트림의 최종연산. 매개변수로 컬렉터를 필요로 함
> * Collector
>   * 인터페이스. 컬렉터는 이 인터페이스를 구현해야 함
> * Collectors
>   * 클래스. static메서드로 미리 작성된 컬렉터를 제공함
* collect()
  * 스트림의 요소를 수집하는 최종 연산, 리듀싱과 유사

  
## 2.7 Collector 구현하기 
* 컬렉터 작성 = 컬렉터 인터페이스 구현
* Collector 인터페이스
  * ```
    public interface Collector<T, A, R> {
      Supplier<A> supplier(); //작업 결과를 저장할 공간을 제공
      BiConsumer<A, T> accumulator(); //스트림의 요소를 수집(collect)할 방법을 제공
      BinaryOperator<A> combiner(); //두 저장공간을 병합할 방법을 제공(병렬 스트림)
      Function<A, R> finisher(); //결과를 최종적으로 변환할 수 있는 방법을 제공
    
      Set<Characteristics> characteristics(); //컬렉터의 특성이 담긴 Set을 반환
  * Collector 인터페이스의 추상메서드
    * supplier()
      * 수집 작업 결과를 저장할 공간을 제공
    * accumulator()
      * 스트림의 요소를 어떻게 supplier()가 제공한 공간에 누적할 것인가를 정의
    * combiner()
      * 병렬 스트림인 경우, 여러 쓰레드에 의해 처리된 결과를 어떻게 합칠것인가를 정의
    * finisher()
      * 작업결과를 변환함
      * 변환이 필요없다면 항등함수 Function.identity()를 반환하면 됨
    * characteristics()
      * 컬렉터가 수행하는 작업의 속성에 대한 정보 제공
      * 아래의 속성 중 해당하는 것을 Set에 담아 반환
        * Characteristics.CONCURRENT
          * 병렬로 처리할 수 있는 작업
        * Characteristics.UNORDERED
          * 스트림의 요소의 순서가 유지될 필요 없는 작업
        * Characteristics.IDENTITY_FINISH
          * finisher()가 항등함수인 작업
      * 예)
        * ```
          public Set<Characteristics> characteristics() {
            return Collection.unmodifiableSet(EnumSet.of(
              Collector.Characteristics.CONCURRENT,
              Collector.Characteristics.UNORDERED
            ));
          }
          //아무런 속성도 지정하고 싶지 않다면,
          Set<Characteristics> characteristics() {
            return Collection.emptySet(); //지정할 특성이 없는 경우 비어있는 set을 반환
          }
  * Collector의 특징
    * 내부적으로 처리하는 과정이 리듀싱과 같음

## 2.8 스트림의 변환




















