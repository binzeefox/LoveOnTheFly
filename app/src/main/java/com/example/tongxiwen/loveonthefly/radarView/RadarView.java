package com.example.tongxiwen.loveonthefly.radarView;

import android.content.Context;
import android.graphics.*;
import android.media.MediaPlayer;
import android.support.annotation.ColorInt;
import android.util.AttributeSet;
import android.view.View;
import com.example.tongxiwen.loveonthefly.ConversionUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by tong.xiwen on 2017/8/31.
 */
public class RadarView extends View {

    private List<Map<Integer, Integer>> mDataMaps;
    private List<String> mTitleList;
    private Rect mRect;
    private int dataCount;
    private Paint mPaint;

    // 绘制
    private Path mPath;

    // 每个数据间角度
    private int mArc;

    public RadarView(Context context) {
        this(context, null, 0);
    }

    public RadarView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RadarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mDataMaps = new ArrayList<>();
        mTitleList = new ArrayList<>();
        mRect = new Rect();
        mPath = new Path();
        mPaint = getPaint(0x5e5e5e, ConversionUtil.dip2px(getContext(),1), Paint.Style.STROKE);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        dataCount = mTitleList.size();
        mArc = 360 / dataCount;

        if (MeasureSpec.getMode(widthMeasureSpec) == MeasureSpec.AT_MOST){
            widthMeasureSpec = MeasureSpec.makeMeasureSpec(ConversionUtil.dip2px(getContext(), 40)
                    , MeasureSpec.EXACTLY);
        }
        if (MeasureSpec.getMode(heightMeasureSpec) == MeasureSpec.AT_MOST){
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(ConversionUtil.dip2px(getContext(), 40)
                    , MeasureSpec.EXACTLY);
        }

        int l = getPaddingLeft();
        int t = getPaddingTop();
        int r = l + MeasureSpec.getSize(widthMeasureSpec);
        int b = t + MeasureSpec.getSize(heightMeasureSpec);

        mRect.set(l, t, r, b);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // 保存最初视图状态
        canvas.save();

        // 绘制外边框
        int x = mRect.centerX();
        int y = mRect.top;
        for (int arc = 0; arc < 360; arc += mArc) {
            if (arc == 0) {
                mPath.moveTo(x, y);
            }else {
                mPath.lineTo(x, y);
            }
            canvas.rotate(mArc);
        }
    }

    /**
     * 创建画笔
     * @param color 画笔颜色
     * @param strokeWidth   画笔宽度（若style为STROKE 或 FILL_AND_STROKE）
     * @param style 画笔风格
     */
    private Paint getPaint(@ColorInt int color, int strokeWidth, Paint.Style style) {
        Paint paint = new Paint();
        paint.setColor(color);
        paint.setStyle(style);
        if (style == Paint.Style.STROKE || style == Paint.Style.FILL_AND_STROKE){
            paint.setStrokeWidth(strokeWidth);
        }
        return paint;
    }
}
