package com.sdaacademy.jawny.daniel.httpdropbox3;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class FileListAdapter extends ArrayAdapter<DropboxFile> {

    public FileListAdapter(Context context, int resource) {
        super(context, resource);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        DropboxFile file = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_row, parent, false);
        }
        TextView textView = (TextView) convertView.findViewById(R.id.text);
        ImageView imageView = (ImageView) convertView.findViewById(R.id.image);
        if (file.getTag().equals("folder")) {
            imageView.setVisibility(View.VISIBLE);

        } else {
            imageView.setVisibility(View.INVISIBLE);
        }
        textView.setText(file.getName());
        return convertView;
    }
}
