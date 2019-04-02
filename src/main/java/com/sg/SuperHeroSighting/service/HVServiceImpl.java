/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.SuperHeroSighting.service;

import com.sg.SuperHeroSighting.daointerface.HVDao;
import com.sg.SuperHeroSighting.daointerface.LocationDao;
import com.sg.SuperHeroSighting.daointerface.OrganizationDao;
import com.sg.SuperHeroSighting.daointerface.PowerDao;
import com.sg.SuperHeroSighting.daointerface.SightingDao;
import com.sg.SuperHeroSighting.dto.HeroVillain;
import com.sg.SuperHeroSighting.dto.Location;
import com.sg.SuperHeroSighting.dto.Organization;
import com.sg.SuperHeroSighting.dto.Power;
import com.sg.SuperHeroSighting.dto.Sighting;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

/**
 *
 * @author RAC
 */
@Component
@Profile("database")
public class HVServiceImpl implements HVService {

    @Autowired
    HVDao hvDao;

    @Autowired
    LocationDao locDao;

    @Autowired
    OrganizationDao orgDao;

    @Autowired
    PowerDao powerDao;

    @Autowired
    SightingDao sightDao;

    @Override
    public HeroVillain addHV(HeroVillain heroVill) {
        return hvDao.addHV(heroVill);
    }

    @Override
    public Location addLoc(Location loc) {
        return locDao.addLoc(loc);
    }

    @Override
    public Organization addOrg(Organization org) {
        return orgDao.addOrg(org);
    }

    @Override
    public Power addPower(Power power) {
        return powerDao.addPower(power);
    }

    @Override
    public Sighting addSighting(Sighting sighting) {
        return sightDao.addsighting(sighting);
    }

    @Override
    public List<HeroVillain> getAllHV() {
        return hvDao.getAllHV();
    }

    @Override
    public List<Location> getAllLoc() {
        return locDao.getAllLoc();
    }

    @Override
    public List<Organization> getAllOrg() {
        return orgDao.getAllOrg();
    }

    @Override
    public List<Power> getAllPower() {
        return powerDao.getAllPower();
    }

    @Override
    public List<Sighting> getAllSighting() {
        return sightDao.getAllSighting();
    }

    @Override
    public HeroVillain getOneHV(int id) {
        return hvDao.getOneHV(id);
    }

    @Override
    public Location getOneLoc(int id) {
        return locDao.getOneLoc(id);
    }

    @Override
    public Organization getOneOrg(int id) {
        return orgDao.getOneOrg(id);
    }

    @Override
    public Power getOnePower(int id) {
        return powerDao.getOnePower(id);
    }

    @Override
    public Sighting getOneSighting(int id) {
        return sightDao.getOneSighting(id);
    }

    @Override
    public boolean updateHV(HeroVillain heroVill) {
        return hvDao.updateHV(heroVill);
    }

    @Override
    public boolean updateLoc(Location loc) {
        return locDao.updateLoc(loc);
    }

    @Override
    public boolean updateOrg(Organization org) {
        return orgDao.updateOrg(org);
    }

    @Override
    public boolean updatePower(Power power) {
        return powerDao.updatePower(power);
    }

    @Override
    public boolean updateSighting(Sighting sighting) {
        return sightDao.updateSighting(sighting);
    }

    @Override
    public boolean deleteHV(int id) {
        return hvDao.deleteHV(id);
    }

    @Override
    public boolean deleteLoc(int id) {
        return locDao.deleteLoc(id);
    }

    @Override
    public boolean deleteOrg(int id) {
        return orgDao.deleteOrg(id);
    }

    @Override
    public boolean deletePower(int id) {
        return powerDao.deletePower(id);
    }

    @Override
    public boolean deleteSighting(int id) {
        return sightDao.deleteSighting(id);
    }

    @Override
    public List<Location> locsForHero(int id) {
        return locDao.locsForHero(id);
    }

    @Override
    public List<HeroVillain> herosForLoc(int id) {
        return hvDao.herosForLoc(id);
    }

    @Override
    public List<Sighting> sightingsFromDate(String locDate) {
        return sightDao.sightingsFromDate(locDate);
    }

    @Override
    public List<HeroVillain> herosFromOrg(int id) {
        return hvDao.herosFromOrg(id);
    }

    @Override
    public List<Organization> orgsFromHero(int id) {
        return orgDao.orgsFromHero(id);
    }

    @Override
    public List<Sighting> sightingsLastTen() {
        return sightDao.sightingsLastTen();
    }
}
