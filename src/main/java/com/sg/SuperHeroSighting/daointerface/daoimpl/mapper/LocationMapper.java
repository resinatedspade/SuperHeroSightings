/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.SuperHeroSighting.daointerface.daoimpl.mapper;

import com.sg.SuperHeroSighting.dto.Location;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author RAC
 */
public final class LocationMapper implements RowMapper<Location> {

    @Override
    public Location mapRow(ResultSet rs, int index) throws SQLException {
        Location td = new Location();
        td.setId(rs.getInt("id"));
        td.setName(rs.getString("name"));
        td.setAddress(rs.getString("address"));
        td.setDescription(rs.getString("description"));
        td.setLongitude(new BigDecimal(rs.getString("longitude")));
        td.setLatitude(new BigDecimal(rs.getString("latitude")));
        return td;
    }
}
