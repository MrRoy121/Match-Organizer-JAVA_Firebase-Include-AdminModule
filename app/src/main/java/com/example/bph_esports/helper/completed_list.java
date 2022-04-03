package com.example.bph_esports.helper;

public class completed_list {
    String type, matchid, tname, kills, uid;

    public completed_list() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMatchid() {
        return matchid;
    }

    public void setMatchid(String matchid) {
        this.matchid = matchid;
    }

    public String getTname() {
        return tname;
    }

    public void setTname(String tname) {
        this.tname = tname;
    }

    public String getKills() {
        return kills;
    }

    public void setKills(String kills) {
        this.kills = kills;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public completed_list(String type, String matchid, String tname, String kills, String uid) {
        this.type = type;
        this.matchid = matchid;
        this.tname = tname;
        this.kills = kills;
        this.uid = uid;
    }
}
