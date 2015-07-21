package es.hol.galvisoft.aerolina.services;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class HttpManager {
    private static final int DEFAULT_TIMEOUT = 10000;
    private HttpClient httpClient;
    private final static String QR_PATH = "https://chart.googleapis.com/chart?cht=qr&chs=170x170&chl=";

    private HttpManager() {
        httpClient = getHttpClient();
    }

    public static HttpManager getInstance() {
        return new HttpManager();
    }

    public BufferedReader get(String url) {
        try {
            HttpGet get = new HttpGet(url);
            HttpResponse response = httpClient.execute(get);
            int responseCode = response.getStatusLine().getStatusCode();
            if (responseCode == 200) {
                HttpEntity entity = response.getEntity();
                return new BufferedReader(new InputStreamReader(entity.getContent()));
            }
        } catch (Exception e) {
            Log.e("get", e.toString());
        }
        return null;
    }

    public BufferedReader post(String url, List<String> postKey, List<String> postValue) {
        try {
            HttpPost post = new HttpPost(url);
            post.addHeader("Cookie", "user_name=yo");
            List<NameValuePair> nameValuePairs = new ArrayList<>(2);
            for (int i = 0; i < postKey.size(); i++) {
                nameValuePairs.add(new BasicNameValuePair(postKey.get(i), postValue.get(i)));
            }
            post.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            HttpResponse response = httpClient.execute(post);
            int responseCode = response.getStatusLine().getStatusCode();
            if (responseCode == 200) {
                HttpEntity entity = response.getEntity();
                return new BufferedReader(new InputStreamReader(entity.getContent()));//, "ISO-8859-1"
            } else {
                return null;
            }
        } catch (Exception ignored) {
        }
        return null;
    }

    public static Bitmap getBitmapFromURL(String link) {
        try {
            String query = URLEncoder.encode(link, "utf-8");
            URL url = new URL(QR_PATH + query);
            Log.e("url", url.toString());
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            return BitmapFactory.decodeStream(input);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Drawable downloadImage(String url) {
        Drawable drawable = null;
        try {
            HttpGet httpRequest = new HttpGet(url);
            HttpClient httpclient = getHttpClient();
            HttpResponse response = httpclient.execute(httpRequest);
            int responseCode = response.getStatusLine().getStatusCode();
            if (responseCode == 200) {
                HttpEntity entity = response.getEntity();
                BufferedHttpEntity bufferedHttpEntity = new BufferedHttpEntity(entity);
                InputStream is = bufferedHttpEntity.getContent();
                drawable = Drawable.createFromStream(is, "");
            }
        } catch (Exception ignored) {
        }
        return drawable;
    }

    public boolean noNetworkConnection(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo info = connectivity.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            if (info != null) {
                if (info.isConnected()) {
                    return false;
                }
            }
            info = connectivity.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            if (info != null) {
                if (info.isConnected()) {
                    return false;
                }
            }
        }
        return true;
    }

    private static HttpClient getHttpClient() {
        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpParams params = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(params, DEFAULT_TIMEOUT);
        HttpConnectionParams.setSoTimeout(params, DEFAULT_TIMEOUT);
        httpClient.setParams(params);
        return httpClient;
    }
}
