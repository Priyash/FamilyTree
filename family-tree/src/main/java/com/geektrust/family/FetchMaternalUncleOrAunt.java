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
public class FetchMaternalUncleOrAunt implements Fetchable {

    private LinkedList<IFamilyMember> fetchMaternalUncleOrAuntList = new LinkedList<>();
    private Boolean FAMILY_MEMBER_FOUND = false;
    private Boolean IS_FROM_FATHER_SIDE = false;

    public FetchMaternalUncleOrAunt() {

    }

    @Override
    public LinkedList<IFamilyMember> fetchPersonInRelation(IFamilyMember familyMemberToFind,
            LinkedList<IFamilyMember> familyTree, IRelationship relation) {

        for (IFamilyMember family_person : familyTree) {
            fetchMaternalUncleOrAuntList = find_family_member(family_person, familyMemberToFind, relation);
        }
        return fetchMaternalUncleOrAuntList;
    }

    private LinkedList<IFamilyMember> find_family_member(IFamilyMember root, IFamilyMember familyMemberToFind,
                                                            IRelationship relation) {
        Map<IFamilyMember, IRelationship> children = root.getRelatioshipList();

        for (Map.Entry<IFamilyMember, IRelationship> entry : children.entrySet()) {
            fetchMaternalUncleOrAuntList = this.processFirstGenChildren(root, entry.getKey(), familyMemberToFind, relation);
        }

        if (fetchMaternalUncleOrAuntList.size() == 0 && FAMILY_MEMBER_FOUND == false) {

            Map<IFamilyMember, IRelationship> children_2 = root.getRelatioshipList();
            for (Map.Entry<IFamilyMember, IRelationship> entry : children_2.entrySet()) {
                fetchMaternalUncleOrAuntList = this.find_family_member(entry.getKey(), familyMemberToFind, relation);
            }
        }

        return fetchMaternalUncleOrAuntList;
    }

    private LinkedList<IFamilyMember> processFirstGenChildren(IFamilyMember parent, IFamilyMember member,
            IFamilyMember familyMemberToFind, IRelationship relation) {
        IFamilyMember father = null;
        IFamilyMember mother = null;

        if (this.hasWife(member) != null) {
            mother = this.hasWife(member);
            IS_FROM_FATHER_SIDE = true;
            father = member;
            fetchMaternalUncleOrAuntList = this.processSecondGenChildren(parent, father, mother, familyMemberToFind,
                    IS_FROM_FATHER_SIDE, relation);
        } else if (this.hasHusband(member) != null) {
            mother = member;
            father = this.hasHusband(member);
            IS_FROM_FATHER_SIDE = false;
            fetchMaternalUncleOrAuntList = this.processSecondGenChildren(parent, father, mother, familyMemberToFind,
                    IS_FROM_FATHER_SIDE, relation);
        }

        return fetchMaternalUncleOrAuntList;
    }


    private LinkedList<IFamilyMember> processSecondGenChildren(IFamilyMember grandParent, IFamilyMember father,
            IFamilyMember mother, IFamilyMember familyMemberToFind, Boolean IS_FROM_FATHER_SIDE, 
            IRelationship relation) {
        if (IS_FROM_FATHER_SIDE) {
            return fetchMaternalUncleOrAuntList;
        } else {
            Map<IFamilyMember, IRelationship> children = mother.getRelatioshipList();
            for (Map.Entry<IFamilyMember, IRelationship> entry : children.entrySet()) {
                if (entry.getKey().getMemberName().equals(familyMemberToFind.getMemberName())
                        && FAMILY_MEMBER_FOUND == false) {
                    FAMILY_MEMBER_FOUND = true;
                    fetchMaternalUncleOrAuntList = this.findSibling(grandParent, father, mother, relation);
                }
            }
        }
        return fetchMaternalUncleOrAuntList;
    }

    private LinkedList<IFamilyMember> findSibling(IFamilyMember grandParent, IFamilyMember father,
            IFamilyMember mother, IRelationship relation){
        if (relation.getRelationType().equals(Constants.MATERNAL_UNCLE)) {
            fetchMaternalUncleOrAuntList = this.findMaleSibling(grandParent, father);
        } else if (relation.getRelationType().equals(Constants.MATERNAL_AUNT)) {
            fetchMaternalUncleOrAuntList = this.findFemaleSibling(grandParent, mother);
        }
        return fetchMaternalUncleOrAuntList;
    }
 

    private LinkedList<IFamilyMember> findMaleSibling(IFamilyMember grandParent, IFamilyMember member) {

        Map<IFamilyMember, IRelationship> children = grandParent.getRelatioshipList();
        for (Map.Entry<IFamilyMember, IRelationship> entry : children.entrySet()) {
            if (entry.getValue().getRelationType().equals(Constants.SON)
                    && !entry.getKey().getMemberName().equals(member.getMemberName())) {
                fetchMaternalUncleOrAuntList.add(entry.getKey());
            }
        }

        return fetchMaternalUncleOrAuntList;
    }

    private LinkedList<IFamilyMember> findFemaleSibling(IFamilyMember grandParent, IFamilyMember member) {

        Map<IFamilyMember, IRelationship> children = grandParent.getRelatioshipList();
        for (Map.Entry<IFamilyMember, IRelationship> entry : children.entrySet()) {
            if (entry.getValue().getRelationType().equals(Constants.DAUGHTER)
                    && !entry.getKey().getMemberName().equals(member.getMemberName())) {
                fetchMaternalUncleOrAuntList.add(entry.getKey());
            }
        }

        return fetchMaternalUncleOrAuntList;
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