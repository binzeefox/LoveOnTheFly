package com.example.tongxiwen.loveonthefly.heartview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import com.example.tongxiwen.loveonthefly.heartview.Heart;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tong.xiwen on 2017/8/31.
 */
public class HeartView extends View {

    private boolean isFilled;
    private boolean inBlock;
    private List<Heart> heartList;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    prepareHeart();
                    invalidate();
                    if (heartList.size() != 0) {
                        mHandler.sendEmptyMessageDelayed(0, 30);
                    }
                    break;
                case 1:
                    inBlock = false;
            }
        }
    };

    public HeartView(Context context) {
        this(context, null, 0);
    }

    public HeartView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HeartView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        heartList = new ArrayList<>();
        isFilled = false;
        inBlock = false;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (heartList.size() != 0) {
            for (Heart heart : heartList) {
                Path path = heart.getPath();
                Paint paint = heart.getPaint();
                canvas.drawPath(path, paint);
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (!inBlock) {
            int x = (int) event.getX();
            int y = (int) event.getY();
            Heart heart = new Heart(x, y);
            if (isFilled){
                heart.setFilled(true);
            }
            heartList.add(heart);
            if (heartList.size() == 1) {
                mHandler.sendEmptyMessage(0);
            }
            inBlock = true;
            mHandler.sendEmptyMessageDelayed(1, 100);
        }
        return true;
    }

    /**
     * 检查元素
     */
    private void prepareHeart() {
        for (int i = 0; i < heartList.size(); i++) {
            Heart heart = heartList.get(i);
            if (!heart.nextState()) {
                heartList.remove(i);
            }
        }
    }


    public void changeFilled(){
        isFilled = !isFilled;
    }
}
