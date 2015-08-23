package neal.canvas;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by songxun.sx on 2015/8/14.
 */
public class MyView extends View {

    NextFrameAction nextFrameAction;
    RectF rectF;
    Paint paint;
    Paint paint2;
    Paint paint3;
    Path path;
    Path path1;
    int width;
    int height;
    int w = 0;
    double startTime;
    int waveAmplitude;
    int waveRange;
    int highLevel;

    public MyView(Context context) {
        super(context);
    }

    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void init() {
        nextFrameAction = new NextFrameAction();
        width = getMeasuredWidth();
        height = getMeasuredHeight();
        waveRange = width;
        rectF = new RectF(5, 5, width - 5, height - 5);
        paint = new Paint();
        paint2 = new Paint();
        paint3=new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.RED);
        paint3.setAntiAlias(true);
        paint3.setColor(0x99ff0000);

        paint2.setAntiAlias(true);
        paint2.setStyle(Paint.Style.STROKE);
        paint2.setStrokeWidth(5);
        paint2.setColor(Color.RED);
        path = new Path();
        path1 = new Path();
        startTime = System.currentTimeMillis();
        waveAmplitude = 20;
        highLevel = (int) (height * (0.5) + waveAmplitude);
    }

    protected class NextFrameAction implements Runnable {

        @Override
        public void run() {
            path.reset();
            path1.reset();
            path.addArc(rectF, 90.0f - 145.0f / 2.0f, 145.0f);
            path1.addArc(rectF, 90.0f - 145.0f / 2.0f, 145.0f);
            w += 5;
            if (w >= (width - 5) * 2) {
                w = 0;
            }
            for (int i = 5; i < width - 5; i++) {
                path.lineTo(i, (float) (highLevel + waveAmplitude * Math.cos((float) (i + w) / (float) (width - 5) * Math.PI)));
                path1.lineTo(i, (float) (highLevel - waveAmplitude * Math.cos((float) (i + w) / (float) (width - 5) * Math.PI)));
            }
            path.close();
            path1.close();
            invalidate();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        init();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawCircle(width / 2, height / 2, width / 2 - 5, paint2);
        //canvas.drawArc(rectF,90.0f-145.0f/2.0f,145.0f,false,paint);
        canvas.drawPath(path, paint);
        canvas.drawPath(path1,paint3);
        postDelayed(nextFrameAction, 4);

    }
}
