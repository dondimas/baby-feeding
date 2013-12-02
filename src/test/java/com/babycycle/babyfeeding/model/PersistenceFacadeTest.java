package com.babycycle.babyfeeding.model;

import android.app.Application;
import com.babycycle.babyfeeding.test_utils.BabyFeedingTestRunner;
import com.babycycle.babyfeeding.test_utils.DBDataCreationHelper;
import com.babycycle.babyfeeding.ui.controller.FeedingButtonsPanelViewController;
import com.google.inject.Inject;
import com.xtremelabs.robolectric.Robolectric;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.fest.assertions.Assertions.assertThat;

/**
 * Created with IntelliJ IDEA.
 * User: dima
 * Date: 5/2/13
 * Time: 6:50 PM
 * To change this template use File | Settings | File Templates.
 */
@RunWith(BabyFeedingTestRunner.class)
public class PersistenceFacadeTest {
    @Inject
    PersistenceFacade persistenceFacade;

    Application application;

    long maxGapMillis = 600000;

    @Before
    public void setUp() throws Exception {
        application = Robolectric.application;

        PersistenceFacade.setMaxGapOneFeedingMillis(maxGapMillis);
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

    @Test
    public void shouldReturnEventsListWithProperOddEvenValues() {
        createEventsList();
        List<FeedEvent> events = persistenceFacade.getFeedEventList(application);
        for(int i = 1; i < events.size(); i++) {
            if(events.get(i-1).getStartTime().getTime() - events.get(i).getFinishTime().getTime() >maxGapMillis) {
                assertThat(events.get(i).odd).isEqualTo(!events.get(i-1).odd);
            } else {
                assertThat(events.get(i).odd).isEqualTo(events.get(i-1).odd);
            }

        }
    }
    @Test
    public void testSaveReminder() throws Exception {

    }

    @Test
    public void testGetReminders() throws Exception {

    }

    @Test
    public void testGetActiveReminders() throws Exception {

    }
    @Test
    @Ignore
    public void shouldCreateProperTextForStartFeedingButton() throws Exception {
        FeedingButtonsPanelViewController panelViewController = new FeedingButtonsPanelViewController();
        createEventsList();
        persistenceFacade.getFeedEventList(application);
        panelViewController.setLastFeedStartTime(persistenceFacade.getLastFeedStartTime());
        String buttonText = panelViewController.getStartFeedingButtonText();
        assertThat(buttonText).contains("Start");
    }

    @Test
    @Ignore
    public void shouldReturnRunningEventIfPreviouslyEventWasStarted() {
        //given
        //updating running event to be sure the finish date is set
        FeedEvent feedEvent = persistenceFacade.getRunningFeedEvent(application);
        if(feedEvent != null) {
            feedEvent.setFinishTime(new Date());
            persistenceFacade.saveFeedEvent(feedEvent, application);
        }
        //when
        FeedEvent startingEventDTO = new FeedEvent();
        Date startingEventTime = new Date();
        startingEventDTO.setStartTime(startingEventTime);
        persistenceFacade.persistStartedFeedEvent(startingEventDTO, application);

        //than
        FeedEvent runningFeedEvent = persistenceFacade.getRunningFeedEvent(application);
        assertThat(runningFeedEvent).isNotNull();
        assertThat(runningFeedEvent.getStartTime().getTime()).isEqualTo(startingEventTime.getTime());
        assertThat(runningFeedEvent.getFinishTime()).isNull();
    }

    @Test
    public void shouldReturnNoFeedEventIfRunningEventWasDeleted() {
        //when
        FeedEvent startingEventDTO = new FeedEvent();
        Date startingEventTime = new Date();
        startingEventDTO.setStartTime(startingEventTime);
        persistenceFacade.persistStartedFeedEvent(startingEventDTO, application);
        persistenceFacade.deleteStartedFeedEvent(application);

        //than
        FeedEvent runningFeedEvent = persistenceFacade.getRunningFeedEvent(application);
        assertThat(runningFeedEvent).isNull();

    }

    @Test
    public void dbShouldReturnLastEvents() {
        List<FeedEvent> reminders = persistenceFacade.getFeedEventList(application);
        assertThat(reminders).isNotEmpty();
    }

    private void createEventsList() {
        DBDataCreationHelper.createEventsList(persistenceFacade, application, maxGapMillis);
    }
}
