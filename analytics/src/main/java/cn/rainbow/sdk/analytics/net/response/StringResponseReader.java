package cn.rainbow.sdk.analytics.net.response;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by bvin on 2016/8/18.
 */
public class StringResponseReader implements ResponseReader<String>{

    @Override
    public String read(InputStream inputStream) throws IOException {
        BufferedReader reader  = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
        StringBuilder sb = new StringBuilder();
        String s;
        while ((s = reader.readLine()) != null) {
            sb.append(s);
        }
        return sb.toString();
    }
}
