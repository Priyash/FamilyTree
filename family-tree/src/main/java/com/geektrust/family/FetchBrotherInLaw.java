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
            LinkedList<IFamilyMember> familyTree) {
        LinkedList<IFamilyMember> brother_in_laws = new LinkedList<>();
        for (IFamilyMember family_person : familyTree) {
            brother_in_laws = find_family_member(family_person, familyMemberToFind);
        }
        return brother_in_laws;
    }

    // SINGLE FUNCTION FOR FINDING THE BROTHER IN LAWS OVERALL COMBINING THE TWO
    // LOGIC
    private LinkedList<IFamilyMember> find_family_member(IFamilyMember root, IFamilyMember familyMemberToFind) {
        brother_in_laws = this.getBrotherInLaws(root, familyMemberToFind);
        brother_in_laws = this.getBrotherInLaws_2(root, familyMemberToFind);
        if (brother_in_laws.size() == 0) {
            Map<IFamilyMember, IRelationship> children = root.getRelatioshipList();
            for (Map.Entry<IFamilyMember, IRelationship> entry : children.entrySet()) {
                find_family_member(entry.getKey(), familyMemberToFind);
            }
        }

        return brother_in_laws;
    }

    // GETTING THE BROTHER IN LAWS FOR THE LOGIC SIBLINGS HUSBAND
    private LinkedList<IFamilyMember> getBrotherInLaws_2(IFamilyMember familyMember, IFamilyMember familyMemberToFind) {
        Map<IFamilyMember, IRelationship> children = familyMember.getRelatioshipList();
        for (Map.Entry<IFamilyMember, IRelationship> entry : children.entrySet()) {
            if (entry.getKey().getMemberName().equals(familyMemberToFind.getMemberName())
                    && FAMILY_MEMBER_FOUND == false) {
                FAMILY_MEMBER_FOUND = true;
                brother_in_laws = getBrotherInLaws(familyMember);
            }
        }

        return brother_in_laws;
    }

    // GETTING THE BROTHER IN LAWS FOR THE LOGIC WIFE'S BROTHER
    private LinkedList<IFamilyMember> getBrotherInLaws(IFamilyMember familyMember, IFamilyMember familyMemberToFind) {
        Map<IFamilyMember, IRelationship> children = familyMember.getRelatioshipList();
        for (Map.Entry<IFamilyMember, IRelationship> entry : children.entrySet()) {
            if (entry.getValue().getRelationType().equals(Constants.DAUGHTER)) {
                if (this.hasHusband(entry.getKey(), familyMemberToFind)) {

                    brother_in_laws = this.getBrothers(familyMember);
                }
            }
        }

        return brother_in_laws;
    }

    // WIFE'S HUSBAND'S BROTHER SEARCH
    private LinkedList<IFamilyMember> getBrothers(IFamilyMember sister) {
        LinkedList<IFamilyMember> brothers = new LinkedList<>();
        for (Map.Entry<IFamilyMember, IRelationship> entry : sister.getRelatioshipList().entrySet()) {
            if (entry.getValue().getRelationType().equals(Constants.SON)) {
                brothers.add(entry.getKey());
            }
        }

        return brothers;
    }

    // THIS FUNCTION CHECKS A DAUGHTER BELONG TO A TREE HAS A HUSBAND OR NOT [GIVEN
    // HUSBAND NAME IN THE QUERY FOR BROTHER-IN-LAW SEARCH]
    private Boolean hasHusband(IFamilyMember member, IFamilyMember familyMemberToFind) {
        Boolean hasHusband = false;
        Map<IFamilyMember, IRelationship> children = member.getRelatioshipList();
        for (Map.Entry<IFamilyMember, IRelationship> entry : children.entrySet()) {
            if (entry.getValue().getRelationType().equals(Constants.HUSBAND)
                    && entry.getKey().getMemberName().equals(familyMemberToFind.getMemberName())
                    && FAMILY_MEMBER_FOUND == false) {
                FAMILY_MEMBER_FOUND = true;
                hasHusband = true;
                break;
            }
        }
        return hasHusband;
    }

    // GETTING THE BROTHER-IN-LAWS FOR THE LOGIC of SIBLINGS HUSBAND
    private LinkedList<IFamilyMember> getBrotherInLaws(IFamilyMember familyMember) {
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

    // THIS FUNCTION CHECKS A DAUGHTER BELONG TO A TREE HAS A HUSBAND OR
    // NOT[OVERLOADED FUNCTION FOR ANOTHER LOGIC]
    private IFamilyMember hasHusband(IFamilyMember member) {
        IFamilyMember hasHusband = null;
        Map<IFamilyMember, IRelationship> children = member.getRelatioshipList();
        for (Map.Entry<IFamilyMember, IRelationship> entry : children.entrySet()) {
            if (entry.getValue().getRelationType().equals(Constants.HUSBAND)) {
                hasHusband = entry.getKey();
                break;
            }
        }
        return hasHusband;
    }

}