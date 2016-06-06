package cn.rainbow.sdk.thanalytics;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class NewActivity extends BaseActivity {

    public static void start(Context context) {
        Intent starter = new Intent(context, NewActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);
        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {//崩溃统计测试
                ImageButton ib = (ImageButton) findViewById(R.id.button2);
            }
        });
    }
}
