package com.cowman.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

public class VolleyController {
    private static VolleyController _instance;
    private RequestQueue _requestQueue;
    private static Context _context;
    private ImageLoader _imageLoader;

    private VolleyController(Context context) {
        _context = context;
        _requestQueue = getRequestQueue();
        _imageLoader = getImageLoader();
    }

    public static synchronized VolleyController getInstance(Context context) {
        if(_instance == null) {
            _instance = new VolleyController(context);
        }

        return _instance;
    }

    public RequestQueue getRequestQueue() {
        if(_requestQueue == null) {
            _requestQueue = Volley.newRequestQueue(_context.getApplicationContext());
        }

        return _requestQueue;
    }

    public ImageLoader getImageLoader() {
        if(_imageLoader == null) {
            _imageLoader = new ImageLoader(getRequestQueue(), new ImageLoader.ImageCache() {
                private final LruCache<String, Bitmap> imageCache = new LruCache<String, Bitmap>(10);

                @Override
                public Bitmap getBitmap(String url) {
                    return imageCache.get(url);
                }

                @Override
                public void putBitmap(String url, Bitmap bitmap) {
                    imageCache.put(url, bitmap);
                }
            });
        }

        return _imageLoader;
    }

    public void addToRequestQueue(Request request) {
        getRequestQueue().add(request);
    }
}
