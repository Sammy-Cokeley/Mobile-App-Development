package com.c323proj11.recipeappproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.squareup.picasso.Picasso;

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
import java.util.ArrayList;

public class ActivityTwo extends AppCompatActivity {

    ListView listView;
    ArrayList<Meal> mealList = new ArrayList<>();
    private InterstitialAd mInterstitial;
    private String category;
    private ProgressDialog progressDialog;
    private String jsonData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two);
        SharedPreferences sp = getSharedPreferences("spApp", MODE_PRIVATE);
        category = sp.getString("Category", "NONE");
        getMealData();

        final ArrayAdapter<Meal> myAdapter = new myMealListAdapter();
        listView = findViewById(R.id.mealList);
        listView.setAdapter(myAdapter);
    }

    public void getMealData() {

        progressDialog = ProgressDialog.show(this, "Meal Info", "Connecting, please wait...", true, true);

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()){
            //Create a new task in a separate thread! (not to get UI thread stuck!)
            new DownloadMealDataTask().execute("https://www.themealdb.com/api/json/v1/1/filter.php?c=" + category);
        } else {
            //textView.setText("No network connection available!");
        }
    }

    private class DownloadMealDataTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            jsonData = downloadFromURL(urls[0]);
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
            for (int i=0;i<jsonRootObject.getJSONArray("meals").length();i++){
                String title = jsonRootObject.getJSONArray("meals").getJSONObject(i).optString("strMeal");
                //Toast.makeText(this, title, Toast.LENGTH_SHORT).show();
                String image = jsonRootObject.getJSONArray("meals").getJSONObject(i).optString("strMealThumb");
                Meal current = new Meal(title,image);
                mealList.add(current);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return data.toString();
    }

    private class myMealListAdapter extends ArrayAdapter<Meal> {

        public myMealListAdapter() {
            super(ActivityTwo.this, R.layout.meal_layout, mealList);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            //return super.getView(position, convertView, parent);
            View itemView = convertView;
            if(itemView == null){
                itemView = getLayoutInflater().inflate(R.layout.meal_layout,parent,false);
            }
            Meal currentMeal = mealList.get(position);
            TextView textViewMeal = itemView.findViewById(R.id.textViewMeal);
            textViewMeal.setText(currentMeal.getTitle());
            ImageView imageViewMeal = itemView.findViewById(R.id.mealImage);
            Picasso.get().load(currentMeal.getImage()).into(imageViewMeal);
            return itemView;
        }
    }
}