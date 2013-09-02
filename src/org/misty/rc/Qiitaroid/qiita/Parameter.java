package org.misty.rc.Qiitaroid.qiita;

/**
 * Created with IntelliJ IDEA.
 * User: arai
 * Date: 13/08/19
 * Time: 14:26
 * To change this template use File | Settings | File Templates.
 */
public class Parameter {
    private String key;
    private String value;

    public static final String URL_NAME = "url_name";
    public static final String PASSWORD = "password";

    public Parameter() {

    }

    public Parameter(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public Parameter(String key, int value) {
        this.key = key;
        this.value = String.valueOf(value);
    }

    public Parameter(String key, long value) {
        this.key = key;
        this.value = String.valueOf(value);
    }

    public String key() {
        return this.key;
    }

    public String value() {
        return this.value;
    }

    public static String encodeParameters(Parameter[] params, boolean flag) {
        if(null == params) {
            return "";
        } else {
            StringBuilder buf = new StringBuilder();
            if(flag) {
                buf.append("?");
            }
            for(int i = 0; i < params.length; i++) {
                if(i != 0) {
                    buf.append("&");
                }
                buf.append(params[i].key + "=" + params[i].value);
            }
            return buf.toString();
        }
    }

    public static Parameter[] getParameters(Parameter... param) {
        int length = param.length;
        Parameter[] ret = new Parameter[0];

        if(0 == length) {

        } else {
            ret = new Parameter[length];
            for(int i = 0; i < length; i++) {
                ret[i] = param[i];
            }
        }

        return ret;
    }
}
