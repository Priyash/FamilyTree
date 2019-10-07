
package com.geektrust.family;

import java.util.LinkedList;
import java.util.Map;

import com.geektrust.family.FamilyMemberInterface.IFamilyMember;
import com.geektrust.family.RelationshipInterface.IRelationship;
import com.geektrust.family.Utility.Constants;

/**
 * Graph class which constructs the family tree 
 */
public class Graph {

    private LinkedList<IFamilyMember> familyTree;
    private static Graph graph = null;
    public Boolean CHILD_ADDITION_SUCCEEDED = false;


    private Graph() {
        familyTree = new LinkedList<>();
    }

    public static Graph getInstance() {
        if (graph == null) {
            graph = new Graph();
        }

        return graph;
    }

    public Boolean isChildAdded(){
        return CHILD_ADDITION_SUCCEEDED;
    }

    public void setChildAdditionValue(Boolean ChildAdded){
        this.CHILD_ADDITION_SUCCEEDED = ChildAdded;
    }

    public IFamilyMember createNewFamilyMember(String memberName) {
        IFamilyMember newMember = new FamilyMember();
        newMember.setMemberName(memberName);
        return newMember;
    }

    public void addToFamilyTree(IFamilyMember src_family_mMember) {
        if (src_family_mMember != null) {
            familyTree.add(src_family_mMember);
        }
    }

    public LinkedList<IFamilyMember> getLatestFamilyTree() {
        return familyTree;
    }

    public void setLatestFamilyTree(LinkedList<IFamilyMember> familyTree){
        this.familyTree = familyTree;
    }

    public void update_family_tree(IFamilyMember existingFamilyPerson, IFamilyMember newFamilyPerson,IRelationship relation) {
        for (IFamilyMember family_person : familyTree) {
            find_and_add(family_person, existingFamilyPerson, newFamilyPerson, relation);
        }
    }

    /**
     * This API used to add child through mother in the graph , this API will check whether the given person is married and female or not then
     * then it will add child through that person otherwise not .
     * @param root
     * @param existingFamilyPerson
     * @param newFamilyPerson
     * @param relation
     * @return Nothing
     */
    public void find_and_add(IFamilyMember root, IFamilyMember existingFamilyPerson, IFamilyMember newFamilyPerson, IRelationship relation) {
        if (root.getMemberName().equals(existingFamilyPerson.getMemberName())) {
            root.addRelationship(newFamilyPerson, relation);
        } else {
            Map<IFamilyMember, IRelationship> fam_list = root.getRelatioshipList();
            if (fam_list != null || !fam_list.isEmpty()) {
                for (Map.Entry<IFamilyMember, IRelationship> entry : fam_list.entrySet()) {
                    if (entry.getKey().getMemberName().equals(existingFamilyPerson.getMemberName()) && this.hasMarried(entry.getKey())) {
                        entry.getKey().addRelationship(newFamilyPerson, relation);
                        CHILD_ADDITION_SUCCEEDED = true;
                        break;
                    } else {
                        find_and_add(entry.getKey(), existingFamilyPerson, newFamilyPerson, relation);
                    }
                }
            }
        }
    }

    /**
     * 
     * @param fetcher
     * @return an object which needs to be casted with corresponding Fetachable class
     */
    public Object getRelationShip(String fetcher) {

        Object object = null;
        for (String fetcher_type : Constants.FETCH_RELATON_TYPE) {
            if (fetcher.equals(fetcher_type)) {
                try {
                    object = Class.forName(fetcher_type).newInstance();
                } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }

        return object;
    }


    private Boolean hasMarried(IFamilyMember member) {
        Boolean hasHusband = false;
        Map<IFamilyMember, IRelationship> fam_list = member.getRelatioshipList();
        for (Map.Entry<IFamilyMember, IRelationship> entry : fam_list.entrySet()) {
            if (entry.getValue().getRelationType().equals(Constants.HUSBAND)) {
                hasHusband = true;
            }
        }

        return hasHusband;
    }

}