package com.geektrust.family.Utility;

import java.util.LinkedList;
import java.util.Map;

import com.geektrust.family.FamilyMemberInterface.IFamilyMember;
import com.geektrust.family.RelationshipInterface.IRelationship;

/**
 * FamilyCheckerUtils
 */
public class FamilyCheckerUtils implements FamilyCheckable {
    LinkedList<IFamilyMember> siblings = new LinkedList<>();
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

    /**
     * This API used to check whether siblings has husband or not .
     * 
     * @param member
     * @param familyMemberToFind
     * @param FAMILY_MEMBER_FOUND
     * @return This return a boolean value which determines siblings has husband or
     *         not
     * 
     */
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

    /**
     * This API used to check whether the family member has a wife or not .
     * 
     * @param member
     * @return variable of type <code>IFamilyMember</code> pointing to the wife
     *         object if family member has a wife.
     */
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
    public LinkedList<IFamilyMember> getSiblings(IFamilyMember root, IFamilyMember familyMemberToFind) {
        
        Map<IFamilyMember, IRelationship> children = root.getRelatioshipList();
        
        Boolean found_member = false;
        for (Map.Entry<IFamilyMember, IRelationship> entry : children.entrySet()) {
            if (entry.getKey().getMemberName().equals(familyMemberToFind.getMemberName())) {
                found_member = true;
                break;
            }
        }

        if (found_member) {
            for (Map.Entry<IFamilyMember, IRelationship> entry : children.entrySet()) {
                    if ((entry.getValue().getRelationType().equals(Constants.SON)
                        || entry.getValue().getRelationType().equals(Constants.DAUGHTER))
                        && !entry.getKey().getMemberName().equals(familyMemberToFind.getMemberName())) {
                    siblings.add(entry.getKey());
                }
            }
        }

        return siblings;
    }
   
    @Override
    public LinkedList<IFamilyMember> getSistersForSisterInLaw(IFamilyMember familyMember, IFamilyMember member) {
        LinkedList<IFamilyMember> sisters = new LinkedList<>();
        for (Map.Entry<IFamilyMember, IRelationship> entry : familyMember.getRelatioshipList().entrySet()) {
            if (entry.getValue().getRelationType().equals(Constants.DAUGHTER)
                    && !entry.getKey().getMemberName().equals(member.getMemberName())) {
                sisters.add(entry.getKey());
            }
        }

        return sisters;
    }

    @Override
    public LinkedList<IFamilyMember> getBrothersForBrotherInlaw(IFamilyMember sister) {
        LinkedList<IFamilyMember> brothers = new LinkedList<>();
        for (Map.Entry<IFamilyMember, IRelationship> entry : sister.getRelatioshipList().entrySet()) {
            if (entry.getValue().getRelationType().equals(Constants.SON)) {
                brothers.add(entry.getKey());
            }
        }
        return brothers;
    }


    public Boolean hasMarried(IFamilyMember member) {
        Boolean hasHusband = false;
        Map<IFamilyMember, IRelationship> fam_list = member.getRelatioshipList();
        for (Map.Entry<IFamilyMember, IRelationship> entry : fam_list.entrySet()) {
            if (entry.getValue().getRelationType().equals(Constants.HUSBAND)) {
                hasHusband = true;
            }
        }

        return hasHusband;
    }

}