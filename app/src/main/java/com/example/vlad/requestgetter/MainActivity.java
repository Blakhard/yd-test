package com.example.vlad.requestgetter;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ((TextView) findViewById(R.id.reqGetSpace)).setMovementMethod(new ScrollingMovementMethod());
    }

    public  void sendReq(View view){
        String link = ((EditText)findViewById(R.id.editHttp)).getText().toString();
        try {
            URL url = new URL(link);
            new Downloader().execute(url);
        }
        catch (java.io.IOException e){
            ((TextView) findViewById(R.id.reqGetSpace)).setText("Oops, wrong URL.");
        }
    }

    private class Downloader extends AsyncTask<URL,Integer,String>{

        @Override
        protected String doInBackground(URL... reqURL) {
            HttpURLConnection connection = null;
            try {
                connection = (HttpURLConnection)reqURL[0].openConnection();
                connection.connect();
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder buf = new StringBuilder();
                while (in.readLine() != null)
                    buf.append(in.readLine());
                connection.disconnect();
                return buf.toString();
            }
            catch (java.io.IOException e){
                return "Something went wrong.";
            }
        }
        protected void onPostExecute(String result){
            ((TextView) findViewById(R.id.reqGetSpace)).setText(result);
        }
    }
}
