package GuidePage.transformer;

import androidx.core.view.ViewCompat;
import android.view.View;


public class StackPageTransformer extends GuiPagePageTransformer {

    @Override
    public void handleInvisiblePage(View view, float position) {
    }

    @Override
    public void handleLeftPage(View view, float position) {
    }

    @Override
    public void handleRightPage(View view, float position) {
        ViewCompat.setTranslationX(view, -view.getWidth() * position);
    }

}