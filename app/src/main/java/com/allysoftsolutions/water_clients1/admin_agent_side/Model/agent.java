package com.allysoftsolutions.water_clients1.admin_agent_side.Model;

public class agent {
    String aname,bottle,collected;

    public agent(String aname, String bottle, String collected) {
        this.aname = aname;
        this.bottle = bottle;
        this.collected = collected;
    }

    public String getAname() {
        return aname;
    }

    public void setAname(String aname) {
        this.aname = aname;
    }

    public String getBottle() {
        return bottle;
    }

    public void setBottle(String bottle) {
        this.bottle = bottle;
    }

    public String getCollected() {
        return collected;
    }

    public void setCollected(String collected) {
        this.collected = collected;
    }
}
