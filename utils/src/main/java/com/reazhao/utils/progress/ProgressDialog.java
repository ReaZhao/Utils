package com.reazhao.utils.progress;


import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.TextView;

import com.reazhao.utils.R;


/**
 * * @author ReaZhao
 * @date 2016/12/15
 * @E-mail 377742053qq.com
 * @desc progressdialog指向类用于自定义初始化 *与预期进度对话框的风格。
 */
public class ProgressDialog extends Dialog {

    private Context mContext = null;

    private static ProgressDialog mCustomProgressDialog = null;

    /**
     * 创建一个实例的progressdialog
     * 
     * @param context
     *       上下文允许访问的资源类型特征
     */
    public ProgressDialog(Context context) {

        super(context);
        this.mContext = context;
    }

    /**
     * 创建一个实例的progressdialog
     * @param context
     *      上下文允许访问的资源类型特征
     * @param theme
     *        theme.
     */
    public ProgressDialog(Context context, int theme) {

        super(context, theme);
    }

    /**
     *创建一个对话框.
     * 
     * @param context
     *        上下文允许访问的资源类型特征
     * @return progressdialog的实例。
     */
    public static ProgressDialog createDialog(Context context) {

        mCustomProgressDialog = new ProgressDialog(context,
                R.style.CustomProgressDialog);
        mCustomProgressDialog.setContentView(R.layout.progressdialog);
        mCustomProgressDialog.getWindow().getAttributes().gravity = Gravity.CENTER;

        return mCustomProgressDialog;
    }

    public void onWindowFocusChanged(boolean hasFocus) {

        if (mCustomProgressDialog == null) {
            return;
        }

        ImageView imageView = (ImageView) mCustomProgressDialog
                .findViewById(R.id.image_loading_view);
        AnimationDrawable animationDrawable = (AnimationDrawable) imageView
                .getBackground();
        animationDrawable.start();
    }

    /**
     * 设置标题
     * @param strTitle
     *        标题
     * @return progressdialog的实例。
     */
    public ProgressDialog setTitile(String strTitle) {

        return mCustomProgressDialog;
    }

    /**
     * 设置内容
     * @param strMessage
     *        内容
     * @return progressdialog的实例。
     */
    public ProgressDialog setMessage(String strMessage) {

        TextView tvMsg = (TextView) mCustomProgressDialog
                .findViewById(R.id.text_loading_msg);

        if (tvMsg != null) {
            tvMsg.setText(strMessage);
        }

        return mCustomProgressDialog;
    }
}
