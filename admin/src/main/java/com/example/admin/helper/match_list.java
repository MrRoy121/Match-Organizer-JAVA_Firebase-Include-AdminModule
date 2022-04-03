package com.example.admin.helper;

public class match_list {
    String region, fee, per, fst, scnd, thrd, max, time, type, pers, uid, map;

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

    public String getScnd() {
        return scnd;
    }

    public void setScnd(String scnd) {
        this.scnd = scnd;
    }

    public String getThrd() {
        return thrd;
    }

    public void setThrd(String thrd) {
        this.thrd = thrd;
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

    public match_list(String region, String fee, String per, String fst, String scnd, String thrd, String max, String time, String type, String pers, String uid, String map) {
        this.region = region;
        this.fee = fee;
        this.per = per;
        this.fst = fst;
        this.scnd = scnd;
        this.thrd = thrd;
        this.max = max;
        this.time = time;
        this.type = type;
        this.pers = pers;
        this.uid = uid;
        this.map = map;
    }

    public match_list() {
    }

}
