package com.sdaacademy.jawny.daniel.httpdropbox3;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    public static final String DROP_BOX = "DropBox";

    @BindView(R.id.list)
    ListView mList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

    }

    public class GetFilesListTask extends AsyncTask<String, Integer, JSONObject> {

        @Override
        protected JSONObject doInBackground(String... params) {

            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);

        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            super.onPostExecute(jsonObject);

        }

        private String sentPost() throws IOException, JSONException {
            MediaType jsonMediaType = MediaType.parse("application/json; charset=utf-8");
            OkHttpClient client = new OkHttpClient();
            String url = "https://api.dropboxapi.com/2/files/list_folder";
            JSONObject json = new JSONObject();
            json.put("path", "");
            String jsonStr = json.toString();

            RequestBody body = RequestBody.create(jsonMediaType, jsonStr);
            Request request = new Request.Builder()
                    .addHeader("Authorization", "Bearer 3TS3KjVdr6AAAAAAAAAAFJ1CyfWp14sQeIAppj6Mf9N4t37r3LgLDalWWXvzYbHm")
                    .addHeader("Content-Type", "application/json")
                    .url(url)
                    .post(body)
                    .build();
            Response response = client.newCall(request).execute();
            return response.body().string();
        }
    }


}
