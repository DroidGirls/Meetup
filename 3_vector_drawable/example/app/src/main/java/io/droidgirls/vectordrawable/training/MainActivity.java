package io.droidgirls.vectordrawable.training;

import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
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
    }

}
