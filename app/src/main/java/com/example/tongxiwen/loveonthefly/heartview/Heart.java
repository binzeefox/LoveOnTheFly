package com.example.tongxiwen.loveonthefly.heartview;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;


/**
 * Created by tong.xiwen on 2017/8/31.
 */
class Heart {

    private static final float C = 0.551915024494f;     // 一个常量，用来计算绘制圆形贝塞尔曲线控制点的位置

    private Path path;
    private Point point;
    private Paint paint;
    private int alpha;
    private int radius;

    private float mDifference = radius*C;        // 圆形的控制点与数据点的差值

    private Point[] dataPs = new Point[4];
    private Point[] ctrlPs = new Point[8];



    Heart(int x, int y){
        // 初始化画笔
        paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(20);
        paint.setColor(Color.RED);
        // 初始化中心点
        point = new Point(x,y);
        // 初始化参数
        alpha = 255;
        radius = 0;

        initPoint();
    }

    private void initPoint() {
        mDifference = radius*C;        // 圆形的控制点与数据点的差值

        // 初始化数据点
        // 上
        dataPs[0] = new Point(point.x, point.y - radius);
        // 右
        dataPs[1] = new Point(point.x + radius, point.y);
        // 下
        dataPs[2] = new Point(point.x, point.y + radius);
        // 左
        dataPs[3] = new Point(point.x - radius, point.y);

            // 初始化圆控制点
        // 上左
        ctrlPs[0] = new Point(dataPs[0].x - (int) mDifference, dataPs[0].y);
        // 上右
        ctrlPs[1] = new Point(dataPs[0].x + (int) mDifference, dataPs[0].y);

        // 右上
        ctrlPs[2] = new Point(dataPs[1].x, dataPs[1].y - (int) mDifference);
        // 右下
        ctrlPs[3] = new Point(dataPs[1].x, dataPs[1].y + (int) mDifference);

        // 下右
        ctrlPs[4] = new Point(dataPs[2].x + (int) mDifference, dataPs[2].y);
        // 下左
        ctrlPs[5] = new Point(dataPs[2].x - (int) mDifference, dataPs[2].y);

        // 左下
        ctrlPs[6] = new Point(dataPs[3].x, dataPs[3].y + (int) mDifference);
        // 左上
        ctrlPs[7] = new Point(dataPs[3].x, dataPs[3].y - (int) mDifference);

        changeToHeart();
    }

    /**
     * 将圆变成心
     */
    private void changeToHeart() {
        dataPs[0].y += radius * 3/5;
        ctrlPs[4].y -= radius * 2/5;
        ctrlPs[5].y -= radius * 2/5;
    }

    /**
     * 返回专用画笔
     */
    Paint getPaint(){
        paint.setAlpha(alpha);
        return paint;
    }

    /**
     * 进入下一状态
     * 若返回false则alpha小于等于0
     */
    boolean nextState(){
        alpha -= 5;
        radius += 10;
        return alpha> 0;
    }

    /**
     * 获取绘制用Path
     */
    Path getPath(){
        path = new Path();

        initPoint();
        path.moveTo(dataPs[0].x, dataPs[0].y);
        path.cubicTo(ctrlPs[1].x, ctrlPs[1].y, ctrlPs[2].x, ctrlPs[2].y, dataPs[1].x, dataPs[1].y);
        path.cubicTo(ctrlPs[3].x, ctrlPs[3].y, ctrlPs[4].x, ctrlPs[4].y, dataPs[2].x, dataPs[2].y);
        path.cubicTo(ctrlPs[5].x, ctrlPs[5].y, ctrlPs[6].x, ctrlPs[6].y, dataPs[3].x, dataPs[3].y);
        path.cubicTo(ctrlPs[7].x, ctrlPs[7].y, ctrlPs[0].x, ctrlPs[0].y, dataPs[0].x, dataPs[0].y);
        return path;
    }

    /**
     * 设置是否为填充
     * @param isFilled  是否填充
     */
    void setFilled(boolean isFilled){
        if (isFilled){
            paint.setStyle(Paint.Style.FILL);
        } else {
            paint.setStyle(Paint.Style.STROKE);
        }
    }

    /**
     * 判断是否为填充
     */
    boolean isFilled(){
        return paint.getStyle() == Paint.Style.FILL;
    }
}
