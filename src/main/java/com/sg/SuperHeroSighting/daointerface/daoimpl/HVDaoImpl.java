/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.SuperHeroSighting.daointerface.daoimpl;

import com.sg.SuperHeroSighting.daointerface.HVDao;
import com.sg.SuperHeroSighting.daointerface.daoimpl.mapper.HeroVillMapper;
import com.sg.SuperHeroSighting.daointerface.daoimpl.mapper.PowerMapper;
import com.sg.SuperHeroSighting.dto.HeroVillain;
import com.sg.SuperHeroSighting.dto.Power;
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
public class HVDaoImpl implements HVDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public HVDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    @Transactional
    public HeroVillain addHV(HeroVillain heroVill) {

        final String INSERT_HEROVILLIAN = "INSERT INTO herovillians"
                + "(name, description) VALUES (?, ?);";
        jdbcTemplate.update(INSERT_HEROVILLIAN,
                heroVill.getName(),
                heroVill.getDescription());

        int newId = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        heroVill.setId(newId);
        insertPower(heroVill);
        return heroVill;
    }

    @Override
    public List<HeroVillain> getAllHV() {
        final String sql = "SELECT * FROM herovillians;";

        List<HeroVillain> heroesNeedingPowers = jdbcTemplate.query(sql, new HeroVillMapper());
        for (HeroVillain heroVill : heroesNeedingPowers) {

            heroVill.setPower(getPowersForHeroVill(heroVill.getId()));
        }
        return heroesNeedingPowers;
    }

    @Override
    public HeroVillain getOneHV(int id) {

        final String SELECT_HeroVill = "SELECT * FROM herovillians WHERE id = ?;";
        HeroVillain heroVill = jdbcTemplate.queryForObject(SELECT_HeroVill, new HeroVillMapper(), id);
        heroVill.setPower(getPowersForHeroVill(id));
        return heroVill;
    }

    private List<Power> getPowersForHeroVill(int id) {
        final String SELECT_POWERSFORHERO = "SELECT powers.* FROM "
                + "herovills_powers inner join powers on"
                + " powers.id = herovills_powers.power_id WHERE herovill_id = ?;";
        return jdbcTemplate.query(SELECT_POWERSFORHERO, new PowerMapper(), id);
    }

    @Override
    @Transactional
    public boolean updateHV(HeroVillain heroVill) {
        final String sql = "UPDATE herovillians SET name = ?, description = ? "
                + "WHERE id = ?;";
        removePowers(heroVill.getId());
        insertPower(heroVill);
        return jdbcTemplate.update(sql,
                heroVill.getName(),
                heroVill.getDescription(),
                heroVill.getId()) > 0;
    }

    private void removePowers(int id) {
        final String DELETE_POWERS = "DELETE FROM herovills_powers WHERE herovill_id = ?";
        jdbcTemplate.update(DELETE_POWERS, id);

    }

    @Override
    @Transactional
    public boolean deleteHV(int id) {
        deleteHeroSightedById(id);
        deleteHeroVillOrgById(id);
        deleteHeroPowersById(id);
        final String sqlHeroVill = "DELETE FROM herovillians WHERE id = ?;";
        return jdbcTemplate.update(sqlHeroVill, id) > 0;
    }

    private boolean deleteHeroSightedById(Integer id) {
        final String sqlSighted = "DELETE FROM herovills_sighted where herovill_id = ?;";
        return jdbcTemplate.update(sqlSighted, id) > 0;
    }

    private boolean deleteHeroPowersById(Integer id) {
        final String sqlPower = "DELETE FROM herovills_powers where herovill_id = ?;";
        return jdbcTemplate.update(sqlPower, id) > 0;
    }

    private boolean deleteHeroVillOrgById(Integer id) {
        final String sqlHeroVillOrg = "DELETE FROM herovills_orgs where herovill_id = ?;";
        return jdbcTemplate.update(sqlHeroVillOrg, id) > 0;
    }

    @Override
    public List<HeroVillain> herosForLoc(int id) {
        final String sql = "SELECT herovillians.* FROM locations inner join "
                + "sightings on sightings.loc_id = locations.id inner join "
                + "herovills_sighted on herovills_sighted.sighted_id = "
                + "sightings.id inner join herovillians on herovillians.id = "
                + "herovills_sighted.herovill_id where locations.id = ?;";
        return jdbcTemplate.query(sql, new HeroVillMapper(), id);
    }

    @Override
    public List<HeroVillain> herosFromOrg(int id) {
        final String sql = "SELECT herovillians.* FROM organizations inner "
                + "join herovills_orgs on herovills_orgs.org_id = "
                + "organizations.id inner join herovillians on herovillians.id = "
                + "herovills_orgs.herovill_id inner join locations on "
                + "locations.id = organizations.loc_id where organizations.id = ?;";
        return jdbcTemplate.query(sql, new HeroVillMapper(), id);
    }

    private void insertPower(HeroVillain heroVill) {
        final String INSERT_HEROVILL_POWER = "INSERT INTO herovills_powers"
                + "(power_id, herovill_Id) VALUES (?, ?);";
        for (Power power : heroVill.getPower()) {
            jdbcTemplate.update(INSERT_HEROVILL_POWER,
                    power.getId(),
                    heroVill.getId());
        }
    }
}
