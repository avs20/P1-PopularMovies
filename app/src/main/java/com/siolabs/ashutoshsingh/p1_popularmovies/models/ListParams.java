package com.siolabs.ashutoshsingh.p1_popularmovies.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ashutoshsingh on 18-03-2016.
 */
public class ListParams implements Parcelable {

    private int page;
    private String sortOrder;

    public ListParams(int page, String sortOrder) {
        this.page = page;
        this.sortOrder = sortOrder;
    }


    public int getPage() {
        return page > 0 ? page : 1;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public String getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.page);
        dest.writeString(this.sortOrder);
    }

    protected ListParams(Parcel in) {
        this.page = in.readInt();
        this.sortOrder = in.readString();
    }

    public static final Parcelable.Creator<ListParams> CREATOR = new Parcelable.Creator<ListParams>() {
        public ListParams createFromParcel(Parcel source) {
            return new ListParams(source);
        }

        public ListParams[] newArray(int size) {
            return new ListParams[size];
        }
    };
}
