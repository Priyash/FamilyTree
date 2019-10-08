package com.geektrust.family;

import com.geektrust.family.RelationshipInterface.IRelationship;
import com.geektrust.family.Utility.Constants;

/**
 * Relationship
 */
public class Relationship implements IRelationship {

    private String relationType;
    private String gender;

    public Relationship(String relationType, String gender){
        this.relationType = relationType;
        this.gender = gender;
    }

    @Override
    public String getRelationType() {
        return relationType;
    }

    @Override
    public String getGender() {
        return gender;
    }

    
}