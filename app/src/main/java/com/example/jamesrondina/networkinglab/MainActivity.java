package com.example.jamesrondina.networkinglab;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    String API_KEY = "&format=json&apiKey=t3ttv3kpwtz83kbe7gh29rd2";
    String SEARCH_CALL = "http://api.walmartlabs.com/v1/search?query=";
    String QUERY;

    Button mCereal;
    Button mChoco;
    Button mTea;

    ListView mListView;
    ArrayList<String> mArrayList;
    ArrayAdapter mArrayAdapter;

    AsyncTask<String, Void, Void> mtask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mCereal = (Button) findViewById(R.id.cereal);
        mChoco = (Button) findViewById(R.id.choco);
        mTea = (Button) findViewById(R.id.tea);

        mListView = (ListView) findViewById(R.id.listView);
        mArrayList = new ArrayList<>();
        mArrayAdapter = new ArrayAdapter(MainActivity.this,
                android.R.layout.simple_list_item_1, android.R.id.text1, mArrayList);

        mListView.setAdapter(mArrayAdapter);

        ConnectivityManager conMag = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = conMag.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            Toast.makeText(MainActivity.this, "Connect Established", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(MainActivity.this, "Connection Error", Toast.LENGTH_LONG).show();
        }

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.cereal:
                        QUERY = "cereal";
                        break;
                    case R.id.choco:
                        QUERY = "chocolate";
                        break;
                    case R.id.tea:
                        QUERY = "tea";
                        break;
                }
                mArrayAdapter.clear();
                getItems(SEARCH_CALL + QUERY + API_KEY);
            }
        };

        mCereal.setOnClickListener(listener);
        mChoco.setOnClickListener(listener);
        mTea.setOnClickListener(listener);

    }

    private String readIt(InputStream inputStream) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader bufferedReader =
                new BufferedReader(new InputStreamReader(inputStream));

        String string;

        while ((string = bufferedReader.readLine()) != null) {
            stringBuilder.append(string);
        }

        return stringBuilder.toString();
    }

    private void downloadUrl(String myUrl) throws IOException, JSONException {
        InputStream inputStream = null;
        try {
            URL url = new URL(myUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setDoInput(true);

            connection.connect();
            inputStream = connection.getInputStream();

            String contentAsString = readIt(inputStream);
            parseJson(contentAsString);
        }
        finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }
    }


    public void getItems(String webURL) {
        mtask = new AsyncTask<String, Void, Void>() {
            @Override
            protected Void doInBackground(String... strings) {
                try {
                    downloadUrl(strings[0]);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void urlText) {
                super.onPostExecute(urlText);
                mArrayAdapter.notifyDataSetChanged();
            }
        }.execute(webURL);
    }

    private void parseJson(String contentAsString) throws JSONException {
        JSONObject root = new JSONObject(contentAsString);
        JSONArray array = root.getJSONArray("items");
        for (int i = 0; i < array.length(); i++) {
            JSONObject item = array.getJSONObject(i);
            mArrayList.add(item.getString("name"));
        }
    }

}
