package com.sdaacademy.jawny.daniel.httpdropbox3;

import android.os.AsyncTask;
import android.widget.ArrayAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class GetFilesListTask extends AsyncTask<String, Integer, JSONObject> {

    private MainActivity mainActivity;

    public void setMainActivity(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    @Override
    protected JSONObject doInBackground(String... params) {
        try {
            return sentRequest();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private List<DropboxFile> convert(JSONArray array) {
        ArrayList<DropboxFile> list = new ArrayList<>();

        for (int i = 0; i < array.length(); i++) {
            JSONObject jsonObject = array.optJSONObject(i);
            DropboxFile dropboxFile = new DropboxFile();
            dropboxFile.setName(jsonObject.optString("name"));
            list.add(dropboxFile);
        }
        return list;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);

    }

    @Override
    protected void onPostExecute(JSONObject jsonObject) {
        super.onPostExecute(jsonObject);

        JSONArray entries = jsonObject.optJSONArray("entries");
        List<DropboxFile> dropboxFiles = convert(entries);
        if (mainActivity != null) {
            mainActivity.setFiles(dropboxFiles);
        }
    }

    private JSONObject sentRequest() throws IOException, JSONException {
        MediaType jsonMediaType = MediaType.parse("application/json; charset=utf-8");
        String json = new JSONObject().put("path", "").toString();
        RequestBody body = RequestBody.create(jsonMediaType, json);
        Request request = new Request.Builder()
                .addHeader("Authorization", "Bearer 3TS3KjVdr6AAAAAAAAAAFJ1CyfWp14sQeIAppj6Mf9N4t37r3LgLDalWWXvzYbHm")
                .addHeader("Content-Type", "application/json")
                .url("https://api.dropboxapi.com/2/files/list_folder")
                .post(body)
                .build();
        OkHttpClient client = new OkHttpClient();
        Response response = client.newCall(request).execute();
        return new JSONObject(response.body().string());
    }
}
