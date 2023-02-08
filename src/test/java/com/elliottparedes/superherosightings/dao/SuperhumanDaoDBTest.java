/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.elliottparedes.superherosightings.dao;

import com.elliottparedes.superherosightings.entities.Location;
import com.elliottparedes.superherosightings.entities.Organization;
import com.elliottparedes.superherosightings.entities.Sighting;
import com.elliottparedes.superherosightings.entities.Superhuman;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 *
 * @author Elliott
 */

@SpringBootTest
public class SuperhumanDaoDBTest 
{
    @Autowired
    SuperhumanDao superhumanDao;
    
    @Autowired
    LocationDao locationDao;
    
    @Autowired
    OrganizationDao organizationDao;
    
    @Autowired
    SightingDao sightingDao;
 
    
    @BeforeEach
    public void setUp() 
    {      
        
        List<Sighting> sightings = sightingDao.getAllSightings();
        
        for(Sighting item : sightings)
        {
            sightingDao.deleteSightingById(item.getId());
        }

        List<Superhuman> superhumans = superhumanDao.getAllSuperhumans();
        
        for(Superhuman item : superhumans )
        {
            superhumanDao.deleteSuperhumanById(item.getId());
        }
        
        List<Organization> organizations = organizationDao.getAllOrganizations();
        for(Organization item: organizations)
        {
            organizationDao.deleteOrganizationById(item.getId());
        }
        
           List<Location> locations = locationDao.getAllLocations();
        
        for(Location loc : locations)
        {
            locationDao.deleteLocationById(loc.getId());
        }
  
    }
    
    @AfterEach
    public void tearDown()
    { 
        List<Location> locations = locationDao.getAllLocations();
          for(Location loc : locations)
        {
            locationDao.deleteLocationById(loc.getId());
        }
        
        
        List<Sighting> sightings = sightingDao.getAllSightings();
        
        for(Sighting item : sightings)
        {
            sightingDao.deleteSightingById(item.getId());
        }

        List<Superhuman> superhumans = superhumanDao.getAllSuperhumans();
        
        for(Superhuman item : superhumans )
        {
            superhumanDao.deleteSuperhumanById(item.getId());
        }
        
        List<Organization> organizations = organizationDao.getAllOrganizations();
        for(Organization item: organizations)
        {
            organizationDao.deleteOrganizationById(item.getId());
        }
        
          
        
      
    }
    
    
    public SuperhumanDaoDBTest() {
    }

    @Test
    public void testSomeMethod() 
    {
        Superhuman flash = new Superhuman();
        flash.setName("Flash");
        flash.setDescription("Fastest man alive");
        flash.setIsHero(true);
        flash.setSuperpower("Super Speed");
        assertTrue(superhumanDao.addSuperhuman(flash).getName().equals("Flash"));
    }
    
    @Test
    public void testGetSuperhumanById()
    {
        Superhuman flash = new Superhuman();
        flash.setName("Flash");
        flash.setDescription("Fastest man alive");
        flash.setIsHero(true);
        flash.setSuperpower("Super Speed");
        assertTrue(superhumanDao.addSuperhuman(flash).getName().equals("Flash"));
    }
    
    @Test
    public void testGetAllSuperhumans()
    {
        
        Superhuman batman = new Superhuman();
        batman.setName("Batman");
        batman.setDescription("Bruce Wayne");
        batman.setSuperpower("Beats up poor people");
        batman.setIsHero(true);
        superhumanDao.addSuperhuman(batman);
        
        
        Superhuman flash = new Superhuman();
        flash.setName("Flash");
        flash.setDescription("Fastest man alive");
        flash.setIsHero(true);
        flash.setSuperpower("Super Speed");
        superhumanDao.addSuperhuman(flash);
        
        assertTrue(superhumanDao.getAllSuperhumans().size()==2);
    }
    
    @Test
    public void testDeleteSuperhumans()
    {
        Superhuman flash = new Superhuman();
        flash.setName("Flash");
        flash.setDescription("Fastest man alive");
        flash.setIsHero(true);
        flash.setSuperpower("Super Speed");
        superhumanDao.addSuperhuman(flash);
        
        int listLength =0;
        List<Superhuman> supers = superhumanDao.getAllSuperhumans();
        listLength=supers.size();
        superhumanDao.deleteSuperhumanById(supers.get(0).getId());
        assertTrue(superhumanDao.getAllSuperhumans().size() < listLength);
    }
    
    @Test
    public void testUpdateSuperhumans()
    {
        Superhuman flash = new Superhuman();
        flash.setName("Flash");
        flash.setDescription("Fastest man alive");
        flash.setIsHero(true);
        flash.setSuperpower("Super Speed");
        flash = superhumanDao.addSuperhuman(flash);
        
        
        Superhuman Robin = new Superhuman();
        Robin.setName("Robin");
        Robin.setDescription("The boy wonder");
        Robin.setSuperpower("Batmans kid");
        Robin.setIsHero(true);
        Robin.setId(flash.getId());
        
        superhumanDao.updateSuperhuman(Robin);
        
        assertTrue(superhumanDao.getSuperhumanById(flash.getId()).getName().equals("Robin"));
    }
}
