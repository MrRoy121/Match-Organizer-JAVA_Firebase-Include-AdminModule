package com.example.bph_esports.helper;

public class history_list {
    String stats, datetime, uid;
    Integer amount;

    public history_list() {
    }

    public history_list(String stats, String datetime, String uid, Integer amount) {
        this.stats = stats;
        this.datetime = datetime;
        this.uid = uid;
        this.amount = amount;
    }

    public String getStats() {
        return stats;
    }

    public void setStats(String stats) {
        this.stats = stats;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }
}
