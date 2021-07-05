package com.geemi.unimp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.geemi.facelibrary.ui.base.GeemiBaseActivity;
import com.geemi.facelibrary.ui.client.CommonWebView;
import com.geemi.facelibrary.utils.MyIntentUtil;

import io.dcloud.feature.sdk.DCUniMPSDK;


public class MainActivity extends GeemiBaseActivity {
    private TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.textView);
        textView.setOnClickListener(v -> {
            try {
                DCUniMPSDK.getInstance().startApp(MainActivity.this,"__UNI__04E3A11");
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
//        Bundle mBundle = new Bundle();
//        mBundle.putString("sTag","sTag");
//        textView.post(() -> MyIntentUtil.setAnimContainerActivity(MainActivity.this,"http://123.56.184.163/smartsiteapp/#/pages/login/login?type=work&user=13905223590&password=223590",mBundle, CommonWebView.class,textView));

    }
}