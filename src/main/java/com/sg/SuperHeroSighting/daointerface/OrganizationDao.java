/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.SuperHeroSighting.daointerface;

import com.sg.SuperHeroSighting.dto.Organization;
import java.util.List;

/**
 *
 * @author RAC
 */
public interface OrganizationDao {

    public Organization addOrg(Organization org);

    public List<Organization> getAllOrg();

    public Organization getOneOrg(int id);

    public boolean updateOrg(Organization org);

    public boolean deleteOrg(int id);
    
    public List<Organization> orgsFromHero(int id);
}
