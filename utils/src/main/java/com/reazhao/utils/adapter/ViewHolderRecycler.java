package com.reazhao.utils.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;

/**
 * @author ReaZhao
 * @date 2017/3/3
 * @E-mail 377742053qq.com
 * @desc RecyclerView优化
 */

public class ViewHolderRecycler extends RecyclerView.ViewHolder {
    private SparseArray<View> views=new SparseArray<>();
    private View view;
    public ViewHolderRecycler(View itemView) {
        super(itemView);
        this.view=itemView;
    }
    public <T extends View> T getView(int resID){
        View v = views.get(resID);
        if (v == null) {
            v = view.findViewById(resID);
            views.put(resID,v);
        }
        return (T) v;
    }
}
