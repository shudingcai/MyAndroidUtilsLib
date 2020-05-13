import android.app.Activity;
import android.app.Application;
import android.content.ComponentCallbacks;
import android.content.res.Configuration;
import android.util.DisplayMetrics;
import android.util.Log;

//屏幕适配方案。修改系统的缩放比率，Density,修改后系统的Density，系统在调用applyDimension，decodeResourceStream 会使用到这个值
/*
   px = dp * density
   可以看出，如果设计图宽为360dp，想要保证在所有设备计算得出的px值都正好是屏幕宽度的话，我们只能修改 density 的值。
   DisplayMetrics#density 就是上述的density
   DisplayMetrics#densityDpi 就是上述的dpi


public static float applyDimension(int unit, float value,
                                       DisplayMetrics metrics)
    {
        switch (unit) {
        case COMPLEX_UNIT_PX:
            return value;
        case COMPLEX_UNIT_DIP:
            return value * metrics.density;
        case COMPLEX_UNIT_SP:
            return value * metrics.scaledDensity;
        case COMPLEX_UNIT_PT:
            return value * metrics.xdpi * (1.0f/72);
        case COMPLEX_UNIT_IN:
            return value * metrics.xdpi;
        case COMPLEX_UNIT_MM:
            return value * metrics.xdpi * (1.0f/25.4f);
        }
        return 0;
   }


   public static Bitmap decodeResourceStream(Resources res, TypedValue value,
        InputStream is, Rect pad, Options opts) {
    if (opts == null) {
        opts = new Options();
    }
    if (opts.inDensity == 0 && value != null) {
        final int density = value.density;
        if (density == TypedValue.DENSITY_DEFAULT) {
            opts.inDensity = DisplayMetrics.DENSITY_DEFAULT;
        } else if (density != TypedValue.DENSITY_NONE) {
            opts.inDensity = density;
        }
    }
    if (opts.inTargetDensity == 0 && res != null) {
        opts.inTargetDensity = res.getDisplayMetrics().densityDpi;
    }
    return decodeStream(is, pad, opts);
}
 */

//使用方法： ScreanAdapterUtils.setCusomDensity(this,MyAplication.getApplication());
public class ScreanAdapterUtils {


    //三星pad宽度1280dp (是dp，是不px) ，SCREEN_WIDTH_DP根据不同的设计图修改，手机一般是360DP
    private final static int SCREEN_WIDTH_DP = 360;
    private static float sNoncompatDensity;
    private static float sNoncompatScaleDensity;


    public static void setCusomDensity(final Activity activity, final Application application) {
        final DisplayMetrics appDisplayMetrics = application.getResources().getDisplayMetrics();
        if (sNoncompatDensity == 0) {
            sNoncompatDensity = appDisplayMetrics.density;
            sNoncompatScaleDensity = appDisplayMetrics.scaledDensity;
            //监听系统改变字体的大小
            application.registerComponentCallbacks(new ComponentCallbacks() {
                @Override
                public void onConfigurationChanged(Configuration newConfig) {
                    if (newConfig != null && newConfig.fontScale > 0) {
                        sNoncompatScaleDensity = application.getResources().getDisplayMetrics().scaledDensity;
                        Log.e("tag", "sNoncompatScaleDensity:" + sNoncompatScaleDensity);
                    }
                }

                @Override
                public void onLowMemory() {

                }
            });
        }

        //根据参考的适配宽度 计算新的Density、ScaleDensity、DensityDpi
        float targetDensity = (float) appDisplayMetrics.widthPixels / SCREEN_WIDTH_DP;
        float targetScaleDensity = (float) targetDensity * (sNoncompatScaleDensity / sNoncompatDensity);
        int targetDensityDpi = (int) (160 * targetDensity);

        //修改全局的
        appDisplayMetrics.density = targetDensity;
        appDisplayMetrics.scaledDensity = targetScaleDensity;
        appDisplayMetrics.densityDpi = targetDensityDpi;

        //修改当前activity
        final DisplayMetrics activityDisplayMetrics = activity.getResources().getDisplayMetrics();
        activityDisplayMetrics.density = targetDensity;
        activityDisplayMetrics.scaledDensity = targetScaleDensity;
        activityDisplayMetrics.densityDpi = targetDensityDpi;

    }
}
