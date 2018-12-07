package tech.xiaosuo.com.swipelist.Views;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Scroller;
import android.widget.TextView;

class SwipeLinearLayout extends LinearLayout {
    private static final String TAG = "SwipeRelativeLayout";
    Scroller scroller;
    private static final int CHILD_TEXT_VIEW = 0;
    private static final int CHILD_DELETE_IMG = 1; //this value is from swipe_item.xml
    private static final int ICON_DEFAULT_WIDTH = 56;
    ImageView deleteImageView;
    TextView itemTextView;
    private int iconWidth = ICON_DEFAULT_WIDTH;
    private int mTouchMoveDistance = 0;
    private static final int STATUS_NONE = 0;
   // private static final int STATUS_ = 0;
    private int swipeStatus ;

    public SwipeLinearLayout(Context context) {
        super(context);
        init();
    }

    public SwipeLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SwipeLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
    /**
     * listview set item width and height for icon delete .,before init();
     * because we can not get the icon w,h, when the view is not visible.
     */
    public void setIconWidth(int width){
        iconWidth = width;
    }

    /**
     * init the data
     */
    private void init(){
        Log.d(TAG," layout begin init");
        if(scroller == null){
            scroller = new Scroller(getContext());
        }
    }
    /*
     * slide the item,then relayout.
     */
    public void Swipe(int deltX){

        Log.d(TAG,"  is Swiping Math.abs(deltX) " + Math.abs(deltX));
        if(Math.abs(deltX) > ICON_DEFAULT_WIDTH  && deltX < 0){// deltx<0 --> swipe left,and the width can not larger than icom width.
            Log.d(TAG,"  is Swiping the deltX is larger ");
                mTouchMoveDistance =  -ICON_DEFAULT_WIDTH;
        }else if(deltX > 0){//deltX > 0 ,swipe right when no up move touch,should close the icon.menu.
            mTouchMoveDistance = 0;
        }else{
            mTouchMoveDistance = deltX;
        }
        Log.d(TAG,"  is Swiping mTouchMoveDistance " + mTouchMoveDistance);
        if(Math.abs(mTouchMoveDistance) <= iconWidth){
            scroller.startScroll(0,0,mTouchMoveDistance,0);
            postInvalidate();
        }

    }

    @Override
    public void computeScroll() {
        super.computeScroll();
       // Log.d(TAG," begin computeScroll");
        if(scroller.computeScrollOffset()){
         //   Log.d(TAG," computeScroll finish");
            if(deleteImageView != null){
                Log.d(TAG," begin update layout textW" + itemTextView.getWidth() + "  iconWidth " + iconWidth + " mTouchMoveDistance: " + mTouchMoveDistance);
                itemTextView.layout(mTouchMoveDistance,itemTextView.getTop(),itemTextView.getWidth() + mTouchMoveDistance,itemTextView.getBottom());//px?
                deleteImageView.layout(itemTextView.getWidth() + mTouchMoveDistance, itemTextView.getTop(),getWidth(), deleteImageView.getBottom());
/*                itemTextView.layout(-iconWidth,itemTextView.getTop(),itemTextView.getWidth() - iconWidth,itemTextView.getBottom());//px?
                deleteImageView.layout(itemTextView.getWidth()-iconWidth, itemTextView.getTop(),getWidth(), deleteImageView.getBottom());*/
            }
        }
        postInvalidate();
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        int count = getChildCount();
        Log.d(TAG," onLayout the layout child number: " + count);
        if(CHILD_DELETE_IMG > count){
            Log.d(TAG,"the value is larger than count,return");
            return;
        }

        itemTextView = (TextView)getChildAt(CHILD_TEXT_VIEW);
        deleteImageView = (ImageView)getChildAt(CHILD_DELETE_IMG);
        Log.d(TAG," deleteImageView width " + deleteImageView.getWidth() + " measured width " + deleteImageView.getMeasuredWidth());
        if(deleteImageView != null && itemTextView != null && deleteImageView.getWidth() == 0){//only init once, if the deleteImage has width and height ,no need set value.
           int height = itemTextView.getHeight();
            Log.d(TAG," deleteImageView height " + height);
          // iconWidth = (int)Utils.px2dp(iconWidth,getResources().getDisplayMetrics());
           LayoutParams params = new LayoutParams(ICON_DEFAULT_WIDTH,ICON_DEFAULT_WIDTH);
          //  LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
          //  params.gravity = Gravity.CENTER_VERTICAL;
            deleteImageView.setLayoutParams(params);
        }
    }


}
