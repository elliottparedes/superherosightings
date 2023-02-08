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
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 *
 * @author Ellio
 */
@SpringBootTest
public class LocationDaoDBTest {
    
    @Autowired
    LocationDao locationDao;
    
    public LocationDaoDBTest() {
    }
    
    @BeforeEach
    public void setUp() 
    {      
        
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
    }
    
    
    @Test
    public void testgetLocationById() 
    {
        Location Dallas  = new Location();
        Dallas.setName("Richardson");
        Dallas.setDescription("location of UTD");
        Dallas.setAddress("5975 Richardson Way");
        Dallas.setCity("Richardson");
        Dallas.setLatitude(new BigDecimal("76.54"));
        Dallas.setLongitude(new BigDecimal("48.87"));
        
        
        Dallas = locationDao.addLocation(Dallas);
        
        assertTrue(locationDao.getLocationById(Dallas.getId()).getName().equals("Richardson"));
    }
    
       
    @Test
    public void testAddAndUpdateLocation()
    {
        
        Location Dallas  = new Location();
        Dallas.setName("Richardson");
        Dallas.setDescription("location of UTD");
        Dallas.setAddress("5975 Richardson Way");
        Dallas.setCity("Richardson");
        Dallas.setLatitude(new BigDecimal("76.54"));
        Dallas.setLongitude(new BigDecimal("48.87"));
        
        
        Dallas = locationDao.addLocation(Dallas);
        
        Location location = new Location();
        location.setName("Moms Place");
        location.setDescription("House of Mom");
        location.setAddress("7035 Faridale");
        location.setCity("FortWorth");
        location.setLongitude(new BigDecimal("45.54"));
        location.setLatitude(new BigDecimal("35.45"));
        
        location.setId(Dallas.getId());
        
        locationDao.updateLocation(location);
        
        assertTrue(locationDao.getLocationById(Dallas.getId()).getName().equals("Moms Place"));
        
    }
    
    
    @Test
    public void testDeleteLocation()
    {
        
        Location Dallas  = new Location();
        Dallas.setName("Richardson");
        Dallas.setDescription("location of UTD");
        Dallas.setAddress("5975 Richardson Way");
        Dallas.setCity("Richardson");
        Dallas.setLatitude(new BigDecimal("76.54"));
        Dallas.setLongitude(new BigDecimal("48.87"));
        
        
        Dallas = locationDao.addLocation(Dallas);
        locationDao.deleteLocationById(Dallas.getId());
        
        assertTrue(locationDao.getAllLocations().size() == 0);
        
        
    }
    
}
