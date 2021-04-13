package com.aaxena.bhojan;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.avaris.flyfood.Menu;

import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Text;

public class AboutDevelopers extends AppCompatActivity {

    private ListView mListView;
    private Button mButton;
    private String [] names = {"Spandan Saxena", "Shrish Sharma"};
    private String [] urls = {"https://spandansaxena.codes/", "https://shrishsharma.me/"};
    private int [] images = {R.drawable.spandan, R.drawable.shrish};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_developers);

        Toast.makeText(this,"Tap on our PFPs ;)",Toast.LENGTH_SHORT).show();

        mListView = findViewById(R.id.devList);
        devAdapter adapter = new devAdapter();
        mListView.setAdapter(adapter);
    }
    public void openCustomTab(Activity activity, CustomTabsIntent customTabsIntent, Uri uri){
        String packageName = "com.android.chrome";
        if (packageName != null) {
            customTabsIntent.intent.setPackage(packageName);
            customTabsIntent.launchUrl(activity, uri);
        }
        else {
            activity.startActivity(new Intent(Intent.ACTION_VIEW, uri));
        }
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
        public View getView(int position, @Nullable View convertView, @NotNull ViewGroup parent) {
            convertView = getLayoutInflater().inflate(R.layout.devlist, parent, false);
            Button mButton = convertView.findViewById(R.id.devMore);
            ImageView mImageView = convertView.findViewById(R.id.imgDev1);
            TextView mTextView = convertView.findViewById(R.id.nameDev1);
            mTextView.setText(names[position]);
            mImageView.setImageResource(images[position]);
            mButton.setOnClickListener(v -> {
                CustomTabsIntent.Builder customIntent = new CustomTabsIntent.Builder();
                customIntent.setToolbarColor(ContextCompat.getColor(AboutDevelopers.this, R.color.orange_500));
                openCustomTab(AboutDevelopers.this, customIntent.build(), Uri.parse(urls[position]));
            });
            return convertView;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent back = new Intent(this, Landing.class);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        startActivity(back);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //Write code here
    }

    @Override
    protected void onPause() {
        super.onPause();
        //Write code here
    }
}