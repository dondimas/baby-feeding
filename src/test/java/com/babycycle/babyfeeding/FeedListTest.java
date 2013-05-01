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

    @Test
    public void dbShouldContainAtLeastOneReminder() {
        List<Reminder> reminders = persistenceFacade.getReminders(application);
        assertThat(reminders).isNotEmpty();
    }

    @Test
    public void shouldReturnUpdatedForTodayReminders() {

        List<Reminder> reminders = persistenceFacade.getUpdatedForTodayReminders(application);
        for(Reminder reminder:reminders){
            Calendar reminderCalendar = Calendar.getInstance();
            int currentDay = reminderCalendar.get(Calendar.DAY_OF_MONTH);
            int currentMonth = reminderCalendar.get(Calendar.MONTH);
            reminderCalendar.setTime(reminder.getTimeOfDay());
            assertThat(reminderCalendar.get(Calendar.DAY_OF_MONTH)).describedAs("Day is wrong").isEqualTo(currentDay);
            assertThat(reminderCalendar.get(Calendar.MONTH)).describedAs("Month is wrong").isEqualTo(currentMonth);
        }

    }

    String testReminderMessage = "Some Message";
    @Test
    public void sudReturnProperIsConfirmedValues() {

        updateExistingReminder();

        createReminder();
        List<Reminder> reminders = persistenceFacade.getUpdatedForTodayReminders(application);
        for(Reminder reminder:reminders){
            if(reminder.getRemindMessage().equals(testReminderMessage)){
                assertThat(reminder.isWasConfirmed()).isTrue();
            } else {
                assertThat(reminder.isWasConfirmed()).isFalse();
            }
        }

    }

    private void updateExistingReminder() {
        Reminder reminderBase = persistenceFacade.getReminders(application).get(0);
        reminderBase.setWasConfirmed(true);
        persistenceFacade.saveReminder(reminderBase, application);
    }
//    @Test
//    public void shouldNotBeConfirmedIfApplicationRunAfterReminderTime() {
//        List<Reminder> reminders = persistenceFacade.getReminders(application);
//        Reminder reminder = reminders.get(0);
//        Date reminderDate
//        if()
//        assertThat(reminders).isNotEmpty();
//    }

    private void createReminder() {
        Calendar calendar = Calendar.getInstance();
        Reminder reminder = new Reminder(testReminderMessage, calendar.getTime());
        reminder.setWasConfirmed(true);
        persistenceFacade.saveReminder(reminder, application);
    }
}
