/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.elliottparedes.superherosightings.controller;

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
public class SuperhumanController 
{
     private final ServiceLayer service;
    
    @Autowired
    public SuperhumanController(ServiceLayer service)
    {
        this.service = service;
    }
    
    @GetMapping("superhumans")
    public String displaySuperhumans(Model model)
    {
        List<Superhuman> superhumans = service.getAllSuperhumans();
        model.addAttribute("superhumans", superhumans);
        return "superhumans";
    }
    
    @PostMapping("addSuperhuman")
    public String addLocation(HttpServletRequest request)
    {
        Superhuman superhuman = new Superhuman();
        superhuman.setName(request.getParameter("name"));
        superhuman.setDescription(request.getParameter("description"));
        superhuman.setSuperpower(request.getParameter("superpower"));
        
        if (request.getParameter("herostatus").equals("True"))
            superhuman.setIsHero(true);
        else 
            superhuman.setIsHero(false);
        
        service.addSuperhuman(superhuman);
        
        return "redirect:/superhumans";
        
        
    }
    @GetMapping("editSuperhuman")
    public String editSuperhuman(HttpServletRequest request, Model model)
    {
        Superhuman superhuman = service.getSuperhuman(Integer.parseInt(request.getParameter("id")));
        model.addAttribute("superhuman", superhuman);
        return "editSuperhuman";
    }
    
    @PostMapping("editSuperhuman")
    public String editSuperhuman(HttpServletRequest request)
    {
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        String power = request.getParameter("superpower");
        String herostatus = request.getParameter("herostatus");
        boolean isHero = true;
        
        if(herostatus.equals("False"))
            isHero=false;
        
        Superhuman superhuman = new Superhuman();
        superhuman.setId(id);
        superhuman.setName(name);
        superhuman.setDescription(description);
        superhuman.setSuperpower(power);
        superhuman.setIsHero(isHero);
        
        
        
        service.editSuperhuman(superhuman);
        
        
        return "redirect:/superhumans";
    }
    @GetMapping("deleteSuperhuman")
    public String deleteSuperhuman(HttpServletRequest request)
    {
        service.deleteSuperhuman(Integer.parseInt(request.getParameter("id")));
        return "redirect:/superhumans";
    }
    
    @GetMapping("displayOrganizations")
    public String displayOrganizations(HttpServletRequest request, Model model)
    {
        Superhuman superhuman = service.getSuperhuman(Integer.parseInt(request.getParameter("superhumanId")));
        List<Organization> organizations = service.getOrganizationsForHero(Integer.parseInt(request.getParameter("superhumanId")));
        List<Organization> allOrganizations = service.getAllOrganizations();
        
        model.addAttribute("superhuman", superhuman);
        model.addAttribute("organizations", organizations);
        model.addAttribute("allOrganizations", allOrganizations);
        
        return "displayOrganizationsForHero";
    }
    
    @PostMapping("/addOrganizationToHero")
    public String addOrganizationToHero(HttpServletRequest request, RedirectAttributes redirectAttributes)
    {
        System.out.println(request.getParameterValues("organizationId")[0]);
        System.out.println(request.getParameterValues("organizationId")[1]);
        System.out.println(request.getParameterValues("organizationId")[2]);
        System.out.println(request.getParameter("organizationId"));
        int superhumanId = Integer.parseInt(request.getParameter("superhumanId"));
        int organizationId = Integer.parseInt(request.getParameter("organizationId"));
        Superhuman superhuman = service.getSuperhuman(superhumanId);
        Organization organization = service.getOrganization(organizationId);
        
        
        service.AddSuperhumanToOrganization(organization, superhuman);
        
        redirectAttributes.addAttribute("superhumanId", superhumanId);
        
        return "redirect:/displayOrganizations";
    }
    
    
    @GetMapping("/removeOrganizationFromHero")
    public String removeHeroFromOrganization(HttpServletRequest request, RedirectAttributes redirectAttributes)
    {
        int superhumanId = Integer.parseInt(request.getParameter("superhumanId"));
        int organizationId = Integer.parseInt(request.getParameter("organizationId"));
        Superhuman superhuman = service.getSuperhuman(superhumanId);
        Organization organization = service.getOrganization(organizationId);
        
        
        service.removeHeroFromOrganization(organization, superhuman);
        
        redirectAttributes.addAttribute("superhumanId", superhumanId);
        
        return "redirect:/displayOrganizations";
    }
    
    
    
    
}
