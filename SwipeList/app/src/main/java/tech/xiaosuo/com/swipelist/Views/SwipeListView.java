package tech.xiaosuo.com.swipelist.Views;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ListView;

public class SwipeListView extends ListView {

    private static  final String TAG = "SwipeListView";
    private static final int DELT_X = 5;
    float downX = -1;
    float downY = -1;
    SwipeLinearLayout mTouchItem;
    public SwipeListView(Context context) {
        super(context);
    }

    public SwipeListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SwipeListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.d(TAG," onInterceptTouchEvent");
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        int action = ev.getAction();

        switch (action){
            case MotionEvent.ACTION_DOWN:
                downX = ev.getX();
                downY = ev.getY();
                int position = pointToPosition((int)downX,(int)downY);
                Log.d(TAG," item touch position: " + position + " first visible position " + getFirstVisiblePosition());
                mTouchItem = (SwipeLinearLayout)getChildAt(position - getFirstVisiblePosition());
                break;
            case MotionEvent.ACTION_MOVE:
                int deltX = (int)(ev.getX() - downX);
                int deltY = (int)(ev.getY() - downY);
                Log.d(TAG," begin to swipe deltX:" + deltX +" deltY: " + deltY);
                if(Math.abs(deltX) > Math.abs(deltY)){
                    if(mTouchItem != null){
                        mTouchItem.Swipe(deltX);
                   }
                }
                break;
            case MotionEvent.ACTION_UP:

                break;
        }
        return super.onTouchEvent(ev);
    }
}
