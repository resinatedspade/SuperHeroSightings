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
public class Organization {

    private int id;
    private String name;
    private String description;
    private String contact;
    private Location loc;
    private List<HeroVillain> heroVill;

    public Organization() {
    }

    public Organization(int id, String name, String description, String contact, Location loc, List<HeroVillain> heroVill) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.contact = contact;
        this.loc = loc;
        this.heroVill = heroVill;
    }

    @Override
    public String toString() {
        return "Organization{" + "id=" + id + ", name=" + name + ", description=" + description + ", contact=" + contact + ", loc=" + loc + ", heroVill=" + heroVill + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + this.id;
        hash = 71 * hash + Objects.hashCode(this.name);
        hash = 71 * hash + Objects.hashCode(this.description);
        hash = 71 * hash + Objects.hashCode(this.contact);
        hash = 71 * hash + Objects.hashCode(this.loc);
        hash = 71 * hash + Objects.hashCode(this.heroVill);
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
        final Organization other = (Organization) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.description, other.description)) {
            return false;
        }
        if (!Objects.equals(this.contact, other.contact)) {
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
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
}
