package edu.uw.tcss450.team2.thermochat.io;

import android.content.Context;
import android.graphics.Bitmap;

import androidx.collection.LruCache;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

/**
 *
 *
 * @author Sierra C
 * @version 12/9/2021
 */
public class RequestQueueSingleton {
    private static RequestQueueSingleton instance;
    private static Context context;

    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;

    private RequestQueueSingleton(Context context) {
        RequestQueueSingleton.context = context;
        mRequestQueue = getmRequestQueue();

        mImageLoader = new ImageLoader(mRequestQueue,
                new ImageLoader.ImageCache() {
                    private final LruCache<String, Bitmap>
                            cache = new LruCache<String, Bitmap>(20);

                    @Override
                    public Bitmap getBitmap(String url) {
                        return cache.get(url);
                    }

                    @Override
                    public void putBitmap(String url, Bitmap bitmap) {
                        cache.put(url, bitmap);
                    }
                });
    }

    /**
     * Gets the instance of the RequestQueueSingleton from context. Creates one if null.
     * @param context
     * @return instance
     */
    public static synchronized RequestQueueSingleton getInstance(Context context) {
        if (instance == null) {
            instance = new RequestQueueSingleton(context);
        }
        return instance;
    }

    /**
     *
     * @return mRequestQueue
     */
    public RequestQueue getmRequestQueue() {
        if (mRequestQueue == null) {
            // getApplicationContext() is key, it keeps you from leaking the
            // Activity or BroadcastReceiver if someone passes one in.
            mRequestQueue = Volley.newRequestQueue(context.getApplicationContext());
        }
        return mRequestQueue;
    }

    /**
     * Adds request to current RequestQueue
     *
     * @param req The request to be added to the queue
     */
    public <T> void addToRequestQueue(Request<T> req) {
        getmRequestQueue().add(req);
    }

    /**
     *
     * @return The image loader associated with this RQS
     */
    public ImageLoader getmImageLoader() {
        return mImageLoader;
    }
}

