## Static Factory Method

### 🪄객체의 생성 방법
- 생성자의 사용
- 정적 팩터리 메소드의 사용

```Java
public class Book {
    private String title;

    private Book(String title){
        this.title= title;
    }

    public static Book titleof(String title){
        return new Book(title);
    }
}
```
```JAVA
Book book = Book.titleof("몰입의 즐거움");
```
---
### 🪄Static Factory Method의 장점
> #### Static factory method에는 이름이 존재한다
생성자에 전달되는 인자들은 어떤 객체가 생성되는 것인지 설명하지 못한다. 
즉, 코드의 **가독성이 떨어진다**는 의미.

하지만 정적 팩터리 메서드는 이름을 잘 짓는다면 **사용하기 용이하고 가독성이 높아**진다.

> #### 기존 클래스는 시그니처별로 하나의 생성자만 넣을 수 있다.
이것을 피하고자 한다면 인자의 순서를 바꾸는 방식이 있을 수 있지만, **이는 사용자가 API 사용 설명서를 읽지 않고는 코드를 이해할 수 없는 끔찍한 일**이 벌어진다. 

Static factory mathod는 같은 **시그니처라도 용도에 따라 적절한 이름을 사용**하면 중복된 시그니처로 클래스를 생성할 수 있다

> #### 호출마다 새로운 객체를 생성할 필요가 없다. 
변경 불가능 클래스의 경우 이미 만들어 둔 객체를 사용하거나, 만든 객체를 캐시하여 재사용하는 등의 방식을 사용할 수 있다. 

<details><summary>자세한 설명</summary>

```JAVA
User user1 = new User("Alice");
User user2 = new User("Alice");
```

위와 같은 코드의 경우 `user1==user2` 의 결과는 `False`이다.\
즉, 메모리 공간에는 서로 다른 두 객체가 존재한다는 의미!

```JAVA
new Boolean(true);
new Boolean(true);
new Boolean(true);
```
마찬가지로 위와 같이 Boolean 객체를 반복해서 생성하는 것은 비효율 적이다.\
따라서 실제론 다음과 같은 캐시 방법을 사용한다. 

```JAVA
public final class MyBoolean {

    private static final MyBoolean TRUE = new MyBoolean(true);
    private static final MyBoolean FALSE = new MyBoolean(false);

    private boolean value;

    private MyBoolean(boolean value) {
        this.value = value;
    }

    public static MyBoolean valueOf(boolean value) {
        return value ? TRUE : FALSE;
    }
}
```
</details>    

즉 동일한 객체가 요청되는 경우가 잦고, 객체 생성에 드는 비용이 클 때, 정적 팩터리 메서드는 성능 개선에 용이하다.

> #### 개체 통제 클래스의 성향을 띈다 
어떤 시점에 어느 정도의 객체가 존재할지 제어할 수 있다. 

**개체 통제 클래스의 장점**

- singleton pattern을 따르도록 하기에 용이
- 객체 생성이 불가능한 클래스를 만들 수 있음
- 변경 불가 클래스의 경우 두 개의 같은 객체가 존재하지 못하도록 할 수 있음

<details><summary>자세한 설명</summary>

객체를 중복 생성하지 않고, 메모리 상의 같은 객체를 가리키도록 만듦으로써 `user1==user2`의 값이 `True`가 된다. 

이렇게 구현된 클래스는 `equals` 대신 `==` 연산자를 사용할 수 있기 때문에 성능 향상에 도움이 될 수 있다
</details>

> #### 반환값 자료형의 하위 자료형 객체를 반환할 수 있다. 

즉 반환되는 객체의 클래스를 더욱 유연하게 결정할 수 있다.

```JAVA
public abstract class Animal {
    public abstract void speak();
    
    // 2. 정적 팩토리 메서드
    public static Animal createAnimal(String type) {
        if (type.equals("dog")) {
            return new Dog(); // 하위 자료형 객체 반환
        } else {
            return new Cat(); // 하위 자료형 객체 반환
        }
    }
}
```

해당 기법은 인터페이스 기반 프레임워크 구현에 적합하다

<details><summary>자세한 설명</summary>

```JAVA
public interface MyInterface {
    void method();
}
```

```JAVA
public final class MyInterfaces {

    private MyInterfaces() {}

    public static MyInterface create() {
        return new MyInterfaceImpl();
    }

    private static class MyInterfaceImpl
            implements MyInterface {
    }
}
```
- 인터페이스는 API만 공개
- 구현 클래스는 숨김
- 생성은 정적 팩터리 메서드가 담당
</details>

메서드에 주어지는 인자를 통해 반환 객체를 동적으로 결정할 수 있고, 이를 통해 소프트웨어 버전에 따라 반환될 객체의 클래스 구현을 달리하여 유지보수의 용이성을 높일 수 있다. 

해당 방식을 사용한 예시로는 대표적으로 JAVA의 `Collection Interface`와 `Enum Class`가 있다

---