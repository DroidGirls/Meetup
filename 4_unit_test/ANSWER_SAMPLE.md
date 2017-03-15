# 回答サンプル

## JUnit4

### JUnit4 でテストを書いてみよう

```java
public class TimeFormatter {

    public static String format(int seconds) {
        if (seconds <= 0) {
            return "";
        }
        if (seconds < 60) {
            return seconds + "秒前";
        }
        if (seconds < 3600) {
            return (seconds / 60) + "分前";
        }
        if (seconds < 86400) {
            return (seconds / 3600) + "時間前";
        }
        return (seconds / 86400) + "日前";
    }
}
```

```java
public class TimeFormatterTest {

    @Test
    public void format_test() {
        assertEquals(TimeFormatter.format(-1), "");
        assertEquals(TimeFormatter.format(1), "1秒前");
        assertEquals(TimeFormatter.format(59), "59秒前");
        assertEquals(TimeFormatter.format(60), "1分前");
        assertEquals(TimeFormatter.format(61), "1分前");
        assertEquals(TimeFormatter.format(119), "1分前");
        assertEquals(TimeFormatter.format(120), "2分前");
        assertEquals(TimeFormatter.format(3599), "59分前");
        assertEquals(TimeFormatter.format(3600), "1時間前");
        assertEquals(TimeFormatter.format(3601), "1時間前");
        assertEquals(TimeFormatter.format(7199), "1時間前");
        assertEquals(TimeFormatter.format(7200), "2時間前");
        assertEquals(TimeFormatter.format(86399), "23時間前");
        assertEquals(TimeFormatter.format(86400), "1日前");
        assertEquals(TimeFormatter.format(86401), "1日前");
        assertEquals(TimeFormatter.format(172799), "1日前");
        assertEquals(TimeFormatter.format(172800), "2日前");
    }
}
```

## Mockito

### interface をモックしてみよう

```java
public class UserServiceTest {

    @Test
    public void test() {
        final Repository repository = mock(Repository.class);
        UserService userService = new UserService(repository);

        userService.getUser(true);
        verify(repository).getUserA();

        userService.getUser(false);
        verify(repository).getUserB();
    }
}
```

### class をモックしてみよう

```java
public class UserServiceTest2 {

    @Test
    public void test() {
        final Repository repository = mock(Repository.class);
        final TargetDiscriminator targetDiscriminator = mock(TargetDiscriminator.class);
        UserService2 userService2 = new UserService2(repository, targetDiscriminator);

        when(targetDiscriminator.isTarget()).thenReturn(true);
        userService2.getUser();
        verify(repository).getUserA();

        when(targetDiscriminator.isTarget()).thenReturn(false);
        userService2.getUser();
        verify(repository).getUserB();
    }
}
```


### インスタンスを spy してみよう


```java
public class UserServiceTest3 {

    @Test
    public void test() {
        final Repository repository = mock(Repository.class);
        UserService3 userService3 = spy(new UserService3(repository));

        when(userService3.isTarget()).thenReturn(true);
        userService3.getUser();
        verify(repository).getUserA();

        when(userService3.isTarget()).thenReturn(false);
        userService3.getUser();
        verify(repository).getUserB();
    }
}
```




## Robolectric

### TextUtils.isEmpty() を使ったコードのテストを書いてみよう

```java
public class User {

    private String firstName;
    private String lastName;

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getName() {
        final boolean needSeparator = !TextUtils.isEmpty(firstName)
                && !.TextUtils.isEmpty(lastName);
        return firstName + (needSeparator ? " " : "") + lastName;
    }
}
```

```
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class UserTest {

    @Test
    public void name_test() {
        final User user = new User();
        user.setFirstName("Yuki");
        user.setLastName("Anzai");
        assertEquals("Yuki", user.getFirstName());
        assertEquals("Anzai", user.getLastName());
        assertEquals("Yuki Anzai", user.getName());
    }

    @Test
    public void name_test_when_first_name_is_empty() {
        final User user = new User();
        user.setFirstName("");
        user.setLastName("Anzai");
        assertEquals("", user.getFirstName());
        assertEquals("Anzai", user.getLastName());
        assertEquals("Anzai", user.getName());
    }

    @Test
    public void name_test_when_last_name_is_empty() {
        final User user = new User();
        user.setFirstName("Yuki");
        user.setLastName("");
        assertEquals("Yuki", user.getFirstName());
        assertEquals("", user.getLastName());
        assertEquals("Yuki", user.getName());
    }

    @Test
    public void name_test_when_name_is_empty() {
        final User user = new User();
        user.setFirstName("");
        user.setLastName("");
        assertEquals("", user.getFirstName());
        assertEquals("", user.getLastName());
        assertEquals("", user.getName());
    }

}
```


### SharedPreferences を使ったコードのテストを書いてみよう

```java
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class InstructionTest {

    @Test
    public void test() {
        final Context context = RuntimeEnvironment.application;
        assertThat(Instruction.isFinished(context)).isFalse();
        TimeUnit.HOURS.toSeconds(1);

        Mockito.mock(Cat.class);

        Instruction.setFinished(context);
        assertThat(Instruction.isFinished(context)).isTrue();
    }
}
```


### Parcelable のテストが通るようにしよう

```java
public class Cat implements Parcelable {

    private final String name;
    private final int age;

    public Cat(String name, int age) {
        this.name = name;
        this.age = age;
    }

    private Cat(Parcel in) {
        name = in.readString();
        age = in.readInt();
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeString(name);
        out.writeInt(age);
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


