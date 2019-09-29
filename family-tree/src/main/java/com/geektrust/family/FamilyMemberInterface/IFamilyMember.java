package com.geektrust.family.FamilyMemberInterface;

import java.util.LinkedList;
import java.util.Map;

import com.geektrust.family.RelationshipInterface.*;

/**
 * IPerson
 */
public interface  IFamilyMember{

    void setMemberName(String name);
    String getMemberName();
    void addRelationship(IFamilyMember familyMember, IRelationship relation);
    Map<IFamilyMember, IRelationship> getRelatioshipList();
}