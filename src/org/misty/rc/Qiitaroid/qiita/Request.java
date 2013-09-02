package org.misty.rc.Qiitaroid.qiita;

import java.net.URL;

/**
 * Created with IntelliJ IDEA.
 * User: arai
 * Date: 13/08/19
 * Time: 14:40
 * To change this template use File | Settings | File Templates.
 */
public class Request {

    private RequestMethod method;
    private String url;
    private Parameter[] params;

    public Request() {

    }

    public Request(RequestMethod method, String url, Parameter[] params) {
        this.method = method;
        this.url = url;
        this.params = params;
    }

    public RequestMethod method() {
        return this.method;
    }

    public Parameter[] parameters() {
        return this.params;
    }

    public URL url() throws Exception{
        return new URL(this.url);
    }

    public void setMethod(RequestMethod method) {
        this.method = method;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    private void test() {
        String val;
        switch (method) {
            case GET:
                val = url +  Parameter.encodeParameters(params, false);
                //HTTP

                break;
            case POST:
                val = Parameter.encodeParameters(params, true);

                break;
            case PUT:
                break;
            case DELETE:
                break;
        }
    }
}
