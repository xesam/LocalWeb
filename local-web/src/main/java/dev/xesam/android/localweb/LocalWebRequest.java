package dev.xesam.android.localweb;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.HashMap;

/**
 * Created by xesamguo@gmail.com on 16-5-9.
 */
public class LocalWebRequest implements Parcelable {

    private String mVersion;
    private HashMap<String, String> optional;

    public LocalWebRequest() {
    }

    public String getVersion() {
        return null;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mVersion);
        dest.writeSerializable(this.optional);
    }

    protected LocalWebRequest(Parcel in) {
        this.mVersion = in.readString();
        this.optional = (HashMap<String, String>) in.readSerializable();
    }

    public static final Parcelable.Creator<LocalWebRequest> CREATOR = new Parcelable.Creator<LocalWebRequest>() {
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
