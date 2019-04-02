/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.SuperHeroSighting.daointerface;

import com.sg.SuperHeroSighting.dto.Sighting;
import java.util.List;

/**
 *
 * @author RAC
 */
public interface SightingDao {

    public Sighting addsighting(Sighting sighting);

    public List<Sighting> getAllSighting();

    public Sighting getOneSighting(int id);

    public boolean updateSighting(Sighting sighting);

    public boolean deleteSighting(int id);
    
    public List<Sighting> sightingsFromDate(String date);
    
    public List<Sighting> sightingsLastTen();
}
