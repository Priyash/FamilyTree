package com.geektrust.family;

import java.util.LinkedList;
import java.util.Map;

import com.geektrust.family.FamilyMemberInterface.IFamilyMember;
import com.geektrust.family.RelationshipInterface.Fetchable;
import com.geektrust.family.RelationshipInterface.IRelationship;
import com.geektrust.family.Utility.Constants;

/**
 * FetchMaternalAunt
 */
public class FetchPaternalAunt implements Fetchable {

    private LinkedList<IFamilyMember> paternalAuntList = new LinkedList<>();
    private Boolean FAMILY_MEMBER_FOUND = false;
    private Boolean IS_FROM_MOTHER_SIDE = false;

    public FetchPaternalAunt() {

    }

    @Override
    public LinkedList<IFamilyMember> fetchPersonInRelation(IFamilyMember familyMemberToFind,
            LinkedList<IFamilyMember> familyTree) {

        for (IFamilyMember family_person : familyTree) {
            paternalAuntList = find_family_member(family_person, familyMemberToFind);
        }
        return paternalAuntList;
    }

    private LinkedList<IFamilyMember> find_family_member(IFamilyMember root, IFamilyMember familyMemberToFind) {
        // paternalAuntList = this.processFirstGenChildren(root, familyMemberToFind);
        Map<IFamilyMember, IRelationship> children = root.getRelatioshipList();

        for (Map.Entry<IFamilyMember, IRelationship> entry : children.entrySet()) {
            paternalAuntList = this.processFirstGenChildren(root, entry.getKey(), familyMemberToFind);
        }

        if (paternalAuntList.size() == 0 && FAMILY_MEMBER_FOUND == false) {

            Map<IFamilyMember, IRelationship> children_2 = root.getRelatioshipList();
            for (Map.Entry<IFamilyMember, IRelationship> entry : children_2.entrySet()) {
                paternalAuntList = this.find_family_member(entry.getKey(), familyMemberToFind);
            }
        }

        return paternalAuntList;
    }

    private LinkedList<IFamilyMember> processFirstGenChildren(IFamilyMember parent, IFamilyMember member,
            IFamilyMember familyMemberToFind) {
        IFamilyMember father = null;
        IFamilyMember mother = null;

        if (this.hasWife(member) != null) {
            mother = this.hasWife(member);
            IS_FROM_MOTHER_SIDE = false;
            father = member;
            paternalAuntList = this.processSecondGenChildren(parent, father, mother, familyMemberToFind,
                    IS_FROM_MOTHER_SIDE);
        } else if (this.hasHusband(member) != null) {
            mother = member;
            father = this.hasHusband(member);
            IS_FROM_MOTHER_SIDE = true;
            paternalAuntList = this.processSecondGenChildren(parent, father, mother, familyMemberToFind,
                    IS_FROM_MOTHER_SIDE);
        }

        return paternalAuntList;
    }

    private LinkedList<IFamilyMember> processSecondGenChildren(IFamilyMember grandParent, IFamilyMember father,
            IFamilyMember mother, IFamilyMember familyMemberToFind, Boolean IS_FROM_FATHER_SIDE) {
        if (IS_FROM_MOTHER_SIDE) {
            return paternalAuntList;
        } else {
            Map<IFamilyMember, IRelationship> children = mother.getRelatioshipList();
            for (Map.Entry<IFamilyMember, IRelationship> entry : children.entrySet()) {
                if (entry.getKey().getMemberName().equals(familyMemberToFind.getMemberName())
                        && FAMILY_MEMBER_FOUND == false) {
                    FAMILY_MEMBER_FOUND = true;
                    paternalAuntList = this.processFemaleSibling(grandParent, mother);
                    break;
                }
            }
        }

        return paternalAuntList;
    }

    private LinkedList<IFamilyMember> processFemaleSibling(IFamilyMember grandParent, IFamilyMember mother) {

        Map<IFamilyMember, IRelationship> children = grandParent.getRelatioshipList();
        for (Map.Entry<IFamilyMember, IRelationship> entry : children.entrySet()) {
            if (entry.getValue().getRelationType().equals(Constants.DAUGHTER)
                    && !entry.getKey().getMemberName().equals(mother.getMemberName())) {
                paternalAuntList.add(entry.getKey());
            }
        }

        return paternalAuntList;
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

}