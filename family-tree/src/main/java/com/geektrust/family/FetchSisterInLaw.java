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
public class FetchSisterInLaw implements Fetchable {
    private LinkedList<IFamilyMember> sister_in_laws = new LinkedList<>();
    private Boolean FAMILY_MEMBER_FOUND = false;
    
    @Override
    public LinkedList<IFamilyMember> fetchPersonInRelation(IFamilyMember familyMemberToFind,
            LinkedList<IFamilyMember> familyTree) {
        LinkedList<IFamilyMember> sister_in_laws = new LinkedList<>();
        for (IFamilyMember family_person : familyTree) {
            sister_in_laws = find_family_member(family_person, familyMemberToFind);
        }
        return sister_in_laws;
    }

    /**
     * This API is a generic API for which it will search the given person to get
     * sister-in-law.
     * 
     * @param root
     * @param familyMemberToFind
     * @return <code>LinkedList</code> of type <code>IFamilyMember</code> which
     *         holds total number sister-in-law objects.
     */
    private LinkedList<IFamilyMember> find_family_member(IFamilyMember root, IFamilyMember familyMemberToFind) {
        sister_in_laws = this.getTotalSisterInLaws(root, familyMemberToFind);
        if(sister_in_laws.size() == 0 && FAMILY_MEMBER_FOUND == false)
        {
            Map<IFamilyMember, IRelationship> children = root.getRelatioshipList();
            for (Map.Entry<IFamilyMember, IRelationship> entry : children.entrySet()) {
                find_family_member(entry.getKey(), familyMemberToFind);
            }
        }
        
        return sister_in_laws;
    }
    
    /**
     * This API accumulates total number of sister-in-law found from a given person.
     * 
     * @param root
     * @param familyMemberToFind
     * @return <code>LinkedList</code> of type <code>IFamilyMember</code> which holds total number sister-in-law objects.
     */
    private LinkedList<IFamilyMember> getTotalSisterInLaws(IFamilyMember root, IFamilyMember familyMemberToFind)
    {
        sister_in_laws = this.getSisterInLaws_V1(root, familyMemberToFind);
        sister_in_laws = this.getSisterInLaws_V2(root, familyMemberToFind);
        return sister_in_laws;
    }

    //===========================================SECTION_V1_FOR_FIRST_LOGIC_START====================================================
    
    /**
     * This API used for getting the sister-in-law i.e getting the wife's sister.
     * @param familyMember
     * @param familyMemberToFind
     * @return The list of the sister-in-law from the given person as a query
     *
     */
    private LinkedList<IFamilyMember> getSisterInLaws_V1(IFamilyMember familyMember, IFamilyMember familyMemberToFind) {
        Map<IFamilyMember, IRelationship> children = familyMember.getRelatioshipList();
        for (Map.Entry<IFamilyMember, IRelationship> entry : children.entrySet()) {
            if (entry.getValue().getRelationType().equals(Constants.DAUGHTER)) {
                if (this.hasHusband(entry.getKey(), familyMemberToFind)) {
                    sister_in_laws = this.getSisters(familyMember, entry.getKey());
                    break;
                }
            }
        }

        return sister_in_laws;
    }

    /**
     * This API used to check whether siblings has husband or not .
     * @param wife
     * @param familyMemberToFind
     * @return This return a boolean value which determines siblings has husband or not 
     * 
     */
    private Boolean hasHusband(IFamilyMember wife, IFamilyMember familyMemberToFind) {
        Boolean hasHusband = false;
        Map<IFamilyMember, IRelationship> wife_relations = wife.getRelatioshipList();
        for (Map.Entry<IFamilyMember, IRelationship> entry : wife_relations.entrySet()) {
            if (entry.getValue().getRelationType().equals(Constants.HUSBAND) && FAMILY_MEMBER_FOUND == false
                    && entry.getKey().getMemberName().equals(familyMemberToFind.getMemberName())) {
                FAMILY_MEMBER_FOUND = true;
                hasHusband = true;
                break;
            }
        }
        return hasHusband;
    }


    /**
     * THIS API is used to get number of sister wife has in family tree.
     * @param familyMember
     * @param member
     * @return A list which has siblings sister.
     */
    private LinkedList<IFamilyMember> getSisters(IFamilyMember familyMember, IFamilyMember member) {
        LinkedList<IFamilyMember> sisters = new LinkedList<>();
        for (Map.Entry<IFamilyMember, IRelationship> entry : familyMember.getRelatioshipList().entrySet()) {
            if (entry.getValue().getRelationType().equals(Constants.DAUGHTER)
                    && !entry.getKey().getMemberName().equals(member.getMemberName())) {
                sisters.add(entry.getKey());
            }
        }

        return sisters;
    }

    // ===========================================SECTION_V1_FOR_FIRST_LOGIC_END====================================================
    
    
    // ===========================================SECTION_V2_FOR_FIRST_LOGIC_START====================================================
    
    /**
     * This API used for getting the sister-in-law i.e getting the sibling's wife
     * 
     * @param familyMember
     * @param familyMemberToFind
     * @return A list which has total number of sister-in-law.
     */
    private LinkedList<IFamilyMember> getSisterInLaws_V2(IFamilyMember familyMember, IFamilyMember familyMemberToFind) {
        Map<IFamilyMember, IRelationship> children = familyMember.getRelatioshipList();
        for (Map.Entry<IFamilyMember, IRelationship> entry : children.entrySet()) {
            if (entry.getKey().getMemberName().equals(familyMemberToFind.getMemberName())
                    && FAMILY_MEMBER_FOUND == false) {
                FAMILY_MEMBER_FOUND = true;
                sister_in_laws = this.getBrothersWives(familyMember, familyMemberToFind);
            }
        }

        return sister_in_laws;
    }
    
    
    /**
     * //This API is used to get number of wives from siblings
     * @param familyMember
     * @param familyMemberToFind
     * @return A list of type which contains number of wives of the siblings  
     */
    private LinkedList<IFamilyMember> getBrothersWives(IFamilyMember familyMember, IFamilyMember familyMemberToFind) {
        LinkedList<IFamilyMember> wives = new LinkedList<>();
        Map<IFamilyMember, IRelationship> children = familyMember.getRelatioshipList();
        for (Map.Entry<IFamilyMember, IRelationship> entry : children.entrySet()) {
            if (!entry.getKey().getMemberName().equals(familyMemberToFind.getMemberName())
                    && this.hasWife(entry.getKey()) != null) {
                wives.add(this.hasWife(entry.getKey()));
            }
        }

        return wives;
    }

    /**
     * This API used to check whether the family member has a wife or not .
     * 
     * @param member
     * @return variable of type <code>IFamilyMember</code> pointing to the wife object if family member has a wife.
     */
    private IFamilyMember hasWife(IFamilyMember member) {
        IFamilyMember wife = null;
        Map<IFamilyMember, IRelationship> children = member.getRelatioshipList();
        for (Map.Entry<IFamilyMember, IRelationship> entry : children.entrySet()) {
            if (entry.getValue().getRelationType().equals(Constants.WIFE)) {
                wife = entry.getKey();
                break;
            }
        }

        return wife;
    }


    // ===========================================SECTION_V1_FOR_FIRST_LOGIC_END====================================================
    
}