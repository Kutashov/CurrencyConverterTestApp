package ru.alexandrkutashov.currencyconvertertestapp;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import ru.alexandrkutashov.currencyconvertertestapp.data.providers.LocalCache;
import ru.alexandrkutashov.currencyconvertertestapp.tools.BaseNetworkTest;
import ru.alexandrkutashov.currencyconvertertestapp.tools.EspressoTools;
import ru.alexandrkutashov.currencyconvertertestapp.ui.main.model.CurrencyModel;
import ru.alexandrkutashov.currencyconvertertestapp.ui.main.view.MainActivity;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.assertThat;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static ru.alexandrkutashov.currencyconvertertestapp.tools.EspressoTools.getTextViewText;

/**
 * Created by Alexandr on 18.07.2017.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CurrencyScreenTest extends BaseNetworkTest {

    private static final String TAG = CurrencyScreenTest.class.getSimpleName();

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(MainActivity.class);

    @BeforeClass
    public static void onceExecutedBeforeAll() {
        setWifiDisabled(CurrencyApplication.getApplication());
        new LocalCache(CurrencyApplication.getApplication()).setCurrencies("");
    }

    @Before
    public void executedBeforeEach() {
        onView(withId(R.id.amount)).perform(clearText());
    }

    @Test
    public void A_EmptyCacheNoNetwork() {
        setWifiEnabled(CurrencyApplication.getApplication(), false);
        registerNetworkReceiver(mActivityRule.getActivity());

        onView(withId(R.id.fromCurrency)).check(EspressoTools.spinnerHasItemsCount(is(0)));
        onView(withId(R.id.toCurrency)).check(EspressoTools.spinnerHasItemsCount(is(0)));
        onView(withId(R.id.convertBtn)).check(matches(isDisplayed()));
        onView(withText(R.string.no_internet)).inRoot(
                withDecorView(not(is(mActivityRule.getActivity().getWindow().getDecorView()))))
                .check(matches(isDisplayed()));

        setWifiEnabled(mActivityRule.getActivity(), true);
        unregisterNetworkReceiver(mActivityRule.getActivity());
    }

    @Test
    public void B_EmptyCacheHasNetwork() {
        onView(withId(R.id.fromCurrency)).check(EspressoTools.spinnerHasItemsCount(is(greaterThan(0))));
        onView(withId(R.id.toCurrency)).check(EspressoTools.spinnerHasItemsCount(is(greaterThan(0))));

        onView(withText(R.string.no_internet)).inRoot(
                withDecorView(not(is(mActivityRule.getActivity().getWindow().getDecorView()))))
                .withFailureHandler(new EspressoTools.PassMissingRoot())
                .check(matches(not(isDisplayed())));

        setWifiEnabled(mActivityRule.getActivity(), false); //for the next test
    }

    @Test
    public void C_NotEmptyCacheNoNetwork() {
        registerNetworkReceiver(mActivityRule.getActivity());

        onView(withId(R.id.fromCurrency)).check(EspressoTools.spinnerHasItemsCount(is(greaterThan(0))));
        onView(withId(R.id.toCurrency)).check(EspressoTools.spinnerHasItemsCount(is(greaterThan(0))));

        onView(withText(R.string.no_internet)).inRoot(
                withDecorView(not(is(mActivityRule.getActivity().getWindow().getDecorView()))))
                .check(matches(isDisplayed()));

        setWifiEnabled(mActivityRule.getActivity(), true);
        unregisterNetworkReceiver(mActivityRule.getActivity());
    }

    @Test
    public void D_CalculateAndSwapCurrencies() {
        onView(withId(R.id.fromCurrency)).perform(click());
        onData(allOf(is(instanceOf(CurrencyModel.class)))).atPosition(2).perform(click());

        onView(withId(R.id.toCurrency)).perform(click());
        onData(allOf(is(instanceOf(CurrencyModel.class)))).atPosition(3).perform(click());

        onView(withId(R.id.amount)).perform(typeText("1"));
        onView(withId(R.id.convertBtn)).perform(click());
        String result1 = getTextViewText(withId(R.id.conversion_result));

        onView(withId(R.id.revert_currencies)).perform(click());
        onView(withId(R.id.convertBtn)).perform(click());
        String result2 = getTextViewText(withId(R.id.conversion_result));

        int err = 100;
        assertThat((int) (Float.parseFloat(result1) * err), is((int) (1 / Float.parseFloat(result2) * err)));
    }


}