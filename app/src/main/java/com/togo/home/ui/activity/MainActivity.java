package com.togo.home.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.togo.home.R;
import com.togo.home.ui.app.App;
import com.togo.home.data.model.ApiResponse;
import com.squareup.picasso.Picasso;

import java.sql.Date;
import java.text.SimpleDateFormat;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


public class MainActivity extends Activity {
    private final static String TAG = MainActivity.class.getSimpleName();

    @BindView(R.id.activity_main_data)
    protected RelativeLayout dataLayout;

    @BindView(R.id.activity_main_weather)
    protected RelativeLayout weatherLayout;

    @BindView(R.id.activity_main_search)
    protected EditText searchEditText;

    @BindView(R.id.activity_main_sys_country_value)
    protected TextView countryTextView;

    @BindView(R.id.activity_main_sys_sunrise_value)
    protected TextView sunriseTextView;

    @BindView(R.id.activity_main_sys_sunset_value)
    protected TextView sunsetTextView;

    @BindView(R.id.activity_main_weather_icon)
    protected ImageView iconImageView;

    @BindView(R.id.activity_main_weather_text)
    protected TextView weatherTextView;

    private Disposable disposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != disposable && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }

    @OnClick(R.id.activity_main_search_button)
    protected void onSearchClick()
    {
        if (!searchEditText.getText().toString().equals(""))
        {
            disposable = App.getRestClient().getWeatherService().getWeather(searchEditText.getText().toString())
                    .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<ApiResponse>() {
                                   @Override
                                   public void accept(@NonNull ApiResponse apiResponse) throws Exception {
                                       handleResponse(apiResponse);
                                   }
                               },
                            new Consumer<Throwable>() {
                                @Override
                                public void accept(@NonNull Throwable throwable) throws Exception {
                                    handleFailure(throwable.getMessage());
                                }
                            });
        }
    }

    private void handleResponse(ApiResponse apiResponse) {
        final Date sunriseDate = new Date(apiResponse.getSys().getSunriseTime() * 1000);
        final Date sunsetDate = new Date(apiResponse.getSys().getSunsetTime() * 1000);
        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh':'mm':'ss a");

        getActionBar().setTitle(apiResponse.getStrCityName());
        countryTextView.setText(apiResponse.getSys().getStrCountry());

        if (!apiResponse.getWeather().isEmpty())
        {
            Picasso.with(MainActivity.this).load("http://openweathermap.org/img/w/" + apiResponse.getWeather().get(0).getStrIconName() + ".png").into(iconImageView);
            weatherTextView.setText(apiResponse.getWeather().get(0).getStrDesc());
        }

        sunsetTextView.setText(simpleDateFormat.format(sunsetDate));
        sunriseTextView.setText(simpleDateFormat.format(sunriseDate));

        searchEditText.setText("");
        Log.e(TAG, "City name : " + apiResponse.getStrCityName());
        dataLayout.setVisibility(View.VISIBLE);
        weatherLayout.setVisibility(View.VISIBLE);
    }

    private void handleFailure(String message) {
        Log.e(TAG, "Error : " + message);
        searchEditText.setText("");
        Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
        dataLayout.setVisibility(View.GONE);
        weatherLayout.setVisibility(View.GONE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
