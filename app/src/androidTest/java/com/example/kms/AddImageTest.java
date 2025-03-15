package com.example.kms;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.intent.Intents.init;
import static androidx.test.espresso.intent.Intents.intending;
import static androidx.test.espresso.intent.Intents.release;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasAction;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import static com.example.kms.RecyclerViewItemCountAssertion.withItemCount;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.net.Uri;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.kms.Activities.GalleryActivity;
import com.example.kms.RecyclerView.RecyclerViewAdapter;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class AddImageTest {

    @Rule
    public ActivityScenarioRule<GalleryActivity> activityRule =
            new ActivityScenarioRule<>(GalleryActivity.class);

    private RecyclerViewAdapter adapter;
    int itemCount;

    @Before
    public void startTest() {
        GalleryActivity.setTesting(true);
        init();
        activityRule.getScenario().onActivity(activity -> {
            adapter = (RecyclerViewAdapter) ((RecyclerView) activity.findViewById(R.id.recyclerView)).getAdapter();
            itemCount = adapter.getItemCount();
        });
    }

    @Test
    public void addQuizTest() {

        Intent resultData = new Intent();
        Uri imageUri = Uri.parse("content://media/external/images/media/1");
        resultData.setData(imageUri);
        Instrumentation.ActivityResult result = new Instrumentation.ActivityResult(Activity.RESULT_OK, resultData);

        intending(hasAction(Intent.ACTION_OPEN_DOCUMENT)).respondWith(result);

        onView(withId(R.id.galleryButton)).perform(click());
        onView(withId(R.id.correctAnswer)).perform(click()).perform(typeText("Test"), closeSoftKeyboard());


        onView(withId(R.id.submitButton)).perform(click());

        onView(withId(R.id.recyclerView)).check(withItemCount(itemCount+1));
    }

    @After
    public void endTest(){
        release();
    }

}
