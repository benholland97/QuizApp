package bh.h63jav.quizapp;

import android.os.Parcel;
import android.os.Parcelable;

class CurrentUser implements Parcelable{

    private String userName;

    CurrentUser(String userName) {
        setUser(userName);
    }

    CurrentUser(){
        setUser("");
    }

    protected CurrentUser(Parcel in) {
        userName = in.readString();
    }

    public static final Creator<CurrentUser> CREATOR = new Creator<CurrentUser>() {
        @Override
        public CurrentUser createFromParcel(Parcel in) {
            return new CurrentUser(in);
        }

        @Override
        public CurrentUser[] newArray(int size) {
            return new CurrentUser[size];
        }
    };

    String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    private void setUser(String userName) {
        setUserName(userName);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(userName);
    }
}
