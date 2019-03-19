= 課題

- https://google.github.io/dagger/

== 課題0

プロジェクトを clone して実行する。


== 課題1

MainActivity の内部で SharpSurroundDecorator のインスタンスを生成しているが、Dagger を使って SharpSurroundDecorator のインスタンスを外から inject するようにしよう。

=== 課題1-1

Dagger を dependency に追加する

```kotlin
apply plugin: 'kotlin-kapt'

...

dependencies {
  ...

  def dagger_version = "2.16"
  implementation "com.google.dagger:dagger:$dagger_version"
  kapt "com.google.dagger:dagger-compiler:$dagger_version"

  ...
}

=== 課題1-2

SharpSurroundDecorator のインスタンスを provide する AppModule を作る

- di パッケージを用意して、そこに AppModule を用意する
- Dagger では Module には @Module をつける
- インスタンスを返すメソッドには @Provides をつけ、メソッド名は provdeXX にする

```kotlin
@Module
class AppModule {

    @Provides
    fun provideSharpSurroundDecorator(): SharpSurroundDecorator {
        return SharpSurroundDecorator()
    }
}
```

=== 課題1-3

AppModule で構成される AppComponent を作る

- AppComponent は di パッケージにおく
- Component は interface
- Component には @Component をつける
- @Component の modules で利用する Module を指定する

```kotlin
@Component(modules = [AppModule::class])
interface AppComponent {
}
```

=== 課題1-4

Application を継承した MainApplication クラスを用意して、onCreate() で AppComponent をセットアップする

- AndroidManifest の設定を忘れずに

```kotlin
class MainApplication : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent.create()
    }
}
```

=== 課題1-5

MainActivity で SharpSurroundDecorator を inject してもらうようにする

- AppComponent に `fun inject(activity: MainActivity)` を追加する
- MainActivity に `@Inject lateinit var decorator : SharpSurroundDecorator` を追加する
- MainActivity の onCreate() で AppComponent の inject() を呼び出す。

```kotlin
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var decorator: SharpSurroundDecorator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (application as MainApplication).appComponent.inject(this)

        setContentView(R.layout.activity_main)

        val text = "hello"

        textView.text = decorator.decorate(text)
    }
}
```

=== 課題1-6

AppModule で SharpSurroundDecorator ではなく Decorator 型で返すようにする

- MainActivity が持つ decorator も Decorator にする

```kotlin
@Module
class AppModule {

    @Provides
    fun provideDecorator(): Decorator {
        return SharpSurroundDecorator()
    }
}
```

=== 課題1-7

SharpSurroundDecorator のインスタンス生成を Dagger にお任せする

- コンストラクタに @Inject をつける
- コンストラクタに引数がある場合、その引数に渡されるインスタンスは Dagger の依存 Module で解決される
- Dagger が依存インスタンスを解決できるもの（コンストラクタに @Inject がついているものや、他で @Provides されているものなど）は、@Provides のメソッドの引数として受け取れる

```kotlin
class SharpSurroundDecorator @Inject constructor() : Decorator {
  ...
}
```

```kotlin
@Module
class AppModule {

    @Provides
    fun provideDecorator(decorator: SharpSurroundDecorator): Decorator {
        return decorator
    }
}
```

=== 課題1-8

@Binds を使って、SharpSurroundDecorator インスタンスを Decorator として Dagger に割り当てる

- @Binds をつけるメソッドは interface か abstract class の Module にしか置けない
- @Binds 用に interface BindModule を用意する
- AppComponent に BindModule も追加する

```kotlin
@Module
class AppModule {

}

@Module
interface BindModule {

    @Binds
    fun bindDecorator(decorator: SharpSurroundDecorator): Decorator
}
```

```kotlin
@Component(modules = [AppModule::class, BindModule::class])
interface AppComponent {

    fun inject(activity: MainActivity)
}
```

== 課題2

Dagger Android Support ライブラリを使おう。

- https://google.github.io/dagger/android

=== 課題2-1

Dagger Android Support　を dependency に追加する

```kotlin
apply plugin: 'kotlin-kapt'

...

dependencies {
  ...

  def dagger_version = "2.16"
  ...
  implementation "com.google.dagger:dagger-android:$dagger_version"
  implementation "com.google.dagger:dagger-android-support:$dagger_version"
  kapt "com.google.dagger:dagger-android-processor:$dagger_version"

  ...
}



=== 課題2-2

AppComponent に AndroidInjectionModule を追加する

- https://google.github.io/dagger/api/latest/dagger/android/AndroidInjectionModule.html

```kotlin
@Component(
    modules = [
        AppModule::class,
        BindModule::class,
        AndroidInjectionModule::class
    ]
)
interface AppComponent {

    fun inject(activity: MainActivity)
}
```

- AndroidInjectionModule はどんな Module なのか確認しよう
- AndroidInjector.Factory はどんな interface なのか確認しよう
- AndroidInjector はどんな interface なのか確認しよう

=== 課題2-3

ActivityBindingModule interface を用意して AppComponent に追加する

```kotlin
@Module
interface ActivityBindingModule {

}
```

```kotlin
@Component(
    modules = [
        AppModule::class,
        BindModule::class,
        AndroidInjectionModule::class,
        ActivityBindingModule::class
    ]
)
interface AppComponent {

    fun inject(activity: MainActivity)
}
```


=== 課題2-4

ActivityScope アノテーションを自分で定義する

Scope.kt
```kotlin
@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class ActivityScope
```



=== 課題2-5

ActivityBindingModule に @ActivityScope と @ContributesAndroidInjector をつけた メソッドを用意して MainActivity　を返すようにする

```Kotlin
@Module
interface ActivityBindingModule {

    @ActivityScope
    @ContributesAndroidInjector
    fun contributeMainActivityInjector(): MainActivity
}
```
