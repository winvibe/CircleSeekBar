package example.bullyboo.ru.myapplication;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;
import android.support.annotation.StyleRes;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

/**
 * by BullyBoo on 01.12.2017.
 */

class CircleProgressDrawer {

    private static final int GRAY_COLOR = 0xffebebed;
    private static final int BLUE_COLOR = 0xff007eff;

    private static final int DEFAULT_MAX_VALUE = 100;
    private static final int DEFAULT_MIN_VALUE = 0;
    private static final int DEFAULT_VALUE = 50;

    private float backgroundCircleLineWidth;
    private float progressCircleLineWidth;

    private int backgroundCircleLineColor;
    private int progressCircleLineColor;

    private float dotRadius;

    private int dotColor;

    private float maxValue;
    private float minValue;
    private int value;

    private boolean showCounter;
    private boolean isClockwise;

    @StyleRes
    private int textAppearance;

    private FrameLayout view;
    private TextView textView;

    private float width;
    private float height;

    private float centerX;
    private float centerY;

    private float baseCircleRadius;

    private float previousAngel;
    private float totalAngel;

    private Paint backgroundCirclePaint;
    private Paint progressCirclePaint;
    private Paint dotPaint;

    private RectF progressArc;

    private boolean isMeasureChanged = false;
    private boolean isSizeChanged = false;
    private boolean isValueChanged = false;

    CircleProgressDrawer(FrameLayout view) {
        this.view = view;
    }

    void init(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes){

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.CircleSeekBar,
                defStyleRes,
                R.style.CircleProgressViewStyle);

        float dp = context.getResources().getDisplayMetrics().density;

        backgroundCircleLineWidth = array.getDimension(
                R.styleable.CircleSeekBar_backgroundCircleLineWidth, 2 * dp);
        progressCircleLineWidth = array.getDimension(
                R.styleable.CircleSeekBar_progressCircleLineWidth, 2 * dp);

        backgroundCircleLineColor = array.getColor(
                R.styleable.CircleSeekBar_backgroundCircleLineColor, GRAY_COLOR);
        progressCircleLineColor = array.getColor(
                R.styleable.CircleSeekBar_progressCircleLineColor, BLUE_COLOR);

        dotRadius = array.getDimension(
                R.styleable.CircleSeekBar_dotRadius, 20 * dp);
        dotColor = array.getColor(
                R.styleable.CircleSeekBar_dotColor, BLUE_COLOR);

        maxValue = array.getInt(
                R.styleable.CircleSeekBar_maxValue, DEFAULT_MAX_VALUE);

        value = array.getInt(
                R.styleable.CircleSeekBar_value, DEFAULT_VALUE);

        textAppearance = array.getResourceId(R.styleable.CircleSeekBar_textAppearance,
                R.style.CircleProgressViewStyle_TextAppearance);

        showCounter = array.getBoolean(R.styleable.CircleSeekBar_showCounter, true);
        isClockwise = array.getBoolean(R.styleable.CircleSeekBar_isClockwise, true);

        array.recycle();

        backgroundCirclePaint = new Paint();
        backgroundCirclePaint.setColor(backgroundCircleLineColor);
        backgroundCirclePaint.setStrokeWidth(backgroundCircleLineWidth);
        backgroundCirclePaint.setAntiAlias(true);
        backgroundCirclePaint.setStyle(Paint.Style.STROKE);

        progressCirclePaint = new Paint();
        progressCirclePaint.setColor(progressCircleLineColor);
        progressCirclePaint.setStrokeWidth(progressCircleLineWidth);
        progressCirclePaint.setAntiAlias(true);
        progressCirclePaint.setStyle(Paint.Style.STROKE);

        dotPaint = new Paint();
        dotPaint.setColor(dotColor);
        dotPaint.setAntiAlias(true);
        dotPaint.setStyle(Paint.Style.FILL);

        previousAngel = degrees(value);
        totalAngel = previousAngel;
    }

    void onLayout(boolean changed, int left, int top, int right, int bottom) {
        if(!showCounter){
            return;
        }

        final int childLeft = view.getPaddingLeft();
        final int childTop = view.getPaddingTop();

        final int childRight = view.getMeasuredWidth() - view.getPaddingRight();
        final int childBottom = view.getMeasuredHeight() - view.getPaddingBottom();

        final int childWidth = childRight - childLeft;
        final int childHeight = childBottom - childTop;

        textView = new TextView(view.getContext());
        textView.setTextAppearance(view.getContext(), textAppearance);
        textView.setText(String.valueOf(value));
        textView.setTextColor(0xff000000);

        view.addView(textView);

        textView.measure(View.MeasureSpec.makeMeasureSpec(childWidth, View.MeasureSpec.AT_MOST),
                View.MeasureSpec.makeMeasureSpec(childHeight, View.MeasureSpec.AT_MOST));

        int curWidth = textView.getMeasuredWidth();
        int curHeight = textView.getMeasuredHeight();

        int centerX = childWidth / 2;
        int centerY = childHeight / 2;

        textView.layout(centerX - curWidth / 2,
                centerY - curHeight / 2,
                centerX + curWidth / 2,
                centerY + curHeight / 2);
    }

    void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        isMeasureChanged = true;
    }

    void onSizeChanged(int w, int h, int oldw, int oldh) {
        isSizeChanged = true;

        width = w;
        height = h;

//        calculating radius
        baseCircleRadius = height > width ? width / 2 : height / 2;
        baseCircleRadius -= backgroundCircleLineWidth > progressCircleLineColor ?
                backgroundCircleLineWidth : progressCircleLineColor;
        baseCircleRadius -= dotRadius;

//        calculating centerX and centerY

        int paddingLeft;
        int paddingTop = view.getPaddingTop();
        int paddingRight;
        int paddingBottom = view.getPaddingBottom();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            paddingLeft = view.getPaddingLeft() != 0 ? view.getPaddingLeft() : view.getPaddingStart();
            paddingRight = view.getPaddingRight() != 0 ? view.getPaddingRight() : view.getPaddingEnd();
        } else {
            paddingLeft = view.getPaddingLeft();
            paddingRight = view.getPaddingRight();
        }

        float innerWidth = width - paddingLeft - paddingRight;

        centerX = view.getPaddingLeft()  + innerWidth / 2;

        float innerHeight = height - paddingTop - paddingBottom;

        centerY = view.getPaddingTop() + innerHeight / 2;

//        creating new rectF for drawing progress arc
        progressArc = new RectF();

        progressArc.set(centerX - baseCircleRadius,
                centerY - baseCircleRadius,
                centerX + baseCircleRadius,
                centerY + baseCircleRadius);
    }

    void onDraw(Canvas canvas) {
        if(isMeasureChanged || isSizeChanged || isValueChanged){
            drawBackgroundCircle(canvas);
            drawProgressArc(canvas);
            drawDot(canvas);
        }
    }

    private void drawBackgroundCircle(Canvas canvas){
        canvas.drawCircle(centerX, centerY ,
                baseCircleRadius, backgroundCirclePaint);
    }

    private void drawProgressArc(Canvas canvas){
        if(isClockwise){
            canvas.drawArc(progressArc, 270, -(degrees(value)),
                    false, progressCirclePaint);
        } else {
            canvas.drawArc(progressArc, 270, degrees(value),
                    false, progressCirclePaint);
        }

    }

    private void drawDot(Canvas canvas){
        float degrees = degrees(value) - 90;

        float x = (float) (Math.cos(Math.toRadians(degrees)) * baseCircleRadius + centerX);

        float y = (float) (Math.sin(Math.toRadians(degrees)) * baseCircleRadius + centerY);

        canvas.drawCircle(x, y, dotRadius, dotPaint);
    }

    private float degrees(float value){
        float totalValues = maxValue - minValue;
        float oneDegree = totalValues / 360;

        return value / oneDegree;
    }

    private int value(float degree){
        float totalValues = maxValue - minValue;
        float oneValue = 360 / totalValues;

//        if(isClockwise){
//            return (int) (-degree / oneValue);
//        } else {
            return (int) (degree / oneValue);
//        }
    }

    private boolean isOnArc = false;

    boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                isOnArc = isOnArc(event);
                return true;
            case MotionEvent.ACTION_MOVE:
                if(isOnArc){
                    double angel = Math.PI * 180 / Math.PI;

                    if(event.getX() < width / 2){
                        angel += Math.acos(computeCos(event.getX(), event.getY())) * 180 / Math.PI;

                    } else {
                        angel -= Math.acos(computeCos(event.getX(), event.getY())) * 180 / Math.PI;
                    }

                    float shift;

                    if(Math.abs(angel - previousAngel) > 300){
                        shift = 360 - totalAngel % 360;
                        if(angel < 50){
                            shift += angel;
                        } else {
                            shift -= angel;
                        }
                    } else {
                        shift = (float) (angel - previousAngel);
                    }

                    if(isClockwise){
                        totalAngel -= shift;
                    } else {
                        totalAngel += shift;
                    }

                    previousAngel = (float) angel;

                    value = value((float) angel);

                    view.invalidate();
                }
                return true;
            case MotionEvent.ACTION_UP:
                isOnArc = false;
                findNearestValue();
                return true;
        }

        return false;
    }

    private float computeCos(float x, float y) {
        float width = x - this.width / 2;
        float height = y - this.height / 2;
        float slope = (float) Math.sqrt(width * width + height * height);
        return height / slope;
    }

    private boolean isOnArc(MotionEvent event){
        final float distance = distance(event.getX(), event.getY(),
                centerX, centerY);

        final float radius = progressArc.width() / 2f;

        final float halfStrokeWidth = dotRadius;

        return Math.abs(distance - radius) <= halfStrokeWidth;
    }

    private static float distance(float x1, float y1, float x2, float y2) {
        return (float) Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
    }

    private void findNearestValue(){
        float currentAngle = totalAngel % 360;

        value = value(currentAngle);

        view.invalidate();
    }
}
