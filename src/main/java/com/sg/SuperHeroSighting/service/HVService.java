/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.SuperHeroSighting.service;

import com.sg.SuperHeroSighting.dto.HeroVillain;
import com.sg.SuperHeroSighting.dto.Location;
import com.sg.SuperHeroSighting.dto.Organization;
import com.sg.SuperHeroSighting.dto.Power;
import com.sg.SuperHeroSighting.dto.Sighting;
import java.util.List;

/**
 *
 * @author RAC
 */
public interface HVService {

    public HeroVillain addHV(HeroVillain heroVill);

    public Location addLoc(Location loc);

    public Organization addOrg(Organization org);

    public Power addPower(Power power);

    public Sighting addSighting(Sighting sighting);

    public List<HeroVillain> getAllHV();

    public List<Location> getAllLoc();

    public List<Organization> getAllOrg();

    public List<Power> getAllPower();

    public List<Sighting> getAllSighting();

    public HeroVillain getOneHV(int id);

    public Location getOneLoc(int id);

    public Organization getOneOrg(int id);

    public Power getOnePower(int id);

    public Sighting getOneSighting(int id);

    public boolean updateHV(HeroVillain heroVill);

    public boolean updateLoc(Location loc);

    public boolean updateOrg(Organization org);

    public boolean updatePower(Power power);

    public boolean updateSighting(Sighting sighting);

    public boolean deleteHV(int id);

    public boolean deleteLoc(int id);

    public boolean deleteOrg(int id);

    public boolean deletePower(int id);

    public boolean deleteSighting(int id);

    public List<Location> locsForHero(int id);

    public List<HeroVillain> herosForLoc(int id);

    public List<Sighting> sightingsFromDate(String date);
    
    public List<Sighting> sightingsLastTen();

    public List<HeroVillain> herosFromOrg(int id);

    public List<Organization> orgsFromHero(int id);
}
