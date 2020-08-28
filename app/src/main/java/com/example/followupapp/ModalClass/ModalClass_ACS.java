package com.example.followupapp.ModalClass;

import android.os.Parcel;
import android.os.Parcelable;

public class ModalClass_ACS implements Parcelable {
    String question;
    int answer_status;
    int visibility_status;

    public ModalClass_ACS(Parcel in) {
        question = in.readString();
        answer_status = in.readInt();
        visibility_status = in.readInt();
    }
    public ModalClass_ACS() {

    }
    public static final Creator<ModalClass_ACS> CREATOR = new Creator<ModalClass_ACS>() {
        @Override
        public ModalClass_ACS createFromParcel(Parcel in) {
            return new ModalClass_ACS(in);
        }

        @Override
        public ModalClass_ACS[] newArray(int size) {
            return new ModalClass_ACS[size];
        }
    };

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public int getAnswer_status() {
        return answer_status;
    }

    public void setAnswer_status(int answer_status) {
        this.answer_status = answer_status;
    }

    public int getVisibility_status() {
        return visibility_status;
    }

    public void setVisibility_status(int visibility_status) {
        this.visibility_status = visibility_status;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.question);
        dest.writeInt(this.answer_status);
        dest.writeInt(this.visibility_status);
    }
}
