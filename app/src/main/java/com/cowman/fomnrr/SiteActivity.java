package com.cowman.fomnrr;

import android.content.Intent;
import android.graphics.Bitmap;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.cowman.utils.VolleyController;

import org.json.JSONException;
import org.json.JSONObject;

public class SiteActivity extends AppCompatActivity {

    private TextView siteName;
    private TextView siteDescription;
    private ProgressBar progressBar;
    private ScrollView scrollView;
    private ImageView siteImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_site);

        siteName = (TextView)findViewById(R.id.site_name);
        siteDescription = (TextView)findViewById(R.id.site_description);
        progressBar = (ProgressBar)findViewById(R.id.site_loading_indicator);
        scrollView = (ScrollView)findViewById(R.id.site_scrollview);
        siteImage = (ImageView)findViewById(R.id.site_image);
    }

    protected void onStart() {
        super.onStart();

        Intent intent = getIntent();

        JsonObjectRequest request = new JsonObjectRequest("http://api.cowman.a2hosted.com/api/sites/" + intent.getStringExtra("siteId"), null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    siteName.setText(response.getString("title"));
                    siteDescription.setText(response.getString("description"));

                    RetrieveImage(response.getString("imageUrl"));
                } catch (JSONException ex) {
                    ex.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("-------------------------------------error" + error.getMessage());
            }
        });

        VolleyController.getInstance(getApplicationContext()).addToRequestQueue(request);
    }

    private void RetrieveImage(String url) {
        VolleyController.getInstance(getApplicationContext()).getImageLoader().get(url.replace("127.0.0.1", "10.0.2.2"), new ImageLoader.ImageListener() {
            @Override
            public void onResponse(ImageLoader.ImageContainer imageContainer, boolean b) {
                Bitmap bitmap = imageContainer.getBitmap();
                //use bitmap
                if(bitmap != null) {
                    siteImage.setImageBitmap(bitmap);
                    progressBar.setVisibility(View.INVISIBLE);
                    scrollView.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });
        //siteImage.setImageUrl(url.replace("127.0.0.1", "10.0.2.2"), VolleyController.getInstance(getApplicationContext()).getImageLoader());
    }
}
