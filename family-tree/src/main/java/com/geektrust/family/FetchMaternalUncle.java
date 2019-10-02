package com.geektrust.family;

import java.util.LinkedList;
import java.util.Map;

import com.geektrust.family.FamilyMemberInterface.IFamilyMember;
import com.geektrust.family.RelationshipInterface.Fetchable;
import com.geektrust.family.RelationshipInterface.IRelationship;
import com.geektrust.family.Utility.Constants;
import com.geektrust.family.Utility.FamilyCheckable;
import com.geektrust.family.Utility.FamilyCheckerUtils;

/**
 * FetchMaternalAunt
 */
public class FetchMaternalUncle implements Fetchable {

    private LinkedList<IFamilyMember> maternalUncleList = new LinkedList<>();
    private Boolean FAMILY_MEMBER_FOUND = false;
    private Boolean IS_FROM_FATHER_SIDE = false;
    private FamilyCheckable checker = new FamilyCheckerUtils();
    
    public FetchMaternalUncle() {

    }

    @Override
    public LinkedList<IFamilyMember> fetchPersonInRelation(IFamilyMember familyMemberToFind,
            LinkedList<IFamilyMember> familyTree) {

        for (IFamilyMember family_person : familyTree) {
            maternalUncleList = find_family_member(family_person, familyMemberToFind);
        }
        return maternalUncleList;
    }

    private LinkedList<IFamilyMember> find_family_member(IFamilyMember root, IFamilyMember familyMemberToFind) {
        // maternalUncleList = this.processFirstGenChildren(root, familyMemberToFind);
        Map<IFamilyMember, IRelationship> children = root.getRelatioshipList();

        for (Map.Entry<IFamilyMember, IRelationship> entry : children.entrySet()) {
            maternalUncleList = this.processFirstGenChildren(root, entry.getKey(), familyMemberToFind);
        }

        if (maternalUncleList.size() == 0 && FAMILY_MEMBER_FOUND == false) {

            Map<IFamilyMember, IRelationship> children_2 = root.getRelatioshipList();
            for (Map.Entry<IFamilyMember, IRelationship> entry : children_2.entrySet()) {
                maternalUncleList = this.find_family_member(entry.getKey(), familyMemberToFind);
            }
        }

        return maternalUncleList;
    }

    private LinkedList<IFamilyMember> processFirstGenChildren(IFamilyMember parent, IFamilyMember member,
            IFamilyMember familyMemberToFind) {
        IFamilyMember father = null;
        IFamilyMember mother = null;

        if (checker.hasWife(member) != null) {
            mother = checker.hasWife(member);
            IS_FROM_FATHER_SIDE = true;
            father = member;
            maternalUncleList = this.processSecondGenChildren(parent, father, mother, familyMemberToFind,
                    IS_FROM_FATHER_SIDE);
        } else if (checker.hasHusband(member) != null) {
            mother = member;
            father = checker.hasHusband(member);
            IS_FROM_FATHER_SIDE = false;
            maternalUncleList = this.processSecondGenChildren(parent, father, mother, familyMemberToFind,
                    IS_FROM_FATHER_SIDE);
        }

        return maternalUncleList;
    }

    private LinkedList<IFamilyMember> processSecondGenChildren(IFamilyMember grandParent, IFamilyMember father,
            IFamilyMember mother, IFamilyMember familyMemberToFind, Boolean IS_FROM_FATHER_SIDE) {
        if (IS_FROM_FATHER_SIDE) {
            return maternalUncleList;
        } else {
            Map<IFamilyMember, IRelationship> children = mother.getRelatioshipList();
            for (Map.Entry<IFamilyMember, IRelationship> entry : children.entrySet()) {
                if (entry.getKey().getMemberName().equals(familyMemberToFind.getMemberName())
                        && FAMILY_MEMBER_FOUND == false) {
                    FAMILY_MEMBER_FOUND = true;
                    maternalUncleList = this.processMaleSibling(grandParent, father);
                    break;
                }
            }
        }

        return maternalUncleList;
    }

    private LinkedList<IFamilyMember> processMaleSibling(IFamilyMember grandParent, IFamilyMember father) {

        Map<IFamilyMember, IRelationship> children = grandParent.getRelatioshipList();
        for (Map.Entry<IFamilyMember, IRelationship> entry : children.entrySet()) {
            if (entry.getValue().getRelationType().equals(Constants.SON)
                    && !entry.getKey().getMemberName().equals(father.getMemberName())) {
                maternalUncleList.add(entry.getKey());
            }
        }

        return maternalUncleList;
    }
}