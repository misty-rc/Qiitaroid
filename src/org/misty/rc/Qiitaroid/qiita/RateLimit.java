package org.misty.rc.Qiitaroid.qiita;

/**
 * Created with IntelliJ IDEA.
 * User: arai
 * Date: 13/09/13
 * Time: 13:31
 * To change this template use File | Settings | File Templates.
 */
public class RateLimit {
    private int remaining;
    private int limit;

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getRemaining() {
        return remaining;
    }

    public void setRemaining(int remaining) {
        this.remaining = remaining;
    }
}
