/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.SuperHeroSighting.daointerface.daoimpl.mapper;

import com.sg.SuperHeroSighting.dto.Sighting;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author RAC
 */
public final class SightingMapper implements RowMapper<Sighting> {

    @Override
    public Sighting mapRow(ResultSet rs, int index) throws SQLException {
        Sighting td = new Sighting();
        td.setId(rs.getInt("id"));
        td.setDate(rs.getString("date"));
        td.setTime(rs.getString("time"));
        return td;
    }
}

