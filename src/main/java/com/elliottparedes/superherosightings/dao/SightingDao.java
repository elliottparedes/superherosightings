/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.elliottparedes.superherosightings.dao;

import com.elliottparedes.superherosightings.entities.Location;
import com.elliottparedes.superherosightings.entities.Sighting;
import com.elliottparedes.superherosightings.entities.Superhuman;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author Ellio
 */
public interface SightingDao 
{
    Sighting getSightingById(int id);
    
    List<Sighting> getAllSightings();
    
    Sighting addSighting(Sighting sighting);
    
    void updateSighting(Sighting sighting);
    
    void deleteSightingById(int id);
    
    List<Sighting> getSightingsForSuperhuman(Superhuman superhuman);
    
    List<Sighting> getSightingsByDate(LocalDate date);
    
    List<Sighting> getSightingsByLocation(Location location);
    
}
