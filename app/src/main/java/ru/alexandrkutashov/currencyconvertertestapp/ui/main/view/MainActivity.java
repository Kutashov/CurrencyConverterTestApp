package ru.alexandrkutashov.currencyconvertertestapp.ui.main.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import ru.alexandrkutashov.currencyconvertertestapp.CurrencyApplication;
import ru.alexandrkutashov.currencyconvertertestapp.R;
import ru.alexandrkutashov.currencyconvertertestapp.ui.main.model.CurrencyModel;
import ru.alexandrkutashov.currencyconvertertestapp.ui.main.presenter.CurrencyPresenter;

public class MainActivity extends AppCompatActivity implements CurrencyView {

    private CurrencyPresenter currencyPresenter = CurrencyApplication.getApplication().getCurrencyPresenter();

    private Spinner fromCurrency;

    private Spinner toCurrency;

    private TextView result;

    private ClearableEditText amount;

    private ProgressBar loading;

    private ImageView revertCurrencies;

    private ImageView convertBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fromCurrency = (Spinner) findViewById(R.id.fromCurrency);
        toCurrency = (Spinner) findViewById(R.id.toCurrency);
        result = (TextView) findViewById(R.id.conversion_result);
        amount = (ClearableEditText) findViewById(R.id.amount);
        loading = (ProgressBar) findViewById(R.id.loading);
        convertBtn = (ImageView) findViewById(R.id.convertBtn);
        convertBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currencyPresenter.onConvertButtonClicked((CurrencyModel) fromCurrency.getSelectedItem(),
                        (CurrencyModel) toCurrency.getSelectedItem(),
                        amount.getEditableText().toString());
            }
        });
        revertCurrencies = (ImageView) findViewById(R.id.revert_currencies);
        revertCurrencies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currencyPresenter.onRevertButtonClicked(
                        fromCurrency.getSelectedItemPosition(), toCurrency.getSelectedItemPosition());
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        currencyPresenter.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        currencyPresenter.bindView(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        currencyPresenter.unbindView();
    }

    @Override
    public void setAdapter(ArrayAdapter adapter) {
        fromCurrency.setAdapter(adapter);
        toCurrency.setAdapter(adapter);
    }

    @Override
    public void updateSpinnersSelection(int positionCurrencyFrom, int positionCurrencyTo) {
        fromCurrency.setSelection(positionCurrencyFrom);
        toCurrency.setSelection(positionCurrencyTo);
    }

    @Override
    public void showConvertButton() {
        convertBtn.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideConvertButton() {
        convertBtn.setVisibility(View.GONE);
    }

    @Override
    public void showResult(String text) {
        result.setText(text);
    }

    @Override
    public void showError(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showProgress() {
        loading.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        loading.setVisibility(View.GONE);
    }
}
