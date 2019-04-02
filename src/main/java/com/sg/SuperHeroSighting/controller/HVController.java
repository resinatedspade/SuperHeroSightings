/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.SuperHeroSighting.controller;

import com.sg.SuperHeroSighting.dto.HeroVillain;
import com.sg.SuperHeroSighting.dto.Location;
import com.sg.SuperHeroSighting.dto.Organization;
import com.sg.SuperHeroSighting.dto.Power;
import com.sg.SuperHeroSighting.dto.Sighting;
import com.sg.SuperHeroSighting.service.HVService;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author RAC
 */
@Controller
public class HVController {

    @Autowired
    HVService service;

    @GetMapping("/index")
    public String homePage(Model model) {

        model.addAttribute("sightings", service.sightingsLastTen());
        return "index";
    }

    @GetMapping("/allHeros")
    public String allHerosPage(Model model) {
        model.addAttribute("allPowers", service.getAllPower());
        model.addAttribute("herovillains", service.getAllHV());
        return "allHeros";
    }

    @RequestMapping("/deleteHero")
    public String deleteHero(HttpServletRequest req) {
        service.deleteHV(Integer.parseInt(req.getParameter("heroid")));
        return "redirect:/allHeros";
    }

    @RequestMapping("/editHero")
    public String editHero(HttpServletRequest req) {
        HeroVillain heroVill = service.getOneHV(Integer.parseInt(req.getParameter("heroid")));
        List<Power> lPowers = new ArrayList<>();
        String[] powers = req.getParameterValues("powers");

        for (int i = 0; i < powers.length; i++) {
            Power power = service.getOnePower(Integer.parseInt(powers[i]));
            lPowers.add(power);
        }
        heroVill.setPower(lPowers);

        heroVill.setName(req.getParameter("name"));
        heroVill.setDescription(req.getParameter("description"));

        service.updateHV(heroVill);
        return "redirect:/allHeros";
    }

    @GetMapping("/allPowers")
    public String allPowersPage(Model model) {
        model.addAttribute("powers", service.getAllPower());
        return "allPowers";
    }

    @RequestMapping("/deletePower")
    public String deletePower(HttpServletRequest req) {
        service.deletePower(Integer.parseInt(req.getParameter("powerid")));
        return "redirect:/allPowers";
    }

    @RequestMapping("/editPower")
    public String editPower(HttpServletRequest req) {
        Power power = service.getOnePower(Integer.parseInt(req.getParameter("powerid")));
        power.setName(req.getParameter("name"));
        power.setDescription(req.getParameter("description"));
        service.updatePower(power);
        return "redirect:/allPowers";
    }

    @GetMapping("/allOrgs")
    public String allOrgsPage(Model model) {
        model.addAttribute("allLocations", service.getAllLoc());
        model.addAttribute("allHeros", service.getAllHV());
        model.addAttribute("orgs", service.getAllOrg());
        return "allOrgs";
    }

    @RequestMapping("/deleteOrg")
    public String deleteOrg(HttpServletRequest req) {
        service.deleteOrg(Integer.parseInt(req.getParameter("orgid")));
        return "redirect:/allOrgs";
    }

    @RequestMapping("/editOrg")
    public String editOrg(HttpServletRequest req) {
        Organization org = service.getOneOrg(Integer.parseInt(req.getParameter("orgid")));
        List<HeroVillain> lHero = new ArrayList<>();
        String[] heroVills = req.getParameterValues("heros");
        Location loc = service.getOneLoc(Integer.parseInt(req.getParameter("location")));

        for (int i = 0; i < heroVills.length; i++) {
            HeroVillain heroVill = service.getOneHV(Integer.parseInt(heroVills[i]));
            lHero.add(heroVill);
        }
        org.setHeroVill(lHero);

        org.setName(req.getParameter("name"));
        org.setDescription(req.getParameter("description"));
        org.setLoc(loc);
        org.setContact(req.getParameter("contact"));
        service.updateOrg(org);
        return "redirect:/allOrgs";
    }

    @GetMapping("/allLocs")
    public String allLocsPage(Model model) {
        model.addAttribute("locations", service.getAllLoc());
        return "allLocs";
    }

    @RequestMapping("/deleteLoc")
    public String deleteLoc(HttpServletRequest req) {
        service.deleteLoc(Integer.parseInt(req.getParameter("locid")));
        return "redirect:/allLocs";
    }

    @RequestMapping("/editLoc")
    public String editLoc(HttpServletRequest req) {
        Location loc = service.getOneLoc(Integer.parseInt(req.getParameter("locid")));
        loc.setName(req.getParameter("name"));
        loc.setDescription(req.getParameter("description"));
        loc.setAddress(req.getParameter("address"));
        loc.setLatitude(new BigDecimal(req.getParameter("latitude")));
        loc.setLongitude(new BigDecimal(req.getParameter("longitude")));
        service.updateLoc(loc);
        return "redirect:/allLocs";
    }

    @GetMapping("/allSigs")
    public String allSigsPage(Model model) {
        model.addAttribute("allLocations", service.getAllLoc());
        model.addAttribute("allHeros", service.getAllHV());
        model.addAttribute("sightings", service.getAllSighting());
        return "allSigs";
    }

    @RequestMapping("/deleteSig")
    public String deleteSig(HttpServletRequest req) {
        service.deleteSighting(Integer.parseInt(req.getParameter("sigid")));
        return "redirect:/allSigs";
    }

    @RequestMapping("/editSig")
    public String editSig(HttpServletRequest req) {
        Sighting sighting = service.getOneSighting(Integer.parseInt(req.getParameter("sigid")));
        List<HeroVillain> lHeros = new ArrayList<>();
        String[] heros = req.getParameterValues("hero");
        Location loc = service.getOneLoc(Integer.parseInt(req.getParameter("location")));
        for (int i = 0; i < heros.length; i++) {
            HeroVillain heroVill = service.getOneHV(Integer.parseInt(heros[i]));
            lHeros.add(heroVill);
        }
        sighting.setHeroVill(lHeros);

        sighting.setDate(req.getParameter("date"));
        sighting.setTime(req.getParameter("time"));
        sighting.setLoc(loc);
        service.updateSighting(sighting);
        return "redirect:/allSigs";
    }

    @GetMapping("/addHero")
    public String addHeroPage(Model model) {
        model.addAttribute("allheros", service.getAllHV());
        model.addAttribute("allpowers", service.getAllPower());
        return "addHero";
    }

    @PostMapping("/addingHero")
    public String addingHero(HttpServletRequest req) {
        HeroVillain heroVill = new HeroVillain();
        List<Power> lPowers = new ArrayList<>();
        String name = req.getParameter("name");
        String description = req.getParameter("description");
        String[] powers = req.getParameterValues("powers");
        for (int i = 0; i < powers.length; i++) {
            Power power = service.getOnePower(Integer.parseInt(powers[i]));
            lPowers.add(power);
        }
        heroVill.setName(name);
        heroVill.setDescription(description);
        heroVill.setPower(lPowers);
        service.addHV(heroVill);
        return "redirect:/addHero";
    }

    @GetMapping("/addPower")
    public String addPowerPage(Model model) {
        return "addPower";
    }

    @PostMapping("/addingPower")
    public String addingPower(HttpServletRequest req) {
        Power power = new Power();
        power.setName(req.getParameter("name"));
        power.setDescription(req.getParameter("description"));
        service.addPower(power);
        return "redirect:/addPower";
    }

    @GetMapping("/addOrg")
    public String addOrgPage(Model model) {
        model.addAttribute("alllocations", service.getAllLoc());
        model.addAttribute("allheros", service.getAllHV());
        return "addOrg";
    }

    @PostMapping("/addingOrg")
    public String addingOrg(HttpServletRequest req) {
        Organization org = new Organization();
        List<HeroVillain> lHero = new ArrayList<>();
        String[] heroVills = req.getParameterValues("heros");
        Location loc = service.getOneLoc(Integer.parseInt(req.getParameter("location")));

        for (int i = 0; i < heroVills.length; i++) {
            HeroVillain heroVill = service.getOneHV(Integer.parseInt(heroVills[i]));
            lHero.add(heroVill);
        }
        org.setHeroVill(lHero);

        org.setName(req.getParameter("name"));
        org.setDescription(req.getParameter("description"));
        org.setContact(req.getParameter("contact"));
        org.setLoc(loc);
        service.addOrg(org);
        return "redirect:/addOrg";
    }

    @GetMapping("/addLoc")
    public String addLocPage(Model model) {
        return "addLoc";
    }

    @PostMapping("/addingLoc")
    public String addingLoc(HttpServletRequest req) {
        Location loc = new Location();
        loc.setName(req.getParameter("name"));
        loc.setDescription(req.getParameter("description"));
        loc.setAddress(req.getParameter("address"));
        loc.setLatitude(new BigDecimal(req.getParameter("latitude")));
        loc.setLongitude(new BigDecimal(req.getParameter("longitude")));
        service.addLoc(loc);
        return "redirect:/addLoc";
    }

    @GetMapping("/addSig")
    public String addSigPage(Model model) {
        model.addAttribute("alllocations", service.getAllLoc());
        model.addAttribute("allheros", service.getAllHV());
        return "addSig";
    }

    @PostMapping("/addingSig")
    public String addingSig(HttpServletRequest req) {
        Sighting sighting = new Sighting();
        List<HeroVillain> lHeros = new ArrayList<>();
        String[] heros = req.getParameterValues("heros");
        Location loc = service.getOneLoc(Integer.parseInt(req.getParameter("location")));
        for (int i = 0; i < heros.length; i++) {
            HeroVillain heroVill = service.getOneHV(Integer.parseInt(heros[i]));
            lHeros.add(heroVill);
        }
        sighting.setHeroVill(lHeros);
        sighting.setDate(req.getParameter("date"));
        sighting.setTime(req.getParameter("time"));
        sighting.setLoc(loc);
        service.addSighting(sighting);
        return "redirect:/addSig";
    }
    String hero;

    @GetMapping("/heroDetails")
    public String heroDetails(HttpServletRequest req, Model model) {
        model.addAttribute("herovill", service.getOneHV(Integer.parseInt(hero)));
        model.addAttribute("locs", service.locsForHero(Integer.parseInt(hero)));
        model.addAttribute("orgs", service.orgsFromHero(Integer.parseInt(hero)));

        return "heroDetails";
    }

    @PostMapping("/heroprocess")
    public String heroProcess(HttpServletRequest req, Model model) {
        this.hero = req.getParameter("heroid");
        return "redirect:/heroDetails";
    }

    String date;

    @GetMapping("/sightingsondate")
    public String sightingsOnDate(Model model) {
        model.addAttribute("sightings", service.sightingsFromDate(date));
        return "sightingsondate";
    }

    @PostMapping("/dateprocess")
    public String dateprocess(HttpServletRequest req, Model model) {
        date = req.getParameter("date");
        return "redirect:/sightingsondate";
    }
}
