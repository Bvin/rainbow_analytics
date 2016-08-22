package cn.rainbow.sdk.analytics.net.response.reader;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by bvin on 2016/8/22.
 */
public class ByteArrayResponseReader implements ResponseReader<byte[]>{

    @Override
    public byte[] read(InputStream inputStream) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len;
        while ((len = inputStream.read(buffer)) != -1) {
            out.write(buffer, 0, len);
        }
        byte[] totals = out.toByteArray();
        out.close();
        return totals;
    }
}
