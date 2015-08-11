package com.hankkin.clearedittextdemo;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

/**
 * Created by Hankkin on 2015/8/11.
 */
public class ClearEditText extends EditText implements View.OnFocusChangeListener,TextWatcher{

    /*清除按钮图片*/
    private Drawable deleteDraw;

    public ClearEditText(Context context) {
        this(context, null);
    }

    public ClearEditText(Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.editTextStyle);
    }

    public ClearEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    /**
     * 初始化清除图片，设置监听事件
     * by Hankkin at:2015年8月11日 15:40:07
     */
    private void init(){
        deleteDraw = getCompoundDrawables()[2];
        if (deleteDraw==null){
            deleteDraw = getResources().getDrawable(R.drawable.delete);
        }
        deleteDraw.setBounds(0, 0, deleteDraw.getIntrinsicWidth(), deleteDraw.getIntrinsicHeight());
        setDeleteVisible(false);
        setOnFocusChangeListener(this);
        addTextChangedListener(this);
    }


    /**
     * 设置清除图片是否可见
     * by Hankkin at:2015年8月11日 16:17:24
     * @param isVisible
     */
    private void setDeleteVisible(boolean isVisible){
        Drawable drawable = isVisible?deleteDraw:null;
        setCompoundDrawables(getCompoundDrawables()[0],getCompoundDrawables()[1],drawable,getCompoundDrawables()[3]);
    }

    /**
     * 根据edittext的焦点变化改变图片的显示与否，输入框里的字符串长度如果大于0，显示，否则隐藏
     * by Hankkin at:2015年8月11日 16:32:47
     * @param v
     * @param hasFocus
     */
    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (hasFocus){
            setDeleteVisible(getText().length()>0);
        }else {
            setDeleteVisible(false);
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    /**
     * 这里我们不能设置edittext的点击事件，所以我们可以这样做一下：
     * 当我们按下的位置 是在（输入框的宽度-图标的宽度-图标距离右侧的宽度）和（输入框的宽度-图标到右侧的宽度）之间
     * 也就是说我们按下的位置处于图标的附近，设置输入框
     * by Hankkin  at:2015年8月11日 16:45:42
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (getCompoundDrawables()[2]!=null){
            if (event.getAction() == MotionEvent.ACTION_UP){
                boolean touchable = event.getX()>(getWidth()-getPaddingRight()-deleteDraw.getIntrinsicWidth())&&
                        (event.getX() < ((getWidth() - getPaddingRight())));
                if (touchable){
                    setText("");
                }

            }
        }
        return super.onTouchEvent(event);
    }

    /**
     * 当输入框里面内容发生变化的时候回调的方法
     * by Hankkin at:2015年8月11日 16:47:14
     * @param text
     * @param start
     * @param lengthBefore
     * @param lengthAfter
     */
    @Override
    public void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        setDeleteVisible(text.length() > 0);
    }
}
