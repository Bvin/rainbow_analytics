package cn.rainbow.sdk.thanalytics;

import android.os.Bundle;
import android.view.View;

import cn.rainbow.sdk.analytics.persistence.PersistenceService;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void nextPage(View view){
        NewActivity.start(this);
    }
}
