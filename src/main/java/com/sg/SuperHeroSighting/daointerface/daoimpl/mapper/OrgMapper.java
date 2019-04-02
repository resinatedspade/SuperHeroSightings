/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.SuperHeroSighting.daointerface.daoimpl.mapper;

import com.sg.SuperHeroSighting.dto.Organization;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author RAC
 */
public final class OrgMapper implements RowMapper<Organization> {

    @Override
    public Organization mapRow(ResultSet rs, int index) throws SQLException {
        Organization td = new Organization();
        td.setId(rs.getInt("id"));
        td.setName(rs.getString("name"));
        td.setDescription(rs.getString("description"));
        td.setContact(rs.getString("contact"));
        return td;
    }
}
