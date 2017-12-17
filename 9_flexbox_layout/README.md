# 資料

https://goo.gl/wWtpST

# 課題
`kadai` ディレクトリを Android Studio で開いてください。
課題1から3はウォーミングアップとして、FlexboxLayout に慣れるための易しめの問題にしています。初めて触れる人は課題1から、既に知っている人、慣れている人は課題4からやってみましょう。

--- 
## 課題1 ウォーミングアップ1
モジュール `warming-up`, レイアウト `warmingup1.xml`

FlexboxLayout を使って下のようなレイアウトに変更して、portrait, land 両方で期待通りか確認してみましょう。
![ウォーミングアップ1](art/warmingup1-portrait.png)
![ウォーミングアップ1](art/warmingup1-land.png)

--- 

## 課題2 ウォーミングアップ2
モジュール `warming-up`, レイアウト `warmingup2.xml` (実行する時は`MainActivity`の `setContentView`の引数を `warmingup2` に変更してください)

FlexboxLayout を使って下のようなレイアウトに変更してみましょう
![ウォーミングアップ2](art/warmingup2-portrait.png)
![ウォーミングアップ2](art/warmingup2-land.png)

--- 

## 課題3 ウォーミングアップ3
モジュール `warming-up`, レイアウト `warmingup3.xml` (実行する時は`MainActivity`の `setContentView`の引数を `warmingup3` に変更してください)

FlexboxLayout を使って下のようなレイアウトに変更してみましょう
![ウォーミングアップ3](art/warmingup3-portrait.png)
![ウォーミングアップ3](art/warmingup3-land.png)

--- 

## 課題4 LinearLayout を FlexboxLayout を使って書き換えてみよう
モジュール `kadai4-app` を Android Studio から実行して、`1`のTextViewをクリックします。現れたダイアログは現在 LinearLayout を使って書かれています(
kadai/app/src/main/res/layout/fragment_flex_item_edit.xml)。それを FlexboxLayout を使用して見た目が良いように書き換えてみましょう。その時下記の点を守って配置してみましょう。
縦向きの画面で確認できたら、画面を横向きにしても条件を守れているかも確認しましょう。
- `Wrap Before`の CheckBox はダイアログの一番左に配置されること
- `Flex Basis Percent`と`Width`は同じ行には配置されないこと
- 入力欄の左端と右端は全ての行で揃うように

---
ヒント: fragment_flex_item_edit.xml のレイアウトファイルを修正するだけで実現できます

flexbox-layout の GitHub レポジトリに FlexboxLayout を使用したバージョンのファイルがありますが、練習のために見ないでやってみましょう。


![FlexboxLayout 使用前](art/flexboxlayout_before.png)
<center>FlexboxLayout使用前</center>

***

![FlexboxLayout 使用後 Portrait](art/flexboxlayout_after_portrait.png)
<center>FlexboxLayout 使用後 Portrait</center>

***

![FlexboxLayout 使用後 Land](art/flexboxlayout_after_land.png)
<center>FlexboxLayout 使用後 Land</center>

--- 

## 課題5 FlexboxLayoutManager を使ってみよう (早く終わった人いれば)
`kadai5-app` モジュールを実行してみましょう。RecyclerView と LinearLayoutManager を使って猫画像が見れます。

![LinearLayoutManager 猫画像](art/kadai5-before.png)
<center>LinearLayoutManager 使用時</center>

これを FlexboxLayoutManager を使用して下記のような見た目にしてみましょう。

ヒント : 今回は Kotlin コードの変更だけで実現できます。

![LinearLayoutManager 猫画像](art/kadai5-after-portrait.png)
<center>FlexboxLayoutManager 使用時 Portrait</center>

![LinearLayoutManager 猫画像](art/kadai5-after-land.png)
<center>FlexboxLayoutManager 使用時 Land</center>
