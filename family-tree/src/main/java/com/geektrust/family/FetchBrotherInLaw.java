package com.geektrust.family;

import java.util.LinkedList;
import java.util.Map;

import com.geektrust.family.FamilyMemberInterface.IFamilyMember;
import com.geektrust.family.RelationshipInterface.IRelationship;
import com.geektrust.family.RelationshipInterface.Fetchable;
import com.geektrust.family.Utility.Constants;

/**
 * FetchSons
 */
public class FetchBrotherInLaw implements Fetchable {
    private LinkedList<IFamilyMember> brother_in_laws = new LinkedList<>();
    private Boolean FAMILY_MEMBER_FOUND = false;
    
    @Override
    public LinkedList<IFamilyMember> fetchPersonInRelation(IFamilyMember familyMemberToFind,
            LinkedList<IFamilyMember> familyTree, IRelationship relation) {
        LinkedList<IFamilyMember> brother_in_laws = new LinkedList<>();
        for (IFamilyMember family_person : familyTree) {
            brother_in_laws = find_family_member(family_person, familyMemberToFind, relation);
        }
        return brother_in_laws;
    }

    // SINGLE FUNCTION FOR FINDING THE BROTHER IN LAWS OVERALL COMBINING THE TWO
    // LOGIC
    private LinkedList<IFamilyMember> find_family_member(IFamilyMember root, IFamilyMember familyMemberToFind,
            IRelationship relation) {
        brother_in_laws = this.getTotalBrotherInLaws(root, familyMemberToFind, relation);
        if (brother_in_laws.size() == 0) {
            Map<IFamilyMember, IRelationship> children = root.getRelatioshipList();
            for (Map.Entry<IFamilyMember, IRelationship> entry : children.entrySet()) {
                find_family_member(entry.getKey(), familyMemberToFind, relation);
            }
        }

        return brother_in_laws;
    }

    private LinkedList<IFamilyMember> getTotalBrotherInLaws(IFamilyMember root, IFamilyMember familyMemberToFind,
            IRelationship relation) {

        if (relation.getRelationType().equals(Constants.BROTHER_IN_LAW)) {
            brother_in_laws = this.getBrotherInLaws_V1(root, familyMemberToFind);
            brother_in_laws = this.getBrotherInLaws_V2(root, familyMemberToFind);
        }
        return brother_in_laws;
    }

    //==============================================SECTION-ONE-START=====================================================

    // GETTING THE BROTHER IN LAWS FOR THE LOGIC WIFE'S BROTHER
    private LinkedList<IFamilyMember> getBrotherInLaws_V1(IFamilyMember familyMember, IFamilyMember familyMemberToFind) {
        Map<IFamilyMember, IRelationship> children = familyMember.getRelatioshipList();
        for (Map.Entry<IFamilyMember, IRelationship> entry : children.entrySet()) {
            if (entry.getValue().getRelationType().equals(Constants.DAUGHTER)) {
                if (this.hasHusband(entry.getKey(), familyMemberToFind, !FAMILY_MEMBER_FOUND)) {
                    FAMILY_MEMBER_FOUND = true;
                    brother_in_laws = this.getWivesBrothers(familyMember);
                }
            }
        }

        return brother_in_laws;
    }

    private LinkedList<IFamilyMember> getWivesBrothers(IFamilyMember sister) {
        LinkedList<IFamilyMember> brothers = new LinkedList<>();
        for (Map.Entry<IFamilyMember, IRelationship> entry : sister.getRelatioshipList().entrySet()) {
            if (entry.getValue().getRelationType().equals(Constants.SON)) {
                brothers.add(entry.getKey());
            }
        }
        return brothers;
    }

    //===============================================SECTION-ONE-END=====================================================

    
    
    
    // ==============================================SECTION-TWO-START===================================================

    // GETTING THE BROTHER IN LAWS FOR THE LOGIC SIBLINGS HUSBAND
    private LinkedList<IFamilyMember> getBrotherInLaws_V2(IFamilyMember familyMember, IFamilyMember familyMemberToFind) {
        Map<IFamilyMember, IRelationship> children = familyMember.getRelatioshipList();
        for (Map.Entry<IFamilyMember, IRelationship> entry : children.entrySet()) {
            if (entry.getKey().getMemberName().equals(familyMemberToFind.getMemberName())
                    && !FAMILY_MEMBER_FOUND) {
                FAMILY_MEMBER_FOUND = true;
                brother_in_laws = getSiblingsHusband(familyMember);
            }
        }

        return brother_in_laws;
    }

    

    // GETTING THE BROTHER-IN-LAWS FOR THE LOGIC of SIBLINGS HUSBAND
    private LinkedList<IFamilyMember> getSiblingsHusband(IFamilyMember familyMember) {
        Map<IFamilyMember, IRelationship> children = familyMember.getRelatioshipList();
        for (Map.Entry<IFamilyMember, IRelationship> entry : children.entrySet()) {
            if (entry.getValue().getRelationType().equals(Constants.DAUGHTER)) {
                if (this.hasHusband(entry.getKey()) != null) {
                    brother_in_laws.add(this.hasHusband(entry.getKey()));
                }
            }
        }

        return brother_in_laws;
    }

    // ==============================================SECTION-TWO-END===================================================

    private IFamilyMember hasHusband(IFamilyMember member) {
        IFamilyMember husband = null;
        Map<IFamilyMember, IRelationship> children = member.getRelatioshipList();
        for (Map.Entry<IFamilyMember, IRelationship> entry : children.entrySet()) {
            if (entry.getValue().getRelationType().equals(Constants.HUSBAND)) {
                husband = entry.getKey();
            }
        }
        return husband;
    }

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


    

}