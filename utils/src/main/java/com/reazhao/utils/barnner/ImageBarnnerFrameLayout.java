package com.reazhao.utils.barnner;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.reazhao.utils.R;

import java.util.List;

/**
 * author ReaZhao
 * Time 2017/5/4
 * E-mail 377742053@qq.com
 * 轮播图的圆点
 */

public class ImageBarnnerFrameLayout extends FrameLayout implements ImageBarnnerViewGroup.ImageBarnnerViewGroupLister,ImageBarnnerViewGroup.ImageBarnnerLister{

    private ImageBarnnerViewGroup imageBarnnerViewGroup;
    private LinearLayout linearLayout;
    //底部圆点布局的高
    private int dotHeight=40;

    private ImageBarnnerFrameLayoutLister imageBarnnerFrameLayoutLister;

    public ImageBarnnerFrameLayoutLister getImageBarnnerFrameLayoutLister() {
        return imageBarnnerFrameLayoutLister;
    }

    public void setImageBarnnerFrameLayoutLister(ImageBarnnerFrameLayoutLister imageBarnnerFrameLayoutLister) {
        this.imageBarnnerFrameLayoutLister = imageBarnnerFrameLayoutLister;
    }

    public interface ImageBarnnerFrameLayoutLister{
        void clickImageIndex(int pos);
    }

    /**
     * 初始化自定义的轮播图核心类
     */
    private void initImageBarnnerViewGroup() {
        imageBarnnerViewGroup = new ImageBarnnerViewGroup(getContext());
        //设置轮播图的宽高，LayoutParams.MATCH_PARENT最好获得手机的宽度，不然图片跟手机的宽不通，会出现屏幕上可以看到第一张图片与第二章图片
        LayoutParams ip = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        imageBarnnerViewGroup.setLayoutParams(ip);
        imageBarnnerViewGroup.setBarnnerViewGroupLister(this);
        addView(imageBarnnerViewGroup);

    }

    /**
     * 初始底部圆点布局
     */

    private void initDotLinearlayout() {
        linearLayout = new LinearLayout(getContext());
        //设置底部圆点的布局  高为40
        LayoutParams ip = new LayoutParams(LayoutParams.MATCH_PARENT, dotHeight);
        linearLayout.setLayoutParams(ip);
        //设置底部圆点为水平布局
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        //设置底部圆点居中
        linearLayout.setGravity(Gravity.CENTER);
        //设置底部圆点的背景颜色
//        linearLayout.setBackgroundColor(Color.parseColor("#FF0000"));

        addView(linearLayout);

        LayoutParams layoutParams = (LayoutParams) linearLayout.getLayoutParams();
        layoutParams.gravity = Gravity.BOTTOM;
        linearLayout.setLayoutParams(layoutParams);


        //android 3.0以后 使用setAlpha(),以前使用setAlpha()调用不同
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            linearLayout.setAlpha(0.5f);
        } else {
            linearLayout.getBackground().setAlpha(100);
        }
    }

    /**
     * 添加底部圆点图片
     * @param bitmaps
     */
    public void addBitmap(List<Bitmap> bitmaps) {
        for (int i = 0; i < bitmaps.size(); i++) {
            Bitmap bitmap = bitmaps.get(i);
            addBitmapToImageBarnnerViewGroup(bitmap);
            addDotToLinearlayout();
        }
    }

    /**
     * 添加底部圆点
     */
    private void addDotToLinearlayout() {
        ImageView imageView = new ImageView(getContext());
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(5, 5, 5, 5);
        imageView.setLayoutParams(lp);
        imageView.setImageResource(R.drawable.dot_normal);
        linearLayout.addView(imageView);
    }

    /**
     * 添加轮播图
     * @param bitmap
     */
    private void addBitmapToImageBarnnerViewGroup(Bitmap bitmap) {
        ImageView imageView = new ImageView(getContext());
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        imageView.setImageBitmap(bitmap);
        imageBarnnerViewGroup.setImageBarnnerLister(this);
        imageBarnnerViewGroup.addView(imageView);
//

    }

    @Override
    public void selectImage(int index) {
        int count = linearLayout.getChildCount();
        for (int i = 0; i < count; i++) {
            ImageView imageView = (ImageView) linearLayout.getChildAt(i);
            if (i == index) {
                imageView.setImageResource(R.drawable.dot_select);
            }else{
                imageView.setImageResource(R.drawable.dot_normal);
            }
        }
    }
    public ImageBarnnerFrameLayout(@NonNull Context context) {
        super(context);
        initImageBarnnerViewGroup();
        initDotLinearlayout();
    }

    public ImageBarnnerFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initImageBarnnerViewGroup();
        initDotLinearlayout();
    }

    public ImageBarnnerFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initImageBarnnerViewGroup();
        initDotLinearlayout();
    }

    @Override
    public void clickImageIndex(int pos) {
        imageBarnnerFrameLayoutLister.clickImageIndex(pos);
    }
}
