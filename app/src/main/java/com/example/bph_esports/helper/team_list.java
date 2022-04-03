package com.example.bph_esports.helper;

public class team_list {
    String name, type, uid, matchid;
    Integer kill;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getMatchid() {
        return matchid;
    }

    public void setMatchid(String matchid) {
        this.matchid = matchid;
    }

    public Integer getKill() {
        return kill;
    }

    public void setKill(Integer kill) {
        this.kill = kill;
    }

    public team_list(String name, String type, String uid, String matchid, Integer kill) {
        this.name = name;
        this.type = type;
        this.uid = uid;
        this.matchid = matchid;
        this.kill = kill;
    }

    public team_list() {
    }
}
