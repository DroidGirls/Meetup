## 資料

https://github.com/DroidGirls/Meetup/blob/master/13_mklit/mlkit.pdf

## 課題1

firebase console https://console.firebase.google.com/ で新しいプロジェクトを作る。

* プロジェクト名 : 好きな名前（例 ML Kit Sample）
* 国 / 地域 : 日本


## 課題2

Android Studio で新しいプロジェクトを作り、firebase プロジェクトに Android アプリを追加する。
（firebase で設定の手順が表示されるので、 google-services.json の設定や dependency の設定もやる）

* Application name : 好きな名前（例 ML Kit Sample）
* kotlin を有効にすること

ヒント :
1. firebase console で Project Overview に行く
2. 「Android アプリ に Firebase を追加」を選択する


## 課題3

Meetup/13_mklit/ の以下のファイルをコピーしてくる。

既存の MainActivity と同じところに配置し（MainActivity は置き換え）、必要に応じてパッケージを直す。
* MainActivity.kt
* ImagePickFragment.kt

res/layout/ に配置する
* activity_main.xml
* fragment_image_pick.xml

res/values/strings.xml

```xml
    <string name="button_change_image">Change Image</string>
    <string name="button_detect">Detect</string>
```

AndroidManifest に Permission を追加する。

```xml
    <uses-permission android:name="android.permission.INTERNET"/>
    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
```


dependency に

```
implementation 'com.google.firebase:firebase-ml-vision:16.0.0'
```
を追加する。


## 課題4 : テキスト認識

MainActivity.kt の TODO: 2 に以下のテキスト認識の実装を行い、ログを確認する。

```kotlin
detectButton.isEnabled = false

val image = FirebaseVisionImage.fromBitmap(bitmap)

FirebaseVision.getInstance()
        .visionTextDetector
        .detectInImage(image)
        .addOnSuccessListener { texts ->
            detectButton.isEnabled = true

            for (block in texts.blocks) {
                for (line in block.lines) {
                    for (element in line.elements) {
                        Log.d("MainActivity", "${element.text}, ${element.boundingBox}")
                    }
                }
            }
        }
        .addOnFailureListener { e ->
            detectButton.isEnabled = true
            e.printStackTrace()
        }
```

## 課題5

検出範囲を写真にオーバーレイ表示する。

Meetup/13_mklit/ の

* GraphicData.kt
* GraphicOverlay.kt

をコピーしてくる。

activity_main.xml に GraphicOverlay を追加する

```xml
...

  <....GraphicOverlay
      android:id="@+id/overlay"
      android:layout_width="0dp"
      android:layout_height="0dp"
      app:layout_constraintBottom_toBottomOf="@id/imageView"
      app:layout_constraintEnd_toEndOf="@id/imageView"
      app:layout_constraintStart_toStartOf="@id/imageView"
      app:layout_constraintTop_toTopOf="@id/imageView" />

</android.support.constraint.ConstraintLayout>
```

MainActivity.kt の文字検出成功時の処理に追記する。

ヒント）

```kotlin
detectButton.isEnabled = true

overlay.clear()

for (block in texts.blocks) {
    for (line in block.lines) {
        for (element in line.elements) {
            overlay.add(GraphicData(
                    element.text,
                    element.boundingBox ?: Rect(),
                    resources,
                    Color.RED))
            Log.d("MainActivity", "${element.text}, ${element.boundingBox}")
        }
    }
}
```

## 課題6 : 顔検出

https://firebase.google.com/docs/ml-kit/android/detect-faces

detectorSpinner に顔検出の項目を追加する。

ヒント)

```kotlin
val detectors = listOf(
        TEXT_DETECTION,
        FACE_DETECTION                
)
```

```kotlin
FACE_DETECTION -> {
    detectButton.isEnabled = false

    val image = FirebaseVisionImage.fromBitmap(bitmap)

    FirebaseVision.getInstance()
            .visionFaceDetector
            .detectInImage(image)
            .addOnSuccessListener { faces ->
                detectButton.isEnabled = true

                overlay.clear()

                for (face in faces) {
                    overlay.add(GraphicData(
                            "",
                            face.boundingBox ?: Rect(),
                            resources,
                            Color.RED))
                    Log.d("MainActivity", "${face.smilingProbability}, ${face.boundingBox}")
                }
            }
            .addOnFailureListener { e ->
                detectButton.isEnabled = true
                e.printStackTrace()
            }
}
```

option を指定する。

* ModeType では速度優先か正確性優先か指定できる。
* LandmarkType では eyes, ears, nose, cheeks, mouth の位置を判定するか指定できる。
* ClassificationType では笑顔かどうか、目が空いているかどうかを判定するか指定できる。
* MinFaceSize は検出する最小の顔の大きさを画像に対する比率で指定する。
* TrackingEnabled は顔に id を割り振るかどうかを指定できる。

```kotlin
val options = FirebaseVisionFaceDetectorOptions.Builder()
        .setModeType(FirebaseVisionFaceDetectorOptions.ACCURATE_MODE)
        .setLandmarkType(FirebaseVisionFaceDetectorOptions.ALL_LANDMARKS)
        .setClassificationType(FirebaseVisionFaceDetectorOptions.ALL_CLASSIFICATIONS)
        .setMinFaceSize(0.15f)
        .setTrackingEnabled(true)
        .build()

FirebaseVision.getInstance()
        .getVisionFaceDetector(options)
        .detectInImage(image)
        ...
```


## 課題7 : バーコードスキャン

https://firebase.google.com/docs/ml-kit/android/read-barcodes

detectorSpinner にバーコードスキャンの項目を追加する。

```kotlin
BARCODE_DETECTION -> {
    detectButton.isEnabled = false

    val image = FirebaseVisionImage.fromBitmap(bitmap)

    FirebaseVision.getInstance()
            .visionBarcodeDetector
            .detectInImage(image)
            .addOnSuccessListener { barcodes ->
                detectButton.isEnabled = true

                overlay.clear()

                for (barcode in barcodes) {
                    overlay.add(GraphicData(
                            barcode.rawValue ?: "",
                            barcode.boundingBox ?: Rect(),
                            resources,
                            Color.RED))
                    Log.d("MainActivity", "${barcode.rawValue}, ${barcode.boundingBox}")
                }
            }
            .addOnFailureListener { e ->
                detectButton.isEnabled = true
                e.printStackTrace()
            }
}
```

option を指定する。option では検出するバーコードの種類を制限できる。

```kotlin

val options = FirebaseVisionBarcodeDetectorOptions.Builder()
        .setBarcodeFormats(
                FirebaseVisionBarcode.FORMAT_EAN_8,
                FirebaseVisionBarcode.FORMAT_EAN_13)
        .build()

FirebaseVision.getInstance()
        .getVisionBarcodeDetector(options)
        .detectInImage(image)
        ...
```



## 課題8 : 画像のラベル付け

https://firebase.google.com/docs/ml-kit/android/label-images

dependency に
```
implementation 'com.google.firebase:firebase-ml-vision-image-label-model:15.0.0'
```
を追加する。

detectorSpinner に画像のラベル付けの項目を追加する。


```kotlin
LABELING -> {
    detectButton.isEnabled = false

    val image = FirebaseVisionImage.fromBitmap(bitmap)

    FirebaseVision.getInstance()
            .visionLabelDetector
            .detectInImage(image)
            .addOnSuccessListener { labels ->
                detectButton.isEnabled = true

                overlay.clear()

                for (label in labels) {
                    Log.d("MainActivity", "${label.label}, ${label.confidence}")
                }
            }
            .addOnFailureListener { e ->
                detectButton.isEnabled = true
                e.printStackTrace()
            }
}
```

option を指定する。

デフォルトでは確率の高い方から 10 個出力されるが、ConfidenceThreshold を指定すると、
それ以上の確率のものだけが出力される。


```kotlin
val options = FirebaseVisionLabelDetectorOptions.Builder()
        .setConfidenceThreshold(0.8f)
        .build()

FirebaseVision.getInstance()
        .getVisionLabelDetector(options)
        .detectInImage(image)
```



## 課題9 : 画像のラベル付け Cloud API

Cloud API は Plan を Blaze に変えないといけないため、課金設定を行える人だけやってみる。

https://console.firebase.google.com/ の左下のメニューから Plan を Blaze に変える。


detectorSpinner に Cloud API での画像のラベル付けの項目を追加する。

```kotlin
CLOUD_LABELING -> {
    detectButton.isEnabled = false

    val image = FirebaseVisionImage.fromBitmap(bitmap)

    FirebaseVision.getInstance()
            .visionCloudLabelDetector
            .detectInImage(image)
            .addOnSuccessListener { labels ->
                detectButton.isEnabled = true

                overlay.clear()

                for (label in labels) {
                    Log.d("MainActivity", "${label.label}, ${label.confidence}")
                }
            }
            .addOnFailureListener { e ->
                detectButton.isEnabled = true
                e.printStackTrace()
            }
}
```

option を指定する。

```kotlin
val options = FirebaseVisionCloudDetectorOptions.Builder()
        .setModelType(FirebaseVisionCloudDetectorOptions.LATEST_MODEL)
        .setMaxResults(15)
        .build()

FirebaseVision.getInstance()
        .getVisionCloudLabelDetector(options)
        .detectInImage(image)
```

## 課題10 : テキスト認識 Cloud API

Cloud API は Plan を Blaze に変えないといけないため、課金設定を行える人だけやってみる。

https://console.firebase.google.com/ の左下のメニューから Plan を Blaze に変える。

detectorSpinner に Cloud API でのテキスト認識の項目を追加する。

```kotlin
CLOUD_TEXT_DETECTION -> {
    detectButton.isEnabled = false

    val image = FirebaseVisionImage.fromBitmap(bitmap)

    FirebaseVision.getInstance()
            .visionCloudTextDetector
            .detectInImage(image)
            .addOnSuccessListener { cloudText ->
                detectButton.isEnabled = true

                overlay.clear()

                for (page in cloudText.pages) {
                    for (block in page.blocks) {
                        for (paragraph in block.paragraphs) {
                            for (word in paragraph.words) {
                                val text = word.symbols.joinToString(separator = "") { it.text }
                                overlay.add(GraphicData(
                                        text,
                                        word.boundingBox ?: Rect(),
                                        resources,
                                        Color.RED))
                                Log.d("MainActivity", "$text, ${word.boundingBox}")
                            }
                        }
                    }
                }
            }
            .addOnFailureListener { e ->
                detectButton.isEnabled = true
                e.printStackTrace()
            }
}
```

option を指定する。

```kotlin
val options = FirebaseVisionCloudDetectorOptions.Builder()
        .setModelType(FirebaseVisionCloudDetectorOptions.LATEST_MODEL)
        .setMaxResults(15)
        .build()

FirebaseVision.getInstance()
        .getVisionCloudTextDetector(options)
        .detectInImage(image)
        ...
```



## 課題11 : ランドマーク認識 Cloud API

https://firebase.google.com/docs/ml-kit/android/recognize-landmarks

Cloud API は Plan を Blaze に変えないといけないため、課金設定を行える人だけやってみる。

https://console.firebase.google.com/ の左下のメニューから Plan を Blaze に変える。


detectorSpinner にランドマーク認識の項目を追加する。

```kotlin
CLOUD_LANDMARK -> {
    detectButton.isEnabled = false

    val image = FirebaseVisionImage.fromBitmap(bitmap)

    FirebaseVision.getInstance()
            .visionCloudLandmarkDetector
            .detectInImage(image)
            .addOnSuccessListener { landmarks ->
                detectButton.isEnabled = true

                overlay.clear()

                for (landmark in landmarks) {
                    overlay.add(GraphicData(
                            landmark.landmark ?: "",
                            landmark.boundingBox ?: Rect(),
                            resources,
                            Color.RED))

                    Log.d("MainActivity", "${landmark.landmark}, ${landmark.confidence}, ${landmark.boundingBox}")
                }
            }
            .addOnFailureListener { e ->
                detectButton.isEnabled = true
                e.printStackTrace()
            }
}
```

option を指定する。

```kotlin
val options = FirebaseVisionCloudDetectorOptions.Builder()
        .setModelType(FirebaseVisionCloudDetectorOptions.LATEST_MODEL)
        .setMaxResults(15)
        .build()

FirebaseVision.getInstance()
        .getVisionCloudLandmarkDetector(options)
        .detectInImage(image)
        .addOnSuccessListener { landmarks ->
```
