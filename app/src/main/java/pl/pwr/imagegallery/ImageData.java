package pl.pwr.imagegallery;

import android.os.Parcel;
import android.os.Parcelable;

public class ImageData implements Parcelable {

    private String url;
    private String title;
    private String date;

    public ImageData(String url, String title, String date) {
        this.url = url;
        this.title = title;
        this.date = date;
    }

    public ImageData(Parcel in) {
        this.url = in.readString();
        this.title = in.readString();
        this.date = in.readString();
    }

    public static final Parcelable.Creator<ImageData> CREATOR = new Parcelable.Creator<ImageData>() {
        @Override
        public ImageData createFromParcel(Parcel in) {
            return new ImageData(in);
        }

        @Override
        public ImageData[] newArray(int size) {
            return new ImageData[size];
        }
    };

    public String getUrl() {
        return url;
    }

    public String getTitle() {
        return title;
    }

    public String getDate() {
        return date;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.url);
        dest.writeString(this.title);
        dest.writeString(this.date);
    }
}
