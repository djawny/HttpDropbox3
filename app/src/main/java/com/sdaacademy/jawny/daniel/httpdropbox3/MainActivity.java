package com.sdaacademy.jawny.daniel.httpdropbox3;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    private List<String> folders = new ArrayList<>();
    StringBuilder path = new StringBuilder();

    @BindView(R.id.path)
    TextView mPath;

    @BindView(R.id.list)
    ListView mList;

    private ArrayAdapter<DropboxFile> listAdapter;
    private GetFilesListTask getFilesListTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        folders.add("");
        setListAdapter();
        mList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DropboxFile file = (DropboxFile) parent.getItemAtPosition(position);
                if (file.getTag().equals("folder")) {
                    folders.add(file.getPath());
                    getFilesListTask = new GetFilesListTask();
                    getFilesListTask.setMainActivity(MainActivity.this);
                    getFilesListTask.execute(file.getPath());
                    displayPath();
                }

            }
        });
        getFilesListTask = new GetFilesListTask();
        getFilesListTask.setMainActivity(this);
        getFilesListTask.execute(folders.get(folders.size() - 1));
    }

    private void displayPath() {
        path.setLength(0);
        path.append("Dropbox");
        path.append(folders.get(folders.size() - 1));
        mPath.setText(path);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (getFilesListTask != null) {
            getFilesListTask.setMainActivity(null);
        }
    }

    @Override
    public void onBackPressed() {
        if (folders.get(folders.size() - 1).equals("")) {
            super.onBackPressed();
        } else {
            folders.remove(folders.size() - 1);
            getFilesListTask = new GetFilesListTask();
            getFilesListTask.setMainActivity(this);
            getFilesListTask.execute(folders.get(folders.size() - 1));
            displayPath();
        }
    }

    private void setListAdapter() {
        listAdapter = new ArrayAdapter<DropboxFile>(this, android.R.layout.simple_dropdown_item_1line);
        mList.setAdapter(listAdapter);
    }

    public void setFiles(List<DropboxFile> dropboxFiles) {
        listAdapter.clear();
        listAdapter.addAll(dropboxFiles);
    }

    public void showToast(String message) {
        new AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton("Ok", null)
                .show();
    }
}
