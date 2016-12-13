package longtianlove.myapplication.util.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;


/**
 * Created by xingerkang on 16/6/7.
 * 这个控件第一个child必须是TextView
 */
public class TextAnimationLayout extends RelativeLayout {
    private TextView child;
    private String textHint;
    private float minTextSize;
    private float orgTextSize;
    private int textLeft;
    private int minTextTop;
    private int orgTextTop;
    private int currentTextTop;
    private int orgTextHight;
    private int minTextHight;
    private Paint hintPaint = new Paint();
    private ValueAnimator animator;

    public TextAnimationLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        initTextParam();
        initChildFocus();
    }

    private void initTextParam(){
        child = (TextView) getChildAt(0);
        textHint = child.getHint().toString();
        Rect hintRect = new Rect();
        hintPaint.setColor(child.getCurrentHintTextColor());
        hintPaint.setAntiAlias(true);
        orgTextSize = child.getTextSize();
        hintPaint.setTextSize(orgTextSize);
        child.getPaint().getTextBounds(textHint, 0, textHint.length(), hintRect);
        orgTextHight = hintRect.height();
        child.setHint("");
        minTextSize = getPaddingTop()*7/10;//Hint的最小高度就是TextAnimationLayout的padding
        minTextHight = getFontHeight(minTextSize);
        paddingTop = getPaddingTop();
        textLeft = paddingLeft = getPaddingLeft();
        paddingBottom = getPaddingBottom();
        paddingRight = getPaddingRight();
        setPadding(paddingLeft,0,paddingRight,0);
        child.setPadding(0, paddingTop / 2 * 3, 0, paddingBottom/2);
    }

    private int paddingTop;
    private int paddingLeft;
    private int paddingBottom;
    private int paddingRight;

    private boolean focus;

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    public int getFontHeight(float fontSize) {
        Paint paint = new Paint();
        paint.setTextSize(fontSize);
        Paint.FontMetrics fm = paint.getFontMetrics();
        return (int) Math.ceil(fm.descent - fm.top) + 2;
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        if(currentTextTop==0){
            currentTextTop = orgTextTop = getHeight()/2+orgTextHight/2;
            minTextTop = paddingTop/2+minTextHight/2+2;
            if(TextUtils.isEmpty(child.getText().toString())){
                currentTextTop = orgTextTop;
                hintPaint.setTextSize(orgTextSize);
            }else {
                currentTextTop = minTextTop;
                hintPaint.setTextSize(minTextSize);
            }
        }
        canvas.drawText(textHint, textLeft, currentTextTop, hintPaint);
    }

    private void initChildFocus(){
        animator = new ValueAnimator();
        animator.setDuration(400);
        animator.setFloatValues(0f, 1f);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float val = (float) animation.getAnimatedValue();
                if (focus) {
                    topAndSmall(val);
                } else {
                    centerAndBig(val);
                }
            }
        });
        child.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean f) {
                focus = f;
                boolean textIsNull = TextUtils.isEmpty(child.getText().toString());
                if (textIsNull) {
                    animator.start();
                }
                if(focus){
                    if(delView!=null)delView.setVisibility(textIsNull? View.INVISIBLE: View.VISIBLE);
                    if(focusListener != null) focusListener.onFocus(TextAnimationLayout.this);
                }else{
                    if(delView!=null)delView.setVisibility(View.INVISIBLE);
                }
            }
        });
        //文本条右侧删除按钮 可能没有
        if(delView!=null){
            delView.setVisibility(View.INVISIBLE);
            child.addTextChangedListener(new TextWatcher() {
                private String bef;

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    bef = s.toString();
                }

                @Override
                public void afterTextChanged(Editable s) {
                    String after = s.toString();
                    if (TextUtils.isEmpty(bef) && !TextUtils.isEmpty(after)) {
                        if(delView!=null)delView.setVisibility(View.VISIBLE);
                    }
                    if (!TextUtils.isEmpty(bef) && TextUtils.isEmpty(after)) {
                        if(delView!=null)delView.setVisibility(View.INVISIBLE);
                    }
                }
            });
        }
        setOnClickListener(orgOnClickListener);
    }
    private OnClickListener onClickListener;
    private OnClickListener orgOnClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            child.setFocusable(true);
            child.setFocusableInTouchMode(true);
            child.requestFocus();
            if (onClickListener != null) {
                onClickListener.onClick(v);
            }
        }
    };
    @Override
    public void setOnClickListener(OnClickListener l) {
        if(l!=orgOnClickListener){
            this.onClickListener = l;
        }else{
            super.setOnClickListener(l);
        }
    }

    private void topAndSmall(float val){
        hintPaint.setTextSize(orgTextSize - (orgTextSize-minTextSize)*val);
        currentTextTop = (int)(orgTextTop-(orgTextTop-minTextTop)*val);
        postInvalidate();
    }
    private void centerAndBig(float val){
        hintPaint.setTextSize(minTextSize + (orgTextSize-minTextSize)*val);
        currentTextTop = (int)(minTextTop+(orgTextTop-minTextTop)*val);
        postInvalidate();
    }




    public interface FocusListener{
        public void onFocus(View view);
    }
    private FocusListener focusListener;
    public void setFocus(FocusListener listener){
        this.focusListener = listener;
    }

    /**
     * 设置删除控件ID
     */
    private View delView;
    public void setDelView(int delViewId){
        delView = findViewById(delViewId);
    }


}
