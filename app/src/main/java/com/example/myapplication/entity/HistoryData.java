package com.example.myapplication.entity;

/**
 * 历史记录
 */
public class HistoryData {

    private int id;
    private int num;//违法次数
    private String tvTime;//违法时间
    private String imgPath1;//违章图片1
    private String imgPath2;//违章图片2
    private String imgPath3;//违章图片3

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public HistoryData(int id, int num, String tvTime, String imgPath1, String imgPath2, String imgPath3) {
        this.id = id;
        this.num = num;
        this.tvTime = tvTime;
        this.imgPath1 = imgPath1;
        this.imgPath2 = imgPath2;
        this.imgPath3 = imgPath3;
    }

    public HistoryData(int num, String tvTime, String imgPath1) {
        this.num = num;
        this.tvTime = tvTime;
        this.imgPath1 = imgPath1;
        this.imgPath2 = imgPath2;
        this.imgPath3 = imgPath3;
    }
    public HistoryData(int num, String tvTime, String imgPath1, String imgPath2, String imgPath3) {
        this.num = num;
        this.tvTime = tvTime;
        this.imgPath1 = imgPath1;
        this.imgPath2 = imgPath2;
        this.imgPath3 = imgPath3;
    }

    public HistoryData(int num, String tvTime, String imgPath1, String imgPath2) {
        this.num = num;
        this.tvTime = tvTime;
        this.imgPath1 = imgPath1;
        this.imgPath2 = imgPath2;
        this.imgPath3 = imgPath3;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getTvTime() {
        return tvTime;
    }

    public void setTvTime(String tvTime) {
        this.tvTime = tvTime;
    }

    public String getImgPath1() {
        return imgPath1;
    }

    public void setImgPath1(String imgPath1) {
        this.imgPath1 = imgPath1;
    }

    public String getImgPath2() {
        return imgPath2;
    }

    public void setImgPath2(String imgPath2) {
        this.imgPath2 = imgPath2;
    }

    public String getImgPath3() {
        return imgPath3;
    }

    public void setImgPath3(String imgPath3) {
        this.imgPath3 = imgPath3;
    }
}
