/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.SuperHeroSighting.daointerface.daoimpl;

import com.sg.SuperHeroSighting.daointerface.OrganizationDao;
import com.sg.SuperHeroSighting.daointerface.daoimpl.mapper.HeroVillMapper;
import com.sg.SuperHeroSighting.daointerface.daoimpl.mapper.LocationMapper;
import com.sg.SuperHeroSighting.daointerface.daoimpl.mapper.OrgMapper;
import com.sg.SuperHeroSighting.daointerface.daoimpl.mapper.PowerMapper;
import com.sg.SuperHeroSighting.dto.HeroVillain;
import com.sg.SuperHeroSighting.dto.Location;
import com.sg.SuperHeroSighting.dto.Organization;
import com.sg.SuperHeroSighting.dto.Power;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author RAC
 */
@Repository
@Profile("database")
public class OrganizationDaoImpl implements OrganizationDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public OrganizationDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    @Transactional
    public Organization addOrg(Organization org) {
        final String INSERT_ORG = "INSERT INTO organizations"
                + "(name, description, contact, loc_id) VALUES (?, ?, ?, ?);";
        jdbcTemplate.update(INSERT_ORG,
                org.getName(),
                org.getDescription(),
                org.getContact(),
                org.getLoc().getId());

        int newId = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        org.setId(newId);
        insertHeroVills(org);
        return org;
    }

    @Override
    public List<Organization> getAllOrg() {
        final String sqlOrg = "SELECT * FROM organizations;";

        List<Organization> orgsNeedHeros = jdbcTemplate.query(sqlOrg, new OrgMapper());

        for (Organization org : orgsNeedHeros) {
            org.setHeroVill(getHerosForOrg(org.getId()));
            org.setLoc(getLocForOrg(org.getId()));
            for (HeroVillain heroVill : org.getHeroVill()) {
                heroVill.setPower(getPowersForHeroVill(heroVill.getId()));
            }
        }
        return orgsNeedHeros;
    }

    @Override
    public Organization getOneOrg(int id) {
        final String SELECT_ORG = "SELECT * FROM organizations WHERE id = ?;";
        Organization org = jdbcTemplate.queryForObject(SELECT_ORG, new OrgMapper(), id);
        org.setLoc(getLocForOrg(id));

        org.setHeroVill(getHerosForOrg(id));
        for (HeroVillain heroVill : org.getHeroVill()) {
            heroVill.setPower(getPowersForHeroVill(heroVill.getId()));
        }

        return org;
    }

    @Override
    @Transactional
    public boolean updateOrg(Organization org) {

        final String sql = "UPDATE organizations SET name = ?, description = ?, "
                + "contact = ?, loc_id = ? WHERE id = ?;";
        removeHero(org.getId());
        insertHeroVills(org);
        return jdbcTemplate.update(sql,
                org.getName(),
                org.getDescription(),
                org.getContact(),
                org.getLoc().getId(),
                org.getId()) > 0;
    }

    private void removeHero(int id) {
        final String DELETE_ORG_HEROS = "DELETE FROM herovills_orgs WHERE org_id = ?";
        jdbcTemplate.update(DELETE_ORG_HEROS, id);
    }

    @Override
    @Transactional
    public boolean deleteOrg(int id) {
        deleteHeroOrgsById(id);
        final String sqlHeroVill = "DELETE FROM organizations WHERE id = ?;";
        return jdbcTemplate.update(sqlHeroVill, id) > 0;
    }

    private boolean deleteHeroOrgsById(Integer id) {
        final String sqlOrg = "DELETE FROM herovills_orgs where org_id = ?;";
        return jdbcTemplate.update(sqlOrg, id) > 0;
    }

    @Override
    public List<Organization> orgsFromHero(int id) {
        final String sql = "SELECT organizations.* FROM herovillians "
                + "inner join herovills_orgs on herovills_orgs.herovill_id = "
                + "herovillians.id inner join organizations on "
                + "organizations.id = herovills_orgs.org_id "
                + "where herovillians.id = ?;";
        List<Organization> orgsNeedHeros = jdbcTemplate.query(sql, new OrgMapper(), id);
        for (Organization org : orgsNeedHeros) {
            org.setLoc(getLocForOrg(org.getId()));
            org.setHeroVill(getHerosForOrg(org.getId()));

            for (HeroVillain heroVill : org.getHeroVill()) {
                heroVill.setPower(getPowersForHeroVill(heroVill.getId()));
            }
        }
        return orgsNeedHeros;
    }

    private List<HeroVillain> getHerosForOrg(int id) {
        try {
            final String SELECT_HEROFORORG = "SELECT herovillians.* FROM herovills_orgs inner join "
                    + "herovillians on herovillians.id = herovills_orgs.herovill_id "
                    + "WHERE herovills_orgs.org_id = ?;";
            return jdbcTemplate.query(SELECT_HEROFORORG, new HeroVillMapper(), id);
        } catch (EmptyResultDataAccessException e) {
            List<HeroVillain> heroVills = new ArrayList<>();
            HeroVillain heroVill = new HeroVillain(0, "unknown", "unknown", getPowersForHeroVill(id));
            heroVills.add(heroVill);
            return heroVills;
        }
    }

    private Location getLocForOrg(int id) {
        try {
            final String SELECT_LOCFORORG = "SELECT locations.* FROM locations inner join "
                    + "organizations on organizations.loc_id = locations.id "
                    + "WHERE organizations.id = ?;";
            return jdbcTemplate.queryForObject(SELECT_LOCFORORG, new LocationMapper(), id);

        } catch (EmptyResultDataAccessException e) {
            Location loc = new Location(0, "None", "unknown", "unknown", new BigDecimal(00.000000), new BigDecimal(00.000000));
            return loc;
        }
    }

    private List<Power> getPowersForHeroVill(int id) {
        try {
            final String SELECT_POWERSFORHERO = "SELECT powers.* FROM "
                    + "herovills_powers inner join powers on"
                    + " powers.id = herovills_powers.power_id WHERE herovill_id = ?;";
            return jdbcTemplate.query(SELECT_POWERSFORHERO, new PowerMapper(), id);
        } catch (EmptyResultDataAccessException e) {
            List<Power> powers = new ArrayList<>();
            Power power = new Power(0, "unknown", "unknown");
            powers.add(power);
            return powers;
        }
    }

    private void insertHeroVills(Organization org) {
        final String INSERT_HEROVILL_ORG = "INSERT INTO herovills_orgs"
                + "(org_id, herovill_Id) VALUES (?, ?);";
        for (HeroVillain heroVill : org.getHeroVill()) {
            jdbcTemplate.update(INSERT_HEROVILL_ORG,
                    org.getId(),
                    heroVill.getId());
        }
    }
}
