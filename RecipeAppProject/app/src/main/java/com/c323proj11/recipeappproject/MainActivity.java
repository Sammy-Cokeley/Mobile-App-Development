package com.c323proj11.recipeappproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
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


public class MainActivity extends AppCompatActivity {

    ListView listView;
    AdView adView;
    ArrayList<Category> list = new ArrayList<>();
    ProgressDialog progressDialog;
    String jsonData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        adView = findViewById(R.id.adView);

        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();

        adView.loadAd(adRequest);
        getRecipeData();

        final ArrayAdapter<Category> myAdapter = new myRecipeListAdapter();
        listView = findViewById(R.id.recipeList);
        listView.setAdapter(myAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String categ = list.get(i).getCategory();
                //Toast.makeText(MainActivity.this,test,Toast.LENGTH_SHORT).show();
                SharedPreferences sp = getSharedPreferences("spApp", MODE_PRIVATE);
                SharedPreferences.Editor edit = sp.edit();
                edit.putString("Category", categ);
                edit.commit();
                Intent nav = new Intent(MainActivity.this, ActivityTwo.class);
                startActivity(nav);
            }
        });
    }



    public void getRecipeData() {

        progressDialog = ProgressDialog.show(this, "Recipe Info", "Connecting, please wait...", true, true);

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()){
            //Create a new task in a separate thread! (not to get UI thread stuck!)
            new DownloadRecipeDataTask().execute("https://www.themealdb.com/api/json/v1/1/categories.php");
        } else {
            //textView.setText("No network connection available!");
        }
    }



    private class DownloadRecipeDataTask extends AsyncTask<String, Void, String> {

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
            for (int i=0;i<jsonRootObject.getJSONArray("categories").length();i++){
                String category = jsonRootObject.getJSONArray("categories").getJSONObject(i).optString("strCategory");
                String image = jsonRootObject.getJSONArray("categories").getJSONObject(i).optString("strCategoryThumb");
                Category current = new Category(category,image);
                list.add(current);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return data.toString();
    }

    private class myRecipeListAdapter extends ArrayAdapter<Category> {


        public myRecipeListAdapter() {
            super(MainActivity.this, R.layout.category_layout, list);
        }



        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            //return super.getView(position, convertView, parent);
            View itemView = convertView;
            if(itemView == null){
                itemView = getLayoutInflater().inflate(R.layout.category_layout,parent,false);
            }
            Category currentCategory = list.get(position);
            TextView textViewCategory = itemView.findViewById(R.id.textViewCategory);
            textViewCategory.setText(currentCategory.getCategory());
            ImageView imageViewCategory = itemView.findViewById(R.id.categoryImage);
            Picasso.get().load(currentCategory.getImage()).into(imageViewCategory);
            /*try {
                URL url = new URL(currentCategory.getImage());
                Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                imageViewCategory.setImageBitmap(bmp);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }*/
            return itemView;
        }
    }



}