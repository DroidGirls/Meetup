# Unit Test 課題

## 資料

[資料](droid_girls_unit_test.pdf)

## JUnit4

```java
testCompile 'junit:junit:4.12'
```

### JUnit4 のテストを実行してみよう

Android Studio で新しいプロジェクトを作ると、ExampleUnitTest というクラスが自動で作られます

まずはこのクラスをそのまま実行して、2 + 2 が 4 になることをテストしよう




### JUnit4 でテストを書いてみよう

以下の仕様を満たすように TimeFormatter.format() を実装し、それをテストしよう

```java
public class TimeFormatter {
    
    public static String format(int seconds) {
        // ここを実装する
    }
}
```

* seconds が0以下のときは "" を返
* seconds が1分未満のときは、"{{seconds}}秒前" を返す
* seconds が1分以上1時間未満のときは、"{{minutes}}分前" を返す
* seconds が1時間以上1日未満のときは "{{hour}}時間前" を返す
* seconds が1日以上のときは "{{day}}日前" を返す


59, 60, 61 など境界になるところをテストしよう。

#### ヒント

```java
assertEquals("", TimeFormatter.format(-1));
```




## Mockito

```java
testCompile 'org.mockito:mockito-core:2.7.17'
```

### interface をモックしてみよう

次のようなインタフェースとクラスがあります。

```java
public interface Repository {

    User getUserA();

    User getUserB();
}
```

```java
public class UserService {

    private final Repository repository;

    public UserService(Repository repository) {
        this.repository = repository;
    }

    public User getUser(boolean isTarget) {
        if (isTarget) {
            return repository.getUserA();
        } else {
            return repository.getUserB();
        }
    }
}
```

Repository を mockito でモックし、UserService.getUser() で isTarget が true のとき getUserA() が、false のとき getUserB() が呼ばれることをテストしよう

#### ヒント

```java
Mockito.mock(Repository.class);

Mockito.verify(repository).getUserA();
```


### class をモックしてみよう

次のようなクラスがあります。

```java
public class TargetDiscriminator {

    private final Random random = new Random();

    boolean isTarget() {
        return random.nextBoolean();
    }
}
```

```java
public class UserService2 {

    private final Repository repository;
    private final TargetDiscriminator targetDiscriminator;

    public UserService2(Repository repository, TargetDiscriminator targetDiscriminator) {
        this.repository = repository;
        this.targetDiscriminator = targetDiscriminator;
    }

    public User getUser() {
        if (targetDiscriminator.isTarget()) {
            return repository.getUserA();
        } else {
            return repository.getUserB();
        }
    }
}
```

TargetDiscriminator をモック化し、TargetDiscriminator.isTarget() の値によって、それぞれ getUserA() 、getUserB() が呼ばれることをテストしよう

#### ヒント

```java
Mockito.mock(TargetDiscriminator.class);

Mockito.when(targetDiscriminator.isTarget()).thenReturn(...);
```



### インスタンスを spy してみよう

実際のインスタンスに対して、メソッド呼び出しをスタブ化したり、メソッドが呼ばれたことを検証するには、```Mockito.spy()``` を使います。

次のようなクラスがあります。

```java
public class UserService3 {

    private final Repository repository;
    private final Random random = new Random();

    public UserService3(Repository repository) {
        this.repository = repository;
    }

    public User getUser() {
        if (isTarget()) {
            return repository.getUserA();
        } else {
            return repository.getUserB();
        }
    }

    public boolean isTarget() {
        return random.nextBoolean();
    }
}
```

UserService3 のインスタンスを spy() して、isTarget() が呼ばれたときに特定の値を返すようにし、値によって、それぞれ getUserA() 、getUserB() が呼ばれることをテストしよう

ヒント

```java
UserService3 userService3 = spy(new UserService3(repository));
```







## Robolectric

```java
testCompile 'org.robolectric:robolectric:3.3.1'
```

### TextUtils.isEmpty() を使ったコードのテストを書いてみよう

以下の仕様を満たす User クラスを実装し、それをテストしよう

* setFirstName() で first name をセットできる
* setLastName() で last name をセットできる
* getFirstName() でセットした first name を取得できる
* getLastName() でセットした last name を取得できる
* getName() で "{{first name}} {{last name}}" を取得できる
* first name が空ではなく last name が空のときは、getName() は "{{first name}} " ではなく "{{first name}}" を返す
* first name が空で last name が空ではないときは、getName() は " {{last name}}" ではなく "{{last name}}" を返す
* first name も last name も空のときは、getName() は " " ではなく "" を返す

#### ヒント

```java
        final User user = new User();
        user.setFirstName("Yuki");
        user.setLastName("Anzai");
        assertEquals("Yuki", user.getFirstName());
        assertEquals("Anzai", user.getLastName());
        assertEquals("Yuki Anzai", user.getName());
```





### SharedPreferences を使ったコードのテストを書いてみよう

次のようなクラスがあります

```java
public class Instruction {

    private static final String KEY = "instruction_is_finished";

    public static boolean isFinished(@NonNull Context context) {
        return pref(context).getBoolean(KEY, false);
    }

    public static void setFinished(@NonNull Context context) {
        pref(context).edit().putBoolean(KEY, true).apply();
    }

    private static SharedPreferences pref(@NonNull Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }
}
```

以下の挙動をテストしよう

* 最初の状態では Instruction.isFinished() は false
* setFinished() を呼んだら Instruction.isFinished() は true

#### ヒント

```java
final Context context = RuntimeEnvironment.application;
```


### Parcelable のテストが通るようにしよう

次のようなクラスがあります。

```java
public class Cat implements Parcelable {

    private final String name;
    private final int age;

    public Cat(String name, int age) {
        this.name = name;
        this.age = age;
    }

    private Cat(Parcel in) {
        // TODO: 実装
    }

    public void writeToParcel(Parcel out, int flags) {
        // TODO: 実装
    }

    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<Cat> CREATOR = new Parcelable.Creator<Cat>() {
        public Cat createFromParcel(Parcel in) {
            return new Cat(in);
        }

        public Cat[] newArray(int size) {
            return new Cat[size];
        }
    };

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }
}
```

このクラスに対する以下のテストがあります。

```java
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class CatTest {

    @Test
    public void parcelable_test() {
        Cat cat = new Cat("mike", 10);

        // インスタンスの状態をParcelに書き出す
        final Parcel out = Parcel.obtain();
        cat.writeToParcel(out, 0);

        // Parcelのインデックスを初期位置に戻す
        out.setDataPosition(0);

        // Parcelに書きだしたインスタンスの状態を書き戻す
        Cat cat2 = Cat.CREATOR.createFromParcel(out);

        assertEquals("mike", cat2.getName());
        assertEquals(10, cat2.getAge());
    }
}
```

このテストが通るように、TODO 部分を実装しましょう。Parcelable については http://y-anz-m.blogspot.jp/2010/03/androidparcelable.html を参考にしてください。














