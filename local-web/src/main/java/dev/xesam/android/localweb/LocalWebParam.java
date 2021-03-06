package dev.xesam.android.localweb;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.HashMap;

/**
 * Created by xesamguo@gmail.com on 16-5-9.
 */
public class LocalWebParam implements Parcelable {

    private String mUrl;
    private HashMap<String, String> optional;

    public LocalWebParam() {
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        this.mUrl = url;
    }

    public void put(String key, String value) {
        optional.put(key, value);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mUrl);
        dest.writeSerializable(this.optional);
    }

    protected LocalWebParam(Parcel in) {
        this.mUrl = in.readString();
        this.optional = (HashMap<String, String>) in.readSerializable();
    }

    public static final Creator<LocalWebParam> CREATOR = new Creator<LocalWebParam>() {
        @Override
        public LocalWebParam createFromParcel(Parcel source) {
            return new LocalWebParam(source);
        }

        @Override
        public LocalWebParam[] newArray(int size) {
            return new LocalWebParam[size];
        }
    };
}
