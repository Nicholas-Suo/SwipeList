package tech.xiaosuo.com.swipelist.Views;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Scroller;
import android.widget.TextView;

import tech.xiaosuo.com.swipelist.Utils;

class SwipeRelativeLayout extends LinearLayout {
    private static final String TAG = "SwipeRelativeLayout";
    Scroller scroller;
    private static final int CHILD_TEXT_VIEW = 0;
    private static final int CHILD_DELETE_IMG = 1; //this value is from swipe_item.xml
    private static final int ICON_DEFAULT_WIDTH = 100;
    ImageView deleteImageView;
    TextView itemTextView;
    private int iconWidth = ICON_DEFAULT_WIDTH;



    public SwipeRelativeLayout(Context context) {
        super(context);
        init();
    }

    public SwipeRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SwipeRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
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
    public void Swipe(){
        scroller.startScroll(0,0,-iconWidth,0);
        Log.d(TAG,"  is Swiping");
        postInvalidate();
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        Log.d(TAG," begin computeScroll");


        if(scroller.computeScrollOffset()){
            Log.d(TAG," computeScroll finish");
            if(deleteImageView != null){
                Log.d(TAG," begin update layout textW" + itemTextView.getWidth() + "  iconWidth " + iconWidth);
                itemTextView.layout(-iconWidth,getTop(),itemTextView.getWidth() - iconWidth,itemTextView.getBottom());//px?
                deleteImageView.layout(itemTextView.getWidth()-iconWidth, getTop(),getWidth(), deleteImageView.getBottom());//itemTextView.getWidth() + iconWidth
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
          // iconWidth = (int)Utils.px2dp(iconWidth,getResources().getDisplayMetrics());
            LayoutParams params = new LayoutParams(iconWidth,height);
            deleteImageView.setLayoutParams(params);
        }
    }


}
