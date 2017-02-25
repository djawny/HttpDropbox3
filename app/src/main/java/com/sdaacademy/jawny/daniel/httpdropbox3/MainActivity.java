package com.sdaacademy.jawny.daniel.httpdropbox3;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    public static final String DROP_BOX = "DropBox";

    @BindView(R.id.list)
    ListView mList;

    private ArrayAdapter<DropboxFile> listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        listAdapter = new ArrayAdapter<DropboxFile>(this, android.R.layout.simple_dropdown_item_1line);
        mList.setAdapter(listAdapter);

        new GetFilesListTask(listAdapter).execute();
    }
}
