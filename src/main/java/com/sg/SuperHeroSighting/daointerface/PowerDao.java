/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.SuperHeroSighting.daointerface;

import com.sg.SuperHeroSighting.dto.Power;
import java.util.List;

/**
 *
 * @author RAC
 */
public interface PowerDao {

    public Power addPower(Power power);

    public List<Power> getAllPower();

    public Power getOnePower(int id);

    public boolean updatePower(Power power);

    public boolean deletePower(int id);
}
