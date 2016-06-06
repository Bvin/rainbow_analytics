package cn.rainbow.sdk.thanalytics;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class NewActivity extends BaseActivity {

    public static void start(Context context) {
        Intent starter = new Intent(context, NewActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);
        findViewById(R.id.button).setOnClickListener(null);
    }
}
