/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.SuperHeroSighting.daointerface.daoimpl.mapper;

import com.sg.SuperHeroSighting.dto.HeroVillain;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author RAC
 */
public final class HeroVillMapper implements RowMapper<HeroVillain> {

    @Override
    public HeroVillain mapRow(ResultSet rs, int index) throws SQLException {
        HeroVillain td = new HeroVillain();
        td.setId(rs.getInt("id"));
        td.setName(rs.getString("name"));
        td.setDescription(rs.getString("description"));
        return td;
    }
}
