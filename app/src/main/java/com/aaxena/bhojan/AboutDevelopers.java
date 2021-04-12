package com.aaxena.bhojan;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class AboutDevelopers extends AppCompatActivity {

    private ListView mListView;
    private String [] names = {"Spandan Saxena", "Shrish Sharma"};
    private int [] images = {R.drawable.shrish, R.drawable.shrish};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_developers);
        mListView = findViewById(R.id.devList);
        devAdapter adapter = new devAdapter();
        mListView.setAdapter(adapter);
    }
    public class devAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return images.length;
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
            convertView = getLayoutInflater().inflate(R.layout.devlist, parent, false);
            ImageView mImageView = convertView.findViewById(R.id.imgDev1);
            TextView mTextView = convertView.findViewById(R.id.nameDev1);
            mTextView.setText(names[position]);
            mImageView.setImageResource(images[position]);
            return convertView;
        }
    }
}