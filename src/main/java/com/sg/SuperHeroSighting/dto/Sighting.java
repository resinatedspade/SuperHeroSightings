/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.SuperHeroSighting.dto;

import java.util.List;
import java.util.Objects;

/**
 *
 * @author RAC
 */
public class Sighting {

    private int id;
    private Location loc;
    private List<HeroVillain> heroVill;
    private String date;
    private String time;

    public Sighting() {
    }

    public Sighting(int id, Location loc, List<HeroVillain> heroVill, String date, String time) {
        this.id = id;
        this.loc = loc;
        this.heroVill = heroVill;
        this.date = date;
        this.time = time;
    }

    @Override
    public String toString() {
        return "Sighting{" + "id=" + id + ", loc=" + loc + ", heroVill=" + heroVill + ", date=" + date + ", time=" + time + '}';
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 71 * hash + this.id;
        hash = 71 * hash + Objects.hashCode(this.loc);
        hash = 71 * hash + Objects.hashCode(this.heroVill);
        hash = 71 * hash + Objects.hashCode(this.date);
        hash = 71 * hash + Objects.hashCode(this.time);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Sighting other = (Sighting) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.date, other.date)) {
            return false;
        }
        if (!Objects.equals(this.time, other.time)) {
            return false;
        }
        if (!Objects.equals(this.loc, other.loc)) {
            return false;
        }
        if (!Objects.equals(this.heroVill, other.heroVill)) {
            return false;
        }
        return true;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Location getLoc() {
        return loc;
    }

    public void setLoc(Location loc) {
        this.loc = loc;
    }

    public List<HeroVillain> getHeroVill() {
        return heroVill;
    }

    public void setHeroVill(List<HeroVillain> heroVill) {
        this.heroVill = heroVill;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
