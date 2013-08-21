package org.misty.rc.Qiitaroid;

import android.app.Activity;
import android.os.Bundle;
import org.misty.rc.Qiitaroid.qiita.QiitaClient;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class MyActivity extends Activity {

    private String user;
    private String password;
    private QiitaClient qiita;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);


        qiita = new QiitaClient(user, password);
    }


    private void testGet() throws IOException {
        URL url = new URL("https://qiita.com/api/v1/auth");
        HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setDoOutput(true);
        conn.connect();

        String postdata = "url_name=misty_rc&password=Qtqvd8ki";
        PrintStream ps = new PrintStream(conn.getOutputStream());
        ps.print(postdata);
        ps.close();

        InputStream is = conn.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));

        StringBuffer buf = new StringBuffer();
        String tmp = null;

        while ((tmp = reader.readLine()) != null) {
            buf.append(tmp);
        }

        System.out.println(buf);


    }
}
