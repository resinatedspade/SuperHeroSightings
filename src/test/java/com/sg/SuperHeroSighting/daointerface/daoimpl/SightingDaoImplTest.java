/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.SuperHeroSighting.daointerface.daoimpl;

import com.sg.SuperHeroSighting.daointerface.HVDao;
import com.sg.SuperHeroSighting.daointerface.LocationDao;
import com.sg.SuperHeroSighting.dto.HeroVillain;
import com.sg.SuperHeroSighting.dto.Location;
import com.sg.SuperHeroSighting.dto.Sighting;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

/**
 *
 * @author RAC
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@TestPropertySource(locations = "classpath:test.properties")
public class SightingDaoImplTest {

    @Autowired
    SightingDaoImpl testDao;

    @Autowired
    HVDao heroDao;

    @Autowired
    LocationDao locDao;

    @Autowired
    JdbcTemplate testTemplate;

    @Before
    public void setUp() {
        testTemplate.execute("SET SQL_SAFE_UPDATES = 0;");
        testTemplate.execute("DELETE FROM herovills_orgs WHERE 1=1;");
        testTemplate.execute("DELETE FROM herovills_sighted WHERE 1=1;");
        testTemplate.execute("DELETE FROM herovills_powers WHERE 1=1;");
        testTemplate.execute("DELETE FROM powers WHERE 1=1;");
        testTemplate.execute("DELETE FROM herovillians WHERE 1=1;");
        testTemplate.execute("DELETE FROM organizations WHERE 1=1;");
        testTemplate.execute("DELETE FROM sightings WHERE 1=1;");
        testTemplate.execute("DELETE FROM locations WHERE 1=1;");

        testTemplate.execute("INSERT INTO herovillians(id, name, description) VALUES (1, 'superman', 'saves the world');");
        testTemplate.execute("INSERT INTO powers (id, name, description) VALUES (1, 'flying', 'defi gravity');");
        testTemplate.execute("INSERT INTO powers (id, name, description) VALUES (2, 'super strength', 'picks things up and puts them down');");
        testTemplate.execute("INSERT INTO locations(id, name, description, address, latitude, longitude) VALUES (1, 'secret base', 'fortress','5555 one st noState 55555', 34.888888, -58.555555);");
        testTemplate.execute("INSERT INTO organizations(id, name, description, contact, loc_id) VALUES (1, 'theHutt', 'pizza delivery', '555-555-5555', 1);");
        testTemplate.execute("INSERT INTO sightings(id, date, time, loc_id) VALUES (1, '01/01/2019', '13:13', 1);");
        testTemplate.execute("INSERT INTO herovills_orgs(id, herovill_id, org_id) VALUES (1, 1, 1);");
        testTemplate.execute("INSERT INTO herovills_sighted(id, herovill_id, sighted_id) VALUES (1, 1, 1);");
        testTemplate.execute("INSERT INTO herovills_powers(id, power_id, herovill_Id) VALUES (1, 1, 1);");
        testTemplate.execute("INSERT INTO herovills_powers(id, power_id, herovill_Id) VALUES (2, 2, 1);");
    }

    /**
     * Test of add sighting method, of class SightingDaoImpl.
     */
    @Test
    public void testAddsighting() {
        List<HeroVillain> heroVills = heroDao.getAllHV();
        List<HeroVillain> addedHeroVills = new ArrayList<>();
        List<Location> locs = locDao.getAllLoc();
        addedHeroVills.add(heroVills.get(0));

        Sighting sighting = new Sighting();
        sighting.setLoc(locs.get(0));
        sighting.setHeroVill(addedHeroVills);
        sighting.setDate(LocalDate.now().format(DateTimeFormatter.ofPattern("MM/dd/yyyy")));
        sighting.setTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm")));

        testDao.addsighting(sighting);
        Sighting addedSighting = testDao.getOneSighting(sighting.getId());

        Assert.assertEquals("Should be 2 sightings ", testDao.getAllSighting().size(), 2);
        Assert.assertEquals("Sighing should have this location ", "secret base", addedSighting.getLoc().getName());
        Assert.assertEquals("Sighting should have 1 hero ", 1, addedSighting.getHeroVill().size());
    }

    /**
     * Test of getAllSighting method, of class SightingDaoImpl.
     */
    @Test
    public void testGetAllSighting() {
        List<Sighting> sightings = testDao.getAllSighting();

        Assert.assertEquals("Should be size 1", 1, sightings.size());
    }

    /**
     * Test of getOneSighting method, of class SightingDaoImpl.
     */
    @Test
    public void testGetOneSighting() {
        Sighting sighting = testDao.getOneSighting(1);

        Assert.assertEquals("Hero Should be ", "superman",
                sighting.getHeroVill().get(0).getName());
        Assert.assertEquals("sighting Should have 1 hero", 1, sighting.getHeroVill().size());
        Assert.assertEquals("Sighting Should have hero with powers", 2,
                sighting.getHeroVill().get(0).getPower().size());
        Assert.assertEquals("Sighting location name is ", "secret base",
                sighting.getLoc().getName());
    }

    /**
     * Test of updateSighting method, of class SightingDaoImpl.
     */
    @Test
    public void testUpdateSighting() {
        Sighting sighting = testDao.getOneSighting(1);
        sighting.getHeroVill().remove(0);

        testDao.updateSighting(sighting);
        Sighting updatedSighting = testDao.getOneSighting(sighting.getId());

        Assert.assertEquals("Should have 0 hero", 0, updatedSighting.getHeroVill().size());
    }

    /**
     * Test of deleteSighting method, of class SightingDaoImpl.
     */
    @Test
    public void testDeleteSighting() {
        Sighting sighting = testDao.getOneSighting(1);
        boolean deleted = testDao.deleteSighting(sighting.getId());

        Assert.assertEquals("Sighting Should be removed ", deleted, true);
    }

    /**
     * Test of sightingsFromDate method, of class SightingDaoImpl.
     */
    @Test
    public void testSightingsFromDate() {
        Sighting sighting = testDao.getOneSighting(1);
        
        List<Sighting> sightings = testDao.sightingsFromDate(sighting.getDate());
        
        Assert.assertEquals("Should be ", 1, sightings.size());
        Assert.assertEquals("Sighting loc name should be", "secret base", 
                sightings.get(0).getLoc().getName());
        
    }
}
