package longtianlove.myapplication.util.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import longtianlove.myapplication.R;

/**
 * Created by long on 2016/12/25.
 */
public class CustomerView extends View {

    public CustomerView(Context context) {
        super(context);
    }

//    public CustomerView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
//        super(context, attrs, defStyleAttr, defStyleRes);
//    }

    public CustomerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public CustomerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray=getContext().obtainStyledAttributes(attrs, R.styleable.CustomerView);
         desireWidth=typedArray.getInt(R.styleable.CustomerView_width,200);
        typedArray.recycle();
    }
    int desireWidth;
    Paint paint=new Paint();
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode=MeasureSpec.getMode(widthMeasureSpec);
        int widthSize=MeasureSpec.getSize(widthMeasureSpec);
        int heightMode=MeasureSpec.getMode(heightMeasureSpec);
        int heightSize=MeasureSpec.getSize(heightMeasureSpec);
        int width=desireWidth;
        if(widthMode==MeasureSpec.EXACTLY){
            width=desireWidth;
        }else if(widthMode==MeasureSpec.AT_MOST){
            width=Math.min(desireWidth,widthSize);
        }else{
            width=desireWidth;
        }
        setMeasuredDimension(width,heightSize);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setColor(Color.RED);
        int x=getWidth()/2;
        int y=getHeight()/2;
        int radius=y;
        canvas.drawCircle(x,y,radius,paint);
    }
}
