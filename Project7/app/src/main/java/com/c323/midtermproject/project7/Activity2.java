package com.c323.midtermproject.project7;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

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
import java.util.List;

public class Activity2 extends AppCompatActivity {

    List<Movie> myMovies = new ArrayList<Movie>();
    ProgressDialog progressDialog;
    String jsonData = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);
        getMovieData();
        populateMovies();
        populateCustomListView();
    }

    private void populateCustomListView() {
        final ArrayAdapter<Movie> myAdapter = new myMovieListAdapter();
        ListView listView = findViewById(R.id.listViewMovieList);
        listView.setAdapter(myAdapter);
    }

    private class myMovieListAdapter extends ArrayAdapter<Movie> {


        public myMovieListAdapter() {
            super(Activity2.this, R.layout.movie_layout, myMovies);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            //return super.getView(position, convertView, parent);
            View itemView = convertView;
            if(itemView == null){
                itemView = getLayoutInflater().inflate(R.layout.movie_layout,parent,false);
            }
            Movie currentMovie = myMovies.get(position);
            TextView textViewTitle = itemView.findViewById(R.id.textViewMovieTitle);
            textViewTitle.setText(currentMovie.getTitle());

            return itemView;
        }
    }

    public void getMovieData() {

        progressDialog = ProgressDialog.show(this, "Movie Info", "Connecting, please wait...", true, true);

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()){
            //Create a new task in a separate thread! (not to get UI thread stuck!)
            new DownloadDataTask().execute("https://api.themoviedb.org/3/discover/movie?api_key=c394372c39883708cf66e304e02cb4d4&language=en-US&sort_by=popularity.desc&include_adult=false&include_video=false&page=1");
            parseData();
        } else {
            //textView.setText("No network connection available!");
        }
    }

    private class DownloadDataTask extends AsyncTask<String, Void, String> {

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
        }
    }

    public void parseData() {
        myMovies.add(new Movie(parseJSONData(jsonData), "","","","",1));
    }

    private String parseJSONData(String jsonData) {
        String data = "";
        try{
            JSONObject jsonRootObject = new JSONObject(jsonData);

            data += jsonRootObject.getJSONArray("results").getJSONObject(0).opt("title");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return data.toString();
    }

    private void populateMovies(){
       /*JSONObject jsonRootObject = new JSONObject(jsonData);
       for (int i=0;i<jsonRootObject.getJSONArray("results").length();i++){
           String title = jsonRootObject.getJSONArray("results").getJSONObject(i).optString("title");
           String genre = jsonRootObject.getJSONArray("results").getJSONObject(i).optString("genre_ids");
           String language = jsonRootObject.getJSONArray("results").getJSONObject(i).optString("original_language");
           String releaseDate = jsonRootObject.getJSONArray("results").getJSONObject(i).optString("release_date");
           String description = jsonRootObject.getJSONArray("results").getJSONObject(i).optString("overview");
           myMovies.add(new Movie(title,genre,language,releaseDate,description,1));
       }*/
       myMovies.add(new Movie("help", "","","","",1));
       myMovies.add(new Movie("me", "","","","",1));
       myMovies.add(new Movie("im", "","","","",1));
       myMovies.add(new Movie("in", "","","","",1));
       myMovies.add(new Movie("glass", "","","","",1));
       myMovies.add(new Movie("box", "","","","",1));
        myMovies.add(new Movie("of", "","","","",1));
        myMovies.add(new Movie("emotion", "","","","",1));

    }
}