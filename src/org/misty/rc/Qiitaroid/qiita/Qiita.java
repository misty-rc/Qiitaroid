package org.misty.rc.Qiitaroid.qiita;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created with IntelliJ IDEA.
 * User: arai
 * Date: 13/09/13
 * Time: 16:34
 * To change this template use File | Settings | File Templates.
 */
public class Qiita implements Parcelable {

    private RequestMethod method;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }

    private Qiita(Parcel in) {

    }


}
