# Design Support Library 課題

http://yaraki.github.io/slides/droidgirls-201611/#1

```
    compile 'com.android.support:design:25.0.1'
```

## TextInputLayout

* https://developer.android.com/reference/android/support/design/widget/TextInputLayout.html

* エラー機能を使ってみよう
* 文字カウント機能を使ってみよう
* パスワードの表示非表示切り替え機能を使ってみよう

<img src="device-2016-11-15-222026.png" width="270">


## Floating Action Button 

* https://developer.android.com/reference/android/support/design/widget/FloatingActionButton.html

* 右下にFABを表示してみよう
* FABをタップしたらToastを出してみよう
* fabSize を変えてみよう

<img src="device-2016-11-15-222905.png" width="270">




## Snackbar

* https://developer.android.com/reference/android/support/design/widget/Snackbar.html

* ボタンがタップされたらSnackbarを表示してみよう
* SnackbarにActionを追加してみよう

<img src="device-2016-11-16-094738.png" width="270">

<img src="device-2016-11-16-095028.png" width="270">






## AppBarLayout

* https://developer.android.com/reference/android/support/design/widget/AppBarLayout.html


* AppBarLayout + Toolbar を表示してみよう

<img src="device-2016-11-16-123521.png" width="270">

* NoActionBarなテーマが必要なので注意

```
    <style name="AppTheme.NoActionBar" parent="Theme.AppCompat.Light.NoActionBar">
        <!-- Customize your theme here. -->
        <item name="colorPrimary">@color/colorPrimary</item>
        <item name="colorPrimaryDark">@color/colorPrimaryDark</item>
        <item name="colorAccent">@color/colorAccent</item>
    </style>
```

* AppBarLayoutにダークテーマを指定するには

```
android:theme="@style/ThemeOverlay.AppCompat.Dark.ActionBar"
```


## CoordinatorLayout

* https://developer.android.com/reference/android/support/design/widget/CoordinatorLayout.html

* AppBarLayout を CoordinatorLayout の中にいれてスクロール時に消えるようにしてみよう

<img src="device-2016-11-16-125038.png" width="270">

* RecyclerView と組み合わせてみよう




## CollapsingToolbarLayout

* https://developer.android.com/reference/android/support/design/widget/CollapsingToolbarLayout.html

* CoordinatorLayout + AppBarLayout + CoordinatorLayout + ImageView + Toolbar で ActionBar が縮む動きを作ってみよう
* app:contentScrim で縮んだときの色を指定してみよう

<img src="device-2016-11-16-135736.png" width="270">

<img src="device-2016-11-16-135815.png" width="270">





## BottomSheet

* https://developer.android.com/reference/android/support/design/widget/BottomSheetDialog.html

* ボタンをタップしたらBottomSheetDialogが表示されるようにしてみよう

<img src="device-2016-11-16-143508.png" width="270">

* Presistent なBottomSheetを作ってみよう

<img src="device-2016-11-16-144910.png" width="270">
<img src="device-2016-11-16-144920.png" width="270">




## TabLayout

* https://developer.android.com/reference/android/support/design/widget/TabLayout.html

* 固定タブを表示してみよう

<img src="device-2016-11-16-095717.png" width="270">

* スクロールできるタブを表示してみよう

<img src="device-2016-11-16-095822.png" width="270">

* ViewPagerと組み合わせてみよう

<img src="device-2016-11-16-100512.png" width="270">




## BottomNavigationView

* https://developer.android.com/reference/android/support/design/widget/BottomNavigationView.html

* BottomNavigationViewを配置してみよう

<img src="device-2016-11-16-150835.png" width="270">




## NavigationView

* https://developer.android.com/reference/android/support/design/widget/NavigationView.html
* https://developer.android.com/reference/android/support/v4/widget/DrawerLayout.html
* https://developer.android.com/training/implementing-navigation/nav-drawer.html

* NavigationViewを配置してみよう

<img src="device-2016-11-16-152122.png" width="270">

<img src="device-2016-11-16-152141.png" width="270">


