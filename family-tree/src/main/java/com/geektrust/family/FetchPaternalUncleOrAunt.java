package com.geektrust.family;

import java.util.LinkedList;
import java.util.Map;

import com.geektrust.family.FamilyMemberInterface.IFamilyMember;
import com.geektrust.family.RelationshipInterface.Fetchable;
import com.geektrust.family.RelationshipInterface.IRelationship;
import com.geektrust.family.Utility.Constants;
import com.geektrust.family.FamilyMemberInterface.*;

/**
 * FetchMaternalAunt
 */
public class FetchPaternalUncleOrAunt implements Fetchable {

    private LinkedList<IFamilyMember> fetchPaternalUncleOrAuntList = new LinkedList<>();
    private Boolean FAMILY_MEMBER_FOUND = false;
    private Boolean IS_FROM_MOTHER_SIDE = false;

    public FetchPaternalUncleOrAunt() {

    }

    @Override
    public LinkedList<IFamilyMember> fetchPersonInRelation(IFamilyMember familyMemberToFind,
            LinkedList<IFamilyMember> familyTree, IRelationship relation) {

        for (IFamilyMember family_person : familyTree) {
            fetchPaternalUncleOrAuntList = find_family_member(family_person, familyMemberToFind, relation);
        }
        return fetchPaternalUncleOrAuntList;
    }

    private LinkedList<IFamilyMember> find_family_member(IFamilyMember root, IFamilyMember familyMemberToFind,
            IRelationship relation) {
        // paternalUncleList = this.processFirstGenChildren(root, familyMemberToFind);
        Map<IFamilyMember, IRelationship> children = root.getRelatioshipList();

        for (Map.Entry<IFamilyMember, IRelationship> entry : children.entrySet()) {
            fetchPaternalUncleOrAuntList = this.processFirstGenChildren(root, entry.getKey(), familyMemberToFind, relation);
        }

        if (fetchPaternalUncleOrAuntList.size() == 0 && FAMILY_MEMBER_FOUND == false) {

            Map<IFamilyMember, IRelationship> children_2 = root.getRelatioshipList();
            for (Map.Entry<IFamilyMember, IRelationship> entry : children_2.entrySet()) {
                fetchPaternalUncleOrAuntList = this.find_family_member(entry.getKey(), familyMemberToFind, relation);
            }
        }

        return fetchPaternalUncleOrAuntList;
    }

    private LinkedList<IFamilyMember> processFirstGenChildren(IFamilyMember parent, IFamilyMember member,
            IFamilyMember familyMemberToFind, IRelationship relation) {
        IFamilyMember father = null;
        IFamilyMember mother = null;

        if (this.hasWife(member) != null) {
            mother = this.hasWife(member);
            IS_FROM_MOTHER_SIDE = false;
            father = member;
            fetchPaternalUncleOrAuntList = this.processSecondGenChildren(parent, father, mother, familyMemberToFind,
                    IS_FROM_MOTHER_SIDE, relation);
        } else if (this.hasHusband(member) != null) {
            mother = member;
            father = this.hasHusband(member);
            IS_FROM_MOTHER_SIDE = true;
            fetchPaternalUncleOrAuntList = this.processSecondGenChildren(parent, father, mother, familyMemberToFind,
                    IS_FROM_MOTHER_SIDE, relation);
        }

        return fetchPaternalUncleOrAuntList;
    }

    private LinkedList<IFamilyMember> processSecondGenChildren(IFamilyMember grandParent, IFamilyMember father,
            IFamilyMember mother, IFamilyMember familyMemberToFind, Boolean IS_FROM_MOTHER_SIDE,
            IRelationship relation) {

        if (IS_FROM_MOTHER_SIDE) {
            return fetchPaternalUncleOrAuntList;
        } else {
            Map<IFamilyMember, IRelationship> children = mother.getRelatioshipList();
            for (Map.Entry<IFamilyMember, IRelationship> entry : children.entrySet()) {
                if (entry.getKey().getMemberName().equals(familyMemberToFind.getMemberName())
                        && FAMILY_MEMBER_FOUND == false) {
                    FAMILY_MEMBER_FOUND = true;
                    fetchPaternalUncleOrAuntList = this.findSiblings(grandParent, father, mother, relation);
                }
            }
        }
        return fetchPaternalUncleOrAuntList;
    }

    private LinkedList<IFamilyMember> findSiblings(IFamilyMember grandParent, IFamilyMember father,
            IFamilyMember mother, IRelationship relation){
        if(relation.getRelationType().equals(Constants.PATERNAL_UNCLE)){
            fetchPaternalUncleOrAuntList = this.findMaleSibling(grandParent, father);
        } else if(relation.getRelationType().equals(Constants.PATERNAL_AUNT)){
            fetchPaternalUncleOrAuntList = this.findFemaleSibling(grandParent, mother);
        }

        return fetchPaternalUncleOrAuntList;
    }

    private LinkedList<IFamilyMember> findMaleSibling(IFamilyMember grandParent, IFamilyMember father) {

        Map<IFamilyMember, IRelationship> children = grandParent.getRelatioshipList();
        for (Map.Entry<IFamilyMember, IRelationship> entry : children.entrySet()) {
            if (entry.getValue().getRelationType().equals(Constants.SON)
                    && !entry.getKey().getMemberName().equals(father.getMemberName())) {
                fetchPaternalUncleOrAuntList.add(entry.getKey());
            }
        }

        return fetchPaternalUncleOrAuntList;
    }

    private LinkedList<IFamilyMember> findFemaleSibling(IFamilyMember grandParent, IFamilyMember mother) {

        Map<IFamilyMember, IRelationship> children = grandParent.getRelatioshipList();
        for (Map.Entry<IFamilyMember, IRelationship> entry : children.entrySet()) {
            if (entry.getValue().getRelationType().equals(Constants.DAUGHTER)
                    && !entry.getKey().getMemberName().equals(mother.getMemberName())) {
                fetchPaternalUncleOrAuntList.add(entry.getKey());
            }
        }

        return fetchPaternalUncleOrAuntList;
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