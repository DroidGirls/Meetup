# VectorDrawable 課題

https://speakerdeck.com/konifar/vectordrawable-and-animatedvectordrawable

```
    compile "com.android.support:support-vector-drawable:25.1.0"
    compile "com.android.support:animated-vector-drawable:25.1.0"
```


## Vector Asset Studio

* https://developer.android.com/studio/write/vector-asset-studio.html

* material icon をインポートしてみよう
* local svg file をインポートしてみよう（cat.svgを用意してあります）

<img src="images/device-2017-01-25-vector_asset_studio.png" width="600">


## VectorDrawable

* https://developer.android.com/reference/android/graphics/drawable/VectorDrawable.html

* ImageViewに表示してみよう
* TextViewの左側に表示してみよう
* 色を変えてみよう
* 45度回転させてみよう
  * ヒント : `<group>` タグ
* TextViewにセットしたVectorDrawableにtintをあててみよう
  * ヒント : `DrawableCompat.setTint()` https://developer.android.com/reference/android/support/v4/graphics/drawable/DrawableCompat.html

<img src="images/device-2017-01-25-vector_drawable.jpg" width="270">


## AnimatedVectorDrawable

* https://developer.android.com/reference/android/graphics/drawable/AnimatedVectorDrawable.html

* ImageViewのアイコンを回転させてみよう
* ImageViewのアイコンの色を変えてみよう
* アニメーションのInterpolatorを変更してみよう
* アニメーションのdurationを変更してみよう

<img src="images/device-2017-01-25-animated_vector_drawable.gif" width="270">


## AndroidIconAnimator

* https://romannurik.github.io/AndroidIconAnimator/

* 回転するアニメーションを作ってみよう
* パスが変形するアニメーションを作ってみよう

<img src="images/device-2017-01-25-android_icon_animator.gif" width="600">


# 参考資料
## VectorDrawable
- SVGの仕様 https://www.w3.org/TR/SVG/
- VectorAssetStudio https://developer.android.com/studio/write/vector-asset-studio.html
- VectorDrawableのパフォーマンス最適化の記事 https://upday.github.io/blog/vector_drawables_optimisation/
- VectorDrawable対応まとめ http://qiita.com/konifar/items/bf581b8f23dea7b30f85
- VectorDrawableをPullRequest上でプレビューしてくれるChrome Extension https://github.com/jmatsu/vector-drawable-previewer
- SupportVectorDrawableの導入解説記事 https://android-developers.googleblog.com/2016/02/android-support-library-232.html
- SupportVectorDrawableがどう動くか内部を解説した記事 http://qiita.com/takahirom/items/c6f5a3204210d1e95c70

## AnimatedVectorDrawable
- AnimatedVectorDrawable https://developer.android.com/reference/android/graphics/drawable/AnimatedVectorDrawable.html
- PathMorphingできるようにPathを整形してくれるGUIツール https://github.com/bonnyfone/vectalign
- Web上でAnimatedVectorDrawableを作れるAndroidIconAnimator https://romannurik.github.io/AndroidIconAnimator/
- AnimatedVectorDrawableをふんだんに使って作られたアプリPlaid https://github.com/nickbutcher/plaid
