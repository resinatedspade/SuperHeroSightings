/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.SuperHeroSighting.daointerface.daoimpl;

import com.sg.SuperHeroSighting.daointerface.LocationDao;
import com.sg.SuperHeroSighting.daointerface.daoimpl.mapper.LocationMapper;
import com.sg.SuperHeroSighting.dto.Location;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author RAC
 */
@Repository
@Profile("database")
public class LocationDaoImpl implements LocationDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public LocationDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    @Transactional
    public Location addLoc(Location loc) {

        final String INSERT_HEROVILLIAN = "INSERT INTO locations"
                + "(name, description, address, latitude, longitude) VALUES (?, ?, ?, ?, ?);";
        jdbcTemplate.update(INSERT_HEROVILLIAN,
                loc.getName(),
                loc.getDescription(),
                loc.getAddress(),
                loc.getLatitude(),
                loc.getLongitude());

        int newId = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        loc.setId(newId);
        return loc;
    }

    @Override
    public List<Location> getAllLoc() {
        final String sql = "SELECT * FROM locations;";
        return jdbcTemplate.query(sql, new LocationMapper());
    }

    @Override
    public Location getOneLoc(int id) {

        final String SELECT_LOCATIONS = "SELECT * FROM locations WHERE id = ?;";
        Location loc = jdbcTemplate.queryForObject(SELECT_LOCATIONS, new LocationMapper(), id);
        return loc;
    }

    @Override
    @Transactional
    public boolean updateLoc(Location loc) {

        final String sql = "UPDATE locations SET name = ?, description = ?, "
                + "address = ?, latitude = ?, longitude = ? "
                + "WHERE id = ?;";

        return jdbcTemplate.update(sql,
                loc.getName(),
                loc.getDescription(),
                loc.getAddress(),
                loc.getLatitude(),
                loc.getLongitude(),
                loc.getId()) > 0;
    }

    @Override
    @Transactional
    public boolean deleteLoc(int id) {
        deleteLocFromSightingsById(id);
        deleteLocFromOrgById(id);
        final String sqlHeroVill = "DELETE FROM locations WHERE id = ?;";
        return jdbcTemplate.update(sqlHeroVill, id) > 0;
    }

    private boolean deleteLocFromSightingsById(Integer id) {
        final String sqlSighted = "UPDATE sightings SET loc_id = null where loc_id = ?;";
        return jdbcTemplate.update(sqlSighted, id) > 0;
    }

    private boolean deleteLocFromOrgById(Integer id) {
        final String sqlHeroVillOrg = "UPDATE organizations SET loc_id = null where loc_id = ?;";
        return jdbcTemplate.update(sqlHeroVillOrg, id) > 0;
    }
    
    @Override
    public List<Location> locsForHero(int id) {
        final String sql = "SELECT locations.* FROM herovillians "
                + "inner join herovills_sighted on herovills_sighted.herovill_id = "
                + "herovillians.id inner Join sightings on sightings.id = "
                + "herovills_sighted.sighted_id inner join locations on "
                + "locations.id = sightings.loc_id where herovillians.id = ?;";
        return jdbcTemplate.query(sql, new LocationMapper(), id);
    }
}

