package com.ads2tex.ads2texdoctor.App;

import android.app.Application;
import android.text.TextUtils;

import com.ads2tex.ads2texdoctor.Utils.LruBitmapCache;
import com.ads2tex.ads2texdoctor.volley.Request;
import com.ads2tex.ads2texdoctor.volley.RequestQueue;
import com.ads2tex.ads2texdoctor.volley.toolbox.ImageLoader;
import com.ads2tex.ads2texdoctor.volley.toolbox.Volley;

public class AppController extends Application {
        public static final String TAG = AppController.class
                .getSimpleName();
        private RequestQueue mRequestQueue;
        private ImageLoader mImageLoader;
        private static AppController mInstance;

        @Override
        public void onCreate() {
            super.onCreate();
            mInstance = this;
        }

        public static synchronized AppController getInstance() {
            return mInstance;
        }

        public RequestQueue getRequestQueue() {
            if (mRequestQueue == null) {
                mRequestQueue = Volley.newRequestQueue(getApplicationContext());
            }

            return mRequestQueue;
        }

        public ImageLoader getImageLoader() {
            getRequestQueue();
            if (mImageLoader == null) {
                mImageLoader = new ImageLoader(this.mRequestQueue,
                        new LruBitmapCache());
            }
            return this.mImageLoader;
        }

        public <T> void addToRequestQueue(Request<T> req, String tag) {
            // set the default tag if tag is empty
            req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
            getRequestQueue().add(req);
        }

        public <T> void addToRequestQueue(Request<T> req) {
            req.setTag(TAG);
            getRequestQueue().add(req);
        }

        public void cancelPendingRequests(Object tag) {
            if (mRequestQueue != null) {
                mRequestQueue.cancelAll(tag);
            }
        }
    }