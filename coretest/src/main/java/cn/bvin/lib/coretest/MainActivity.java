package cn.bvin.lib.coretest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;

import java.util.HashMap;

import cn.rainbow.sdk.analytics.persistence.Persistable;
import cn.rainbow.sdk.analytics.persistence.PersistenceService;

public class MainActivity extends AppCompatActivity {

    private HashMap<Integer,String> mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mMap = new HashMap<>();
    }

    public void write(View v){
        PersistenceService.getInstance(this).save(new City("北京"));
        PersistenceService.getInstance(this).save(new City("上海"));
        PersistenceService.getInstance(this).save(new City("广州"));
        PersistenceService.getInstance(this).save(new City("深圳"));
        PersistenceService.getInstance(this).save(new City("南京"));
        PersistenceService.getInstance(this).save(new City("苏州"));
        PersistenceService.getInstance(this).save(new City("杭州"));
        PersistenceService.getInstance(this).save(new City("厦门"));
        PersistenceService.getInstance(this).end();
    }

    class City implements Persistable{

        private String city;

        public City(String city) {
            this.city = city;
        }

        @Override
        public String toPersistableString() {
            return "city="+city;
        }
    }

    public void read(View view){
        PersistenceService.getInstance(this).query(new PersistenceService.SQLCallback<SparseArray<String>>() {
            @Override
            public void callback(SparseArray<String> stringSparseArray) {

                Log.d("query", stringSparseArray.size()+"");
                for (int i = 0; i < stringSparseArray.size(); i++) {
                    Integer key = stringSparseArray.keyAt(i);
                    mMap.put(key,stringSparseArray.get(key));
                    Log.d("query", key+","+stringSparseArray.get(key));
                }
            }
        });
        PersistenceService.getInstance(this).end();
    }

    public void send(View view){
        TransportService.startFromLocal(this,"http://wthrcdn.etouch.cn/weather_mini",mMap);
    }
}
