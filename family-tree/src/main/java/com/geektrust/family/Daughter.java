package com.geektrust.family;

import com.geektrust.family.RelationshipInterface.IRelationship;
import com.geektrust.family.Utility.Constants;
import com.geektrust.family.GenderInterface.IGender;

/**
 * Husband
 */
public class Daughter implements IRelationship {

    @Override
    public String getRelationType() {
       
        return Constants.DAUGHTER;
    }

    @Override
    public IGender getGender() {
        
        return new FemaleGenderImpl();
    }

    
}