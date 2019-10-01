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
public class FetchDaughters implements Fetchable {
    private LinkedList<IFamilyMember> daughters = new LinkedList<>();
    private Boolean FAMILY_MEMBER_FOUND = false;
    @Override
    public LinkedList<IFamilyMember> fetchPersonInRelation(IFamilyMember familyMemberToFind,
            LinkedList<IFamilyMember> familyTree) {
        LinkedList<IFamilyMember> daughters = new LinkedList<>();
        for (IFamilyMember family_person : familyTree) {
            daughters = find_family_member(family_person, familyMemberToFind);
        }
        return daughters;
    }

    private LinkedList<IFamilyMember> find_family_member(IFamilyMember root, IFamilyMember familyMemberToFind) {
        if (root.getMemberName().equals(familyMemberToFind.getMemberName())) {
            daughters = this.getDaughters(root);
        } else {
            Map<IFamilyMember, IRelationship> fam_list = root.getRelatioshipList();
            if (fam_list != null || !fam_list.isEmpty()) {
                for (Map.Entry<IFamilyMember, IRelationship> entry : fam_list.entrySet()) {
                    IFamilyMember husband = this.hasHusband(entry.getKey());
                    if (husband != null && husband.getMemberName().equals(familyMemberToFind.getMemberName())
                            && FAMILY_MEMBER_FOUND == false) {
                        FAMILY_MEMBER_FOUND = true;
                        daughters = this.getDaughters(entry.getKey());
                        break;
                    } else if (husband == null
                            && entry.getKey().getMemberName().equals(familyMemberToFind.getMemberName())
                            && FAMILY_MEMBER_FOUND == false) {
                        FAMILY_MEMBER_FOUND = true;
                        daughters = this.getDaughters(this.hasWife(entry.getKey()));
                        break;
                    } else {
                        find_family_member(entry.getKey(), familyMemberToFind);
                    }
                }
            }
        }

        return daughters;
    }

    private LinkedList<IFamilyMember> getDaughters(IFamilyMember familyPerson) {
        LinkedList<IFamilyMember> sons = new LinkedList<>();
        Map<IFamilyMember, IRelationship> fam_list = familyPerson.getRelatioshipList();
        for (Map.Entry<IFamilyMember, IRelationship> entry : fam_list.entrySet()) {
            if (entry.getValue().getRelationType().equals(Constants.DAUGHTER)) {
                sons.add(entry.getKey());
            }
        }

        return sons;
    }


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


    private IFamilyMember hasWife(IFamilyMember member) {
        IFamilyMember wife = null;
        Map<IFamilyMember, IRelationship> children = member.getRelatioshipList();
        for (Map.Entry<IFamilyMember, IRelationship> entry : children.entrySet()) {
            if (entry.getValue().getRelationType().equals(Constants.WIFE)) {
                wife = entry.getKey();
            }
        }
        return wife;
    }

}