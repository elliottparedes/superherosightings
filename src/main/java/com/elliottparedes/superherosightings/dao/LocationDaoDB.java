/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.elliottparedes.superherosightings.dao;

import com.elliottparedes.superherosightings.dao.OrganizationDaoDB.OrganizationMapper;
import com.elliottparedes.superherosightings.entities.Location;
import com.elliottparedes.superherosightings.entities.Organization;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Ellio
 */
@Repository
public class LocationDaoDB implements LocationDao 
{
    @Autowired
    JdbcTemplate jdbc;

    @Override
    public Location getLocationById(int id) 
    {
        try
        {
            final String SELECT_SUPERHUMAN_BY_ID = "SELECT * FROM location WHERE locationID = ?";
            Location location = jdbc.queryForObject(SELECT_SUPERHUMAN_BY_ID, new LocationMapper(), id);
            return location;
            
        } catch(DataAccessException ex)
        {
            return null;
        }
        
    }

    @Override
    public List<Location> getAllLocations() 
    {
        final String SELECT_ALL_LOCATIONS = "SELECT * FROM location;";
        List<Location> locations = jdbc.query(SELECT_ALL_LOCATIONS, new LocationMapper());
        return locations;
        
    }

    @Override
    public Location addLocation(Location location) 
    {
        final String INSERT_LOCATION = "INSERT INTO location(locationName,locationDescription,address,city,latitude,longitude) values (?,?,?,?,?,?);";
        jdbc.update(INSERT_LOCATION,location.getName(),location.getDescription() ,location.getAddress(), location.getCity(), location.getLatitude(), location.getLongitude());
        int newID = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        location.setId(newID);
        return location;
    
    }   
    
    @Override
    public void updateLocation(Location location) 
    {
        final String UPDATE_LOCATION = "UPDATE location SET locationName = ?, locationDescription = ?, address = ?, city = ?, latitude = ?, longitude = ? WHERE locationID = ?";
        
        jdbc.update(UPDATE_LOCATION,
                    location.getName(),
                    location.getDescription(),
                    location.getAddress(),
                    location.getCity(),
                    location.getLatitude(),
                    location.getLongitude(),
                    location.getId());
       
    }

    @Override
    @Transactional
    public void deleteLocationById(int id) 
    {        
        
        final String DELETE_SIGHTING = "DELETE FROM sighting WHERE locationID = ?;";
        jdbc.update(DELETE_SIGHTING, id);
        
        
        final String GET_ORGANIZATIONS = "SELECT * FROM organization INNER JOIN location ON location.locationID = organization.locationID WHERE organization.locationID = ?;";
        List<Organization> organizations = jdbc.query(GET_ORGANIZATIONS, new OrganizationMapper(),id);
        
       
        
       
        
        final String DELETE_FROM_SUPERHUMAN_ORGANIZATION = "DELETE FROM superhuman_organization WHERE organizationID = ?;";
        
        final String DELETE_ORGANIZATION = "DELETE FROM organization WHERE locationID = ?;";
        
        for(Organization o : organizations)
        { 
          
          
            jdbc.update(DELETE_FROM_SUPERHUMAN_ORGANIZATION,o.getId()); 
            jdbc.update(DELETE_ORGANIZATION,id);
          
        }
  
        final String DELETE_LOCATION = "DELETE FROM location WHERE locationID = ?;";
        jdbc.update(DELETE_LOCATION,id);
        
        
    }
    
    
    public static final class LocationMapper implements RowMapper<Location>
    {
        @Override
        public Location mapRow(ResultSet rs, int index) throws SQLException 
        {
           Location location = new Location();
           location.setId(rs.getInt("locationID"));
           location.setName(rs.getString("locationName"));
           location.setDescription(rs.getString("locationDescription"));
           location.setAddress(rs.getString("address"));
           location.setCity(rs.getString("city"));
           location.setLongitude(new BigDecimal(rs.getString("longitude")));
           location.setLatitude(new BigDecimal(rs.getString("latitude")));
            
            
           return location;
        }
    }
}
