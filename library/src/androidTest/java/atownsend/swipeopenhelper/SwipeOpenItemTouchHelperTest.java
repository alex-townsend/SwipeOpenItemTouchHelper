package atownsend.swipeopenhelper;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.action.GeneralLocation;
import android.support.test.espresso.action.GeneralSwipeAction;
import android.support.test.espresso.action.Press;
import android.support.test.espresso.action.Swipe;
import android.support.test.espresso.matcher.BoundedMatcher;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import atownsend.swipeopenhelper.test.R;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.core.deps.guava.base.Preconditions.checkNotNull;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Instrumentation tests for SwipeOpenItemTouchHelper
 */
@RunWith(AndroidJUnit4.class) public class SwipeOpenItemTouchHelperTest {

  @Rule public final ActivityTestRule<SwipeOpenItemTouchHelperTestActivity> activityRule =
      new ActivityTestRule<>(SwipeOpenItemTouchHelperTestActivity.class);

  private final Instrumentation instrumentation = InstrumentationRegistry.getInstrumentation();

  private SwipeOpenItemTouchHelper helper;

  @Before public void setup() {
    SwipeOpenItemTouchHelperTestActivity activity = activityRule.getActivity();
    // reset the orientation to portrait -- seems to prevent tests from failing after the state saving test
    activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    helper = activity.helper;
  }

  @Test public void swipeCloseOnActionTest() {

    // swipe open position 1
    onView(withId(R.id.test_recycler)).perform(actionOnItemAtPosition(1, swipeRight()));
    onView(withId(R.id.test_recycler)).check(matches(atPosition(1, checkTranslationX(true))));

    // swipe open position 3
    onView(withId(R.id.test_recycler)).perform(actionOnItemAtPosition(3, swipeLeft()));

    // position 1 should have closed, and position 3 should be open
    onView(withId(R.id.test_recycler)).check(matches(atPosition(3, checkTranslationX(false))));
    onView(withId(R.id.test_recycler)).check(matches(atPosition(1, checkZeroTranslation())));
  }

  @Test public void scrollCloseOnActionTest() {

    // swipe open position 2
    onView(withId(R.id.test_recycler)).perform(actionOnItemAtPosition(2, swipeRight()));
    onView(withId(R.id.test_recycler)).check(matches(atPosition(2, checkTranslationX(true))));

    // scroll on position 3
    onView(withId(R.id.test_recycler)).perform(actionOnItemAtPosition(3, scroll()));

    // position 2 should now be closed
    onView(withId(R.id.test_recycler)).check(matches(atPosition(2, checkZeroTranslation())));
  }

  @Test public void stateSavingTest() {
    instrumentation.runOnMainSync(new Runnable() {
      @Override public void run() {
        helper.setCloseOnAction(false);
      }
    });

    // open positions 1 and 2
    onView(withId(R.id.test_recycler)).perform(actionOnItemAtPosition(1, swipeRight()));
    onView(withId(R.id.test_recycler)).perform(actionOnItemAtPosition(2, swipeLeft()));

    // rotate the screen
    rotateScreen();

    // both positions should still be open
    onView(withId(R.id.test_recycler)).check(matches(atPosition(1, checkTranslationX(true))));
    onView(withId(R.id.test_recycler)).check(matches(atPosition(2, checkTranslationX(false))));
  }

  @Test public void closePositionsTest() {
    instrumentation.runOnMainSync(new Runnable() {
      @Override public void run() {
        helper.setCloseOnAction(false);
      }
    });
    // open positions 1 and 2
    onView(withId(R.id.test_recycler)).perform(actionOnItemAtPosition(1, swipeRight()));
    onView(withId(R.id.test_recycler)).perform(actionOnItemAtPosition(2, swipeLeft()));

    instrumentation.runOnMainSync(new Runnable() {
      @Override public void run() {
        helper.closeOpenPosition(1);
        helper.closeOpenPosition(2);
      }
    });

    // both positions should be closed
    onView(withId(R.id.test_recycler)).check(matches(atPosition(1, checkZeroTranslation())));
    onView(withId(R.id.test_recycler)).check(matches(atPosition(2, checkZeroTranslation())));
  }

  @Test public void closeAllPositionsTest() {
    instrumentation.runOnMainSync(new Runnable() {
      @Override public void run() {
        helper.setCloseOnAction(false);
      }
    });
    // open positions 1 and 2
    onView(withId(R.id.test_recycler)).perform(actionOnItemAtPosition(1, swipeRight()));
    onView(withId(R.id.test_recycler)).perform(actionOnItemAtPosition(2, swipeLeft()));

    instrumentation.runOnMainSync(new Runnable() {
      @Override public void run() {
        helper.closeAllOpenPositions();
      }
    });

    // both positions should be closed
    onView(withId(R.id.test_recycler)).check(matches(atPosition(1, checkZeroTranslation())));
    onView(withId(R.id.test_recycler)).check(matches(atPosition(2, checkZeroTranslation())));
  }

  /**
   * Uses a slow swipe to simulate a scroll
   * @return the view action
   */
  private ViewAction scroll() {
    return new GeneralSwipeAction(Swipe.SLOW, GeneralLocation.BOTTOM_CENTER,
        GeneralLocation.TOP_CENTER, Press.FINGER);
  }

  /**
   * Rotates the screen of the test activity
   */
  private void rotateScreen() {
    final Context context = InstrumentationRegistry.getTargetContext();
    final int orientation = context.getResources().getConfiguration().orientation;

    Activity activity = activityRule.getActivity();
    activity.setRequestedOrientation((orientation == Configuration.ORIENTATION_PORTRAIT)
        ? ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE : ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
  }

  /**
   * Matcher for finding the SwipeView for a SwipeOpenViewHolders in a RecyclerView
   * @param position the position of the view holder
   * @param itemMatcher matcher to compare the SwipeView to
   * @return a Matcher that compares a SwipeOpenViewHolder at a position with a passed in matcher
   */
  public static Matcher<View> atPosition(final int position, @NonNull final Matcher<View> itemMatcher) {
    checkNotNull(itemMatcher);
    return new BoundedMatcher<View, RecyclerView>(RecyclerView.class) {
      @Override protected boolean matchesSafely(RecyclerView view) {
        RecyclerView.ViewHolder viewHolder = view.findViewHolderForAdapterPosition(position);
        if (viewHolder == null || !(viewHolder instanceof SwipeOpenViewHolder)) {
          // has no item on such position
          return false;
        }
        return itemMatcher.matches(((SwipeOpenViewHolder) viewHolder).getSwipeView());
      }

      @Override
      public void describeTo(Description description) {
        description.appendText("has item at position " + position + ": ");
        itemMatcher.describeTo(description);
      }

    };
  }

  /**
   * Checks for a positive or negative translationX in a View
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
          return ViewCompat.getTranslationX(item) > 0;
        } else {
          return ViewCompat.getTranslationX(item) < 0;
        }
      }
    };
  }

  /**
   * Checks for zero translation of a view
   * @return a matcher that checks that all translation on a view is zero
   */
  public static Matcher<View> checkZeroTranslation() {
    return new BoundedMatcher<View, View>(View.class) {

      @Override public void describeTo(Description description) {
        description.appendText("translation should be zero");
      }

      @Override protected boolean matchesSafely(View item) {
          return ViewCompat.getTranslationX(item) == 0 && ViewCompat.getTranslationY(item) == 0;
      }
    };
  }

  private static ViewAction swipeRight() {
    return new GeneralSwipeAction(Swipe.FAST, GeneralLocation.CENTER_LEFT,
        GeneralLocation.CENTER_RIGHT, Press.FINGER);
  }

  private static ViewAction swipeLeft() {
    return new GeneralSwipeAction(Swipe.FAST, GeneralLocation.CENTER_RIGHT,
        GeneralLocation.CENTER_LEFT, Press.FINGER);
  }
}
