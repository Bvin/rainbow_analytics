package cn.rainbow.sdk.analytics.net.response.reader;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by bvin on 2016/8/18.
 */
public interface ResponseReader<T> {
    T read(InputStream inputStream) throws IOException;
}
