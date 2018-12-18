package tech.xiaosuo.com.swipelist.Views;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ListView;

public class SwipeListView extends ListView {

    private static  final String TAG = "SwipeListView";
    private static final int DELT_X = 5;
    float downX = -1;
    float downY = -1;
    SwipeLinearLayout mTouchItem;
    private boolean isSwiping = false;//the menuitemview is swiping

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
        Log.d(TAG," touch event : onInterceptTouchEvent");
        int action = ev.getAction();
        switch (action){
            case MotionEvent.ACTION_DOWN:
                downX = ev.getX();
                downY = ev.getY();
                int position = pointToPosition((int)downX,(int)downY);
                Log.d(TAG," item touch position: " + position + " first visible position " + getFirstVisiblePosition());
                View view  = getChildAt(position - getFirstVisiblePosition());
                if(view instanceof  SwipeLinearLayout){
                   if(mTouchItem != null && mTouchItem.isStatusOpend() && !isRangeOfView(mTouchItem,ev)){
                       Log.d(TAG," touch a new itemView,need hide pre itemView Right menu");
                       mTouchItem.hideRightMenu();
                       mTouchItem = null;
                       return true;
                   }
                }

/*                if(mTouchItem != null && mTouchItem.isStatusOpend()){
                    mTouchItem.hideRightMenu();
                }*/
                break;
            default:
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.d(TAG,"  touch event : dispatchTouchEvent");

        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        int deltX;
        int deltY;
        switch (action){
            case MotionEvent.ACTION_DOWN:
                downX = ev.getX();
                downY = ev.getY();
                int position = pointToPosition((int)downX,(int)downY);
                Log.d(TAG," item touch position: " + position + " first visible position " + getFirstVisiblePosition());
                View view  = getChildAt(position - getFirstVisiblePosition());
/*                if(mTouchItem != null && mTouchItem.isStatusOpend()){
                    Log.d(TAG," has click diff item,need close pre item right menu.");
                    mTouchItem.hideRightMenu();
                    mTouchItem = null;
                }*/
                if(mTouchItem == null){
                    mTouchItem = (SwipeLinearLayout)view;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                 deltX = (int)(ev.getX() - downX);
                 deltY = (int)(ev.getY() - downY);
                Log.d(TAG," ACTION_MOVE begin to swipe deltX:" + deltX +" deltY: " + deltY);

                if(Math.abs(deltX) > Math.abs(deltY)){
                    isSwiping = true;
                    /*if(mTouchItem!= null && mTouchItem.isStatusOpend()){
                        mTouchItem.swipeItemView(SwipeLinearLayout.ICON_DEFAULT_WIDTH - deltX);
                        return true;
                    }*/
                    if(mTouchItem != null){
                      //
                        mTouchItem.swipeItemView(deltX);
                   }
                }
                break;
            case MotionEvent.ACTION_UP:
                 deltX = (int)(ev.getX() - downX);
                 deltY = (int)(ev.getY() - downY);
                Log.d(TAG," ACTION_UP begin to swipe deltX:" + deltX +" deltY: " + deltY);
                if(mTouchItem != null && isSwiping){
                    if(Math.abs(deltX) >= SwipeLinearLayout.ICON_DEFAULT_WIDTH/2){
                            mTouchItem.swipeItemView(-SwipeLinearLayout.ICON_DEFAULT_WIDTH);
                    }else if(Math.abs(deltX) < SwipeLinearLayout.ICON_DEFAULT_WIDTH/2){
                            mTouchItem.hideRightMenu();//swipeItemView(-SwipeLinearLayout.ICON_DEFAULT_WIDTH);
                    }
                    isSwiping = false;
                }
                break;
        }
        return super.onTouchEvent(ev);
    }

    /**
     * check the touch point whether is in the range of the given view.
     * @param view
     * @param ev
     * @return
     */
    private  boolean isRangeOfView(View view,MotionEvent ev){

             int[]  location = new int[2];
             view.getLocationOnScreen(location);
             int x = location[0];
             int y = location[1];

             if(ev.getRawX() < x || ev.getRawX() > (x + view.getWidth()) || ev.getRawY() < y || ev.getRawY() > (y + view.getHeight())){
                 Log.d(TAG," the touch point is in another view");
                  return false;
             }
            Log.d(TAG," the touch point is in the same  view");
             return true;
    }
}
