/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.elliottparedes.superherosightings.controller;

import com.elliottparedes.superherosightings.entities.Sighting;
import com.elliottparedes.superherosightings.service.ServiceLayer;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 *
 * @author Ellio
 */
@Controller
public class IndexController 
{

    private final ServiceLayer service;
    
    @Autowired
    public IndexController(ServiceLayer service)
    {
        this.service = service;
    }
    
    @GetMapping(value = {"","/","/index"})
    public String displayIndex(Model model)
    {
        List<Sighting> sightings = new ArrayList();
        sightings = service.getAllSightings();
        
        List<Sighting> filteredSightings = sightings.stream().limit(10).collect(Collectors.toList());
        
 
        model.addAttribute("sightings",filteredSightings);
     
        
        return "index";
    }
    
    
    
}
