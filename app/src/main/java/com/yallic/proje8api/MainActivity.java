package com.yallic.proje8api;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {

    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);

    }

    public void getRates(View view){

        DownloadData downloadData = new DownloadData();
        try {
            String url = "https://coronavirus-tracker-api.herokuapp.com/v2/latest";
             downloadData.execute(url);
        }catch (Exception e){

        }
    }

    private class DownloadData extends AsyncTask<String,Void,String>{

        @Override
        protected String doInBackground(String... strings) {
            String sonuc = "";
            URL url ;
            HttpsURLConnection httpsURLConnection;
            try {

                url = new URL(strings[0]);
                httpsURLConnection = (HttpsURLConnection) url.openConnection();
                InputStream inputStream = httpsURLConnection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);

                int data = inputStreamReader.read();

                while (data>0)
                {
                    char character = (char) data;
                    sonuc += character;

                    data = inputStreamReader.read();
                }
                return  sonuc;

            }catch (Exception e){
                return  "hata";
            }

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {

                JSONObject jsonObject = new JSONObject(s);
                JSONObject location = jsonObject.getJSONObject("latest");
                String deaths = location.getString("deaths");
                textView.setText(deaths);
            } catch (JSONException e) {
                e.printStackTrace();
            }



        }
    }


}

