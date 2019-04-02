/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.SuperHeroSighting.daointerface.daoimpl;

import com.sg.SuperHeroSighting.daointerface.SightingDao;
import com.sg.SuperHeroSighting.daointerface.daoimpl.mapper.HeroVillMapper;
import com.sg.SuperHeroSighting.daointerface.daoimpl.mapper.LocationMapper;
import com.sg.SuperHeroSighting.daointerface.daoimpl.mapper.PowerMapper;
import com.sg.SuperHeroSighting.daointerface.daoimpl.mapper.SightingMapper;
import com.sg.SuperHeroSighting.dto.HeroVillain;
import com.sg.SuperHeroSighting.dto.Location;
import com.sg.SuperHeroSighting.dto.Power;
import com.sg.SuperHeroSighting.dto.Sighting;
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
public class SightingDaoImpl implements SightingDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public SightingDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    @Transactional
    public Sighting addsighting(Sighting sighting) {
        final String INSERT_HEROVILLIAN = "INSERT INTO sightings"
                + "(date, time, loc_id) VALUES (?, ?, ?);";
        jdbcTemplate.update(INSERT_HEROVILLIAN,
                sighting.getDate(),
                sighting.getTime(),
                sighting.getLoc().getId());

        int newId = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        sighting.setId(newId);
        insertHeroVills(sighting);
        return sighting;
    }

    @Override
    public List<Sighting> getAllSighting() {
        final String sql = "SELECT * FROM sightings;";
        List<Sighting> sightingsNeedHeros = jdbcTemplate.query(sql, new SightingMapper());

        for (Sighting sighting : sightingsNeedHeros) {
            sighting.setHeroVill(getHerosForSighting(sighting.getId()));
            sighting.setLoc(getLocForSighting(sighting.getId()));
            for (HeroVillain heroVill : sighting.getHeroVill()) {
                heroVill.setPower(getPowersForHeroVill(heroVill.getId()));
            }
        }
        return sightingsNeedHeros;
    }

    @Override
    public Sighting getOneSighting(int id) {
        final String SELECT_HeroVill = "SELECT * FROM sightings WHERE id = ?;";
        Sighting sighting = jdbcTemplate.queryForObject(SELECT_HeroVill, new SightingMapper(), id);
        sighting.setLoc(getLocForSighting(id));
        sighting.setHeroVill(getHerosForSighting(id));
        for (HeroVillain heroVill : sighting.getHeroVill()) {
            heroVill.setPower(getPowersForHeroVill(heroVill.getId()));
        }
        return sighting;
    }

    @Override
    @Transactional
    public boolean updateSighting(Sighting sighting) {
        final String sql = "UPDATE sightings SET date = ?, time = ?, "
                + "loc_id = ? WHERE id = ?;";
        removeHero(sighting.getId());
        insertHeroVills(sighting);

        return jdbcTemplate.update(sql,
                sighting.getDate(),
                sighting.getTime(),
                sighting.getLoc().getId(),
                sighting.getId()) > 0;
    }

    private void removeHero(int id) {
        final String DELETE_ORG_HEROS = "DELETE FROM herovills_sighted WHERE sighted_id = ?";
        jdbcTemplate.update(DELETE_ORG_HEROS, id);
    }

    @Override
    @Transactional
    public boolean deleteSighting(int id) {
        deleteSightedHerosById(id);
        final String sqlSighting = "DELETE FROM sightings WHERE id = ?;";
        return jdbcTemplate.update(sqlSighting, id) > 0;
    }

    private boolean deleteSightedHerosById(Integer id) {
        final String sqlSighted = "DELETE FROM herovills_sighted where sighted_id = ?;";
        return jdbcTemplate.update(sqlSighted, id) > 0;
    }

    @Override
    public List<Sighting> sightingsFromDate(String date) {
        final String sql = "SELECT DISTINCT sightings.* FROM sightings inner join "
                + "herovills_sighted on sightings.id = herovills_sighted.sighted_id"
                + " inner Join herovillians on herovillians.id = herovills_sighted.herovill_id"
                + " inner join locations on locations.id = sightings.loc_id "
                + "where sightings.date = ?;";

        List<Sighting> sightingsNeedHeros = jdbcTemplate.query(sql, new SightingMapper(), date);

        for (Sighting sighting : sightingsNeedHeros) {
            sighting.setHeroVill(getHerosForSighting(sighting.getId()));
            sighting.setLoc(getLocForSighting(sighting.getId()));

            for (HeroVillain heroVill : sighting.getHeroVill()) {
                heroVill.setPower(getPowersForHeroVill(heroVill.getId()));
            }
        }
        return sightingsNeedHeros;
    }

    private List<HeroVillain> getHerosForSighting(int id) {
        try {
            final String SELECT_HEROFORORG = "SELECT herovillians.* FROM herovills_sighted inner join "
                    + "herovillians on herovillians.id = herovills_sighted.herovill_id "
                    + "WHERE herovills_sighted.sighted_id = ?;";
            return jdbcTemplate.query(SELECT_HEROFORORG, new HeroVillMapper(), id);
        } catch (EmptyResultDataAccessException e) {
            List<HeroVillain> heroVills = new ArrayList<>();
            HeroVillain heroVill = new HeroVillain(0, "None", "unknown", getPowersForHeroVill(id));
            heroVills.add(heroVill);
            return heroVills;
        }
    }

    private Location getLocForSighting(int id) {
        try {
            final String SELECT_LOCFORORG = "SELECT locations.* FROM locations inner join "
                    + "sightings on sightings.loc_id = locations.id "
                    + "WHERE sightings.id = ?;";
            return jdbcTemplate.queryForObject(SELECT_LOCFORORG, new LocationMapper(), id);
        } catch (EmptyResultDataAccessException e) {
            Location loc = new Location(0, "unknown", "unknown", "unknown", new BigDecimal(00.000000), new BigDecimal(00.000000));
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

    private void insertHeroVills(Sighting sighting) {
        final String INSERT_HEROVILL_SIGHTING = "INSERT INTO herovills_sighted"
                + "(sighted_id, herovill_Id) VALUES (?, ?);";
        for (HeroVillain heroVill : sighting.getHeroVill()) {
            jdbcTemplate.update(INSERT_HEROVILL_SIGHTING,
                    sighting.getId(),
                    heroVill.getId());
        }
    }

    @Override
    public List<Sighting> sightingsLastTen() {
        final String sql = "SELECT * FROM sightings ORDER BY sightings.date DESC LIMIT 10;";
        List<Sighting> sightingsNeedHeros = jdbcTemplate.query(sql, new SightingMapper());

        for (Sighting sighting : sightingsNeedHeros) {
            sighting.setHeroVill(getHerosForSighting(sighting.getId()));
            sighting.setLoc(getLocForSighting(sighting.getId()));
            for (HeroVillain heroVill : sighting.getHeroVill()) {
                heroVill.setPower(getPowersForHeroVill(heroVill.getId()));
            }
        }
        return sightingsNeedHeros;
    }
}
