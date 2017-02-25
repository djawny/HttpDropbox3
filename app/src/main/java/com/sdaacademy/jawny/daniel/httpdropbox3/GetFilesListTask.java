package com.sdaacademy.jawny.daniel.httpdropbox3;

import android.os.AsyncTask;

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

public class GetFilesListTask extends AsyncTask<String, Integer, GetFilesListResult> {

    private MainActivity mainActivity;

    public void setMainActivity(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    @Override
    protected GetFilesListResult doInBackground(String... params) {
        GetFilesListResult result = new GetFilesListResult();
        try {
            Response response = sentRequest(params[0]);
            int statusCode = response.code();
            if (statusCode >= 200 && statusCode <= 299) {
                JSONObject jsonObject = new JSONObject(response.body().string());
                result.setJsonObject(jsonObject);
                result.setSuccess(true);
            } else {
                result.setErrorMessage(response.body().string());
                result.setSuccess(false);
            }
        } catch (IOException e) {
            e.printStackTrace();
            result.setErrorMessage("Błąd sieci");
            result.setSuccess(false);
        } catch (JSONException e) {
            e.printStackTrace();
            result.setErrorMessage("Błąd JSON");
            result.setSuccess(false);
        }
        return result;
    }

    private List<DropboxFile> convert(JSONArray array) {
        ArrayList<DropboxFile> list = new ArrayList<>();

        for (int i = 0; i < array.length(); i++) {
            JSONObject jsonObject = array.optJSONObject(i);
            DropboxFile dropboxFile = new DropboxFile();
            dropboxFile.setTag(jsonObject.optString(".tag"));
            dropboxFile.setName(jsonObject.optString("name"));
            dropboxFile.setPath(jsonObject.optString("path_lower"));
            list.add(dropboxFile);
        }
        return list;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);

    }

    @Override
    protected void onPostExecute(GetFilesListResult result) {
        super.onPostExecute(result);

        if (result.isSuccess()) {
            JSONArray entries = result.getJsonObject().optJSONArray("entries");
            List<DropboxFile> dropboxFiles = convert(entries);
            if (mainActivity != null) {
                mainActivity.setFiles(dropboxFiles);
            }
        } else {
            if (mainActivity != null) {
                mainActivity.showToast(result.getErrorMessage());
            }
        }
    }

    private Response sentRequest(String param) throws IOException, JSONException {
        if (param == null) param = "";
        MediaType jsonMediaType = MediaType.parse("application/json; charset=utf-8");
        String json = new JSONObject().put("path", param).toString();
        RequestBody body = RequestBody.create(jsonMediaType, json);
        Request request = new Request.Builder()
                .addHeader("Authorization", "Bearer 3TS3KjVdr6AAAAAAAAAAFJ1CyfWp14sQeIAppj6Mf9N4t37r3LgLDalWWXvzYbHm")
                .addHeader("Content-Type", "application/json")
                .url("https://api.dropboxapi.com/2/files/list_folder")
                .post(body)
                .build();
        OkHttpClient client = new OkHttpClient();
        return client.newCall(request).execute();
    }
}
