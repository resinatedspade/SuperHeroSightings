/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.SuperHeroSighting.daointerface.daoimpl;

import com.sg.SuperHeroSighting.daointerface.PowerDao;
import com.sg.SuperHeroSighting.dto.HeroVillain;
import com.sg.SuperHeroSighting.dto.Power;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
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
public class HVDaoImplTest {

    @Autowired
    HVDaoImpl testDao;
    @Autowired
    PowerDao powerDao;

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
     * Test of addHV method, of class HVDaoImpl.
     */
    @Test
    public void testAddHV() {

        List<Power> powers = powerDao.getAllPower();
        List<Power> heroPowers = new ArrayList<>();
        HeroVillain heroVill = new HeroVillain();
        heroVill.setName("tommy");
        heroVill.setDescription("paper delivery");
        heroPowers.add(powers.get(1));
        heroVill.setPower(heroPowers);

        testDao.addHV(heroVill);
        HeroVillain vill = testDao.getOneHV(heroVill.getId());

        Assert.assertEquals("Should be 2 HV", testDao.getAllHV().size(), 2);
        Assert.assertEquals("tommy should have 1 power", 1, vill.getPower().size());
        Assert.assertEquals("The power should be", "super strength", vill.getPower().get(0).getName());
    }

    /**
     * Test of getAllHV method, of class HVDaoImpl.
     */
    @Test
    public void testGetAllHV() {
        List<HeroVillain> heroVills = testDao.getAllHV();

        Assert.assertEquals("Should be size 1", 1, heroVills.size());
    }

    /**
     * Test of getOneHV method, of class HVDaoImpl.
     */
    @Test
    public void testGetOneHV() {
        HeroVillain heroVill = testDao.getOneHV(1);

        Assert.assertEquals("Hero Should be SuperMan", "superman", heroVill.getName());
        Assert.assertEquals("SuperMan Should have 2 powers", 2, heroVill.getPower().size());
        Assert.assertEquals("SuperMan Should have Super Strength", "super strength",
                heroVill.getPower().get(1).getName());
        Assert.assertEquals("SuperMan Should have Flying", "flying",
                heroVill.getPower().get(0).getName());
    }

    /**
     * Test of updateHV method, of class HVDaoImpl.
     */
    @Test
    public void testUpdateHV() {

        HeroVillain heroVill = testDao.getOneHV(1);
        heroVill.getPower().remove(0);

        testDao.updateHV(heroVill);
        HeroVillain updatedHeroVill = testDao.getOneHV(1);

        Assert.assertEquals("Should have 1 power", 1, updatedHeroVill.getPower().size());
    }

    /**
     * Test of deleteHV method, of class HVDaoImpl.
     */
    @Test
    public void testDeleteHV() {
        HeroVillain heroVill = testDao.getOneHV(1);
        boolean deleted = testDao.deleteHV(heroVill.getId());

        Assert.assertEquals("Hero Should be removed list of heros is empty", deleted, true);
    }

    /**
     * Test of herosForLoc method, of class HVDaoImpl.
     */
    @Test
    public void testHerosForLoc() {
        List<HeroVillain> heroVills = testDao.herosForLoc(1);

        Assert.assertEquals("Should be 1 hero", 1, heroVills.size());
    }

    /**
     * Test of herosFromOrg method, of class HVDaoImpl.
     */
    @Test

    public void testHerosFromOrg() {
        List<HeroVillain> heroVills = testDao.herosFromOrg(1);

        Assert.assertEquals("Should be 1 hero", 1, heroVills.size());
    }
}
