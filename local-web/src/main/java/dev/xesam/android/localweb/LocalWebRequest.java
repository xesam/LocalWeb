package dev.xesam.android.localweb;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.HashMap;

/**
 * Created by xesamguo@gmail.com on 16-5-9.
 */
public class LocalWebRequest implements Parcelable {

    private String mVersion;
    private String mUrl;
    private HashMap<String, String> optional;

    public LocalWebRequest() {
    }

    public String getVersion() {
        return mVersion;
    }

    public void setVersion(String version) {
        this.mVersion = version;
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        this.mUrl = url;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mVersion);
        dest.writeString(this.mUrl);
        dest.writeSerializable(this.optional);
    }

    protected LocalWebRequest(Parcel in) {
        this.mVersion = in.readString();
        this.mUrl = in.readString();
        this.optional = (HashMap<String, String>) in.readSerializable();
    }

    public static final Creator<LocalWebRequest> CREATOR = new Creator<LocalWebRequest>() {
        @Override
        public LocalWebRequest createFromParcel(Parcel source) {
            return new LocalWebRequest(source);
        }

        @Override
        public LocalWebRequest[] newArray(int size) {
            return new LocalWebRequest[size];
        }
    };
}
