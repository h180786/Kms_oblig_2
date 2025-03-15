package com.example.kms;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.init;
import static androidx.test.espresso.intent.Intents.release;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withTagValue;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.CoreMatchers.equalTo;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.kms.Activities.QuizActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class QuizActivityTest {

    @Rule
    public ActivityScenarioRule<QuizActivity> quizRule =
            new ActivityScenarioRule<>(QuizActivity.class);


    @Before
    public void startTest(){
        init();
    }

    @Test
    public void testScore1(){
        onView(withTagValue(equalTo("Wrong answer 1"))).perform(click());
        onView(withTagValue(equalTo("Right answer"))).perform(click());

        onView(withId(R.id.score)).check(matches(withText("1")));
        onView(withId(R.id.totalScore)).check(matches(withText("2")));
    }

    @Test
    public void testScore2(){
        onView(withTagValue(equalTo("Wrong answer 2"))).perform(click());
        onView(withTagValue(equalTo("Right answer"))).perform(click());
        onView(withTagValue(equalTo("Right answer"))).perform(click());

        onView(withId(R.id.score)).check(matches(withText("2")));
        onView(withId(R.id.totalScore)).check(matches(withText("3")));
    }

    @After
    public void endTest(){
     release();
     
    }




}
