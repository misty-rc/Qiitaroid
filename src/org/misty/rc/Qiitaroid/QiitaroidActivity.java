package org.misty.rc.Qiitaroid;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.ListView;
import com.deploygate.sdk.DeployGate;
import org.misty.rc.Qiitaroid.qiita.QiitaClient;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.URL;

public class QiitaroidActivity extends Activity {

    private String user;
    private String password;
    private QiitaClient qiita;

    private DrawerLayout _DrawerLayout;
    private ListView _DrawerList;
    private ActionBarDrawerToggle _DrawerToggle;

    private GestureDetector _GestureDetector;
    private SharedPreferences _Preference;

    public static final String TOKEN = "token";

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DeployGate.logInfo("starting activity");
        DeployGate.logWarn("Test Warning from DeployGate");

        _Preference = PreferenceManager.getDefaultSharedPreferences(this);

        if(_Preference.getString(TOKEN, null).isEmpty()) {
            //login
            setContentView(R.layout.login);
        } else {
            //main
            setContentView(R.layout.activity_main);
            mainInitialize();
        }

        //qiita = new QiitaClient(user, password);
    }

    private void mainInitialize() {
        _GestureDetector = new GestureDetector(this, new QiitaGestureHandler());


    }

    private class QiitaGestureHandler implements GestureDetector.OnGestureListener {
        @Override
        public boolean onDown(MotionEvent e) {
            return false;
        }

        @Override
        public void onShowPress(MotionEvent e) {
        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            return false;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            return false;
        }

        @Override
        public void onLongPress(MotionEvent e) {
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            return false;
        }
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
