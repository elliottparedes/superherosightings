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
public class OrganizationDaoDBTest {
    
    @Autowired
    SightingDao sightingDao;
    
    @Autowired
    LocationDao locationDao;
    
    @Autowired
    SuperhumanDao superhumanDao;
    
    @Autowired
    OrganizationDao organizationDao;
    
    
    public OrganizationDaoDBTest() 
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
    public void testAddAndGetOrganizationById() 
    {
        Location Dallas  = new Location();
        Dallas.setName("Richardson");
        Dallas.setDescription("location of UTD");
        Dallas.setAddress("5975 Richardson Way");
        Dallas.setCity("Richardson");
        Dallas.setLatitude(new BigDecimal("76.54"));
        Dallas.setLongitude(new BigDecimal("48.87"));
        
        
        Dallas = locationDao.addLocation(Dallas);
        
        Organization org = new Organization();
        org.setName("Evil");
        org.setDescription("Every Villain Is Lemons");
        org.setLocation(Dallas);
        
        org = organizationDao.addOrganization(org);
        
        
        
        
        
        Organization organization = organizationDao.getOrganizationById(1);
        assertTrue(organizationDao.getOrganizationById(org.getId()).getName().equals("Evil"));
        
        
    }
    
    @Test
    public void testGetAllOrganizations()
    {
        Location Dallas  = new Location();
        Dallas.setName("Richardson");
        Dallas.setDescription("location of UTD");
        Dallas.setAddress("5975 Richardson Way");
        Dallas.setCity("Richardson");
        Dallas.setLatitude(new BigDecimal("76.54"));
        Dallas.setLongitude(new BigDecimal("48.87"));
        
        
        Dallas = locationDao.addLocation(Dallas);
        
        Organization org = new Organization();
        org.setName("Evil");
        org.setDescription("Every Villain Is Lemons");
        org.setLocation(Dallas);
        
        org = organizationDao.addOrganization(org);
        
        
        
        
       List<Organization> organizations = organizationDao.getAllOrganizations();
       assertTrue(organizations.size() > 0);
    
    }
    

    
    @Test
    public void testAddSuperhumanToOrganizationAndGetMembersOfOrganization()
    {
        Location Dallas  = new Location();
        Dallas.setName("Richardson");
        Dallas.setDescription("location of UTD");
        Dallas.setAddress("5975 Richardson Way");
        Dallas.setCity("Richardson");
        Dallas.setLatitude(new BigDecimal("76.54"));
        Dallas.setLongitude(new BigDecimal("48.87"));
        
        
        Dallas = locationDao.addLocation(Dallas);
        
        Organization org = new Organization();
        org.setName("Evil");
        org.setDescription("Every Villain Is Lemons");
        org.setLocation(Dallas);
        
        org = organizationDao.addOrganization(org);
        
        
        Superhuman manray = new Superhuman();
        manray.setName("Manray");
        manray.setSuperpower("Powers of the deep");
        manray.setDescription("Born in Bikini Bottom");
        manray.setIsHero(false);
        
        manray = superhumanDao.addSuperhuman(manray);
        
       
        organizationDao.addSuperhumanToOrganization(manray, org);
        
        List<Superhuman> list = organizationDao.getSuperhumansByOrganization(org);
        
        assertTrue(list.size()>0);
        
        
               
    }
    
    @Test
    public void testUpdateOrganization()
    {
        Location location = new Location();
        location.setName("Spring Field Monument");
        location.setDescription("A peaceful little place");
        location.setAddress("1435 Spring Ln");
        location.setCity("Spring Field");
        location.setLongitude(new BigDecimal("45.56"));
        location.setLatitude(new BigDecimal("88.56"));
        
        location = locationDao.addLocation(location);
        
        Organization org = new Organization();
        org.setName("Evil");
        org.setDescription("Every Villain Is Lemons");
        org.setLocation(location);
        
        org = organizationDao.addOrganization(org);
        
        org.setName("GOOD");
        Organization newOrg = org;
        
        organizationDao.updateOrganization(org);
        
        assertTrue(organizationDao.getOrganizationById(org.getId()).getName().equals("GOOD"));
    }
    

    

        @Test
        public void testGetOrganizationBySuperhuman()
        {
            Location Dallas  = new Location();
            Dallas.setName("Richardson");
            Dallas.setDescription("location of UTD");
            Dallas.setAddress("5975 Richardson Way");
            Dallas.setCity("Richardson");
            Dallas.setLatitude(new BigDecimal("76.54"));
            Dallas.setLongitude(new BigDecimal("48.87"));


            Dallas = locationDao.addLocation(Dallas);

            Organization org = new Organization();
            org.setName("Evil");
            org.setDescription("Every Villain Is Lemons");
            org.setLocation(Dallas);
            org = organizationDao.addOrganization(org);
            
            Organization org2 = new Organization();
            org2.setName("League of villains");
            org2.setDescription("Villains and more villains");
            org2.setLocation(Dallas);

            org2 = organizationDao.addOrganization(org2);


            Superhuman manray = new Superhuman();
            manray.setName("Manray");
            manray.setSuperpower("Powers of the deep");
            manray.setDescription("Born in Bikini Bottom");
            manray.setIsHero(false);
            manray = superhumanDao.addSuperhuman(manray);
            
            organizationDao.addSuperhumanToOrganization(manray, org);
            organizationDao.addSuperhumanToOrganization(manray, org2);
            
            List<Organization> orgs = organizationDao.getOrganizationsBySuperhuman(manray);
            
            assertTrue(orgs.size()>0);

        }
                
    
}
