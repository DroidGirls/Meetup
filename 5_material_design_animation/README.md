# material design animation　課題
https://speakerdeck.com/takahirom/how-to-implement-material-design-animation-droid-girls-version

## 準備
### PixabayのAPIを準備する  
pixabayのAPIを利用するので、以下で登録を行ってください。  
http://pixabay.com  

このページでAPI keyをコピーします。  
https://pixabay.com/api/docs/  
![image](https://qiita-image-store.s3.amazonaws.com/0/27388/9b85ac5d-0b3f-4fcd-fb04-ae3edaba958e.png)


### サンプルアプリをビルドする  
GitHubのサンプルをcloneしてください。  
https://github.com/takahirom/material-motion-hands-on  

**project/appフォルダに pixabay_keyという名前で、API keyだけ書いて保存をしてください。(重要)**  

実行すると以下のように表示されるはずです。  
![image](https://qiita-image-store.s3.amazonaws.com/0/27388/7f41fdc1-56a7-843a-0d27-0412fd46943c.png)  

今回のmeetupではこのアプリにmaterial motionを付けていくことを目的とします。  
今回はAndroid 5.0以降の端末ではアニメーションし、5.0未満の端末ではアニメーションは満足に行えないが動作するというところを目指して実装していきます。  
### 参考用にPlaidアプリとmaterial-elementアプリをインストールして動きを見る  
自分で一から書くより、標準的と思われる実装を真似るのが一番の近道だと思います。  
参考にインストールしてみましょう。  
また使いたい実装があれば、コードをクローンして見てみましょう。  

nickbutcher/Plaid  
Googleのマテリアルデザインの参考実装となります。  
https://play.google.com/store/apps/details?id=io.plaidapp&hl=ja  
https://github.com/nickbutcher/plaid  

takahirom/material-element  
(私が作ったものになります。)  
https://deploygate.com/distributions/38cf137b8da496b1cf1a0290ddd34d4ce4c42fea#install  
https://github.com/takahirom/material-element  

## 課題 1. タッチフィードバックを実装してみよう  
現状、ユーザーはどの猫を押したのかわかりにくいです。タッチフィードバックを追加して、わかりやすくしてあげたいです。  
先程の猫の画像を押した時に波紋状のエフェクトが広がる"放射状の反応"(Radial reaction)を実装してあげましょう。  
参考: https://material.io/guidelines/motion/material-motion.html#material-motion-how-does-material-move  
タッチフィードバックを利用するにはフィードバックに利用するDrawableをViewに設定します。  

<details><summary>ヒント</summary><p>

### 設定するDrawable  
フィードバックに利用するDrawableとして`?android:attr/selectableItemBackground`を設定してあげるのが楽で良いでしょう。  
これを利用することで、  
Android 5.0以降では、Android 5.0以降で利用できる"放射状の反応"のRippleDrawableを利用し、  
Android 5.0未満では普通のフィードバックを利用でき、Android 5.0未満では灰色の単色を表示してくれます。  
自分でdrawableのxmlファイルを作っても構いません。  

### Viewへの設定方法  
画像の上にタッチフィードバックを表示したい場合、レイアウトで`android:foreground`属性を利用することで設定することができます。  
しかし、`android:foreground`属性はFrameLayout以外ではAndroid 6.0以降でしか利用することができません。  

|| FrameLayout.setForeground | FrameLayout以外のView.setForeground |
|---|---|---|
|API Level20 Android 4.0〜4.4 | ○ | X |
|23以上 Android 6.0以上 | ○ | ○ |

今回はImageView(AppCompatImageViewでも良い)を継承したカスタムビューを作って対応してあげることでタッチフィードバックの対応を行ってみましょう。  

### やること
リスト内の猫の画像のレイアウトは`project/app/src/main/res/layout/item_photo.xml`にあります。  

1. ForegroundImageViewなどのカスタムViewを作成し、レイアウトのImageViewを置き換えてあげましょう。    
ForegroundImageViewの実装はこちらを利用するとよいでしょう。("ForegroundImageView"で検索すると沢山の人が実装を行っていることがわかります。)  
https://github.com/hzsweers/plaid/blob/z/moarbackport/app/src/main/java/io/plaidapp/ui/widget/ForegroundImageView.java  

2. またandroid:foregroundという属性をxmlで記述できるようにするため、以下のファイルも追加してあげましょう。  
https://github.com/nickbutcher/plaid/blob/master/app/src/main/res/values/attrs_foreground_view.xml  

3. 実装が完了すると以下のようになり、タップした時にタッチフィードバックを見ることが出来るはずです。  
![image](https://qiita-image-store.s3.amazonaws.com/0/27388/a9ff6aee-d340-59db-b939-73ccee03ceb1.png)  

参考:  
DroidKaigi 2017 逆引きマテリアルデザイン  
http://yaraki.github.io/slides/droidkaigi2017/index.html#14  
Material Design度が高まるRipple Effect対応  
http://qiita.com/nissiy/items/bf2742ffb990e3c8f875  
</p></details>


## 2. 画像読み込みをリッチにしてみよう  
マテリアルデザインガイドラインのLoading imagesを実装してみましょう。  
https://material.io/guidelines/patterns/loading-images.html  
画像の**色**にご着目ください。  
![sample.gif](https://qiita-image-store.s3.amazonaws.com/0/27388/fbf68709-0403-e68f-0ee6-683d831ee3f9.gif)  
こちらマテリアルデザインの**Loading images**にあるものになります。  
https://material.google.com/patterns/loading-images.html  
彩度(saturation)を変えてアニメーションさせています。  
詳細はこちらの記事にあります。  
http://qiita.com/takahirom/items/0d0aacfea94b25dcaceb#%E8%89%B2%E3%81%AE%E5%BD%A9%E5%BA%A6%E3%81%AE%E3%82%A2%E3%83%8B%E3%83%A1%E3%83%BC%E3%82%B7%E3%83%A7%E3%83%B3

## 3. エレベーションを状態によって変えてみよう  
マテリアルデザインガイドラインのDynamic elevation offsetsを実装してみましょう。  
画像の**影**にご注目ください。  
![ripple.gif](https://qiita-image-store.s3.amazonaws.com/0/27388/6cb906ca-945e-45a1-cfc2-02846b8e0e16.gif)  
[ガイドライン](https://material.io/guidelines/material-design/elevation-shadows.html#)  
[実装方法について書かれたQiita](http://qiita.com/takahirom/items/2e67333adecb189dbc93#%E3%83%AC%E3%82%B9%E3%83%9D%E3%83%B3%E3%82%B7%E3%83%96%E9%AB%98%E5%BA%A6%E3%81%A8%E5%8B%95%E7%9A%84%E9%AB%98%E5%BA%A6%E3%82%AA%E3%83%95%E3%82%BB%E3%83%83%E3%83%88responsive-elevation-and-dynamic-elevation-offsets-1)  

## 4. 画面遷移を動かしてみよう
SharedElementTransitionをただ適応してみましょう。  

<details><summary>ヒント</summary><p>

Activityを起動する側  

```java
final Intent launchIntent = DetailActivity.getLaunchIntent(MainActivity.this, item);
final ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(MainActivity.this, photoView, getString(R.string.transition_name_photo));
ActivityCompat.startActivity(MainActivity.this, launchIntent, optionsCompat.toBundle());
```

起動される側のActivity

activity_detail.xml

```xml
<ImageView
...
    android:transitionName="@string/transition_name_photo"
... />
```

</p></details>


<details><summary>うまく動かないとき</summary><p>

### そもそもアニメーションしない  
→ OSバージョンはAndroid 5.0以上ですか？(対応していません)  
→ Transition Nameは設定しましたか？(設定しましょう)  
### 画像が小さくなるアニメーションになってしまう  
画像の表示がまだ終わっていないためです。  
Glideの画像の取得が終わるまで待ってあげましょう。  

以下でアニメーションの開始を待つことができます。  

```java
ActivityCompat.postponeEnterTransition(this);
```

以下で開始することができます。

```
 ActivityCompat.startPostponedEnterTransition(DetailActivity.this);
```

Glideと組み合わせると以下のようになります。

```java
ActivityCompat.postponeEnterTransition(this);
final ImageView imageView = (ImageView) findViewById(R.id.photo);
Glide
        .with(this)
        .load(photoDetail.getWebformatURL().replace("640", "340"))
        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
        .dontAnimate()
        .listener(new RequestListener<String, GlideDrawable>() {
            @Override
            public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                return false;
            }
            @Override
            public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                ActivityCompat.startPostponedEnterTransition(DetailActivity.this);
                return false;
            }
        })
        .into(imageView);
```
</p></details>

## 5. マテリアルな動きを適応してみよう

* イージングカーブを適応  
https://material.io/guidelines/motion/duration-easing.html    
SharedElementTransitionを突然動き出さないように、画面内での自然な動きをInterporatorを適応してみましょう。    
標準カーブのfast_out_slow_inを適応することで可能です。    
* Arcアニメーションを適応する  
GravityArcMotionクラス(Plaidリポジトリ内にある)を適応してみましょう。  
TransitionのPathMotionに自分で作ったクラスを指定することでできます。  
https://material.io/guidelines/motion/movement.html#movement-movement-within-screen-bounds  


<details><summary>ヒント</summary><p>

標準のtransitionは以下のように定義されています。このTransitionを変えることで、遷移するtransitionを変更することができます。

```xml
        <item name="windowSharedElementEnterTransition">@transition/move</item>
        <item name="windowSharedElementExitTransition">@transition/move</item>
```

https://android.googlesource.com/platform/frameworks/base/+/686de427113ec40c3104eabcfec60cb3a153385f/core/res/res/values/themes_material.xml#536

move.xmlの内容は以下のようになっています。

```xml
<transitionSet xmlns:android="http://schemas.android.com/apk/res/android">
    <changeBounds/>
    <changeTransform/>
    <changeClipBounds/>
    <changeImageTransform/>
</transitionSet>
```

このtransitionを変更するにはvalues-v21/styles.xmlのthemeに以下のように設定してあげましょう。

```xml
        <item name="android:windowSharedElementEnterTransition">@transition/photo_transition</item>
        <item name="android:windowSharedElementExitTransition">@transition/photo_transition</item>
```

photo_transition.xmlはresフォルダにtransitionフォルダを追加して、move.xmlをコピーして作ってみましょう。  

それにinterpolatorを追加しましょう。  
以下のplaidのtransitionフォルダにあるtransitionの記述が参考になるはずです。  
https://github.com/nickbutcher/plaid/tree/master/app/src/main/res/transition  


</p></details>


<details><summary>Tips</summary><p>

開発者オプションのAnimator duration scaleを10倍とかにすると各アニメーションが分かりやすいです。  

またこのAnimator duration scaleはもっと気軽に切り替えができる方法があります  
ここからダウンロードできるapkをインストールして  
https://github.com/nickbutcher/AnimatorDurationTile/releases

```
adb shell pm grant uk.co.nickbutcher.animatordurationtile android.permission.WRITE_SECURE_SETTINGS
```

上記のadbコマンドを入力することで、Custom Quick Settings(通知領域の設定の部分)から切り替えることができるようになります。  

</p></details>


## 6. Choreographyや他のパターンを実装してみよう(時間があれば)  

### Choreography    
もっと詳細にさまざまなことが決まっています。    
例えば横の長さと縦の長さを別のタイミングでに変化させると言ったことです。  
![image](https://qiita-image-store.s3.amazonaws.com/0/27388/de8ee395-60fa-0832-25e5-3f26c2f06938.png)  
(https://material.io/guidelines/motion/choreography.html より)  

これに実装するには現状では標準のクラスでは難しそうです。[サポートライブラリのコード](https://android.googlesource.com/platform/frameworks/support/+/master/transition/ics/android/support/transition/ChangeBoundsPort.java)を真似して、縦横のアニメーションの時間をずらしたカスタムTransitionを作成することでできそうです。  
自分でカスタムTransitionクラスを作成してみましょう。  

<details><summary>ヒント</summary><p>

カスタムTransitionの作成方法についてはスライドでも説明しますが、以下に書いてあります。  
https://developer.android.com/training/transitions/custom-transitions.html  


[サンプルのリポジトリのanswerブランチでカスタムTransitionを作成することによって挑戦はしています](https://github.com/takahirom/material-motion-hands-on/blob/answer/app/src/main/java/com/github/takahirom/motion_app/animation/transition/AsymmetricTransform.java)が、  
完璧な動きはできていません。  
2点上手くできていない点があります。  

* 1点目は横幅と縦幅のアニメーションと移動のアニメーションがくっついてしまっていて、移動の方にだけGravityPathMotionをかけたいのですが、かけられていない点  
* 2点目はRecyclerViewの右側にあるImageViewから遷移した場合に、アニメーションが真ん中に寄ってしまっている。(たぶんchangeImageTransformと競合しておかしいことになっている？)

</p></details>  



### Navigational Transition  
Googleカレンダーなどで利用されている詳細画面に遷移するときのパターンです。   
これはカスタムTransitionでElevationを移動中に変えることによって、実現することができます。　　
![image](https://qiita-image-store.s3.amazonaws.com/0/27388/0674cc4c-00d0-a5a6-51ac-aa363cc50d07.png)   
(https://material.io/guidelines/patterns/navigational-transitions.html より)  

今回は新たにActivityを追加して、DetailActivityから詳細要素を選択して、移動できるようにしてみましょう。  
![navigational_transition](https://qiita-image-store.s3.amazonaws.com/0/27388/624a7456-9e7d-daaa-a235-9b5a82ff7351.gif)

<details><summary>ヒント</summary><p>

以下のようなカスタムTransitionを作成することで実装できます。  
https://github.com/nickbutcher/plaid/blob/master/app/src/main/java/io/plaidapp/ui/transitions/LiftOff.java  

注意点としてElevationは背景がついているViewにしかつけられないようなので、Viewに背景色をつけておいて、Transitionの対象としてください。  

答えになってしまいますが、以下に対応があるので躓いたら参考にしてください。  
https://github.com/takahirom/material-motion-hands-on/commit/feeaa1ccdabd081c9ab3552c3ccd3f6af129b8b8
</p></details>
