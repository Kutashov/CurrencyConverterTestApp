package ru.alexandrkutashov.currencyconvertertestapp.ui.main.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by Alexandr on 14.07.2017.
 */

public class ClearableEditText extends AppCompatEditText {

    private static final int DRAWABLE_RIGHT = 2;

    public ClearableEditText(Context context) {
        super(context);
    }

    public ClearableEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ClearableEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void inflateClearBtn(boolean visible) {
        if (visible) {
            if (getCompoundDrawables()[DRAWABLE_RIGHT] == null || !getCompoundDrawables()[DRAWABLE_RIGHT].isVisible()) {
                Drawable x = getResources().getDrawable(android.R.drawable.ic_menu_close_clear_cancel);
                x.setBounds(0, 0, x.getIntrinsicWidth(), x.getIntrinsicHeight());
                setCompoundDrawables(null, null, x, null);
            }
        } else {
            setCompoundDrawables(null, null, null, null);
        }

    }

    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter);
        inflateClearBtn(text.length() > 0);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if(event.getAction() == MotionEvent.ACTION_UP && getCompoundDrawables()[DRAWABLE_RIGHT] != null) {
            if(event.getRawX() >= (getRight() - getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                setText("");
                return true;
            }
        }
        return super.onTouchEvent(event);
    }
}
