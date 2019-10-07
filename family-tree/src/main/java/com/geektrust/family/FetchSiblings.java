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
public class FetchSiblings implements Fetchable {
    private LinkedList<IFamilyMember> siblings = new LinkedList<>();
    
    @Override
    public LinkedList<IFamilyMember> fetchPersonInRelation(IFamilyMember familyMemberToFind,
            LinkedList<IFamilyMember> familyTree, IRelationship relation) {
        LinkedList<IFamilyMember> siblings = new LinkedList<>();
        for (IFamilyMember family_person : familyTree) {
            siblings = find_family_member(family_person, familyMemberToFind, relation);
        }
        return siblings;
    }

    private LinkedList<IFamilyMember> find_family_member(IFamilyMember root, IFamilyMember familyMemberToFind,
            IRelationship relation) {
        siblings = this.getSiblings(root, familyMemberToFind, relation);
        if (siblings.size() == 0) {
            Map<IFamilyMember, IRelationship> children = root.getRelatioshipList();
            for (Map.Entry<IFamilyMember, IRelationship> entry : children.entrySet()) {
                find_family_member(entry.getKey(), familyMemberToFind, relation);
            }
        }

        return siblings;
    }


    private LinkedList<IFamilyMember> getSiblings(IFamilyMember root, IFamilyMember familyMemberToFind,
            IRelationship relation) {

        Map<IFamilyMember, IRelationship> children = root.getRelatioshipList();

        Boolean found_member = false;
        for (Map.Entry<IFamilyMember, IRelationship> entry : children.entrySet()) {
            if (entry.getKey().getMemberName().equals(familyMemberToFind.getMemberName())) {
                found_member = true;
                break;
            }
        }

        if (found_member && relation.getRelationType().equals(Constants.SIBLING)) {
            siblings = this.getMaleSiblings(children, familyMemberToFind);
            siblings = this.getFemaleSiblings(children, familyMemberToFind);
        }

        return siblings;
    }


    private LinkedList<IFamilyMember> getMaleSiblings(Map<IFamilyMember, IRelationship> children, 
            IFamilyMember familyMemberToFind){
        for (Map.Entry<IFamilyMember, IRelationship> entry : children.entrySet()) {
            if ((entry.getValue().getRelationType().equals(Constants.SON)
                    && !entry.getKey().getMemberName().equals(familyMemberToFind.getMemberName()))) {
                siblings.add(entry.getKey());
            }
        }
        return siblings;
    }

    private LinkedList<IFamilyMember> getFemaleSiblings(Map<IFamilyMember, IRelationship> children, 
            IFamilyMember familyMemberToFind){
        for (Map.Entry<IFamilyMember, IRelationship> entry : children.entrySet()) {
            if ((entry.getValue().getRelationType().equals(Constants.DAUGHTER)
                    && !entry.getKey().getMemberName().equals(familyMemberToFind.getMemberName()))) {
                siblings.add(entry.getKey());
            }
        }
        return siblings;
    }
}