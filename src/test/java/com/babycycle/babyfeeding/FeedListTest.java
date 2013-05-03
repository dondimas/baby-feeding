package com.babycycle.babyfeeding;

import android.app.Application;
import android.content.Context;
import com.babycycle.babyfeeding.model.PersistenceFacade;
import com.babycycle.babyfeeding.model.Reminder;
import com.babycycle.babyfeeding.test_utils.BabyFeedingTestRunner;
import com.google.inject.Inject;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.xtremelabs.robolectric.Robolectric;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.fest.assertions.Assertions.assertThat;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: dima
 * Date: 4/30/13
 * Time: 11:48 PM
 * To change this template use File | Settings | File Templates.
 */
@RunWith(BabyFeedingTestRunner.class)
public class FeedListTest {

    @Inject
    PersistenceFacade persistenceFacade;

    Application application;

    @Before
    public void setUp() throws Exception {
        application = Robolectric.application;
    }


}
