/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.elliottparedes.superherosightings.dao;

import com.elliottparedes.superherosightings.entities.Organization;
import com.elliottparedes.superherosightings.entities.Superhuman;
import java.util.List;

/**
 *
 * @author Ellio
 */
public interface OrganizationDao 
{
    Organization getOrganizationById(int id);
    
    List<Organization> getAllOrganizations();
    
    Organization addOrganization( Organization organization);
    
    void updateOrganization (Organization organization);
    
    void deleteOrganizationById(int id);
    
    void addSuperhumanToOrganization( Superhuman superhuman, Organization organization);
    
    void removeSuperhumanFromOrganization(Superhuman superhuman, Organization organization);
    
    List<Superhuman> getSuperhumansByOrganization(Organization organization);
    
    List<Organization> getOrganizationsBySuperhuman(Superhuman superhuman);
    
    
    
}
