package com.geektrust.family;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.geektrust.family.FamilyMemberInterface.IFamilyMember;
import com.geektrust.family.RelationshipInterface.Fetchable;
import com.geektrust.family.RelationshipInterface.IRelationship;
import com.geektrust.family.Utility.Constants;
import com.geektrust.family.Utility.DataManager;
import com.geektrust.family.Utility.IDataReader;
import com.geektrust.family.Utility.TxtFileDataReader;

/**
 * GraphBuilder class which build the graph from the Graph instance and does the related operation neccessary for building
 * the graph.
 */
public class GraphBuilder{

    GraphBuilder()
    {

    }
    /**
     * This API add children either son or daughter to the graph by finding the person which exist in the graph.
     * @param command_list
     * @return nothing.
     */
    private void addChild(String[] command_list)
    {
        String personToSearch = command_list[1];
        String personToAdd = command_list[2];
        String personToAddGenderType = command_list[3];

        IRelationship relation = null;
        IFamilyMember existingFamilyMember = Graph.getInstance().createNewFamilyMember(personToSearch);
        IFamilyMember newFamilyMember = Graph.getInstance().createNewFamilyMember(personToAdd);
        if (personToAddGenderType.equals(Constants.MALE)) {
            relation = new Son();
        } else {
            relation = new Daughter();
        }

        Graph.getInstance().update_family_tree(existingFamilyMember, newFamilyMember, relation);
    }

    /**
     * This API add spouse either husband or wife to the graph by finding the
     * person which exist in the graph.
     * 
     * @param command_list
     * @return nothing .
     */
    private void addSpouse(String[] command_list)
    {
        String personToSearch = command_list[1];
        String personToAdd = command_list[2];
        String personToAddGenderType = command_list[3];

        IRelationship relation = null;
        IFamilyMember existingFamilyMember = Graph.getInstance().createNewFamilyMember(personToSearch);
        IFamilyMember newFamilyMember = Graph.getInstance().createNewFamilyMember(personToAdd);
        if (personToAddGenderType.equals(Constants.MALE)) {
            relation = new Husband();
        } else {
            relation = new Wife();
        }

        Graph.getInstance().update_family_tree(existingFamilyMember, newFamilyMember, relation);
    }

    /**
     * This APi builds the family tree from the given data from the file named as 'build.txt'
     * @param family_data_list
     * @return nothing .
     */
    public void build_family_tree(List<String> family_data_list)
    {
        for(String family_data : family_data_list)
        {
            String[] command_list = family_data.split(" ");

            
            if(command_list[0].equals(Constants.COMMAND[Constants.COMMAND_TYPE_ADD_CHILD]))
            {
                this.addChild(command_list);
            }
            else if(command_list[0].equals(Constants.COMMAND[Constants.COMMAND_TYPE_ADD_SPOUSE]))
            {
                this.addSpouse(command_list);
            }
        }
    }

    

    /**
     * This API handler manages reading data from the data-source such as txt file
     * stored in Resources/ dir.
     * 
     * @return A <code>List</code> of type <code>String</code> containing the instructions needed for building the initial 
     * family tree .
     */
    public List<String> createEmptyFamilyDataList()
    {
        IDataReader build_family_reader = (TxtFileDataReader) DataManager.getInstance()
                .gDataReader(Constants.DATA_CLASS[Constants.DATA_CLASS_TYPE_TXT]);

        File build_file = build_family_reader.getFileFromResources(Constants.BUILD_TREE_FILE);
        List<String> family_data_list = new ArrayList<>();
        build_family_reader.ReadData(build_file, family_data_list);

        return family_data_list;
    } 

    // =====================================EXTERNAL========================================================================

    public List<String> readCommandFromTxtFile(String dataFile) {
        IDataReader build_family_reader = (TxtFileDataReader) DataManager.getInstance()
                .gDataReader(Constants.DATA_CLASS[Constants.DATA_CLASS_TYPE_TXT]);

        File build_file = build_family_reader.getFileFromResources(dataFile);
        List<String> family_data_list = new ArrayList<>();
        build_family_reader.ReadData(build_file, family_data_list);

        return family_data_list;
    }

    public void processData(List<String> family_data_list){
        String output = "";
        for(String data : family_data_list){
            String[] splitted_data = data.split(" ");
            if(splitted_data[0].equals(Constants.COMMAND[Constants.COMMAND_TYPE_GET_RELATIONSHIP])){
                output = this.getRelationShip(data);
            }
            else if(splitted_data[0].equals(Constants.COMMAND[Constants.COMMAND_TYPE_ADD_CHILD])){
                output = this.addExternalChild(data);
            }
        }
    }


    public String addExternalChild(String command_list){
        String[] splitted_command_list = command_list.split(" ");
        this.addChild(splitted_command_list);
        
        if(Graph.getInstance().CHILD_ADDITION_SUCCEEDED){
            return Constants.CHILD_ADDITION_SUCCEEDED;
        }

        return Constants.CHILD_ADDITION_FAILED;
    }


    public String getRelationShip(String command_list) {
        String[] splitted_command_list = command_list.split(" ");
        LinkedList<IFamilyMember> relationList = new LinkedList<>();
        
        IFamilyMember personToGetRelationship = Graph.getInstance().createNewFamilyMember(splitted_command_list[1]);
        for(int i = 0; i < Constants.EXTERNAL_RELATION_TYPE.length; i++){
            if (Constants.EXTERNAL_RELATION_TYPE[i].equals(splitted_command_list[2])) {
                
                Fetchable fetcher = (Fetchable) Graph.getInstance().getRelationShip(Constants.FETCH_RELATON_TYPE[i]);
                
                relationList = fetcher.fetchPersonInRelation(personToGetRelationship,
                        Graph.getInstance().getLatestFamilyTree());
            }
        }
        
        //TODO : NEED TO ADD MORE TYPE OF RELATIONSHIP


        return relationList.toString();
    }

    public void build()
    {
        IFamilyMember family_root_female = Graph.getInstance().createNewFamilyMember(Constants.ANGA);
        Graph.getInstance().addToFamilyTree(family_root_female);
        
        List<String> family_data_list = this.createEmptyFamilyDataList();
        build_family_tree(family_data_list);
    }

    
}