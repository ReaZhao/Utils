package com.reazhao.utils.adapter;

import android.util.SparseArray;
import android.view.View;

/**
 * Created by Simon.Wu on 2015/5/18.
 */
public class ViewHolder {
    private SparseArray<View> views = new SparseArray<View>();
    private View convertView;

    public ViewHolder(View convertView) {
        this.convertView = convertView;
    }

    public <T extends View> T getView(int resId) {
        View v = views.get(resId);
        if (null == v) {
            v = convertView.findViewById(resId);
            views.put(resId, v);
        }
        return (T) v;
    }
}
