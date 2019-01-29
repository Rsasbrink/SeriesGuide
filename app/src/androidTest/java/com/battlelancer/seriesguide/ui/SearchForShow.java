package com.battlelancer.seriesguide.ui;


import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.battlelancer.seriesguide.R;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.pressImeActionButton;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;
@LargeTest
@RunWith(AndroidJUnit4.class)
public class SearchForShow {
    String title = "Friends";

    @Rule
    public ActivityTestRule<ShowsActivity> mActivityTestRule = new ActivityTestRule<>(ShowsActivity.class);

    @Test
    public void searchForShow() {
        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(7000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        ViewInteraction floatingActionButton = onView(
                allOf(withId(R.id.buttonShowsAdd), withContentDescription("Serie toevoegen"),
                        childAtPosition(
                                allOf(withId(R.id.rootLayoutShows),
                                        childAtPosition(
                                                withId(R.id.drawer_layout),
                                                0)),
                                2),
                        isDisplayed()));
        floatingActionButton.perform(click());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(700);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction appCompatAutoCompleteTextView = onView(
                allOf(withId(R.id.editTextSearchBar),
                        childAtPosition(
                                allOf(withId(R.id.containerSearchBar),
                                        childAtPosition(
                                                withId(R.id.sgToolbar),
                                                0)),
                                0),
                        isDisplayed()));
        appCompatAutoCompleteTextView.perform(replaceText(title), closeSoftKeyboard());

        ViewInteraction appCompatAutoCompleteTextView2 = onView(
                allOf(withId(R.id.editTextSearchBar), withText(title),
                        childAtPosition(
                                allOf(withId(R.id.containerSearchBar),
                                        childAtPosition(
                                                withId(R.id.sgToolbar),
                                                0)),
                                0),
                        isDisplayed()));
        appCompatAutoCompleteTextView2.perform(pressImeActionButton());

        ViewInteraction frameLayout = onView(
                allOf(childAtPosition(
                        allOf(withId(R.id.recyclerViewShowsDiscover),
                                childAtPosition(
                                        withId(R.id.constraintLayoutShowsDiscover),
                                        1)),
                        0),
                        isDisplayed()));
        frameLayout.perform(click());

        ViewInteraction textView = onView(
                allOf(withId(R.id.textViewAddTitle),

                        isDisplayed()));
        try {
            Thread.sleep(7000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        textView.check(matches(withText(title)));
    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
