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
public class FetchSons implements Fetchable {
    private LinkedList<IFamilyMember> sons = new LinkedList<>();
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
        if (root.getMemberName().equals(familyMemberToFind.getMemberName())) {
            sons = this.getSons(root);
        } else {
            Map<IFamilyMember, IRelationship> fam_list = root.getRelatioshipList();
            if (fam_list != null || !fam_list.isEmpty()) {
                for (Map.Entry<IFamilyMember, IRelationship> entry : fam_list.entrySet()) {
                    if (entry.getKey().getMemberName().equals(familyMemberToFind.getMemberName())) {
                        sons = this.getSons(entry.getKey());
                        break;
                    } else {
                        find_family_member(entry.getKey(), familyMemberToFind);
                    }
                }
            }
        }

        return sons;
    }

    private LinkedList<IFamilyMember> getSons(IFamilyMember familyPerson)
    {
        LinkedList<IFamilyMember> sons = new LinkedList<>();
        Map<IFamilyMember, IRelationship> fam_list = familyPerson.getRelatioshipList();
        for (Map.Entry<IFamilyMember, IRelationship> entry : fam_list.entrySet()) {
            if(entry.getValue().getRelationType().equals(Constants.SON))
            {
                sons.add(entry.getKey());
            }
        }

        return sons;
    }

}