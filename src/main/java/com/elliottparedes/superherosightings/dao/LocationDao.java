/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.elliottparedes.superherosightings.dao;

import com.elliottparedes.superherosightings.entities.Location;
import java.util.List;

/**
 *
 * @author Ellio
 */
public interface LocationDao 
{
    Location getLocationById(int id);
    
    List<Location> getAllLocations();
    
    Location addLocation(Location location);
    
    void updateLocation(Location location);
    
    void deleteLocationById(int id);
}
