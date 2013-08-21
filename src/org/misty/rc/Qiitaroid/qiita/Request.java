package org.misty.rc.Qiitaroid.qiita;

/**
 * Created with IntelliJ IDEA.
 * User: arai
 * Date: 13/08/19
 * Time: 14:40
 * To change this template use File | Settings | File Templates.
 */
public class Request {

    private RequestMethod method;

    public Request() {

    }

    public void setMethod(RequestMethod method) {
        this.method = method;
    }

    private String url;

    public void setUrl(String url) {
        this.url = url;
    }
}
