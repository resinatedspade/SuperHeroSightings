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
public class HeroVillain {

    private int id;
    private String name;
    private String description;
    private List<Power> power;

    public HeroVillain() {
    }

    public HeroVillain(int id, String name, String description, List<Power> power) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.power = power;
    }

    @Override
    public String toString() {
        return "HeroVillian{" + "id=" + id + ", name=" + name + ", description=" + description + ", power=" + power + '}';
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 23 * hash + this.id;
        hash = 23 * hash + Objects.hashCode(this.name);
        hash = 23 * hash + Objects.hashCode(this.description);
        hash = 23 * hash + Objects.hashCode(this.power);
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
        final HeroVillain other = (HeroVillain) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.description, other.description)) {
            return false;
        }
        if (!Objects.equals(this.power, other.power)) {
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

    public List<Power> getPower() {
        return power;
    }

    public void setPower(List<Power> power) {
        this.power = power;
    }
}
