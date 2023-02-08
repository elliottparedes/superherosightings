/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.elliottparedes.superherosightings.service;

import com.elliottparedes.superherosightings.dao.LocationDao;
import com.elliottparedes.superherosightings.dao.OrganizationDao;
import com.elliottparedes.superherosightings.dao.SightingDao;
import com.elliottparedes.superherosightings.dao.SuperhumanDao;
import com.elliottparedes.superherosightings.entities.Location;
import com.elliottparedes.superherosightings.entities.Organization;
import com.elliottparedes.superherosightings.entities.Sighting;
import com.elliottparedes.superherosightings.entities.Superhuman;
import java.math.BigDecimal;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author Ellio
 */
@Component
public class ServiceLayer 
{
    @Autowired
    LocationDao locationDao;
    
    @Autowired
    OrganizationDao organizationDao;
    
    @Autowired
    SuperhumanDao superhumanDao;
    
    @Autowired
    SightingDao sightingDao;

    
    public List<Sighting> getAllSightings()
    {
        return sightingDao.getAllSightings();
    }
    
    public List<Location> getAllLocations()
    {
        return locationDao.getAllLocations();
    }
    
    public List<Superhuman> getAllSuperhumans()
    {
        return superhumanDao.getAllSuperhumans();
    }
    
    public void addSuperhuman(Superhuman superhuman)
    {
        superhumanDao.addSuperhuman(superhuman);
    }
    
    public void editSuperhuman(Superhuman superhuman)
    {
        superhumanDao.updateSuperhuman(superhuman);
    }
    
    public Superhuman getSuperhuman(int id)
    {
        return superhumanDao.getSuperhumanById(id);
    }
    
    public String addLocation(HttpServletRequest request, RedirectAttributes redirectAttributes)
    {
        
        
        Location location = new Location();
        location.setName(request.getParameter("name"));
        location.setDescription(request.getParameter("description"));
        location.setAddress(request.getParameter("address"));
        location.setCity(request.getParameter("city"));
        
        try 
        {
            location.setLatitude(new BigDecimal(request.getParameter("latitude")));
            location.setLongitude(new BigDecimal(request.getParameter("longitude")));
            
        } catch (NumberFormatException e)
        {
            System.out.println("We caught the error yay!");
            redirectAttributes.addAttribute("invalidInput", true);
            return "redirect:/locations";
        }
        
        
        boolean biggerthan90 = location.getLatitude().compareTo(new BigDecimal("90")) > 0;
        boolean smallerThanNegative90 = location.getLatitude().compareTo(new BigDecimal("-90")) < 0;
        
        boolean biggerThan180 = location.getLongitude().compareTo(new BigDecimal("180")) > 0;
        boolean smallerThanNeg180 = location.getLongitude().compareTo(new BigDecimal("-180")) < 0;
      
        if(biggerthan90 || smallerThanNegative90 || biggerThan180 || smallerThanNeg180)
        {
            redirectAttributes.addAttribute("invalidInput", true);
            return "redirect:/locations";
        }
        
        locationDao.addLocation(location);
        
        return "redirect:/locations";
    }
    public Location getLocationById(int id)
    {
        return locationDao.getLocationById(id);
    }
    
    public List<Organization> getAllOrganizations()
    {
        List<Organization> organizations = organizationDao.getAllOrganizations();
        return organizations;
    }
    public void addOrganization(Organization organization)
    {
        organizationDao.addOrganization(organization);
    }
    public void addSighting(Sighting sighting)
    {
        sightingDao.addSighting(sighting);
    }
    
    public void deleteSighting(int id)
    {
        sightingDao.deleteSightingById(id);
    }
    
    public void deleteSuperhuman(int id)
    {
        superhumanDao.deleteSuperhumanById(id);
    }
    public void deleteLocation(int id)
    {
        locationDao.deleteLocationById(id);
    }
    public void deleteOrganization(int id)
    {
        organizationDao.deleteOrganizationById(id);
    }
    public Organization getOrganization(int id)
    {
       return organizationDao.getOrganizationById(id);
    }
    public List<Organization> getOrganizationsForHero(int id)
    {
       return organizationDao.getOrganizationsBySuperhuman(superhumanDao.getSuperhumanById(id));
    }
    
    public List<Superhuman> getSuperhumansForOrganization(int id)
    {
        return organizationDao.getSuperhumansByOrganization(organizationDao.getOrganizationById(id));
    }
    
    public void AddSuperhumanToOrganization(Organization organization, Superhuman superhuman)
    {
        organizationDao.addSuperhumanToOrganization(superhuman, organization);
    }
    public void removeHeroFromOrganization(Organization organization, Superhuman superhuman)
    {
        organizationDao.removeSuperhumanFromOrganization(superhuman, organization);
    }
    
    public Sighting getSighting(int id)
    {
        return sightingDao.getSightingById(id);
    }
    
    public void editSighting(Sighting sighting)
    {
        sightingDao.updateSighting(sighting);
    }
    
    public void editOrganization(Organization organization)
    {
        organizationDao.updateOrganization(organization);
    }
    
    public String editLocation(HttpServletRequest request, RedirectAttributes redirectAttributes)
    {
           
        Location location = new Location();
        location.setId(Integer.parseInt(request.getParameter("id")));
        location.setName(request.getParameter("name"));
        location.setDescription(request.getParameter("description"));
        location.setAddress(request.getParameter("address"));
        location.setCity(request.getParameter("city"));
        
        try 
        {
            location.setLatitude(new BigDecimal(request.getParameter("latitude")));
            location.setLongitude(new BigDecimal(request.getParameter("longitude")));
            
        } catch (NumberFormatException e)
        {
            System.out.println("We caught the error yay!");
            redirectAttributes.addAttribute("invalidInput", true);
            redirectAttributes.addAttribute("id",location.getId());
            return "redirect:/editLocation";
        }
        
        
        boolean biggerthan90 = location.getLatitude().compareTo(new BigDecimal("90")) > 0;
        boolean smallerThanNegative90 = location.getLatitude().compareTo(new BigDecimal("-90")) < 0;
        
        boolean biggerThan180 = location.getLongitude().compareTo(new BigDecimal("180")) > 0;
        boolean smallerThanNeg180 = location.getLongitude().compareTo(new BigDecimal("-180")) < 0;
      
        if(biggerthan90 || smallerThanNegative90 || biggerThan180 || smallerThanNeg180)
        {
            redirectAttributes.addAttribute("invalidInput", true);
            redirectAttributes.addAttribute("id",location.getId());
            return "redirect:/editLocation";
        }
        
        
        locationDao.updateLocation(location);
        
        return "redirect:/locations";
        
     
    }
}
