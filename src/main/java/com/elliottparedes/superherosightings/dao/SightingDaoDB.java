/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.elliottparedes.superherosightings.dao;

import com.elliottparedes.superherosightings.entities.Location;
import com.elliottparedes.superherosightings.entities.Sighting;
import com.elliottparedes.superherosightings.entities.Superhuman;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
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
public class SightingDaoDB implements SightingDao 
{
    @Autowired
    JdbcTemplate jdbc;   
     

    @Override
    public Sighting getSightingById(int id) 
    {
       try 
       {
           final String SELECT_SIGHTING_BY_ID = "SELECT * FROM sighting inner join location on sighting.locationID = location.locationID inner join superhuman on sighting.superhumanID = superhuman.superhumanID where sightingID = ?";
           Sighting sighting = jdbc.queryForObject(SELECT_SIGHTING_BY_ID, new SightingMapper(),id);        
           return sighting;
       } catch(DataAccessException ex)
       {
           return null;
       }
    }

    @Override
    public List<Sighting> getAllSightings() 
    {
       final String SELECT_ALL_SIGHTINGS = "SELECT * FROM sighting inner join location on sighting.locationID = location.locationID inner join superhuman on sighting.superhumanID = superhuman.superhumanID;";
       List<Sighting> sightings = jdbc.query(SELECT_ALL_SIGHTINGS, new SightingMapper());
       return sightings;
    } 

    @Override
    @Transactional
    public Sighting addSighting(Sighting sighting) 
    {
     
        final String INSERT_SIGHTING = "INSERT INTO sighting(locationID, superhumanID, date) VALUES (?,?,?);";
        jdbc.update(INSERT_SIGHTING, sighting.getLocation().getId(), sighting.getSuperHuman().getId(),Timestamp.valueOf(sighting.getDate()));
        
        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        sighting.setId(newId);
        
        return sighting;
    }

    @Override
    public void updateSighting(Sighting sighting) 
    {
        final String UPDATE_SIGHTING = "UPDATE sighting set locationID = ?, superhumanID = ?, date = ? where sightingID = ?;";
        jdbc.update(UPDATE_SIGHTING,sighting.getLocation().getId(),sighting.getSuperHuman().getId() ,Timestamp.valueOf(sighting.getDate()), sighting.getId());
    }   

    @Override
    public void deleteSightingById(int id) 
    {
   
       
        
        final String DELETE_SIGHTING = "DELETE FROM sighting WHERE sightingID = ?;";
        jdbc.update(DELETE_SIGHTING, id);
        
        
        
    }

    @Override
    public List<Sighting> getSightingsForSuperhuman(Superhuman superhuman) 
    {
        final String SELECT_SIGHTINGS_FOR_SUPERHUMAN = 
                                                "select * " +
                                                "from sighting inner join location " +
                                                "on sighting.locationID = location.locationID " +
                                                "inner join superhuman " +
                                                "on sighting.superhumanID = superhuman.superhumanID " +
                                                "where superhuman.superhumanName = ?;";
       List<Sighting> sightings = jdbc.query(SELECT_SIGHTINGS_FOR_SUPERHUMAN, new SightingMapper(),superhuman.getName());
       return sightings;
    }

    @Override
    public List<Sighting> getSightingsByDate(LocalDate date) 
    {
       Timestamp t = Timestamp.valueOf(date.atStartOfDay());
        final String SELECT_SIGHTINGS_BY_DATE = "select * " +
                                                "from sighting " +
                                                "inner join superhuman " +
                                                "on sighting.superhumanID = superhuman.superhumanID " +
                                                "inner join location " +
                                                "on sighting.locationID = location.locationID " +
                                                "where Date(sighting.date) = ?;";
        List<Sighting> sightings = jdbc.query(SELECT_SIGHTINGS_BY_DATE, new SightingMapper() , date);
        return sightings;
    }

    @Override
    public List<Sighting> getSightingsByLocation(Location location) 
    {
        final String SELECT_SIGHTINGS_BY_LOCATION = "SELECT * FROM sighting inner join location on sighting.locationID = location.locationID inner join superhuman on sighting.superhumanID = superhuman.superhumanID where location.locationID = ?;";
        List<Sighting> sightings = jdbc.query(SELECT_SIGHTINGS_BY_LOCATION,new SightingMapper(), location.getId());
        return sightings; 
    }
    
   
    public static final class SightingMapper implements RowMapper<Sighting>
    {
       
       
        
        @Override
        public Sighting mapRow(ResultSet rs, int index) throws SQLException
        {
            
            Location location = new Location();
            Sighting sighting = new Sighting();
            Superhuman superhuman = new Superhuman();
            
            sighting.setId(rs.getInt("sightingID"));
            
            location.setId(rs.getInt("locationID"));
            location.setName(rs.getString("LocationName"));
            location.setDescription(rs.getString("locationDescription"));
            location.setAddress(rs.getString("address"));
            location.setCity(rs.getString("city"));
            location.setLatitude(rs.getBigDecimal("latitude"));
            location.setLongitude(rs.getBigDecimal("longitude"));
            
            sighting.setLocation(location);
            sighting.setDate(rs.getObject("date", Timestamp.class ).toLocalDateTime());
            
            
            
            superhuman.setId(rs.getInt("superhumanID"));
            superhuman.setName(rs.getString("superhumanName"));
            superhuman.setDescription(rs.getString("superhumanDescription"));
            superhuman.setSuperpower(rs.getString("superPower"));
            superhuman.setIsHero(rs.getBoolean("isHero"));
            
            
            sighting.setSuperHuman(superhuman);
            return sighting;
        }
    }
    
}
