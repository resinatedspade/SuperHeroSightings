/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.SuperHeroSighting.daointerface;

import com.sg.SuperHeroSighting.dto.Location;
import java.util.List;

/**
 *
 * @author RAC
 */
public interface LocationDao {

    public Location addLoc(Location loc);

    public List<Location> getAllLoc();

    public Location getOneLoc(int id);

    public boolean updateLoc(Location loc);

    public boolean deleteLoc(int id);
    
    public List<Location> locsForHero(int id);
}
