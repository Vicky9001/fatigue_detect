package com.example.myapplication.entity;


import android.os.Parcel;
import android.os.Parcelable;

/**
 * 联系人
 */
public class LianXiRenData implements Parcelable {

    private int id;
    private String name;//名称
    private String phoneNumber;//手机号
    private int sex;//性别
    private String imgPath;//图片地址

    public LianXiRenData() {
    }

    public LianXiRenData(int id, String name, String phoneNumber, int sex, String imgPath) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.sex = sex;
        this.imgPath = imgPath;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeString(this.phoneNumber);
        dest.writeInt(this.sex);
        dest.writeString(this.imgPath);
    }

    public void readFromParcel(Parcel source) {
        this.id = source.readInt();
        this.name = source.readString();
        this.phoneNumber = source.readString();
        this.sex = source.readInt();
        this.imgPath = source.readString();
    }

    protected LianXiRenData(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.phoneNumber = in.readString();
        this.sex = in.readInt();
        this.imgPath = in.readString();
    }

    public static final Parcelable.Creator<LianXiRenData> CREATOR = new Parcelable.Creator<LianXiRenData>() {
        @Override
        public LianXiRenData createFromParcel(Parcel source) {
            return new LianXiRenData(source);
        }

        @Override
        public LianXiRenData[] newArray(int size) {
            return new LianXiRenData[size];
        }
    };
}
