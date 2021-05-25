package com.example.moviecataloguesubmissionfinal.Class;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "film", indices = @Index(value = {"title"}, unique = true))
public class Film implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @SerializedName("vote_count")
    private String vote_count;
    @SerializedName("vote_average")
    private String vote_average;
    @SerializedName(value = "title", alternate = {"name"})
    private String title;
    @SerializedName("original_language")
    private String original_language;
    @SerializedName("overview")
    private String overview;
    @SerializedName(value = "release_date", alternate = {"first_air_date"})
    private String release_date;
    @SerializedName("poster_path")
    private String photo;
    @SerializedName("media_type")
    private String filmType;

    public Film() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getVote_count() {
        return vote_count;
    }

    public void setVote_count(String vote_count) {
        this.vote_count = vote_count;
    }

    public String getVote_average() {
        return vote_average;
    }

    public void setVote_average(String vote_average) {
        this.vote_average = vote_average;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFilmType() {
        return filmType;
    }

    public void setFilmType(String filmType) {
        this.filmType = filmType;
    }

    public String getOriginal_language() {
        return original_language;
    }

    public void setOriginal_language(String original_language) {
        this.original_language = original_language;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    protected Film(Parcel in) {
        id = in.readInt();
        vote_count = in.readString();
        vote_average = in.readString();
        title = in.readString();
        original_language = in.readString();
        overview = in.readString();
        release_date = in.readString();
        photo = in.readString();
        filmType = in.readString();
    }

    public static final Creator<Film> CREATOR = new Creator<Film>() {
        @Override
        public Film createFromParcel(Parcel in) {
            return new Film(in);
        }

        @Override
        public Film[] newArray(int size) {
            return new Film[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(vote_count);
        dest.writeString(vote_average);
        dest.writeString(title);
        dest.writeString(original_language);
        dest.writeString(overview);
        dest.writeString(release_date);
        dest.writeString(photo);
        dest.writeString(filmType);
    }
}