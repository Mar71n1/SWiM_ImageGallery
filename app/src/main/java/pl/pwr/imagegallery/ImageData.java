package pl.pwr.imagegallery;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class ImageData implements Parcelable {

    private String url;
    private String title;
    private String date;
    private ArrayList<String> tags;

    public ImageData(String url, String title, String date, ArrayList<String> tags) {
        this.url = url;
        this.title = title;
        this.date = date;
        this.tags = tags;
    }

    public ImageData(Parcel in) {
        this.url = in.readString();
        this.title = in.readString();
        this.date = in.readString();
        tags = new ArrayList<>();
        in.readList(tags, null);
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

    public ArrayList<String> getTags() { return tags; }

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
        dest.writeList(this.tags);
    }
}
