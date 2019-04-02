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
import com.sg.SuperHeroSighting.dto.Organization;
import java.util.ArrayList;
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
public class OrganizationDaoImplTest {

    @Autowired
    OrganizationDaoImpl testDao;

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
     * Test of addOrg method, of class OrganizationDaoImpl.
     */
    @Test
    public void testAddOrg() {

        List<HeroVillain> heroVills = heroDao.getAllHV();
        List<HeroVillain> addedHeroVills = new ArrayList<>();
        List<Location> locs = locDao.getAllLoc();
        addedHeroVills.add(heroVills.get(0));

        Organization org = new Organization();
        org.setName("added org");
        org.setDescription("newest org");
        org.setContact("555-555-5555");
        org.setLoc(locs.get(0));
        org.setHeroVill(addedHeroVills);

        testDao.addOrg(org);
        Organization addedOrg = testDao.getOneOrg(org.getId());

        Assert.assertEquals("Should be 2 Organizations ", testDao.getAllOrg().size(), 2);
        Assert.assertEquals("Organization name should be ", "added org", addedOrg.getName());
        Assert.assertEquals("Organization should have 1 hero ", 1, addedOrg.getHeroVill().size());
    }

    /**
     * Test of getAllOrg method, of class OrganizationDaoImpl.
     */
    @Test
    public void testGetAllOrg() {
        List<Organization> orgs = testDao.getAllOrg();

        Assert.assertEquals("Should be size 1", 1, orgs.size());
    }

    /**
     * Test of getOneOrg method, of class OrganizationDaoImpl.
     */
    @Test
    public void testGetOneOrg() {
        Organization org = testDao.getOneOrg(1);

        Assert.assertEquals("Organization Should be ", "theHutt", org.getName());
        Assert.assertEquals("Org Should have 1 hero", 1, org.getHeroVill().size());
        Assert.assertEquals("Org Should have hero with powers", 2,
                org.getHeroVill().get(0).getPower().size());
        Assert.assertEquals("Orgs location name is ", "secret base",
                org.getLoc().getName());
    }

    /**
     * Test of updateOrg method, of class OrganizationDaoImpl.
     */
    @Test
    public void testUpdateOrg() {
        Organization org = testDao.getOneOrg(1);
        org.getHeroVill().remove(0);

        testDao.updateOrg(org);
        Organization updatedOrg = testDao.getOneOrg(org.getId());

        Assert.assertEquals("Should have 0 hero", 0, updatedOrg.getHeroVill().size());
    }

    /**
     * Test of deleteOrg method, of class OrganizationDaoImpl.
     */
    @Test
    public void testDeleteOrg() {
        Organization org = testDao.getOneOrg(1);
        boolean deleted = testDao.deleteOrg(org.getId());

        Assert.assertEquals("Organization Should be removed ", deleted, true);
    }

    /**
     * Test of orgsFromHero method, of class OrganizationDaoImpl.
     */
    @Test
    public void testOrgsFromHero() {
        List<Organization> orgs = testDao.orgsFromHero(1);

        Assert.assertEquals("Should be 1 org", 1, orgs.size());
    }

}
