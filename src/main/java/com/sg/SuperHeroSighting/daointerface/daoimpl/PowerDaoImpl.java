/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.SuperHeroSighting.daointerface.daoimpl;

import com.sg.SuperHeroSighting.daointerface.PowerDao;
import com.sg.SuperHeroSighting.daointerface.daoimpl.mapper.PowerMapper;
import com.sg.SuperHeroSighting.dto.Power;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 *
 * @author RAC
 */
@Repository
@Profile("database")
public class PowerDaoImpl implements PowerDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PowerDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Power addPower(Power power) {
        final String INSERT_POWER = "INSERT INTO powers"
                + "(name, description) VALUES (?, ?);";
        jdbcTemplate.update(INSERT_POWER,
                power.getName(),
                power.getDescription());

        int newId = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        power.setId(newId);
        return power;
    }

    @Override
    public List<Power> getAllPower() {
        final String sql = "SELECT * FROM powers;";
        return jdbcTemplate.query(sql, new PowerMapper());
    }

    @Override
    public Power getOnePower(int id) {
        final String SELECT_POWER = "SELECT * FROM powers WHERE id = ?;";
        return jdbcTemplate.queryForObject(SELECT_POWER, new PowerMapper(), id);
    }

    @Override
    public boolean updatePower(Power power) {
        final String sql = "UPDATE powers SET name = ?, description = ? "
                + "WHERE id = ?;";

        return jdbcTemplate.update(sql,
                power.getName(),
                power.getDescription(),
                power.getId()) > 0;
    }

    @Override
    public boolean deletePower(int id) {
        deletePowerFromHerosById(id);
        final String sqlPower = "DELETE FROM powers WHERE id = ?;";
        return jdbcTemplate.update(sqlPower, id) > 0;
    }

    private boolean deletePowerFromHerosById(Integer id) {
        final String sqlPower = "DELETE FROM herovills_powers where power_id = ?;";
        return jdbcTemplate.update(sqlPower, id) > 0;
    }
}
