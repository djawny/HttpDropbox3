package com.sdaacademy.jawny.daniel.httpdropbox3;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    private StringBuilder pathBuilder = new StringBuilder();
    private String currentFolderName = "";
    private String currentPath = "";

    @BindView(R.id.path)
    TextView mPath;

    @BindView(R.id.list)
    ListView mList;

    private FileListAdapter listAdapter;
    private GetFilesListTask getFilesListTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setListAdapter();
        mList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DropboxFile file = (DropboxFile) parent.getItemAtPosition(position);
                if (file.getTag().equals("folder")) {
                    currentFolderName = file.getName();
                    currentPath = file.getPath();
                    getFiles();
                } else {
                    downloadFile(file);
                }
            }
        });
        getFiles();
    }

    private void downloadFile(DropboxFile file) {
        new DownloadFileTask(this).execute(file);
    }

    private void getFiles() {
        getFilesListTask = new GetFilesListTask();
        getFilesListTask.setMainActivity(MainActivity.this);
        getFilesListTask.execute(currentPath);
        displayPath();
    }

    private void displayPath() {
        pathBuilder.setLength(0);
        pathBuilder.append("Dropbox");
        pathBuilder.append(currentPath);
        mPath.setText(pathBuilder);
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
        if (currentPath.equals("")) {
            super.onBackPressed();
        } else {
            int lastIndexOf = currentPath.lastIndexOf(currentFolderName);
            currentPath = currentPath.substring(0, lastIndexOf - 1);
            lastIndexOf = currentPath.lastIndexOf("/");
            currentFolderName = currentPath.substring(lastIndexOf + 1, currentPath.length());
            getFiles();
        }
    }

    private void setListAdapter() {
        listAdapter = new FileListAdapter(this, R.layout.list_row);
        mList.setAdapter(listAdapter);
    }

    public void setFiles(List<DropboxFile> dropboxFiles) {
        listAdapter.clear();
        listAdapter.addAll(dropboxFiles);
    }

    public void showError(String message) {
        new AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton("Ok", null)
                .show();
    }
}
