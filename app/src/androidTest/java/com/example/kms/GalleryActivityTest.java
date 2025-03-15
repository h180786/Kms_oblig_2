package com.example.kms;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.intent.Intents.init;
import static androidx.test.espresso.intent.Intents.release;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import static com.example.kms.RecyclerViewItemCountAssertion.withItemCount;

import android.os.SystemClock;
import android.view.View;

import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.kms.Activities.GalleryActivity;

import org.hamcrest.Matcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class GalleryActivityTest {

    @Rule
    public ActivityScenarioRule<GalleryActivity> activityRule =
            new ActivityScenarioRule<>(GalleryActivity.class);

    @Before
    public void startTest() {
        init();
    }

    @Test
    public void deleteImage() {
        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.
                actionOnItemAtPosition(0, clickChildViewWithId(R.id.deleteButton)));
        SystemClock.sleep(1000);
        onView(withId(R.id.recyclerView)).check(withItemCount(2));

    }

    @After
    public void endTest() {
        release();
    }

    private static ViewAction clickChildViewWithId(final int id) {
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return isDisplayed();
            }

            @Override
            public String getDescription() {
                return "Click on a child view with specified id.";
            }


            @Override
            public void perform(UiController uiController, View view) {
                View v = view.findViewById(id);
                v.performClick();
            }
        };
    }
}