/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.elliottparedes.superherosightings.dao;

import com.elliottparedes.superherosightings.entities.Superhuman;
import java.util.List;

/**
 *
 * @author Ellio
 */
public interface SuperhumanDao 
{
    Superhuman getSuperhumanById(int id);
    
    List<Superhuman> getAllSuperhumans();
    
    Superhuman addSuperhuman(Superhuman superhuman);
    
    void updateSuperhuman(Superhuman superhuman);
    
    void deleteSuperhumanById(int id);
    
    
    
}
