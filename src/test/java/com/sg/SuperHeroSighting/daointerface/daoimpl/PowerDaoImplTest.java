/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.SuperHeroSighting.daointerface.daoimpl;

import com.sg.SuperHeroSighting.dto.Power;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.*;
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
public class PowerDaoImplTest {

    @Autowired
    PowerDaoImpl testDao;

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
     * Test of addPower method, of class PowerDaoImpl.
     */
    @Test
    public void testAddPower() {
        Power power = new Power();
        power.setName("new power");
        power.setDescription("the great power");

        testDao.addPower(power);
        Power addedPower = testDao.getOnePower(power.getId());

        Assert.assertEquals("Should be 3 ", testDao.getAllPower().size(), 3);
        Assert.assertEquals("Power name should be", "new power", addedPower.getName());
        Assert.assertEquals("Power description should be ", "the great power", addedPower.getDescription());
    }

    /**
     * Test of getAllPower method, of class PowerDaoImpl.
     */
    @Test
    public void testGetAllPower() {
        List<Power> powers = testDao.getAllPower();

        Assert.assertEquals("Should be size ", 2, powers.size());
    }

    /**
     * Test of getOnePower method, of class PowerDaoImpl.
     */
    @Test
    public void testGetOnePower() {
        Power power = testDao.getOnePower(1);

        Assert.assertEquals("Power name Should be ", "flying", power.getName());
        Assert.assertEquals("Power description Should be ", "defi gravity", power.getDescription());
    }

    /**
     * Test of updatePower method, of class PowerDaoImpl.
     */
    @Test
    public void testUpdatePower() {
        Power power = testDao.getOnePower(1);
        power.setDescription("updated Description");
        testDao.updatePower(power);
        Power updatedPower = testDao.getOnePower(power.getId());

        Assert.assertEquals("Power description should be ", "updated Description", updatedPower.getDescription());
    }

    /**
     * Test of deletePower method, of class PowerDaoImpl.
     */
    @Test
    public void testDeletePower() {
        Power power = testDao.getOnePower(1);
        boolean deleted = testDao.deletePower(power.getId());

        Assert.assertEquals("Power Should be removed ", deleted, true);
    }
}
