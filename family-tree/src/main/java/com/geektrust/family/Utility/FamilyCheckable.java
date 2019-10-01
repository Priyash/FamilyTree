
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
    LinkedList<IFamilyMember> getSiblings(IFamilyMember siblings);

    LinkedList<IFamilyMember> processFirstGenChildren(IFamilyMember parent, IFamilyMember member,IFamilyMember familyMemberToFind);
    LinkedList<IFamilyMember> processSecondGenChildren(IFamilyMember grandParent, IFamilyMember father,IFamilyMember mother, IFamilyMember familyMemberToFind, Boolean IS_MOM_OR_DAD_SIDE);


    LinkedList<IFamilyMember> processSibling(IFamilyMember grandParent, IFamilyMember father, IRelationship relation);
    
} 