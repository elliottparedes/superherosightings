/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.elliottparedes.superherosightings.controller;

import com.elliottparedes.superherosightings.entities.Location;
import com.elliottparedes.superherosightings.entities.Sighting;
import com.elliottparedes.superherosightings.entities.Superhuman;
import com.elliottparedes.superherosightings.service.ServiceLayer;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
public class SightingsController 
{
    private final ServiceLayer service;
    
    @Autowired
    public SightingsController(ServiceLayer service)
    {
        this.service = service;
    }
    
    @GetMapping("sightings")
    public String displaySightings(Model model)
    {
        List<Sighting> sightings = service.getAllSightings();
        List<Superhuman> superhumans = service.getAllSuperhumans();
        List<Location> locations = service.getAllLocations();
        
        model.addAttribute("sightings",sightings);
        model.addAttribute("superhumans",superhumans);
        model.addAttribute("locations", locations);
        
        return "sightings";
    }
    @PostMapping("addSighting")
    public String addSighting(HttpServletRequest request)
    {
        Superhuman superhuman = service.getSuperhuman(Integer.parseInt(request.getParameter("name")));
   
        Location location = service.getLocationById(Integer.parseInt(request.getParameter("location")));
        String date = request.getParameter("date");
   
        String time = request.getParameter("time");
        
        int hours = Integer.parseInt(time.substring(0, 2));
       
        int minutes = Integer.parseInt(time.substring(3));
        
        LocalDate newDate = LocalDate.parse(request.getParameter("date"));
        
        LocalTime newTime = LocalTime.of(hours,minutes,0);
        
        LocalDateTime localdatetime = LocalDateTime.of(newDate,newTime);
        
        System.out.println("This is the local date time: " + localdatetime.toString());
        
        Sighting sighting = new Sighting();
        sighting.setDate(localdatetime);
        sighting.setSuperHuman(superhuman);
        sighting.setLocation(location);
        
        service.addSighting(sighting);
        
        return "redirect:/sightings";
    }
    
    @GetMapping("deleteSighting")
    public String deleteSighting(HttpServletRequest request)
    {
        int id = Integer.parseInt(request.getParameter("id"));
        
        service.deleteSighting(id);
        
        return "redirect:/sightings";
    }
    
    @GetMapping("editSighting")
    public String editSighting(HttpServletRequest request, Model model)
    {
        Sighting sighting = service.getSighting(Integer.parseInt(request.getParameter("sightingId")));
        Superhuman superhuman = service.getSuperhuman(Integer.parseInt(request.getParameter("superhumanId")));
        
        model.addAttribute("sighting", sighting);
        model.addAttribute("superhuman", superhuman);
        
        List<Sighting> sightings = service.getAllSightings();
        List<Superhuman> superhumans = service.getAllSuperhumans();
        List<Location> locations = service.getAllLocations();
        
        model.addAttribute("sightings",sightings);
        model.addAttribute("superhumans",superhumans);
        model.addAttribute("locations", locations);
        
        
        return "editSighting";
        
    }
    
    @PostMapping("editSighting")
    public String editSighting(HttpServletRequest request, RedirectAttributes redirectAttributes)
    {
        int sightingId = Integer.parseInt(request.getParameter("sightingId")); 
        int superhumanId = Integer.parseInt(request.getParameter("superhumanId"));
        int locationId = Integer.parseInt(request.getParameter("locationId"));
        
        Superhuman superhuman = service.getSuperhuman(superhumanId);
        Location location = service.getLocationById(locationId);
        
        String date = request.getParameter("date");
   
        String time = request.getParameter("time");
        
        int hours = Integer.parseInt(time.substring(0, 2));
       
        int minutes = Integer.parseInt(time.substring(3));
        
        LocalDate newDate = LocalDate.parse(request.getParameter("date"));
        
        LocalTime newTime = LocalTime.of(hours,minutes,0);
        
        LocalDateTime localdatetime = LocalDateTime.of(newDate,newTime);
        
      
        Sighting sighting = new Sighting();
        sighting.setId(sightingId);
        sighting.setDate(localdatetime);
        sighting.setSuperHuman(superhuman);
        sighting.setLocation(location);
        
        service.editSighting(sighting);
        

        return "redirect:/sightings";
    }
    

}
