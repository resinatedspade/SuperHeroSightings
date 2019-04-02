/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.SuperHeroSighting.daointerface.daoimpl;

import com.sg.SuperHeroSighting.dto.Location;
import java.math.BigDecimal;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
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
@Profile("test")
public class LocationDaoImplTest {

    @Autowired
    LocationDaoImpl testDao;

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
     * Test of addLoc method, of class LocationDaoImpl.
     */
    @Test
    public void testAddLoc() {

        Location loc = new Location();
        loc.setName("added location");
        loc.setDescription("the second location");
        loc.setAddress("5555 one st noState 55555");
        loc.setLatitude(new BigDecimal("34.888888"));
        loc.setLongitude(new BigDecimal("-58.555555"));

        testDao.addLoc(loc);
        Location addedLoc = testDao.getOneLoc(loc.getId());

        Assert.assertEquals("Should be 2 locations", testDao.getAllLoc().size(), 2);
        Assert.assertEquals("addedLocation should have id 2", loc.getId(), addedLoc.getId());
        Assert.assertEquals("The location name should be", "added location", addedLoc.getName());
    }

    /**
     * Test of getAllLoc method, of class LocationDaoImpl.
     */
    @Test
    public void testGetAllLoc() {
        List<Location> locations = testDao.getAllLoc();
        Assert.assertEquals("Should be size ", 1, locations.size());
    }

    /**
     * Test of getOneLoc method, of class LocationDaoImpl.
     */
    @Test
    public void testGetOneLoc() {
        Location loc = testDao.getOneLoc(1);

        Assert.assertEquals("Location name Should be ", "secret base", loc.getName());
        Assert.assertEquals("secret base description Should be ", "fortress", loc.getDescription());
        Assert.assertEquals("secret base address Should be ", "5555 one st noState 55555", loc.getAddress());
    }

    /**
     * Test of updateLoc method, of class LocationDaoImpl.
     */
    @Test
    public void testUpdateLoc() {
        Location loc = testDao.getOneLoc(1);
        loc.setDescription("dump house");
        testDao.updateLoc(loc);
        Location updatedLoc = testDao.getOneLoc(loc.getId());

        Assert.assertEquals("description should be changed to ", "dump house", updatedLoc.getDescription());
    }

    /**
     * Test of deleteLoc method, of class LocationDaoImpl.
     */
    @Test
    public void testDeleteLoc() {
        Location loc = testDao.getOneLoc(1);
        boolean deleted = testDao.deleteLoc(loc.getId());

        Assert.assertEquals("Location Should be removed ", deleted, true);
    }

    /**
     * Test of locsForHero method, of class LocationDaoImpl.
     */
    @Test
    public void testLocsForHero() {
        List<Location> locs = testDao.locsForHero(1);

        Assert.assertEquals("Should be 1 location", 1, locs.size());
    }
}
