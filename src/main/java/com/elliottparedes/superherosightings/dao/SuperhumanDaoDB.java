/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.elliottparedes.superherosightings.dao;

import com.elliottparedes.superherosightings.entities.Superhuman;
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
public class SuperhumanDaoDB implements SuperhumanDao 
{
    @Autowired
    JdbcTemplate jdbc;

    @Override
    public Superhuman getSuperhumanById(int id) 
    {
        try
        {
            final String SELECT_SUPERHUMAN_BY_ID = "SELECT * FROM superhuman WHERE SuperhumanID = ?";
            Superhuman superhuman = jdbc.queryForObject(SELECT_SUPERHUMAN_BY_ID, new SuperhumanMapper(), id);
            return superhuman;
            
        } catch(DataAccessException ex)
        {
            return null;
        }
       
    }

    @Override
    public List<Superhuman> getAllSuperhumans() {
     
            final String SELECT_ALL_SUPERHUMANS = "SELECT * FROM superhuman;";
            List<Superhuman> superhumans = jdbc.query(SELECT_ALL_SUPERHUMANS, new SuperhumanMapper());
            return superhumans;

    }

    @Override
    public Superhuman addSuperhuman(Superhuman superhuman) 
    {
        final String INSERT_SUPERHUMAN = "INSERT INTO superhuman(superhumanName,superhumanDescription, superpower, isHero) " + "VALUES(?,?,?,?);";
        jdbc.update(INSERT_SUPERHUMAN,superhuman.getName(),superhuman.getDescription(),superhuman.getSuperpower(),superhuman.isIsHero());
        int newID = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        superhuman.setId(newID);
        
        return superhuman;
    }

    @Override
    public void updateSuperhuman(Superhuman superhuman) {
       final String UPDATE_SUPERHUMAN = "UPDATE superhuman SET superhumanName = ?, superhumanDescription = ?, superpower = ?, isHero=? WHERE superhumanID = ?";
       
       jdbc.update(UPDATE_SUPERHUMAN,
                   superhuman.getName(),
                   superhuman.getDescription(),
                   superhuman.getSuperpower(),
                   superhuman.isIsHero(),
                   superhuman.getId());
    }

    @Override
    @Transactional
    public void deleteSuperhumanById(int id) 
    {

        
        final String DELETE_SUPERHUMAN_ORGANIZATION = "DELETE FROM superhuman_organization WHERE superhumanID = ?";
        jdbc.update(DELETE_SUPERHUMAN_ORGANIZATION, id);
        
        final String DELETE_SUPERHUMAN = "DELETE FROM superhuman WHERE superhumanID = ?";
        jdbc.update(DELETE_SUPERHUMAN, id);
       
    }
    
    public static final class SuperhumanMapper implements RowMapper<Superhuman>
    {
        @Override
        public Superhuman mapRow(ResultSet rs, int index) throws SQLException 
        {
            Superhuman superhuman = new Superhuman();
            superhuman.setId(rs.getInt("superhumanID"));
            superhuman.setName(rs.getString("superhumanName"));
            superhuman.setDescription(rs.getString("superhumanDescription"));
            superhuman.setSuperpower(rs.getString("superPower"));
            superhuman.setIsHero(rs.getBoolean("isHero"));
            
            return superhuman;
        }
    }
    
}
