package org.misty.rc.Qiitaroid.qiita;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created with IntelliJ IDEA.
 * User: arai
 * Date: 13/08/26
 * Time: 15:51
 * To change this template use File | Settings | File Templates.
 */
public class HttpClient {

    public static Response execute(Request request) throws Exception{
        String val;

        switch (request.method()) {
            case GET:
                val = Parameter.encodeParameters(request.parameters(), false);
                break;
            case POST:
                val = Parameter.encodeParameters(request.parameters(), true);
                HttpURLConnection conn = (HttpURLConnection)request.url().openConnection();
                conn.setRequestMethod(RequestMethod.POST.toString());
                conn.setDoOutput(true);
                conn.connect();

                PrintStream ps = new PrintStream(conn.getOutputStream());
                ps.print(val);
                ps.close();

                InputStream is = conn.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));

                StringBuilder buf = new StringBuilder();
                String tmp;

                while((tmp = reader.readLine()) != null) {
                    buf.append(tmp);
                }

                break;
            case PUT:
                break;
            case DELETE:
                break;
        }



        return null;
    }
}
