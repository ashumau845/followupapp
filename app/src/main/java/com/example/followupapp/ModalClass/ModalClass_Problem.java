package com.example.followupapp.ModalClass;

import android.os.Parcel;
import android.os.Parcelable;

public class ModalClass_Problem implements Parcelable {
    String problem,diagnosed;

    public ModalClass_Problem() {
    }

    public ModalClass_Problem(Parcel in) {
        problem = in.readString();
        diagnosed = in.readString();
    }

    public static final Creator<ModalClass_Problem> CREATOR = new Creator<ModalClass_Problem>() {
        @Override
        public ModalClass_Problem createFromParcel(Parcel in) {
            return new ModalClass_Problem(in);
        }

        @Override
        public ModalClass_Problem[] newArray(int size) {
            return new ModalClass_Problem[size];
        }
    };

    public String getProblem() {
        return problem;
    }

    public void setProblem(String problem) {
        this.problem = problem;
    }

    public String getDiagnosed() {
        return diagnosed;
    }

    public void setDiagnosed(String diagnosed) {
        this.diagnosed = diagnosed;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(problem);
        dest.writeString(diagnosed);
    }
}
