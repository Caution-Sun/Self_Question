package org.techtown.qself;

import android.os.Parcel;
import android.os.Parcelable;

public class Question implements Parcelable {
    int num;
    String title;
    String question;
    String answer;
    String tag;

    public Question(int num, String title, String question, String answer, String tag) {
        this.num = num;
        this.title = title;
        this.question = question;
        this.answer = answer;
        this.tag = tag;
    }

    public Question(Parcel src){
        num = src.readInt();
        title = src.readString();
        question = src.readString();
        answer = src.readString();
        tag = src.readString();
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getTag() {return tag;}

    public void setTag(String tag) {this.tag = tag;}

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator(){
        public Question createFromParcel(Parcel in){
            return new Question(in);
        }
        public Question[] newArray(int size){
            return new Question[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(num);
        dest.writeString(title);
        dest.writeString(question);
        dest.writeString(answer);
        dest.writeString(tag);
    }
}
