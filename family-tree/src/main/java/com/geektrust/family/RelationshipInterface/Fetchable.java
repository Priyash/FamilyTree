package com.geektrust.family.RelationshipInterface;

import java.util.LinkedList;

import com.geektrust.family.FamilyMemberInterface.IFamilyMember;

/**
 * fetchable
 */
public interface Fetchable {

    LinkedList<IFamilyMember> fetchPersonInRelation(IFamilyMember familyMember, LinkedList<IFamilyMember> familyTree);
    
}