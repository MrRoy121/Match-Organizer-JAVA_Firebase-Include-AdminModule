package com.example.bph_esports.helper;

public class match_list {
    String region;
    String fee;
    String per;

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public String getPer() {
        return per;
    }

    public void setPer(String per) {
        this.per = per;
    }

    public String getFst() {
        return fst;
    }

    public void setFst(String fst) {
        this.fst = fst;
    }

    public String getSnd() {
        return snd;
    }

    public void setSnd(String snd) {
        this.snd = snd;
    }

    public String getThd() {
        return thd;
    }

    public void setThd(String thd) {
        this.thd = thd;
    }

    public String getMax() {
        return max;
    }

    public void setMax(String max) {
        this.max = max;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPers() {
        return pers;
    }

    public void setPers(String pers) {
        this.pers = pers;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getMap() {
        return map;
    }

    public void setMap(String map) {
        this.map = map;
    }

    public match_list(String region, String fee, String per, String fst, String snd, String thd, String max, String time, String type, String pers, String uid, String map) {
        this.region = region;
        this.fee = fee;
        this.per = per;
        this.fst = fst;
        this.snd = snd;
        this.thd = thd;
        this.max = max;
        this.time = time;
        this.type = type;
        this.pers = pers;
        this.uid = uid;
        this.map = map;
    }

    String fst;
    String snd;
    String thd;
    String max;
    String time;
    String type;
    String pers;
    String uid;
    String map;


    public match_list() {
    }
}
