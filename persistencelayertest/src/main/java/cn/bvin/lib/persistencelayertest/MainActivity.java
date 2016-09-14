package cn.bvin.lib.persistencelayertest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;

import cn.rainbow.sdk.analytics.persistence.Persistable;
import cn.rainbow.sdk.analytics.persistence.PersistenceService;

public class MainActivity extends AppCompatActivity {

    PersistenceService mPersistenceService;
    SparseArray<String> mSparseArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mPersistenceService = PersistenceService.getInstance(getApplicationContext());
    }

    public void insert(View v){
        for (int i = 0; i < 10; i++) {
            final int finalI = i;
            mPersistenceService.save(new Persistable() {
                @Override
                public String toPersistableString() {
                    return "'Event #"+ finalI+"'";//一定要单引号引用
                }
            });
        }
        mPersistenceService.end();
    }

    public void query(View view){
        mPersistenceService.query(new PersistenceService.SQLCallback<SparseArray<String>>() {
            @Override
            public void callback(SparseArray<String> stringSparseArray) {
                mSparseArray = stringSparseArray;
                for (int i = 0; i < stringSparseArray.size(); i++) {
                    Log.d("query: ", stringSparseArray.keyAt(i) + "=" + stringSparseArray.get(stringSparseArray.keyAt(i)));
                }
            }
        });
        mPersistenceService.end();
    }

    public void delete(View view){
        if (mSparseArray != null) {
            for (int i = 0; i < mSparseArray.size(); i++) {
                mPersistenceService.delete(mSparseArray.keyAt(i));
            }
        }
        mPersistenceService.end();
    }

}
