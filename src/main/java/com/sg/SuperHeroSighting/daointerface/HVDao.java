/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.SuperHeroSighting.daointerface;

import com.sg.SuperHeroSighting.dto.HeroVillain;
import java.util.List;

/**
 *
 * @author RAC
 */
public interface HVDao {

    public HeroVillain addHV(HeroVillain heroVill);

    public List<HeroVillain> getAllHV();

    public HeroVillain getOneHV(int id);

    public boolean updateHV(HeroVillain heroVill);

    public boolean deleteHV(int id);
    
    public List<HeroVillain> herosForLoc(int id);
    
    public List<HeroVillain> herosFromOrg(int id);
}
