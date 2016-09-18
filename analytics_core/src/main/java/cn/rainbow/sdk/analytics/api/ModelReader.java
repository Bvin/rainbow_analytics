package cn.rainbow.sdk.analytics.api;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import cn.rainbow.sdk.analytics.net.response.reader.ResponseReader;

/**
 * Created by bvin on 2016/9/14.
 */
public class ModelReader implements ResponseReader<Model>{

    @Override
    public Model read(InputStream inputStream) throws IOException {
        BufferedReader reader  = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
        StringBuilder sb = new StringBuilder();
        String s;
        while ((s = reader.readLine()) != null) {
            sb.append(s);
        }
        try {
            return new Model(sb.toString());
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}
