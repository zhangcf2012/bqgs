package com.libs.share;

import com.libs.utils.AndroidUtils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;


public class LayoutEx extends ViewGroup {
    private static int VIEW_MARGIN = 10;
    private Context mContext;
    
    public LayoutEx(Context context) {
        super(context);
        mContext = context;
    }
    
    public LayoutEx(Context context, AttributeSet ast) {
        super(context, ast);
        mContext = context;
    }
    
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        for (int index = 0; index < getChildCount(); index++) {
            final View child = getChildAt(index);
            // measure
            child.measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
    
    private void measureMargin(int width, int childWidth, int count) {
        if (count * childWidth < width) { // 所有的子类宽度之合小于屏幕宽度
            double margin = (width - count * childWidth) / (count + 1);
            if (margin > VIEW_MARGIN) {
                VIEW_MARGIN = (int) margin;
            } else {
                VIEW_MARGIN = (width - (count - 1) * childWidth) / count;
            }
        } else {// 所有的子类宽度之合大于屏幕宽度
            int scount = width / childWidth;// 取出合适的个数
            if (scount > 4) {
                scount = 4;
            }
            double margin = (width - scount * childWidth) / (scount + 1);
            if (margin > VIEW_MARGIN) {
                VIEW_MARGIN = (int) margin;
            } else {
                VIEW_MARGIN = (width - scount * childWidth) / scount;
            }
        }
    }
    
    @SuppressLint("DrawAllocation")
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int botom) {
        
        final int count = getChildCount();
        int row = 0;// which row lay you view relative to parent
        int lengthX = left; // right position of child relative to parent
        int lengthY = top; // bottom position of child relative to parent
        for (int i = 0; i < count; i++) {
            
            final View child = this.getChildAt(i);
            int width = child.getMeasuredWidth();
            int height = child.getMeasuredHeight();
            if (right != 0 && i == 0 && VIEW_MARGIN == 10) {
                measureMargin(right, width, count);
            }
            lengthX += width + VIEW_MARGIN;
            lengthY = row * (height + 10) + height + top;
            
            // if it can't drawing on a same line , skip to next line
            if (lengthX > right) {
                lengthX = width + VIEW_MARGIN + left;
                row++;
                lengthY = row * (height + 10) + height + top;
                
            }
            child.layout(lengthX - width, lengthY - height, lengthX, lengthY);
            
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, lengthY + 20);
            params.topMargin = AndroidUtils.dip2px(mContext, 10);
            params.gravity = Gravity.CENTER_HORIZONTAL;
            this.setLayoutParams(params);
            
        }
        
    }
    
}
