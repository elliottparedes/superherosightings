/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.elliottparedes.superherosightings.controller;

import com.elliottparedes.superherosightings.entities.Location;
import com.elliottparedes.superherosightings.service.ServiceLayer;
import java.math.BigDecimal;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author Ellio
 */
@Controller
public class LocationController 
{
    private final ServiceLayer service;
    
    @Autowired
    public LocationController(ServiceLayer service)
    {
        this.service = service;
    }

    @GetMapping("locations")
    public String displayLocations(Model model)
    {
        List<Location> locations = service.getAllLocations();
        
        model.addAttribute("locations",locations);
        
        return "locations";
    }
    
    @PostMapping("addLocation")
    public String addLocation(HttpServletRequest request, RedirectAttributes redirectAttributes)
    {
        return service.addLocation(request,redirectAttributes); 
    }
    
    @GetMapping("deleteLocation")
    public String deleteLocation(HttpServletRequest request)
    {
        service.deleteLocation(Integer.parseInt(request.getParameter("id")));
        
        return "redirect:/locations";
    }
    
    @GetMapping("editLocation")
    public String editLocation(HttpServletRequest request, Model model)
    {
        Location location = service.getLocationById(Integer.parseInt(request.getParameter("id")));
        model.addAttribute("location", location);
        
        return "editLocation";
    }
    @PostMapping("editLocation")
    public String editLocation(HttpServletRequest request, RedirectAttributes redirectAttributes)
    {
        return service.editLocation(request, redirectAttributes);
    }
    
    
}
