package com.andexert.retrofitexample.ui.activity;

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

import com.andexert.retrofitexample.R;
import com.andexert.retrofitexample.app.App;
import com.andexert.retrofitexample.rest.model.ApiResponse;
import com.andexert.retrofitexample.rest.service.ApiConstant;
import com.squareup.picasso.Picasso;

import java.sql.Date;
import java.text.SimpleDateFormat;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);


    }

    @OnClick(R.id.activity_main_search_button)
    protected void onSearchClick()
    {
        if (!searchEditText.getText().toString().equals(""))
        {
            String appid = ApiConstant.APP_ID;
            Call<ApiResponse> call = App.getRestClient().getWeatherService().getWeather(searchEditText.getText().toString(), appid);
            call.enqueue(new Callback<ApiResponse>() {
                @Override
                public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                    if (response.isSuccessful()) {
                        handleResponse(response.body());
                    } else {
                        handleFailure(response.code() + response.message());
                    }
                }

                @Override
                public void onFailure(Call<ApiResponse> call, Throwable t) {
                    handleFailure(t.getMessage());
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
