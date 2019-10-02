package com.geektrust.family;

import java.util.LinkedList;
import java.util.Map;

import com.geektrust.family.FamilyMemberInterface.IFamilyMember;
import com.geektrust.family.RelationshipInterface.IRelationship;
import com.geektrust.family.RelationshipInterface.Fetchable;
import com.geektrust.family.Utility.Constants;
import com.geektrust.family.Utility.FamilyCheckable;
import com.geektrust.family.Utility.FamilyCheckerUtils;

/**
 * FetchSons
 */
public class FetchBrotherInLaw implements Fetchable {
    private LinkedList<IFamilyMember> brother_in_laws = new LinkedList<>();
    private Boolean FAMILY_MEMBER_FOUND = false;
    private FamilyCheckable checker = new FamilyCheckerUtils();
    
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
                if (checker.hasHusband(entry.getKey(), familyMemberToFind, FAMILY_MEMBER_FOUND)) {
                    FAMILY_MEMBER_FOUND = true;
                    brother_in_laws = checker.getBrothersForBrotherInlaw(familyMember);
                }
            }
        }

        return brother_in_laws;
    }

    // GETTING THE BROTHER-IN-LAWS FOR THE LOGIC of SIBLINGS HUSBAND
    private LinkedList<IFamilyMember> getBrotherInLaws(IFamilyMember familyMember) {
        Map<IFamilyMember, IRelationship> children = familyMember.getRelatioshipList();
        for (Map.Entry<IFamilyMember, IRelationship> entry : children.entrySet()) {
            if (entry.getValue().getRelationType().equals(Constants.DAUGHTER)) {
                if (checker.hasHusband(entry.getKey()) != null) {
                    brother_in_laws.add(checker.hasHusband(entry.getKey()));
                }
            }
        }

        return brother_in_laws;
    }

}