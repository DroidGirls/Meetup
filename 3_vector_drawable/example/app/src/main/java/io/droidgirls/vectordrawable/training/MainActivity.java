package io.droidgirls.vectordrawable.training;

import android.content.res.ColorStateList;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set tint color
        TextView txtDrawableTint = (TextView) findViewById(R.id.txt_drawable_tint);
        ColorStateList colors = ResourcesCompat.getColorStateList(getResources(), R.color.colorAccent, null);

        Drawable[] drawables = txtDrawableTint.getCompoundDrawables();
        if (drawables.length > 0) {
            Drawable drawable = DrawableCompat.wrap(drawables[0]);
            DrawableCompat.setTintList(drawable, colors);
            txtDrawableTint.setCompoundDrawables(drawable, null, null, null);
        }

        final ImageView imgPlay = (ImageView) findViewById(R.id.img_play);
        imgPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startAnimation(imgPlay, R.drawable.avd_play_to_pause_new, R.drawable.avd_pause_to_play_new);
            }
        });
    }

    private static void startAnimation(ImageView imageView,
                                       @DrawableRes int inactiveResId,
                                       @DrawableRes int activeResId) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // ImageViewがselect状態かどうかで判定してセットするAnimatedDrawableを決める
            int drawableResId = imageView.isSelected() ? activeResId : inactiveResId;

            // Drawableをセットする。setImageResource()だと動かないので注意
            Drawable drawable = imageView.getContext().getDrawable(drawableResId);
            imageView.setImageDrawable(drawable);

            if (drawable instanceof Animatable) {
                Animatable animatable = (Animatable) drawable;
                // アニメーション中だったらいったん止める
                if (animatable.isRunning()) {
                    animatable.stop();
                }
                // アニメーションを開始
                animatable.start();
            }

            // 次にタップしたら逆方向のアニメーションを実行するためにselect状態を切り替える
            imageView.setSelected(!imageView.isSelected());
        } else {
            int drawableResId = imageView.isSelected() ? activeResId : inactiveResId;
            imageView.setImageResource(drawableResId);
            imageView.setSelected(!imageView.isSelected());
        }
    }

}
