# RxJava 2 課題

## 準備
### 1. レポジトリのクローン
つぎのレポジトリをクローンして、Android Studioで `6_rxjava/RxJavaExample` を開き、ビルドが通ることを確認する。
https://github.com/DroidGirls/Meetup

つぎのような画面が表示されれば大丈夫です。

![screenshot_1498401255](https://user-images.githubusercontent.com/6446183/27516980-dd8f272a-59fe-11e7-890c-8f677d14982c.png)

### 2. GitHub ApplicationのClient IDとClient Secretを準備する
同じIPアドレスから実行できるAPIの頻度には制限があります。つぎのページを参考にして、専用のGitHub OAuth ApplicationのClient IDとClient Secretを準備しておいてください。

[Registering OAuth Apps | GitHub Developer Guide](https://developer.github.com/apps/building-integrations/setting-up-and-registering-oauth-apps/registering-oauth-apps/)

<img width="558" alt="new_oauth_application" src="https://user-images.githubusercontent.com/6446183/27516972-c0e2a0d4-59fe-11e7-9b7d-7d4fb5afa70b.png">

### 3. コードにClient IDとClient Secretを埋め込む
`MainActivity.java`の定数`CLIENT_ID`と`CLIENT_SECRET`に、取得したIDとシークレットを指定しておきましょう。

```java
public class MainActivity extends AppCompatActivity {
  private static final String TAG = MainActivity.class.getSimpleName();
  private static final String CLIENT_ID = "XXXXX"; // <- client ID
  private static final String CLIENT_SECRET = "XXXXX"; // <- client secret
```

## 課題 1. コントリビュータ一覧を表示してみよう
つぎのように、GitHub APIを実行するサービスクラスは既にあるので、それを使ってLoadボタンがタップされたらリストにコントリビュータ一覧を表示してみましょう。

```java
service = new Retrofit.Builder().baseUrl("https://api.github.com")
    .client(client)
    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
    .addConverterFactory(MoshiConverterFactory.create())
    .build()
    .create(GitHubService.class);

// 指定されたOwnerとRepositoryのコントリビュータ一覧を取得する
service.contributors(owner, repo)

// adapterに取得したContributorリストをセットする
adapter.setContributors(contributors)
```

![screenshot_1498403457](https://user-images.githubusercontent.com/6446183/27517212-fe674d92-5a03-11e7-88d3-4ba0a83af115.png)

ヒント1. `subscribe()`を呼ぶまでは通信は実行されません
ヒント2. `subscribeOn()`と`observeOn()`を使って実行スレッドをコントロールしましょう
ヒント3. Activityの`onDestroy()`時にDisposableの`dispose()`を呼ぶるのを忘れないように

## 課題 2. 先頭の5名のコントリビュータだけ表示してみよう
このAPIの結果の上位5件だけ表示してみてください。出来たらRxJavaのオペレータを使うと、この後の課題が楽になります。

ヒント1. APIの戻り値は`List`型でユーザーIDがまとめて返ってきますが、これを1つずつにするオペレータがあります
ヒント2. takeオペレータを使ってみましょう

## 課題 3. コントリビュータの氏名も表示してみよう
このリストはGitHubのユーザーIDしか表示されません。別のGitHub APIを実行して、フルネームも取得してみましょう。

![screenshot_1498403327](https://user-images.githubusercontent.com/6446183/27517231-1f4c8d2e-5a04-11e7-8b49-f48bc83e620f.png)

ヒント1. `GitHubService#users()`メソッドを呼ぶと、ユーザーの詳細情報が取得できます
ヒント2. このような1つ1つのイベントから、新しいObservableを作るときに使うオペレータは何でしょうか？

## 課題 4. Loadボタンのenabled/disabledを切り替えてみよう
いまの実装ではOwner欄とRepository欄が空欄でもLoadボタンが押せてしまいます。RxJavaを利用することで、両方が空欄でないときにのみLoadボタンをenabledに、そうでない場合はdisabledにしてみてください。

![screenshot_1498401255](https://user-images.githubusercontent.com/6446183/27517244-3dbd2962-5a04-11e7-9058-eda989d7fb1b.png)

ヒント1. combineLatestオペレータを使ってみましょう

## 発展課題 1. 連打に対応してみよう
いまの実装ではLoadボタンを連打すると、連打した数だけリクエストが飛んでしまいます。連打されたときに最新以外のリクエストをキャンセルするにはどうしたら良いか考えてみてください。

ヒント1. 使うDisposableを工夫することで可能になります

## 発展課題 2. フルネームの取得を並列にしよう
課題3.のフルネームを取得する処理は、単純に実装すると1つずつ取得することになります。これを並列にするにはどうしたら良いか考えてみてください。

ヒント1. ログを見たときに`--> GET`と`<-- 200 OK`が交互に表示され**なければ**並列になっています
ヒント2. 上位5名の順番が保証されているかどうか確認しましょう
ヒント3. flatMap/switchMap/concatMapの違いについて調べてみましょう
