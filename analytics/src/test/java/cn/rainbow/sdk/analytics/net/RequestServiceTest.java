package cn.rainbow.sdk.analytics.net;

import android.app.Application;
import android.content.Intent;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import cn.rainbow.sdk.analytics.BuildConfig;
import cn.rainbow.sdk.analytics.event.Event;

import static org.junit.Assert.*;

/**
 * Created by bvin on 2016/8/22.
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class,sdk = 21)
public class RequestServiceTest {

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testOnHandleIntent() throws Exception {
        Application application = RuntimeEnvironment.application;
        Intent intent = new Intent(application, RequestService.class);
        Event event = new Event(0, "event", "nodesc");
        intent.putExtra("event", event);
        RequestService requestService = new RequestService();
        requestService.onCreate();

        event = new Event(0, "event1", "nodesc");
        intent.putExtra("event", event);

        event = new Event(0, "event2", "nodesc");
        intent.putExtra("event", event);
        //requestService.onDestroy();
    }
}