package com.babycycle.babyfeeding;

import android.app.Application;
import com.babycycle.babyfeeding.model.PersistenceFacade;
import com.babycycle.babyfeeding.model.Reminder;
import com.babycycle.babyfeeding.test_utils.BabyFeedingTestRunner;
import com.google.inject.Inject;
import com.xtremelabs.robolectric.Robolectric;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.fest.assertions.Assertions.assertThat;

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

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void dbShouldContainOneReminder() {
        Application application = Robolectric.application;
        List<Reminder> reminders = persistenceFacade.getReminders(application);
        assertThat(reminders).isNotEmpty();
    }
}
