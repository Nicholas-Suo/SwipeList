package tech.xiaosuo.com.swipelist;

import android.util.DisplayMetrics;
import android.util.TypedValue;

public class Utils {
    /*
     dp2px
     */
    public static float dp2px(int unit,float dp,DisplayMetrics displayMetrics){
        float px = dp;
        px = TypedValue.applyDimension(unit,dp,displayMetrics);
        return px;
    }

    /*
 px2dp
 */
    public static float px2dp(int px,DisplayMetrics displayMetrics){
        int dp = 0;
        dp =  (int)(px/displayMetrics.density);
        return dp;
    }
}
