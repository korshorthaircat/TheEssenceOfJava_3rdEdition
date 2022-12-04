# 3. 애너테이션(annotation)

## 3.1 애너테이션이란?
* 프로그램의 소스코드 안에 다른 프로그램을 위한 정보를 미리 약속된 형식으로 포함시킨 것
* 주석(comment)처럼 <U>프로그래밍 언어에 영향을 미치지 않으면서도 다른 프로그램에게 유용한 정보를 제공</U>할 수 있음
  * 예)
    * 자신이 작성한 소스코드 중 특정 메서드만 테스트하기를 원하는 상황
      ```java
      @Test //이 메서드가 테스트 대상임을 테스트 프로그램에게 알림
      public void method() { //... }
      //모든 프로그램에게 의미가 있는 것은 아니고, 
      //해당 프로그램에 미리 정의된 종류와 형식으로 작성해야만 의미가 있음.
  * 종류
    * JDK에서 기본적으로 제공하는 표준 애너테이션
      * 주로 컴파일러를 위한 것, 컴파일러에게 유용한 정보를 제공함
      * 메타 애너테이션 제공 - 새로운 애너테이션 정의시 사용
    * 다른 프로그램에서 제공하는 애너테이션

## 3.2 표준 애너테이션
### **@Override**
* 컴파일러에게 오버라이딩하는 메서드라는 것을 알림

### **@Deprecated**
* 앞으로 사용하지 않을 것을 권장하는 대상에 붙임
* 예)
  * java.util.Date클래스의 대부분의 메서드에는 '@Deprecated'가 붙어있는데, 이 메서드 대신 jdk1.1부터 추가된 Calender클래스의 get()을 사용하라고 유도

### **@SuppressWarnings**
* 컴파일러의 특정 경고메세지가 나타나지 않게 해줌
* 컴파일러의 경고메세지
  * 무시하고 넘어갈 수도 있지만, 일반적으로 모두 확인하고 해결해서 컴파일 후 어떠한 메세지도 나타나지 않게 해야 함
  * 그러나 경우에 따라 경고가 발생할 것을 알면서도 묵인할 때가 있음
    * 이 경우 다른 경고들을 놓치기 쉬움
    * 묵인해야할 경고가 발생하는 대상에 이 애너테이션을 붙이면 다른 경고들을 놓치지 않을 수 있음
* 억제할 수 있는 경고 메세지의 종류
  * "deprecation"
    * @Deprecated가 붙은 대상을 사용해 발생하는 경고를 억제
  * "unchecked"
    * 지네릭스로 타입을 지정하지 않았을 때 발생하는 경고를 억제
  * "rawtypes"
    * 지네릭스를 사용하지 않아서 발생하는 경고를 억제
  * "varargs"
    * 가변인자의 타입이 지네릭 타입일 때 발생하는 경고를 억제 
* 사용법
  * 억제하려는 경고메세지를 애너테이션 뒤의 괄호()에 문자열로 지정
  * 예)
    ```java
    @SuppressWarnings("unchecked") //지네릭스 타입을 지정하지 않았을 때 발생하는 경고 억제
    ArrayList list = new ArrayList();
  * 둘 이상의 경고를 억제하려면 배열에서처럼 괄호 추가 유의
    ```java
    @SuppressWarnings({"deprecation", "unchecked", "varargs"}}) 

### **@SafeVarags**
* 지네릭스 타입의 가변인자에 사용(JDK1.7)
* 메서드에 선언된 가변인자의 타입이 non-reifiable 타입일 경우 해당 메서드를 선언하는 부분과 호출하는 부분에서 "unchecked"경고가 발생한다. 
  * --> 해당 코드에 문제가 없다면, 이 경고를 억제하기위해 @SafeVargs를 붙여야 한다.
  * 참고)
    * reifiable 타입
      * 컴파일 후에도 제거되지 않는 타입
    * non-reifiable 타입
      * 컴파일 후 제거되는 타입
      * 지네릭 타입들은 대부분 컴파일 시 제거됨
* 사용 대상
  * static이나 final이 붙은 메서드와 생성자
  * 유의! 오버라이드될 수 있는 메서드에는 사용될 수 없음

### **@FunctionalInterface**
* 함수형 인터페이스라는 것을 알림(JDK1.8)
* 함수형 인터페이스를 선언할 때 이 애너테이션을 붙이면, 컴파일러가 함수형 인터페이스를 올바르게 선언했는지 확인하고 잘못된 경우에 에러를 발생시킴
  * --> 붙이면 실수를 방지할 수 있음
    * 참고)
      * 함수형 인터페이스는 추상메서드가 하나뿐이어야 한다는 제약이 있음

### **@Native**
* 네이티브 메서드에서 참조되는 상수 앞에 붙임(JDK1.8)
* 예)
    ```java
  @Native
  public static final long MTN_VALUE = 0x80000000000000L; //java.lang.Long 클래스에 정의된 상수
* 네이티브 메서드(native method)
  * JVM이 설치된 OS의 메서드
  * 보통 C언어로 작성되어 있음. 자바에서는 메서드의 선언부만 정의하고 구현하지는 않음. 따라서 추상메서드처럼 선언부만 있고 몸통이 없다. 
  * 모든 클래스의 조상인 Object클래스의 메서드들은 대부분 네이티브 메서드이다.
    * 네이티브 메서드는 자바로 정의되어있으므로 호출하는 방법은 자바의 일반메서드와 같다.
    * 그러나 실제로 호출되는 것은 OS의 메서드이다.
    * 자바에 정의된 네이티브 메서드의 OS의 메서드를 연결해주는 작업이 존재한다.

### 메타 애너테이션(애너테이션을 정의하는데 사용)
* '애너테이션을 위한 애너테이션'
  * 애너테이션을 정의할 때 애너테이션의 적용대상(target)이나 유지기간(retention)등을 지정하는데 사용

#### **@Target**
* 애너테이션이 적용가능한 대상을 지정하는데 사용
  * 애너테이션 적용 대상의 종류
    * java.lang.annotation.ElementType이라는 열거형에 정의되어 있음
    * 종류
      * ANNOTATION_TYPE - 애너테이션
      * CONSTRUCTOR - 생성자
      * FIELD - 필드(멤버변수, enum상수)
      * LOCAL_VARIABLE - 지역변수
      * METHOD - 메서드
      * PACKAGE - 패키지
      * PARAMETER - 매개변수
      * TYPE - 타입(클래스, 인터페이스, enum)을 선언할 때
      * TYPE_PARAMETER - 타입 매개변수(JDK1.8)
      * TYPE_USE - 타입이 사용되는 모든 곳(JDK1.8), 해당 타입의 변수를 선언할 때
        * 주의) FIELD는 기본형에, TYPE_USE는 참조형에 사용됨!
  * 사용 예)
    ```java
    import static java.lang.annotation.ElementType.*;
    //static import문을 쓰면 'ElementType'을 'TYPE'와 같이 간단히 할 수 있음
    
    @Target({TYPE, FIELD, METHOD, PARAMETER, CONSTRUCTOR, LOCAL_VARIABLE})
    //애너테이션을 적용할 수 있는 대상을 여러개의 값을 지정할 경우 중괄호{} 사용에 유의
    @Retention(RetentionPolicy.SOURCE)
    public @interface SuppressWarnings {
      Strng[] value();
    }

    @Target({FIELD, TYPE, TYPE_USE}) //적용 대상이 FIELD, TYPE, TYPE_USE
    paublic @interface MyAnnotation { } //MyAnnotation을 정의
    
    @MyAnnotation //적용 대상이 TYPE인 경우
    class MyClass {
      @MyAnnotation //적용 대상이 FIELD인 경우
      int i; 
      @MyAnnotation //적용 대상이 TYPE_USE인 경우
      MyClass mc;
    }

#### **@Documented**
* 애너테이션 정보가 javadoc으로 작성된 문서에 포함되게 함
* 기본 애너테이션 중 @Override, @SuppressWarnings를 제외하고는 모두 이 메타애너테이션이 붙어있음.

#### **@Inherited**
* 애너테이션이 자손 클래스에 상속되도록 함
  * 조상 클래스에 이 애너테이션을 붙이면, 자손클래스도 이 애너테이션이 붙은 것과 같이 인식된다.
  ```java
  @Inherited //@SuperAnno가 자손까지 영향을 미치게 한다.
  @interface SuperAnno {}
  
  @SuperAnno
  class Parent {}
  class Child extends Parent {} //Child클래스에도 @SuperAnno가 붙은 것으로 인식


#### **@Retention**
* 애너테이션이 유지되는 범위(기간)를 지정하는데 사용
* 애너테이션 유지 정책(retention policy)의 종류
  * SOURCE
    * 소스파일에만 존재. 클래스파일에는 존재하지 않음.
    * @Override나 @SuppressWarning처럼 컴파일러가 사용하는 애너테이션의 유지정책은 SOURCE임.
      * 컴파일러를 직접 작성할 것이 아니라면 이 유지정책은 필요 없음.
  * CLASS
    * 클래스 파일에 존재. 실행시에 사용 불가. 기본값.
    * 컴파일러가 애너테이션의 정보를 클래스파일에 저장할 수 있게는 하지만, 클래스파일이 JVM에 로딩될 때는 애너테이션의 정보가 무시되어 실행시 애너테이션의 정보를 읽을 수 없음
      * --> 유지정책의 기본값은 CLASS이지만 잘 사용되지 않는 이유가 된다.
  * RUNTIME
    * 클래스 파일에 존재. 실행시에 사용가능.
    * 실행시에 '리플렉션(reflection)'을 통해 클래스파일에 저장된 애너테이션의 정보를 읽어 처리할 수 있음
    * @FunctionInterface는 컴파일러가 체크해주는 애너테이션이지만, 실행시에도 사용되므로 유지정책은 RUNTIME임.
        ```java
      @Documented
      @Retention(RetentionPolicy.RUNTIME)
      @Target(ElementType.TYPE)
      public @interface FuntionalInterface {}
    * 참고) 지역변수에 붙은 애너테이션은 컴파일러만 인식할 수 있으므로, 유지정책을 RUNTIME으로 붙여도 실행시에는 인식되지 않음

#### **@Repeatable**
* 애너테이션을 반복해서 적용할 수 있게 함(JDK1.8)
* 보통은 하나의 대상에 한 종류의 애너테이션을 붙이는데, @Repeatable이 붙은 애너테이션은 여러번 붙일 수 있음
  ```java
  //@ToDo애너테이션을 정의함
  @Repeatable(Todo.class) //ToDO애너테이션을 여러 번 반복해서 쓸 수 있게 해줌
  @interface ToDo {
    String value();
  }
  //MyClass에 @ToDo를 여러번 쓸 수 있음(@Repeatable에 의해)
  @ToDo("delete test codes")
  @ToDo("override inherited methods")
  class MyClass{ ... }
* 일반적인 애너테이션과 달리 같은 이름의 애너테이션이 여러 개가 하나의 대상에 적용될 수 있으므로, 이 애너테이션을 하나로 묶어 다룰 수 있는 애너테이션도 추가로 정의해야 함
    ```java
  @interface ToDos { //여러 개의 ToDo애너테이션을 담을 컨테이너 애너테이션 @ToDos
    ToDo[] value(); //ToDo애너테이션 배열 타입의 요소를 선언. 주의! 이름이 반드시 value이어야 함
  }  
  
  //@ToDo애너테ㅣ이션을 정의함
    @Repeatable(Todo.class) //ToDO애너테이션을 여러 번 반복해서 쓸 수 있게 해줌
    @interface ToDo {
    String value();
}

## 3.4 애너테이션 타입 정의하기(직접 애너테이션 만들어 사용해보기!)
* <U>새로운 애너테이션 정의하는 법</U>
  * '@' 기호를 붙이는 것을 제외하면 인터페이스를 정의하는 것과 동일함
    * 애너테이션에도 인터페이스처럼 상수를 정의할 수 있지만, 디폴트 메서드는 정의할 수 없음
  * 엄밀히 말해, '@Override'는 애너테이션이고, 'Override'는 애너테이션의 타입임
  
    ```java
    @interface 애너테이션이름 { 
      타입 요소이름(); //애너테이션의 요소를 선언함
      ...
    }  

### 애너테이션의 요소
* 애너테이션의 요소(element)
  * 애너테이션 내에 선언된 메서드
* 추상 메서드의 형태를 가짐(반환값이 있고, 매개변수는 없음), 상속을 통해 구현하지 않아도 됨
    ```java
  @interface TestInfo {
    int count();
    String testedBy();
    String[] testTools();
    TestType testType(); //enum TestType{FIRST, FINAL}
    DateTime testDate(); //자신이 아닌 다른 애너테이션(@DateTime)을 포함할 수 있음
  }
  @interface DateTime{
    String yymmdd();
    String hhmmss();
  }
* 애너테이션을 적용할 때 애너테이션의 요소를 빠짐없이 지정해주어야 함.
  * 요소의 이름도 같이 적어주므로 순서는 상관없음
  ```java
  @TestInfo(count=3, testedBy="Kim", testTools={"JUnit", "AutoTester"}, testType=TestType.FIRST, testDate=@DateTime(yymmdd="160101", hhmmss="235959"))
  public class NewClass { ... }

* 각 요소는 기본값을 가질 수 있으며, 기본 값이 있는 요소는 애너테이션 정의시 값을 지정하지않으면 기본값 사용됨
  ```java
  @interface TestInfo {
    int count() default 1; //기본값을 1로 지정
  }
  
  @TestInfo //@TestInfo(count=1)과 동일 
  public class NewClass { ... }
  
* 애너테이션 요소가 오직 하나뿐이고 이름이 value인 경우, 애너테이션을 적용할 요소의 이름을 생략하고 값만 쓰기 가능
  ```java
  @interface TestInfo{
    String value();
  }
  @TestInfo("passed") //@TestInfo(value="passed")와 동일
  class NewClass{ ... }
  
* 요소의 타입이 배열인 경우, 괄호{}를 사용해 여러개의 값 지정 가능
  ```java
  @interface TestInfo{
    String[] testTools();
  }
  @TestInfo(testTools={"JUnit", "AutoTester"}) //값이 여러개인 경우
  @TestInfo(testTools="JUnit") //값이 하나일 때는 괄호{} 생략 가능
  @TestInfo(testTools={}) //값이 없을 때는 괄호{}가 반드시 필요

* 기본값 지정시에도 괄호{} 사용 가능
  ```java
  @interface TestInfo{
    String[] info() dafult {"aaa", "bbb"}; //기본값이 여러개인 경우 {} 사용
    String[] info2() default "ccc"; //기본값이 하나인 경우 괄호 생략 가능
  }
  @TestInfo //@TestInfo(info={"aaa", "bbb"}, info2="ccc")와 동일
  @TestInfo(info2={}) //@TestInfo(info={"aaa", "bbb"}, info2={})와 동일

* 요소의 타입이 배열일때도 요소의 이름이 value이면, 요소의 이름이 생략할 수 있음

### java.lang.annotation.Annotation
* 모든 애너테이션의 조상 Annotation
  * 그러나 애너테이션은 상속이 허용되지 않으므로 명시적으로 Annotation을 조상으로 지정할 수 없음(extends Annotation은 허용되지 않는 표현)
  * 또한 Annotation은 애너테이션이 아니라 일반적 인터페이스로 정의되어 있음
    * 모든 애너테이션 객체에 대해 equals(), hashCode(), toString()과 같은 메서드 호출 가능

### 마커 애너테이션 Marker Annotation
* 값을 지정할 필요가 없는 경우, 애너테이션의 요소를 하나도 정의하지 않을 수 있음
  * 예) 
    * Serializable인터페이스, Cloneable인터페이스
  ```java
  @Target(ElementType.METHOD)
  @Retention(RetentionPolicy.SOURCE)
  public @interface Override {} //마커애너테이션. 정의된 요소가 하나도 없음.
  
  @Target(ElementType.METHDO)
  @Retention(RetentionPolicy.SOURCE)
  public @interface Test {} //마커애너테이션. 정의된 요소가 하나도 없음.

### 애너테이션 요소의 규칙
* 요소의 타입은 기본형, String, enum, 애너테이션, Class만 허용됨
* ()안에 매개변수를 선언할 수 없음
* 예외를 선언할 수 없음
* 요소를 타입 매개변수로 정의할 수 없음
  ```java
  @interface AnnoTest{
    int id = 100; //OK. 상수 선언. static final int id = 100;
    String major(int i, int j); //에러. 매개변수를 선언할 수 없음.
    String minor() throws Exception; //에러. 예외를 선언할 수 없음.
    ArrayList<T> list(); //에러. 요소의 타입에 타입 매개변수 사용 불가 
  }