/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.elliottparedes.superherosightings.dao;

import com.elliottparedes.superherosightings.dao.SuperhumanDaoDB.SuperhumanMapper;
import com.elliottparedes.superherosightings.entities.Location;
import com.elliottparedes.superherosightings.entities.Organization;
import com.elliottparedes.superherosightings.entities.Superhuman;
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
public class OrganizationDaoDB implements OrganizationDao 
{
    @Autowired 
    JdbcTemplate jdbc;

    @Override
    public Organization getOrganizationById(int id) 
    {
       try
       {
         final String SELECT_ORGANIZATION_BY_ID = "SELECT * from organization inner join location on organization.locationID = location.locationID where organizationID  = ?;";
         Organization organization = jdbc.queryForObject(SELECT_ORGANIZATION_BY_ID, new OrganizationMapper(), id);
         return organization;
       }
       catch(DataAccessException ex)
        {
            return null;
        }
    }

    @Override
    public List<Organization> getAllOrganizations() 
    {
       final String SELECT_ALL_ORGANIZATIONS = "SELECT * from organization inner join location on organization.locationID = location.locationID;";
       List<Organization> organizations = jdbc.query(SELECT_ALL_ORGANIZATIONS, new OrganizationMapper());
       return organizations;
    }

    @Override
    public Organization addOrganization(Organization organization) 
    {
        
       final String INSERT_ORGANIZATION = "INSERT INTO organization (organizationName, organizationDescription, locationID) values (?, ?, ?);";
       jdbc.update(INSERT_ORGANIZATION, organization.getName(), organization.getDescription(), organization.getLocation().getId());
       int newID = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
       organization.setId(newID);
       return organization;
    }
    
    @Override
    public void addSuperhumanToOrganization( Superhuman superhuman, Organization organization)
    {
        final String ADD_SUPERHUMAN_TO_ORGANIZATION = "INSERT INTO superhuman_organization(superhumanID, organizationID) values (?,?);";
        jdbc.update(ADD_SUPERHUMAN_TO_ORGANIZATION, superhuman.getId(),organization.getId());
        
    }
    
    @Override
    public void removeSuperhumanFromOrganization(Superhuman superhuman, Organization organization)
    {
        final String REMOVE_SUPERHUMAN_FROM_ORGANIZATION = "DELETE FROM superhuman_organization WHERE superhumanID=? AND organizationID=?;";
        jdbc.update(REMOVE_SUPERHUMAN_FROM_ORGANIZATION,superhuman.getId(),organization.getId());
        
    }

    @Override
    public void updateOrganization(Organization organization) {
        final String UPDATE_ORGANIZATION = "UPDATE organization SET organizationName = ?,organizationDescription = ?, locationID =? WHERE organizationID = ?;";
        jdbc.update(UPDATE_ORGANIZATION,organization.getName(), organization.getDescription(), organization.getLocation().getId(), organization.getId());
        
    }

    @Override
    @Transactional
    public void deleteOrganizationById(int id) 
    {
        final String DELETE_FROM_SUPERHUMAN_ORGANIZATION = "DELETE FROM superhuman_organization WHERE organizationID = ?;";
        jdbc.update(DELETE_FROM_SUPERHUMAN_ORGANIZATION,id);
        
        final String DELETE_ORGANIZATION = "DELETE FROM organization WHERE organizationID = ?;";
        jdbc.update(DELETE_ORGANIZATION,id);
        
        
    }

    @Override
    public List<Superhuman> getSuperhumansByOrganization(Organization organization) 
    {
        final String SELECT_SUPERHUMANS_BY_ORGANIZATION = "SELECT * from superhuman inner join superhuman_organization on superhuman.superhumanID = superhuman_organization.superhumanID inner join organization on superhuman_organization.organizationID = organization.organizationID inner join location on organization.locationID = location.locationID WHERE organizationName = ?;";
        List<Superhuman> superhumans = jdbc.query(SELECT_SUPERHUMANS_BY_ORGANIZATION, new SuperhumanMapper(), organization.getName());
        return superhumans;
    }
    
    @Override
    public List<Organization> getOrganizationsBySuperhuman(Superhuman superhuman)
    {
        final String SELECT_ORGANIZATION_BY_SUPERHUMAN = "SELECT * from organization inner join superhuman_organization on organization.organizationID = superhuman_organization.organizationID inner join superhuman on superhuman_organization.superhumanID = superhuman.superhumanID inner join location on organization.locationID = location.locationID WHERE superhumanName = ?;";
        List<Organization> organizations = jdbc.query(SELECT_ORGANIZATION_BY_SUPERHUMAN, new OrganizationMapper(), superhuman.getName());
        
        return organizations;
    }
    
    public static final class OrganizationMapper implements RowMapper<Organization>
    {
        @Override
        public Organization mapRow(ResultSet rs, int index) throws SQLException
        {
           Location location = new Location();
           location.setId(rs.getInt("locationID"));
           location.setName(rs.getString("locationName"));
           location.setDescription(rs.getString("locationDescription"));
           location.setAddress(rs.getString("address"));
           location.setCity(rs.getString("city"));
           location.setLongitude(new BigDecimal(rs.getString("longitude")));
           location.setLatitude(new BigDecimal(rs.getString("latitude")));
            
            Organization organization = new Organization();
            organization.setId(rs.getInt("organizationID"));
            organization.setName(rs.getString("organizationName"));
            organization.setDescription(rs.getString("organizationDescription"));
            organization.setLocation(location);
            
            
            return organization;
        }

    }
    
    
    
}
