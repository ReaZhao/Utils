package com.reazhao.mvpmodel.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import com.reazhao.mvpmodel.R;
import com.reazhao.utils.PhoneUtils;
import com.reazhao.utils.ToastUtils;

public class MainActivity extends AppCompatActivity {
    private AutoCompleteTextView mAutoCompleteTextView;
    private TextView mTextView;
    private String[] m_sAutoS=null;
    private ArrayAdapter<String> arrayAdapter;
    private TextView viewById;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewById = (TextView) findViewById(R.id.hint);
        f_onCreate();
    }

    public void f_onCreate( )
    {
        mAutoCompleteTextView = (AutoCompleteTextView)findViewById(R.id.AutoCompleteTextView_Main_Test);
        mTextView = (TextView)findViewById(R.id.TextView_Main_Show);

        m_sAutoS = new String[]{"Android","Android JDK","Android Blog","Android BBS",
                "Google Map","Google Search","Google Wear","Baidu Map","Baidu Search"};
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, m_sAutoS);
        mAutoCompleteTextView.setAdapter(arrayAdapter);
        mAutoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ToastUtils.showShortToast("点击了"+position);
                e_Button_Main_Clean_Clicked(position);
            }
        });
    }

    public void e_Button_Main_Clean_Clicked(int position)
    {
        mAutoCompleteTextView.setText("");
        mTextView.setText("输入结果:"+m_sAutoS[position]);
    }

    @Override
    protected void onResume() {
        super.onResume();
        String pho= PhoneUtils.getPhoneStatus();
        viewById.setText(pho);
    }
}
