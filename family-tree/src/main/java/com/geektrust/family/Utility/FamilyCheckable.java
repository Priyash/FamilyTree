
package com.geektrust.family.Utility;

import java.util.LinkedList;

import com.geektrust.family.FamilyMemberInterface.IFamilyMember;
import com.geektrust.family.RelationshipInterface.IRelationship;

/**
 * FamilyCheckable
 */
public interface FamilyCheckable {

    IFamilyMember hasHusband(IFamilyMember member);
    Boolean hasHusband(IFamilyMember wife, IFamilyMember familyMemberToFind, Boolean FAMILY_MEMBER_FOUND);
    IFamilyMember hasWife(IFamilyMember member);
    
    LinkedList<IFamilyMember> getChildren(IFamilyMember familyPerson, IRelationship relation);
    
    LinkedList<IFamilyMember> getSistersForSisterInLaw(IFamilyMember familyMember, IFamilyMember member);
    LinkedList<IFamilyMember> getBrothersForBrotherInlaw(IFamilyMember sister);
    
    LinkedList<IFamilyMember> getSiblings(IFamilyMember siblings, IFamilyMember familyMemberToFind);

    public Boolean hasMarried(IFamilyMember member);
    
} 