package org.misty.rc.Qiitaroid;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * Created with IntelliJ IDEA.
 * User: arai
 * Date: 13/09/10
 * Time: 13:07
 * To change this template use File | Settings | File Templates.
 */
public class QiitaLoader extends AsyncTaskLoader<String> {

    private String _data;
    private String _url = null;

    public QiitaLoader(Context context, Bundle args) {
        super(context);
        if(null != args) {
            _url = args.getString("url");
        }
    }

    @Override
    public String loadInBackground() {
        StringBuilder buf = new StringBuilder();
        URL url;

        try {
            if(TextUtils.isEmpty(_url)) {
                url = new URL("https://qiita.com/api/v1/users/misty_rc");
            } else {
                url = new URL(_url);
            }
            HttpsURLConnection conn = (HttpsURLConnection)url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Connection", "close");
            conn.setFixedLengthStreamingMode(0);
            conn.connect();

            Log.d("QIITA", "response code: " + conn.getResponseCode());

//            int code = conn.getResponseCode();
            InputStream is = new BufferedInputStream(conn.getInputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));

            String tmp;

            while((tmp = reader.readLine()) != null) {
                buf.append(tmp);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return buf.toString();
    }

    @Override
    public void deliverResult(String data) {
        if(isReset()) {
            return;
        }

        _data = data;
        super.deliverResult(data);
    }

    @Override
    protected void onStartLoading() {
        if(_data != null) {
            deliverResult(_data);
        } else {
            forceLoad();
        }
    }

    @Override
    protected void onStopLoading() {
        cancelLoad();
    }

    @Override
    protected void onReset() {
        super.onReset();
        onStopLoading();
        _data = null;
    }
}
