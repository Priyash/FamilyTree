package com.geektrust.family;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

import com.geektrust.family.FamilyMemberInterface.IFamilyMember;
import com.geektrust.family.RelationshipInterface.IRelationship;

/**
 * Person
 */
public class FamilyMember implements IFamilyMember{

    Map<IFamilyMember, IRelationship> relationShipList;
    String memberName;
    FamilyMember()
    {
        memberName = "";
        relationShipList = new LinkedHashMap<>();
    }

    @Override
    public void addRelationship(IFamilyMember familyMember, IRelationship relation) {
        relationShipList.put(familyMember, relation);
    }

    @Override
    public Map<IFamilyMember, IRelationship> getRelatioshipList() {
        return relationShipList;
    }

    @Override
    public void setMemberName(String name) {
        if(!name.isEmpty()){
            this.memberName = name;
        }
        else{
            throw new RuntimeException("Member name should not be empty");
        }
        
    }

    @Override
    public String getMemberName() {
        return this.memberName;
    }

}