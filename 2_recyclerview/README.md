# RecyclerView 課題

http://www.slideshare.net/yanzm/droidgirls-recyclerview

```
    compile 'com.android.support:recyclerview-v7:25.1.0'
```


## LinearLayoutManager

* https://developer.android.com/reference/android/support/v7/widget/LinearLayoutManager.html

* ListView みたいなリストを作ってみよう
* orientation を horizontal にしてみよう
* reverseLayout にしてみよう
* stackFromEnd にしてみよう

<img src="device-2016-12-21-082246.png" width="270">



## GridLayoutManager 

* https://developer.android.com/reference/android/support/v7/widget/GridLayoutManager.html

* 3列のグリッドを作ってみよう

<img src="device-2016-12-21-082832.png" width="270">

* 10で割り切れる位置のアイテムは横いっぱいに広がるようにしてみよう
* ヒント : https://developer.android.com/reference/android/support/v7/widget/GridLayoutManager.html#setSpanSizeLookup(android.support.v7.widget.GridLayoutManager.SpanSizeLookup)

<img src="device-2016-12-21-083014.png" width="270">




## StaggeredGridLayoutManager

* https://developer.android.com/reference/android/support/v7/widget/StaggeredGridLayoutManager.html

* StaggeredGridLayoutManager を使ってみよう

<img src="device-2016-12-21-082636.png" width="270">






## DividerItemDecoration

* https://developer.android.com/reference/android/support/v7/widget/DividerItemDecoration.html

* DividerItemDecoration を使って区切り線を表示してみよう

<img src="device-2016-12-21-083636.png" width="270">

* [中級者向け] DividerItemDecoration の色を変えてみよう
** ヒント : https://developer.android.com/reference/android/support/v7/widget/DividerItemDecoration.html#setDrawable(android.graphics.drawable.Drawable)

<img src="device-2016-12-21-083712.png" width="270">




## Item Click

* https://developer.android.com/reference/android/support/design/widget/CoordinatorLayout.html

* リストのアイテムをタップしたときに Toast を表示してみよう

<img src="device-2016-12-21-084618.png" width="270">




## notify**

* https://developer.android.com/reference/android/support/v7/widget/RecyclerView.Adapter.html#notifyItemChanged(int)
* https://developer.android.com/reference/android/support/v7/widget/RecyclerView.Adapter.html#notifyItemInserted(int)
* https://developer.android.com/reference/android/support/v7/widget/RecyclerView.Adapter.html#notifyItemMoved(int, int)
* https://developer.android.com/reference/android/support/v7/widget/RecyclerView.Adapter.html#notifyItemRemoved(int)

* https://developer.android.com/reference/android/support/v7/widget/RecyclerView.Adapter.html#notifyItemRangeChanged(int, int)
* https://developer.android.com/reference/android/support/v7/widget/RecyclerView.Adapter.html#notifyItemRangeInserted(int, int)
* https://developer.android.com/reference/android/support/v7/widget/RecyclerView.Adapter.html#notifyItemRangeRemoved(int, int)

* ボタンをタップしたらランダムな位置にアイテムが追加されるようにしてみよう

<img src="device-2016-12-21-085052.png" width="270">




## Header & Footer

* getItemViewType() を Override して Header と Footer をつけてみよう

<img src="device-2016-12-21-185441.png" width="270">
<img src="device-2016-12-21-185503.png" width="270">


