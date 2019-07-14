package com.cowman.fomnrr;

import android.content.Intent;
import android.graphics.Bitmap;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.cowman.utils.VolleyController;

public class MainActivity extends AppCompatActivity {

    //private ImageView mainImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*mainImage = (ImageView)findViewById(R.id.main_image);

        VolleyController.getInstance(getApplicationContext()).getImageLoader().get("http://api.cowman.a2hosted.com/images/1.jpg", new ImageLoader.ImageListener() {
            @Override
            public void onResponse(ImageLoader.ImageContainer imageContainer, boolean b) {
                Bitmap bitmap = imageContainer.getBitmap();
                //use bitmap
                if(bitmap != null) {
                    mainImage.setImageBitmap(bitmap);
                }
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                volleyError.printStackTrace();
            }
        });*/
    }

    public void viewEvents(View view) {
        startActivity(new Intent(this, EventsActivity.class));
    }

    public void viewMap(View view) {
        startActivity(new Intent(this, MapsActivity.class));
    }

    public void viewMembership(View view) {
        startActivity(new Intent(this, MembershipActivity.class));
    }
}
