package com.geektrust.family;

import java.util.LinkedList;
import java.util.Map;

import com.geektrust.family.FamilyMemberInterface.IFamilyMember;
import com.geektrust.family.RelationshipInterface.IRelationship;
import com.geektrust.family.RelationshipInterface.Fetchable;
import com.geektrust.family.Utility.Constants;
import com.geektrust.family.FamilyMemberInterface.*;

/**
 * FetchSons
 */
public class FetchSonsOrDaughters implements Fetchable {
    private LinkedList<IFamilyMember> sonsOrDaughtersList = new LinkedList<>();
    private Boolean FAMILY_MEMBER_FOUND = false;

    @Override
    public LinkedList<IFamilyMember> fetchPersonInRelation(IFamilyMember familyMemberToFind,
            LinkedList<IFamilyMember> familyTree, IRelationship relation) {
        LinkedList<IFamilyMember> sonsOrDaughtersList = new LinkedList<>();
        for (IFamilyMember family_person : familyTree) {
            sonsOrDaughtersList = find_family_member(family_person, familyMemberToFind, relation);
        }
        return sonsOrDaughtersList;
    }

    private LinkedList<IFamilyMember> find_family_member(IFamilyMember root, IFamilyMember familyMemberToFind,
            IRelationship relation) {
        if (root.getMemberName().equals(familyMemberToFind.getMemberName()) && FAMILY_MEMBER_FOUND == false) {
            FAMILY_MEMBER_FOUND = true;
            sonsOrDaughtersList = this.findSonsOrDaughters(root, familyMemberToFind, relation);
        } else {
            Map<IFamilyMember, IRelationship> fam_list = root.getRelatioshipList();
            if (fam_list != null || !fam_list.isEmpty()) {
                for (Map.Entry<IFamilyMember, IRelationship> entry : fam_list.entrySet()) {
                    sonsOrDaughtersList = this.findSonsOrDaughters(entry.getKey(), familyMemberToFind, relation);
                }
            }
        }

        return sonsOrDaughtersList;
    }

    private LinkedList<IFamilyMember> findSonsOrDaughters(IFamilyMember member, IFamilyMember familyMemberToFind,
            IRelationship relation){
        IFamilyMember husband = this.hasHusband(member);
        if (husband != null && husband.getMemberName().equals(familyMemberToFind.getMemberName())
                && FAMILY_MEMBER_FOUND == false) {
            FAMILY_MEMBER_FOUND = true;
            sonsOrDaughtersList = this.getChildren(member, relation);
        } else if (husband == null && member.getMemberName().equals(familyMemberToFind.getMemberName())
                && FAMILY_MEMBER_FOUND == false) {
            FAMILY_MEMBER_FOUND = true;
            sonsOrDaughtersList = this.getChildren(this.hasWife(member), relation);
        } else {
            find_family_member(member, familyMemberToFind, relation);
        }

        return sonsOrDaughtersList;
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

    public LinkedList<IFamilyMember> getChildren(IFamilyMember familyPerson, IRelationship relation) {
        
        LinkedList<IFamilyMember> children = new LinkedList<>();
        Map<IFamilyMember, IRelationship> fam_list = familyPerson.getRelatioshipList();
        for (Map.Entry<IFamilyMember, IRelationship> entry : fam_list.entrySet()) {
            if (relation.getRelationType().equals(Constants.SON)
                    && entry.getValue().getRelationType().equals(Constants.SON)) {
                children.add(entry.getKey());
            } else if (relation.getRelationType().equals(Constants.DAUGHTER)
                    && entry.getValue().getRelationType().equals(Constants.DAUGHTER)) {
                children.add(entry.getKey());
            }
        }

        return children;
    }

}