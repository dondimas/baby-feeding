package com.babycycle.babyfeeding.ui.controller;

import android.app.Application;
import android.widget.ImageView;
import com.babycycle.babyfeeding.R;
import com.babycycle.babyfeeding.model.PersistenceFacade;
import com.babycycle.babyfeeding.test_utils.BabyFeedingTestRunner;
import com.babycycle.babyfeeding.ui.activity.FeedRunningActivity;
import com.babycycle.babyfeeding.ui.activity.helpers.TabsCommunicator;
import com.google.inject.Inject;
import com.xtremelabs.robolectric.Robolectric;
import com.xtremelabs.robolectric.shadows.ShadowImageView;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created with IntelliJ IDEA.
 * User: dima
 * Date: 01/12/13
 * Time: 15:07
 * To change this template use File | Settings | File Templates.
 */
@RunWith(BabyFeedingTestRunner.class)
public class FeedRunningActivityControllerTest {

    @Inject
    PersistenceFacade persistenceFacade;

    @Inject
    TabsCommunicator tabsCommunicator;

    @Inject
    FeedRunningActivityController feedRunningActivityController;

    Application application;
    FeedRunningActivity feedRunningActivity;
    ShadowImageView bottleShadow;
    ShadowImageView leftBreastShadow;
    ShadowImageView rightBreastShadow;

    @Before
    public void setUp() throws Exception {
        application = Robolectric.application;
        feedRunningActivity = mock(FeedRunningActivity.class);

        when(feedRunningActivity.getLeftBreastImageView()).thenReturn(prepareImageRadioButton(R.drawable.left_breast));
        when(feedRunningActivity.getRightBreastImageView()).thenReturn(prepareImageRadioButton(R.drawable.right_breast));
        when(feedRunningActivity.getBottleImageView()).thenReturn(prepareImageRadioButton(R.drawable.bottle));


        when(feedRunningActivity.getResources()).thenReturn(application.getResources());

        feedRunningActivityController.setFeedRunningActivity(feedRunningActivity);

        bottleShadow = Robolectric.shadowOf(feedRunningActivity.getBottleImageView());
        leftBreastShadow = Robolectric.shadowOf(feedRunningActivity.getLeftBreastImageView());
        rightBreastShadow = Robolectric.shadowOf(feedRunningActivity.getRightBreastImageView());

    }

    private ImageView prepareImageRadioButton(int drawableId) {
        ImageView image = new ImageView(application);
        image.setImageDrawable(application.getResources().getDrawable(drawableId));
        return image;
    }

    @Test
    public void shouldSetLeftBreastSelectedWhenLeftWasPreviouslySelected() {
        //given
        tabsCommunicator.setSelectedSource(TabsCommunicator.FeedSource.LEFT_BREAST);

        //when
        feedRunningActivityController.startFeedRunning();
        //than
        assertThat(leftBreastShadow.getImageDrawable()).isEqualTo(application.getResources().getDrawable(R.drawable.left_breast_selected));
        assertThat(rightBreastShadow.getImageDrawable()).isEqualTo(application.getResources().getDrawable(R.drawable.right_breast));
        assertThat(bottleShadow.getImageDrawable()).isEqualTo(application.getResources().getDrawable(R.drawable.bottle));
    }

    @Test
    public void shouldHaveRightBreastSelectedWhenRightWasPreviouslySelected() {
        //given
        tabsCommunicator.setSelectedSource(TabsCommunicator.FeedSource.RIGHT_BREAST);

        //when
        feedRunningActivityController.startFeedRunning();
        //than
        assertThat(rightBreastShadow.getImageDrawable()).isEqualTo(application.getResources().getDrawable(R.drawable.right_breast_selected));

        assertThat(bottleShadow.getImageDrawable()).isEqualTo(application.getResources().getDrawable(R.drawable.bottle));
        assertThat(leftBreastShadow.getImageDrawable()).isEqualTo(application.getResources().getDrawable(R.drawable.left_breast));
    }

    @Test
    public void shouldHaveBottleSelectedWhenBottleWasPreviouslySelected() {
        //given
        tabsCommunicator.setSelectedSource(TabsCommunicator.FeedSource.BOTTLE);

        //when
        feedRunningActivityController.startFeedRunning();
        //than
        assertThat(bottleShadow.getImageDrawable()).isEqualTo(application.getResources().getDrawable(R.drawable.bottle_selected));

        assertThat(rightBreastShadow.getImageDrawable()).isEqualTo(application.getResources().getDrawable(R.drawable.right_breast));
        assertThat(leftBreastShadow.getImageDrawable()).isEqualTo(application.getResources().getDrawable(R.drawable.left_breast));
    }

}
