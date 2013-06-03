package com.babycycle.babyfeeding.ui.tab_fragments;

import android.app.Application;
import com.babycycle.babyfeeding.model.FeedDay;
import com.babycycle.babyfeeding.model.PersistenceFacade;
import com.babycycle.babyfeeding.test_utils.BabyFeedingTestRunner;
import com.babycycle.babyfeeding.test_utils.DBDataCreationHelper;
import com.google.inject.Inject;
import com.xtremelabs.robolectric.Robolectric;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Calendar;
import java.util.List;

import static org.fest.assertions.Assertions.assertThat;

/**
 * Created with IntelliJ IDEA.
 * User: dima
 * Date: 6/3/13
 * Time: 10:42 AM
 * To change this template use File | Settings | File Templates.
 */
@RunWith(BabyFeedingTestRunner.class)
public class StatsPresenterTest {

    @Inject
    PersistenceFacade persistenceFacade;

    Application application;

    long maxGapMillis = 6000000;

    @Inject
    StatsPresenter statsPresenter;

    @Before
    public void setUp() throws Exception {
        application = Robolectric.application;

        PersistenceFacade.setMaxGapOneFeedingMillis(maxGapMillis);
    }

    @Test
    public void testRefreshListData() throws Exception {
        DBDataCreationHelper.createEventsList(persistenceFacade, application, maxGapMillis);
        //when
        List<FeedDay> dayList = statsPresenter.pullEventsAndGroupFeedDays();

        //than
        assertThat(dayList).isNotEmpty();
        assertThat(dayList.get(0).getChemical()).describedAs("Chemical amount is wrong").isEqualTo(120);
        assertThat(dayList.get(0).getNatural()).describedAs("Natural amount is wrong").isEqualTo(60);
        for(FeedDay feedDay:dayList)
        {
            assertThat(feedDay.getDay()).isNotNull();
        }
    }
}
