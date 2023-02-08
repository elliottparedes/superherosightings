/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.elliottparedes.superherosightings.controller;

import com.elliottparedes.superherosightings.entities.Location;
import com.elliottparedes.superherosightings.entities.Organization;
import com.elliottparedes.superherosightings.entities.Superhuman;
import com.elliottparedes.superherosightings.service.ServiceLayer;
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
public class OrganizationController 
{
    private final ServiceLayer service;
    
    @Autowired
    public OrganizationController(ServiceLayer service)
    {
        this.service = service;
    }
    
    @GetMapping("organizations")
    public String displayOrganizations(Model model)
    {
        List<Organization> organizations = service.getAllOrganizations();
        List<Location> locations = service.getAllLocations();
        model.addAttribute("organizations",organizations);
        model.addAttribute("locations", locations);
        return "organizations";
    }
    
    @PostMapping("addOrganization")
    public String addOrganization(HttpServletRequest request)
    {
        Organization organization = new Organization();
        
        organization.setName(request.getParameter("name"));
        organization.setDescription(request.getParameter("description"));
        
       organization.setLocation(service.getLocationById(Integer.parseInt(request.getParameter("location"))));
        
       
       service.addOrganization(organization);
        
        return "redirect:/organizations";
    }
    
    @GetMapping("deleteOrganization")
    public String deleteOrganization(HttpServletRequest request)
    {   
        service.deleteOrganization(Integer.parseInt(request.getParameter("id")));
       
        return "redirect:/organizations";
    }
    
    @GetMapping("displaySuperhumans")
    public String displaySuperhuamns(HttpServletRequest request, Model model)
    {
        List<Superhuman> superhumans = service.getSuperhumansForOrganization(Integer.parseInt(request.getParameter("id")));
        
        Organization organization = service.getOrganization(Integer.parseInt(request.getParameter("id")));
        
        model.addAttribute("superhumans", superhumans);
        model.addAttribute("organization", organization);
        
        return "displaySuperhumansForOrganization";
    }
    @GetMapping("editOrganization")
    public String editOrganization(HttpServletRequest request, Model model)
    {
        
        Organization organization = service.getOrganization(Integer.parseInt(request.getParameter("id")));
        List<Location> locations = service.getAllLocations();
        model.addAttribute("organization", organization);
        model.addAttribute("locations", locations);
        
        
        return "editOrganization";
    }
    @PostMapping("editOrganization")
    public String editOrganization(HttpServletRequest request)
    {
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        
        int locationId = Integer.parseInt(request.getParameter("location"));
        
        Organization organization = new Organization();
        organization.setId(id);
        organization.setName(name);
        organization.setDescription(description);
        organization.setLocation(service.getLocationById(locationId));
        
        service.editOrganization(organization);
        
        return "redirect:/organizations";
    }
}
