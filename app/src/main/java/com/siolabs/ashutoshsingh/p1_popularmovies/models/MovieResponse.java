package com.siolabs.ashutoshsingh.p1_popularmovies.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ashutoshsingh on 17-03-2016.
 */
public class MovieResponse implements Parcelable {
    public String overview;
    public String original_title;
    public String poster_path;
    public String release_date;
    public double vote_average;
    public int runtime;

    public int id;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRuntime() {
        return runtime;
    }

    public void setRuntime(int runtime) {
        this.runtime = runtime;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

    public String getPoster_path() {
        return "http://image.tmdb.org/t/p/w185" + poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public Double getVote_average() {
        return vote_average;
    }

    public void setVote_average(Double vote_average) {
        this.vote_average = vote_average;
    }

    public MovieResponse() {
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.overview);
        dest.writeString(this.original_title);
        dest.writeString(this.poster_path);
        dest.writeString(this.release_date);
        dest.writeDouble(this.vote_average);
        dest.writeInt(this.runtime);
        dest.writeInt(this.id);
    }

    protected MovieResponse(Parcel in) {
        this.overview = in.readString();
        this.original_title = in.readString();
        this.poster_path = in.readString();
        this.release_date = in.readString();
        this.vote_average = in.readDouble();
        this.runtime = in.readInt();
        this.id = in.readInt();
    }

    public static final Creator<MovieResponse> CREATOR = new Creator<MovieResponse>() {
        public MovieResponse createFromParcel(Parcel source) {
            return new MovieResponse(source);
        }

        public MovieResponse[] newArray(int size) {
            return new MovieResponse[size];
        }
    };
}
