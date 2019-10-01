package com.geektrust.family.Utility;

import java.util.LinkedList;
import java.util.Map;

import com.geektrust.family.FamilyMemberInterface.IFamilyMember;
import com.geektrust.family.RelationshipInterface.IRelationship;

/**
 * FamilyCheckerUtils
 */
public class FamilyCheckerUtils implements FamilyCheckable {


    public FamilyCheckerUtils (){
        
    }

    
    @Override
    public IFamilyMember hasHusband(IFamilyMember member) {
        IFamilyMember husband = null;
        Map<IFamilyMember, IRelationship> children = member.getRelatioshipList();
        for (Map.Entry<IFamilyMember, IRelationship> entry : children.entrySet()) {
            if (entry.getValue().getRelationType().equals(Constants.HUSBAND)) {
                husband = entry.getKey();
            }
        }
        return husband;
    }

    @Override
    public Boolean hasHusband(IFamilyMember member, IFamilyMember familyMemberToFind, Boolean FAMILY_MEMBER_FOUND) {
        Boolean hasHusband = false;
        Map<IFamilyMember, IRelationship> children = member.getRelatioshipList();
        for (Map.Entry<IFamilyMember, IRelationship> entry : children.entrySet()) {
            if (entry.getValue().getRelationType().equals(Constants.HUSBAND)
                    && entry.getKey().getMemberName().equals(familyMemberToFind.getMemberName())
                    && FAMILY_MEMBER_FOUND == false) {
                hasHusband = true;
                break;
            }
        }
        return hasHusband;
    }

    @Override
    public IFamilyMember hasWife(IFamilyMember member) {
        IFamilyMember wife = null;
        Map<IFamilyMember, IRelationship> children = member.getRelatioshipList();
        for (Map.Entry<IFamilyMember, IRelationship> entry : children.entrySet()) {
            if (entry.getValue().getRelationType().equals(Constants.WIFE)) {
                wife = entry.getKey();
            }
        }
        return wife;
    }

    @Override
    public LinkedList<IFamilyMember> getChildren(IFamilyMember familyPerson, IRelationship relation) {
        LinkedList<IFamilyMember> children = new LinkedList<>();
        Map<IFamilyMember, IRelationship> fam_list = familyPerson.getRelatioshipList();
        for (Map.Entry<IFamilyMember, IRelationship> entry : fam_list.entrySet()) {
            if(relation.getRelationType().equals(Constants.SON) && entry.getValue().getRelationType().equals(Constants.SON)){
                children.add(entry.getKey());
            }
            else if( relation.getRelationType().equals(Constants.DAUGHTER) && entry.getValue().getRelationType().equals(Constants.DAUGHTER)) {
                children.add(entry.getKey());
            }
        }

        return children;
    }

    @Override
    public LinkedList<IFamilyMember> getSiblings(IFamilyMember siblings) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public LinkedList<IFamilyMember> processFirstGenChildren(IFamilyMember parent, IFamilyMember member,
            IFamilyMember familyMemberToFind) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public LinkedList<IFamilyMember> processSecondGenChildren(IFamilyMember grandParent, IFamilyMember father,
            IFamilyMember mother, IFamilyMember familyMemberToFind, Boolean IS_MOM_OR_DAD_SIDE) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public LinkedList<IFamilyMember> processSibling(IFamilyMember grandParent, IFamilyMember father,
            IRelationship relation) {
        // TODO Auto-generated method stub
        return null;
    }

    
}