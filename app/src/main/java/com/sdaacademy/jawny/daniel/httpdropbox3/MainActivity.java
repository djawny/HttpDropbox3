package com.sdaacademy.jawny.daniel.httpdropbox3;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.list)
    ListView mList;

    private ArrayAdapter<DropboxFile> listAdapter;
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
                    getFilesListTask = new GetFilesListTask();
                    getFilesListTask.setMainActivity(MainActivity.this);
                    getFilesListTask.execute(file.getPath());
                }

            }
        });
        getFilesListTask = new GetFilesListTask();
        getFilesListTask.setMainActivity(this);
        getFilesListTask.execute("");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (getFilesListTask != null) {
            getFilesListTask.setMainActivity(null);
        }
    }

    private void setListAdapter() {
        listAdapter = new ArrayAdapter<DropboxFile>(this, android.R.layout.simple_dropdown_item_1line);
        mList.setAdapter(listAdapter);
    }

    public void setFiles(List<DropboxFile> dropboxFiles) {
        listAdapter.addAll(dropboxFiles);
    }

    public void showToast(String message) {
//        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        new AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton("Ok", null)
                .show();
    }
}
