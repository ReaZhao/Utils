package com.reazhao.utils.barnner;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Scroller;

import java.util.Timer;
import java.util.TimerTask;

/**
 * author ReaZhao
 * Time 2017/4/26
 * E-mail 377742053@qq.com
 * 轮播图核心类
 */

public class ImageBarnnerViewGroup extends ViewGroup {
    //ViewGroup的总个数
    private int children;
    //子视图高度
    private int childrenheight;
    //子视图宽度
    private int childrenwidth;
    //保存用户第一次按下的坐标 X 因为只用左右滑动来判断到那张图片，所以不用Y坐标
    private int x;
    //轮播图  图片的索引
    private int index = 0;

    private Scroller scroller;
    //是否是点击事件 true 点击事件 false 不是点击事件
    private boolean isClick;
    //添加图片的单击事件
    private ImageBarnnerLister ImageBarnnerLister;
    public interface ImageBarnnerLister  {
        void clickImageIndex(int pos);
    }
    public ImageBarnnerLister getImageBarnnerLister() {
        return ImageBarnnerLister;
    }

    public void setImageBarnnerLister(ImageBarnnerLister ImageBarnnerLister) {
        this.ImageBarnnerLister = ImageBarnnerLister;
    }
    private ImageBarnnerViewGroupLister barnnerViewGroupLister;

    public ImageBarnnerViewGroupLister getBarnnerViewGroupLister() {
        return barnnerViewGroupLister;
    }

    public void setBarnnerViewGroupLister(ImageBarnnerViewGroupLister barnnerViewGroupLister) {
        this.barnnerViewGroupLister = barnnerViewGroupLister;
    }

    public interface ImageBarnnerViewGroupLister{
        void selectImage(int index);
    }



    //是否自动轮播  默认轮播
    private boolean isAuto = true;
    //定时器
    private Timer timer = new Timer();
    private TimerTask task;
    //处理定时器返回
    private Handler autoHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    //如果是最后一张图片则从第一张重新开始轮播
                    if (++index >= children) {
                        index = 0;
                    }
                   scrollTo( childrenwidth * index,0);
                    barnnerViewGroupLister.selectImage(index);
                    break;
                default:
                    break;
            }
        }
    };

    /**
     * 启动轮播
     */
    private void startAuto() {
        isAuto = true;
    }

    /**
     * 停止轮播
     */
    private void stopAuto() {
        isAuto = false;
    }

    public ImageBarnnerViewGroup(Context context) {
        super(context);
        initObj();
    }

    public ImageBarnnerViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        initObj();
    }

    public ImageBarnnerViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initObj();
    }

    private void initObj() {
        scroller = new Scroller(getContext());

        task = new TimerTask() {
            @Override
            public void run() {
                if (isAuto) {
                    autoHandler.sendEmptyMessage(0);
                }
            }
        };
        //100毫秒后每隔3秒钟执行一次轮播
        timer.schedule(task, 100, 3000);
    }

    /**
     * 重绘
     */
    @Override
    public void computeScroll() {
        super.computeScroll();
        if (scroller.computeScrollOffset()) {
            scrollTo(scroller.getCurrX(), 0);
            invalidate();
        }
    }

    /**
     * 得到ViewGroup
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //求出子视图的个数
        children = getChildCount();
        if (0 == children) {
            //这里设置为0,0  所以xml布局中看不到大小
            setMeasuredDimension(0, 0);
        } else {
            //测量子视图的高度和宽度
            measureChildren(widthMeasureSpec, heightMeasureSpec);
            //根据子视图的宽度和高度求出该ViewGroup的宽度和高度
            View view = getChildAt(0);
            //得到每个字视图的宽度和高度
            childrenheight = view.getMeasuredHeight();
            childrenwidth = view.getMeasuredWidth();
            //得到所有子视图的宽度
            int width = view.getMeasuredWidth() * children;
            setMeasuredDimension(width, childrenheight);
        }
    }

    /**
     * @param changed ViewGroup位置改变为true
     * @param l       左边
     * @param t       上边
     * @param r       右边
     * @param b       下边
     */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (changed) {
            int leftMargin = 0;
            for (int i = 0; i < children; i++) {
                View view = getChildAt(i);
                view.layout(leftMargin, 0, leftMargin + childrenwidth, childrenheight);
                leftMargin += childrenwidth;
            }
        }

    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return true;
    }

    /**
     * 用户在屏幕上操作的事件
     *
     * @param event
     * @return true 表示用户操作的手势已经处理
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN://用户按下的一瞬间
                stopAuto();
                //判断滑动是否完成
                if (!scroller.isFinished()) {
                    scroller.abortAnimation();
                }
                isClick=true;
                x = (int) event.getX();
                break;
            case MotionEvent.ACTION_MOVE://用户按下之后再屏幕上移动的过程
                int moveX = (int) event.getX();
                int distance = moveX - x;
                scrollBy(-distance, 0);
                if (Math.abs(moveX - x)>20) {
                    isClick=false;
                }
                //保存滑动后的坐标，会滑动后手指不离开屏幕，distance才不会变
                x = moveX;
                break;
            case MotionEvent.ACTION_UP://用户按下抬起的一瞬间
                int scrollX = getScrollX();
                index = (scrollX + childrenwidth / 2) / childrenwidth;
                if (index < 0) {//说明滑动到第一张图片
                    index = 0;
                } else if (index > children - 1) {//说明滑动到最后一张图片
                    index = children - 1;
                }
                if (isClick) {
                    ImageBarnnerLister.clickImageIndex(index);
                } else {
                    int dx = index * childrenwidth - scrollX;
                    scroller.startScroll(scrollX, 0, dx, 0);
                    postInvalidate();
                    barnnerViewGroupLister.selectImage(index);
                }
                startAuto();
//                scrollTo(index * childrenwidth, 0);
                break;
            default:
                break;
        }
        return true;
    }

}
