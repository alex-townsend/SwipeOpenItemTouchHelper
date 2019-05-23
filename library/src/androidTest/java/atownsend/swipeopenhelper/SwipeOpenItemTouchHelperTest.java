package atownsend.swipeopenhelper;

import android.view.View;
import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.action.GeneralLocation;
import androidx.test.espresso.action.GeneralSwipeAction;
import androidx.test.espresso.action.Press;
import androidx.test.espresso.action.Swipe;
import androidx.test.espresso.matcher.BoundedMatcher;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import atownsend.swipeopenhelper.test.R;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.swipeLeft;
import static androidx.test.espresso.action.ViewActions.swipeRight;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

/**
 * Instrumentation tests for SwipeOpenItemTouchHelper
 */
@RunWith(AndroidJUnit4.class) public class SwipeOpenItemTouchHelperTest {

  @Test public void swipeCloseOnActionTest() {

    try (ActivityScenario<SwipeOpenItemTouchHelperTestActivity> ignored =
             ActivityScenario.launch(SwipeOpenItemTouchHelperTestActivity.class)) {

      // swipe open position 1
      onView(withId(R.id.test_recycler)).perform(actionOnItemAtPosition(1, swipeRight()));
      onView(withText("Test 1")).check(matches(checkTranslationX(true)));

      // swipe open position 3
      onView(withId(R.id.test_recycler)).perform(actionOnItemAtPosition(3, swipeLeft()));

      // position 1 should have closed, and position 3 should be open
      onView(withText("Test 3")).check(matches(checkTranslationX(false)));
      onView(withText("Test 1")).check(matches(checkZeroTranslation()));
    }
  }

  @Test public void scrollCloseOnActionTest() {
    try (ActivityScenario<SwipeOpenItemTouchHelperTestActivity> ignored = ActivityScenario.launch(
        SwipeOpenItemTouchHelperTestActivity.class)) {

      // swipe open position 2
      onView(withId(R.id.test_recycler)).perform(actionOnItemAtPosition(2, swipeRight()));
      onView(withText("Test 2")).check(matches(checkTranslationX(true)));

      // scroll on position 3
      onView(withId(R.id.test_recycler)).perform(actionOnItemAtPosition(3, scroll()));

      // position 2 should now be closed
      onView(withText("Test 2")).check(matches(checkZeroTranslation()));
    }
  }

  @Test
  public void stateSavingTest() {
    try (ActivityScenario<SwipeOpenItemTouchHelperTestActivity> scenario = ActivityScenario.launch(
        SwipeOpenItemTouchHelperTestActivity.class)) {

      scenario.onActivity(activity -> activity.helper.setCloseOnAction(false));

      // open positions 1 and 2
      onView(withId(R.id.test_recycler)).perform(actionOnItemAtPosition(1, swipeRight()));
      onView(withId(R.id.test_recycler)).perform(actionOnItemAtPosition(2, swipeLeft()));

      // recreate the activity
      scenario.recreate();

      // both positions should still be open
      onView(withText("Test 1")).check(matches(checkTranslationX(true)));
      onView(withText("Test 2")).check(matches(checkTranslationX(false)));
    }
  }

  @Test public void closePositionsTest() {
    try (ActivityScenario<SwipeOpenItemTouchHelperTestActivity> scenario = ActivityScenario.launch(
        SwipeOpenItemTouchHelperTestActivity.class)) {

      scenario.onActivity(activity -> activity.helper.setCloseOnAction(false));

      // open positions 1 and 2
      onView(withId(R.id.test_recycler)).perform(actionOnItemAtPosition(1, swipeRight()));
      onView(withId(R.id.test_recycler)).perform(actionOnItemAtPosition(2, swipeLeft()));

      scenario.onActivity(activity -> {
        activity.helper.closeOpenPosition(1);
        activity.helper.closeOpenPosition(2);
      });

      // both positions should be closed
      onView(withText("Test 1")).check(matches(checkZeroTranslation()));
      onView(withText("Test 2")).check(matches(checkZeroTranslation()));
    }
  }

  @Test public void closeAllPositionsTest() {
    try (ActivityScenario<SwipeOpenItemTouchHelperTestActivity> scenario =
             ActivityScenario.launch(SwipeOpenItemTouchHelperTestActivity.class)) {
      scenario.onActivity(activity -> activity.helper.setCloseOnAction(false));
      // open positions 1 and 2
      onView(withId(R.id.test_recycler)).perform(actionOnItemAtPosition(1, swipeRight()));
      onView(withId(R.id.test_recycler)).perform(actionOnItemAtPosition(2, swipeLeft()));

      scenario.onActivity(activity -> activity.helper.closeAllOpenPositions());

      // both positions should be closed
      onView(withText("Test 1")).check(matches(checkZeroTranslation()));
      onView(withText("Test 2")).check(matches(checkZeroTranslation()));
    }
  }

  @Test public void openPositionTest() {
    try (ActivityScenario<SwipeOpenItemTouchHelperTestActivity> scenario =
             ActivityScenario.launch(SwipeOpenItemTouchHelperTestActivity.class)) {
      scenario.onActivity(activity -> {
        activity.helper.setCloseOnAction(true);
        activity.helper.openPositionStart(1);
      });

      onView(withText("Test 1")).check(matches(checkTranslationX(true)));
      scenario.onActivity(activity -> activity.helper.openPositionEnd(2));

      onView(withText("Test 2")).check(matches(checkTranslationX(false)));
      onView(withText("Test 1")).check(matches(checkZeroTranslation()));
    }
  }

  /**
   * Uses a slow swipe to simulate a scroll
   *
   * @return the view action
   */
  private ViewAction scroll() {
    return new GeneralSwipeAction(Swipe.SLOW, GeneralLocation.BOTTOM_CENTER,
        GeneralLocation.TOP_CENTER, Press.FINGER);
  }

  /**
   * Checks for a positive or negative translationX in a View
   *
   * @param positive true if positive translation, false if negative
   * @return matcher for checking positive/negative translationX
   */
  public static Matcher<View> checkTranslationX(final boolean positive) {
    return new BoundedMatcher<View, View>(View.class) {

      @Override public void describeTo(Description description) {
        description.appendText("translationX should be non-zero");
      }

      @Override protected boolean matchesSafely(View item) {
        if (positive) {
          return item.getTranslationX() > 0;
        } else {
          return item.getTranslationX() < 0;
        }
      }
    };
  }

  /**
   * Checks for zero translation of a view
   *
   * @return a matcher that checks that all translation on a view is zero
   */
  public static Matcher<View> checkZeroTranslation() {
    return new BoundedMatcher<View, View>(View.class) {

      @Override public void describeTo(Description description) {
        description.appendText("translation should be zero");
      }

      @Override protected boolean matchesSafely(View item) {
        return item.getTranslationX() == 0;
      }
    };
  }
}
