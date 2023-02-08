/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.elliottparedes.superherosightings.dao;

import com.elliottparedes.superherosightings.entities.Location;
import com.elliottparedes.superherosightings.entities.Organization;
import com.elliottparedes.superherosightings.entities.Sighting;
import com.elliottparedes.superherosightings.entities.Superhuman;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 *
 * @author Ellio
 */
@SpringBootTest
public class SightingDaoDBTest {
    
    private static final Logger LOG = LoggerFactory.getLogger(SightingDaoDBTest.class);
    
    
    @Autowired
    SuperhumanDao superhumanDao;
    
    @Autowired
    LocationDao locationDao;
    
    @Autowired
    OrganizationDao organizationDao;
    
    @Autowired
    SightingDao sightingDao;
    
    public SightingDaoDBTest() 
    {
         
    }
    
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

    @Test
    public void testGetSightingById() 
    {
        Location Dallas  = new Location();
        Dallas.setName("Fort Worth");
        Dallas.setDescription("Fort in Texas");
        Dallas.setAddress("0124 Fort Wroth Ave");
        Dallas.setCity("Fort=Worth");
        Dallas.setLatitude(new BigDecimal("66.54"));
        Dallas.setLongitude(new BigDecimal("28.87"));
        
        
        Location newDallas = locationDao.addLocation(Dallas);
        
        Sighting sighting = new Sighting();
        sighting.setLocation(Dallas);
        sighting.setDate(LocalDateTime.now());
        
        
        
        Superhuman green = new Superhuman();
        green.setName("Green Lantern");
        green.setDescription("Magical Ring Bearer");
        green.setSuperpower("Imagination becomes reality");
        green.setIsHero(true);
        
        
        
        Superhuman newgreen = superhumanDao.addSuperhuman(green);
        
        sighting.setSuperHuman(newgreen);
        
        int id = sightingDao.addSighting(sighting).getId();

        assertTrue(sightingDao.getSightingById(id).getId() == id);
        
    }
    
    @Test
    public void testGetAllSightingsANDaddSighting()
    {
        Location Dallas  = new Location();
        Dallas.setName("Fort Worth");
        Dallas.setDescription("Fort in Texas");
        Dallas.setAddress("0124 Fort Wroth Ave");
        Dallas.setCity("Fort=Worth");
        Dallas.setLatitude(new BigDecimal("66.54"));
        Dallas.setLongitude(new BigDecimal("28.87"));
        
        
        Location newDallas = locationDao.addLocation(Dallas);
        
        Sighting sighting = new Sighting();
        sighting.setLocation(Dallas);
        sighting.setDate(LocalDateTime.now());
        
        
        
        Superhuman green = new Superhuman();
        green.setName("Green Lantern");
        green.setDescription("Magical Ring Bearer");
        green.setSuperpower("Imagination becomes reality");
        green.setIsHero(true);
        
        Superhuman newgreen = superhumanDao.addSuperhuman(green);
        
        sighting.setSuperHuman(newgreen);
        
        sightingDao.addSighting(sighting);
        
        
        List<Sighting> sightings = sightingDao.getAllSightings();
        
        assertTrue(sightings.size()>0);
        
       
    }

    
    @Test
    public void testUpdateSighting()
    {
        Location Dallas  = new Location();
        Dallas.setName("Richardson");
        Dallas.setDescription("location of UTD");
        Dallas.setAddress("5975 Richardson Way");
        Dallas.setCity("Richardson");
        Dallas.setLatitude(new BigDecimal("76.54"));
        Dallas.setLongitude(new BigDecimal("48.87"));
        
        
        Location newDallas = locationDao.addLocation(Dallas);
        
        Sighting sighting = new Sighting();
       
        sighting.setLocation(newDallas);
        sighting.setDate(LocalDateTime.now());
        
        Superhuman green = new Superhuman();
        green.setName("Green Lantern");
        green.setDescription("Magical Ring Bearer");
        green.setSuperpower("Imagination becomes reality");
        green.setIsHero(true);
        
        
        
        Superhuman newgreen = superhumanDao.addSuperhuman(green);
        
        sighting.setSuperHuman(newgreen);
        
        sighting = sightingDao.addSighting(sighting);
        
        
        Superhuman Red = new Superhuman();
        Red.setName("Red Lantern");
        Red.setDescription("Magical Ring Bearer");
        Red.setSuperpower("Imagination becomes reality");
        Red.setIsHero(false);
        
        Red = superhumanDao.addSuperhuman(Red);
        
        sighting.setSuperHuman(Red);
        int id = sighting.getId();
        sightingDao.updateSighting(sighting);
        
        assertTrue(sightingDao.getSightingById(id).getSuperHuman().getName().equals("Red Lantern"));
    }
    
    @Test 
    public void testDeleteSightingByID()
    {
        Location Dallas  = new Location();
        Dallas.setName("Richardson");
        Dallas.setDescription("location of UTD");
        Dallas.setAddress("5975 Richardson Way");
        Dallas.setCity("Richardson");
        Dallas.setLatitude(new BigDecimal("76.54"));
        Dallas.setLongitude(new BigDecimal("48.87"));
        
        
        Location newDallas = locationDao.addLocation(Dallas);
        
        Sighting sighting = new Sighting();
       
        sighting.setLocation(newDallas);
        sighting.setDate(LocalDateTime.now());
        
        Superhuman green = new Superhuman();
        green.setName("Green Lantern");
        green.setDescription("Magical Ring Bearer");
        green.setSuperpower("Imagination becomes reality");
        green.setIsHero(true);
        
        
        
        Superhuman newgreen = superhumanDao.addSuperhuman(green);
        
        sighting.setSuperHuman(newgreen);
        
        sighting = sightingDao.addSighting(sighting);
        
        sightingDao.deleteSightingById(sighting.getId());
        
        assertTrue(sightingDao.getAllSightings().size()==0);
    }
    
    
    @Test
    public void testGetSightingsForSuperhuman()
    {
        Superhuman jeff = new Superhuman();
        jeff.setName("Superman");
        
        
        LOG.info(sightingDao.getSightingsForSuperhuman(jeff).toString());
    }
    
    
    @Test
    public void testGetSightingsByDate() 
    { 
        
        Location Dallas  = new Location();
        Dallas.setName("Richardson");
        Dallas.setDescription("location of UTD");
        Dallas.setAddress("5975 Richardson Way");
        Dallas.setCity("Richardson");
        Dallas.setLatitude(new BigDecimal("76.54"));
        Dallas.setLongitude(new BigDecimal("48.87"));
        
        
        Location newDallas = locationDao.addLocation(Dallas);
        
        Sighting sighting = new Sighting();
       
        sighting.setLocation(newDallas);
        sighting.setDate(LocalDateTime.now());
        
        Superhuman green = new Superhuman();
        green.setName("Green Lantern");
        green.setDescription("Magical Ring Bearer");
        green.setSuperpower("Imagination becomes reality");
        green.setIsHero(true);
        
        Superhuman newgreen = superhumanDao.addSuperhuman(green);
        
        sighting.setSuperHuman(newgreen);
        
        sighting = sightingDao.addSighting(sighting);
 
        
        LocalDate date = sighting.getDate().toLocalDate();
        
        
 
        assertTrue(sightingDao.getSightingsByDate(date).size()>0);
    }
    
    @Test
    public void testGetSightingsByLocation()
    {
        Location Dallas  = new Location();
        Dallas.setName("Richardson");
        Dallas.setDescription("location of UTD");
        Dallas.setAddress("5975 Richardson Way");
        Dallas.setCity("Richardson");
        Dallas.setLatitude(new BigDecimal("76.54"));
        Dallas.setLongitude(new BigDecimal("48.87"));
        
        
        Location newDallas = locationDao.addLocation(Dallas);
        
        Sighting sighting = new Sighting();
       
        sighting.setLocation(newDallas);
        sighting.setDate(LocalDateTime.now());
        
        Superhuman green = new Superhuman();
        green.setName("Green Lantern");
        green.setDescription("Magical Ring Bearer");
        green.setSuperpower("Imagination becomes reality");
        green.setIsHero(true);
        
        Superhuman newgreen = superhumanDao.addSuperhuman(green);
        
        sighting.setSuperHuman(newgreen);
        
        sighting = sightingDao.addSighting(sighting);
        
        Location location = sighting.getLocation();
        
        assertTrue(sightingDao.getSightingsByLocation(location).size()>0);
    }
}
