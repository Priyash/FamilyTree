package com.geektrust.family;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.geektrust.family.FamilyMemberInterface.IFamilyMember;
import com.geektrust.family.GraphInterface.IBuildable;
import com.geektrust.family.RelationshipInterface.IRelationship;
import com.geektrust.family.Utility.Constants;
import com.geektrust.family.Utility.DataManager;
import com.geektrust.family.Utility.IDataReader;
import com.geektrust.family.Utility.TxtFileDataReader;

/**
 * InternalGraphBuilder
 */
public class InternalGraphBuilder implements IBuildable {

    private List<String> family_data_list = new ArrayList<>();

    public InternalGraphBuilder (){

    }


    @Override
    public void build() {
        IFamilyMember family_root_female = Graph.getInstance().createNewFamilyMember(Constants.ANGA);
        Graph.getInstance().addToFamilyTree(family_root_female);
        List<String> family_data_list = this.createFamilyDataList();
        this.buildFamilyTree(family_data_list);
    }

    @Override
    public void buildWithExternalFile(String filePath) {}


    public void buildFamilyTree(List<String> family_data_list) {

        family_data_list = this.createFamilyDataList();
        for (String family_data : family_data_list) {
            String[] command_list = family_data.split(" ");
            this.addFamilyMember(command_list);
        }

    }

    private void addFamilyMember(String[] commandList){
        if (commandList[0].equals(Constants.COMMAND[Constants.COMMAND_TYPE_ADD_CHILD])) {
            this.addChild(commandList);
        } else if (commandList[0].equals(Constants.COMMAND[Constants.COMMAND_TYPE_ADD_SPOUSE])) {
            this.addSpouse(commandList);
        }
    }

    
    /**
     * This API add children either son or daughter to the graph by finding the
     * person which exist in the graph.
     * 
     * @param command_list
     * @return nothing.
     */
    private void addChild(String[] command_list) {
        String personToSearch = command_list[1];
        String personToAdd = command_list[2];
        String personToAddGenderType = command_list[3];

        IRelationship relation = null;
        IFamilyMember existingFamilyMember = Graph.getInstance().createNewFamilyMember(personToSearch);
        IFamilyMember newFamilyMember = Graph.getInstance().createNewFamilyMember(personToAdd);
        if (personToAddGenderType.equals(Constants.MALE)) {
            relation = new Relationship(Constants.SON, Constants.MALE);
        } else {
            relation = new Relationship(Constants.DAUGHTER, Constants.FEMALE);
        }

        Graph.getInstance().update_family_tree(existingFamilyMember, newFamilyMember, relation);
    }


    /**
     * This API add spouse either husband or wife to the graph by finding the person
     * which exist in the graph.
     * 
     * @param command_list
     * @return nothing .
     */
    private void addSpouse(String[] command_list) {
        String personToSearch = command_list[1];
        String personToAdd = command_list[2];
        String personToAddGenderType = command_list[3];

        IRelationship relation = null;
        IFamilyMember existingFamilyMember = Graph.getInstance().createNewFamilyMember(personToSearch);
        IFamilyMember newFamilyMember = Graph.getInstance().createNewFamilyMember(personToAdd);
        if (personToAddGenderType.equals(Constants.MALE)) {
            relation = new Relationship(Constants.HUSBAND, Constants.MALE);
        } else {
            relation = new Relationship(Constants.WIFE, Constants.FEMALE);
        }

        Graph.getInstance().update_family_tree(existingFamilyMember, newFamilyMember, relation);
    }


    private List<String> createFamilyDataList() {
        IDataReader build_family_reader = (TxtFileDataReader) DataManager.getInstance().gDataReader(Constants.DATA_CLASS[Constants.DATA_CLASS_TYPE_TXT]);

        File build_file = build_family_reader.getFileFromResources(Constants.BUILD_TREE_FILE);
        List<String> family_data_list = new ArrayList<>();
        build_family_reader.ReadData(build_file, family_data_list);

        return family_data_list;
    }

    
}