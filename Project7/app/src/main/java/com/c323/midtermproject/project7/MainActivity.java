package com.c323.midtermproject.project7;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Dictionary;

public class MainActivity extends AppCompatActivity {

    EditText city;
    TextView weather, description, temperature, feelsLike;
    String jsonData = "";
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        city = findViewById(R.id.editTextLocation);
        weather = findViewById(R.id.textViewWeather);
        description = findViewById(R.id.textViewDescription);
        temperature = findViewById(R.id.textViewTemperature);
        feelsLike = findViewById(R.id.textViewFeels);
    }

    public void getWeatherData(View view) {

        progressDialog = ProgressDialog.show(this, "Weather Info", "Connecting, please wait...", true, true);

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()){
            //Create a new task in a separate thread! (not to get UI thread stuck!)
            new DownloadWeatherDataTask().execute("https://api.openweathermap.org/data/2.5/weather?q="+ city.getText().toString() +"&appid=9ec1cd8b40280d198656598aca81b02a");
        } else {
            //textView.setText("No network connection available!");
        }
    }

    private class DownloadWeatherDataTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            jsonData =  downloadFromURL(urls[0]);
            return jsonData;
        }

        private String downloadFromURL(String url) {
            InputStream is = null;
            StringBuffer result  = new StringBuffer();
            URL myUrl = null;
            try{
                myUrl = new URL(url);
                HttpURLConnection connection = (HttpURLConnection) myUrl.openConnection();
                connection.setReadTimeout(3000);
                connection.setConnectTimeout(3000);
                connection.setRequestMethod("GET");
                connection.setDoInput(true);
                connection.connect();
                int responceCode = connection.getResponseCode();
                if (responceCode != HttpURLConnection.HTTP_OK){
                    throw new IOException("Http error code: "+responceCode);
                }
                is = connection.getInputStream();
                progressDialog.dismiss();

                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    result.append(line);
                }

            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return result.toString();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            parseJSONData(jsonData);
        }
    }


    private String parseJSONData(String jsonData) {
        String data = "";
        try{
            JSONObject jsonRootObject = new JSONObject(jsonData);
            String weath = jsonRootObject.getJSONArray("weather").getJSONObject(0).optString("main");
            weather.setText("Today's Weather: " + weath);
            String descript = jsonRootObject.getJSONArray("weather").getJSONObject(0).optString("description");
            description.setText("Description: " + descript);
            String temp = jsonRootObject.getJSONObject("main").get("temp").toString();
            temperature.setText("Temperature: " + temp);
            String feel = jsonRootObject.getJSONObject("main").get("feels_like").toString();
            feelsLike.setText("Feels like: " + feel);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return data.toString();
    }

    public void toActivityTwo(View view) {
        Intent at = new Intent(MainActivity.this, Activity2.class);
        startActivity(at);
    }
}
