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
public class FetchSons implements Fetchable {
    private LinkedList<IFamilyMember> sons = new LinkedList<>();
    private Boolean FAMILY_MEMBER_FOUND = false;
    private FamilyCheckable checker = new FamilyCheckerUtils();
    @Override
    public LinkedList<IFamilyMember> fetchPersonInRelation(IFamilyMember familyMemberToFind,
            LinkedList<IFamilyMember> familyTree) {
        LinkedList<IFamilyMember> sons = new LinkedList<>();
        for (IFamilyMember family_person : familyTree) {
            sons = find_family_member(family_person, familyMemberToFind);
        }
        return sons;
    }

    private LinkedList<IFamilyMember> find_family_member(IFamilyMember root, IFamilyMember familyMemberToFind) {
        if (root.getMemberName().equals(familyMemberToFind.getMemberName()) && FAMILY_MEMBER_FOUND == false) {
            FAMILY_MEMBER_FOUND = true;
            sons = checker.getChildren(root, new Son());
        } else {
            Map<IFamilyMember, IRelationship> fam_list = root.getRelatioshipList();
            if (fam_list != null || !fam_list.isEmpty()) {
                for (Map.Entry<IFamilyMember, IRelationship> entry : fam_list.entrySet()) {
                    IFamilyMember husband = checker.hasHusband(entry.getKey());
                    if (husband != null && husband.getMemberName().equals(familyMemberToFind.getMemberName()) && FAMILY_MEMBER_FOUND == false) {
                        FAMILY_MEMBER_FOUND = true;
                        sons = checker.getChildren(entry.getKey(), new Son());
                        break;
                    } else if(husband == null && entry.getKey().getMemberName().equals(familyMemberToFind.getMemberName()) && FAMILY_MEMBER_FOUND == false) {
                        FAMILY_MEMBER_FOUND = true;
                        sons = checker.getChildren(checker.hasWife(entry.getKey()), new Son());
                        break;
                    }else {
                        find_family_member(entry.getKey(), familyMemberToFind);
                    }
                }
            }
        }

        return sons;
    }

}