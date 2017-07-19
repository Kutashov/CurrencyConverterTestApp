package ru.alexandrkutashov.currencyconvertertestapp.tools;

import android.os.IBinder;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.FailureHandler;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.NoMatchingRootException;
import android.support.test.espresso.NoMatchingViewException;
import android.support.test.espresso.PerformException;
import android.support.test.espresso.Root;
import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.ViewAssertion;
import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.base.DefaultFailureHandler;
import android.support.test.espresso.util.HumanReadables;
import android.support.test.espresso.util.TreeIterables;
import android.support.v7.widget.RecyclerView;
import android.test.AssertionFailedError;
import android.view.View;
import android.view.WindowManager;
import android.widget.Spinner;
import android.widget.TextView;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Assert;

import java.util.ArrayList;
import java.util.concurrent.TimeoutException;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.matcher.ViewMatchers.assertThat;
import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static android.support.test.espresso.matcher.ViewMatchers.isRoot;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.view.View.FIND_VIEWS_WITH_TEXT;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;


public final class EspressoTools {

    public static ViewAssertion spinnerHasItemsCount(final Matcher<Integer> count) {
        return new ViewAssertion() {
            @Override
            public void check(View view, NoMatchingViewException e) {
                if (!(view instanceof Spinner)) {
                    throw e;
                }
                Spinner rv = (Spinner) view;
                if (rv.getAdapter() == null) {
                    return;
                }

                assertThat(rv.getAdapter().getCount(), count);
            }
        };
    }


    public static ViewAssertion hasHolderItemAtPosition(final int index,
                                                        final Matcher<RecyclerView.ViewHolder> viewHolderMatcher) {
        return new ViewAssertion() {
            @Override
            public void check(View view, NoMatchingViewException e) {
                if (!(view instanceof RecyclerView)) {
                    throw e;
                }
                RecyclerView rv = (RecyclerView) view;
                Assert.assertThat(rv.findViewHolderForAdapterPosition(index), viewHolderMatcher);
            }
        };
    }

    public static ViewAssertion hasViewWithTextAtPosition(final int index, final CharSequence text) {
        return new ViewAssertion() {
            @Override
            public void check(View view, NoMatchingViewException e) {
                if (!(view instanceof RecyclerView)) {
                    throw e;
                }
                RecyclerView rv = (RecyclerView) view;
                ArrayList<View> outviews = new ArrayList<>();
                rv.findViewHolderForAdapterPosition(index).itemView.findViewsWithText(outviews, text,
                        FIND_VIEWS_WITH_TEXT);
                assertThat(outviews.size(), is(greaterThan(0)));
            }
        };
    }

    public static ViewAssertion doesntHaveViewWithText(final String text) {
        return new ViewAssertion() {
            @Override
            public void check(View view, NoMatchingViewException e) {
                if (!(view instanceof RecyclerView)) {
                    throw e;
                }
                RecyclerView rv = (RecyclerView) view;
                ArrayList<View> outviews = new ArrayList<>();
                for (int index = 0; index < rv.getAdapter().getItemCount(); index++) {
                    rv.findViewHolderForAdapterPosition(index).itemView.findViewsWithText(outviews, text,
                            FIND_VIEWS_WITH_TEXT);
                    if (outviews.size() > 0) break;
                }
                assertThat(outviews.size(), is(0));
            }
        };
    }

    public static ViewAction waitId(final int viewId, final long millis) {
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return isRoot();
            }

            @Override
            public String getDescription() {
                return "wait for a specific view with id <" + viewId + "> during " + millis + " millis.";
            }

            @Override
            public void perform(final UiController uiController, final View view) {
                uiController.loopMainThreadUntilIdle();
                final long startTime = System.currentTimeMillis();
                final long endTime = startTime + millis;
                final Matcher<View> viewMatcher = withId(viewId);

                do {
                    for (View child : TreeIterables.breadthFirstViewTraversal(view)) {
                        // found view with required ID
                        if (viewMatcher.matches(child)) {
                            return;
                        }
                    }

                    uiController.loopMainThreadForAtLeast(50);
                }
                while (System.currentTimeMillis() < endTime);

                // timeout happens
                throw new PerformException.Builder()
                        .withActionDescription(this.getDescription())
                        .withViewDescription(HumanReadables.describe(view))
                        .withCause(new TimeoutException())
                        .build();
            }
        };
    }

    public static String getTextViewText(final Matcher<View> matcher) {
        final String[] stringHolder = {null};
        onView(matcher).perform(new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return isAssignableFrom(TextView.class);
            }

            @Override
            public String getDescription() {
                return "getting text from a TextView";
            }

            @Override
            public void perform(UiController uiController, View view) {
                TextView tv = (TextView) view; //Save, because of check in getConstraints()
                stringHolder[0] = tv.getText().toString();
            }
        });
        return stringHolder[0];
    }

    public static class PassMissingRoot implements FailureHandler {
        private final FailureHandler defaultHandler
                = new DefaultFailureHandler(InstrumentationRegistry.getTargetContext());
        @Override public void handle(Throwable error, Matcher<View> viewMatcher) {
            if (!(error instanceof NoMatchingRootException)) {
                defaultHandler.handle(error, viewMatcher);
            }
        }
    }

    public class WindowManagerLayoutParamTypeMatcher extends TypeSafeMatcher<Root> {
        private final String description;
        private final int type;
        private final boolean expectedWindowTokenMatch;
        public WindowManagerLayoutParamTypeMatcher(String description, int type) {
            this(description, type, true);
        }
        public WindowManagerLayoutParamTypeMatcher(String description, int type, boolean expectedWindowTokenMatch) {
            this.description = description;
            this.type = type;
            this.expectedWindowTokenMatch = expectedWindowTokenMatch;
        }
        @Override public void describeTo(Description description) {
            description.appendText(this.description);
        }
        @Override public boolean matchesSafely(Root root) {
            if (type == root.getWindowLayoutParams().get().type) {
                IBinder windowToken = root.getDecorView().getWindowToken();
                IBinder appToken = root.getDecorView().getApplicationWindowToken();
                if (windowToken == appToken == expectedWindowTokenMatch) {
                    // windowToken == appToken means this window isn't contained by any other windows.
                    // if it was a window for an activity, it would have TYPE_BASE_APPLICATION.
                    return true;
                }
            }
            return false;
        }
    }
}
