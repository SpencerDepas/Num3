package com.example.spencer.num3e;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * Created by spencer on 9/25/2014.
 */
public class CustomAdapter extends BaseAdapter{

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
       /* if(convertView==null)
        {
            LayoutInflater inflater = (LayoutInflater) ListViewWithBaseAdapter.this.getSystemService(MyActivity.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.custom_list_view, parent,false);
        }

        TextView chapterName = (TextView)convertView.findViewById(R.id.textViewNumber);*/


        /*codeLearnChapter chapter = codeLearnChapterList.get(arg0);

        chapterName.setText(chapter.chapterName);
        chapterDesc.setText(chapter.chapterDescription);*/


        return convertView;
    }
}
