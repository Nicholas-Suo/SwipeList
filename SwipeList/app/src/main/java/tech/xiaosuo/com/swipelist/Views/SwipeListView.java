package tech.xiaosuo.com.swipelist.Views;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ListView;

public class SwipeListView extends ListView {

    private static  final String TAG = "SwipeListView";
    float downX = -1;
    float downY = -1;
    SwipeRelativeLayout mTouchItem;
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
                mTouchItem = (SwipeRelativeLayout)getChildAt(position - getFirstVisiblePosition());
                break;
            case MotionEvent.ACTION_MOVE:
                float deltX = ev.getX() - downX;
                float deltY = ev.getY() - downY;
                Log.d(TAG," begin to swipe");
                mTouchItem.Swipe();
                break;
        }
        return super.onTouchEvent(ev);
    }
}
