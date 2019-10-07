package com.geektrust.family;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.geektrust.family.FamilyMemberInterface.IFamilyMember;
import com.geektrust.family.GraphInterface.IBuildable;
import com.geektrust.family.RelationshipInterface.Fetchable;
import com.geektrust.family.RelationshipInterface.IRelationship;
import com.geektrust.family.Utility.Constants;
import com.geektrust.family.Utility.DataManager;
import com.geektrust.family.Utility.IDataReader;
import com.geektrust.family.Utility.TxtFileDataReader;

/**
 * InternalGraphBuilder
 */
public class ExternalGraphBuilder implements IBuildable{

    private List<String> family_data_list = new ArrayList<>();
    private List<String> childAdditionList = new ArrayList<>();
    @Override
    public void build() {}

    @Override
    public void buildWithExternalFile(String filePath) {
        this.readCommandFromTxtFile(filePath);
        this.processData();
    }

    private List<String> readCommandFromTxtFile(String dataFile) {
        IDataReader build_family_reader = (TxtFileDataReader) DataManager.getInstance()
                .gDataReader(Constants.DATA_CLASS[Constants.DATA_CLASS_TYPE_TXT]);

        File build_file = new File(dataFile);
        build_family_reader.ReadData(build_file, family_data_list);

        return family_data_list;
    }

    private void processData() {
        LinkedList<IFamilyMember> output_relation = null;
        String output_child_addition = "";
        for (String data : family_data_list) {
            this.displayOutput(data, output_relation, output_child_addition);
        }
    }

    private void displayOutput(String data, LinkedList<IFamilyMember> output_relation, String output_child_addition){
        String[] splitted_data = data.split(" ");
        if (splitted_data[0].equals(Constants.COMMAND[Constants.COMMAND_TYPE_GET_RELATIONSHIP])) {
            this.showOutput(data, output_relation, output_child_addition, true);
        } else if (splitted_data[0].equals(Constants.COMMAND[Constants.COMMAND_TYPE_ADD_CHILD])) {
            this.showOutput(data, output_relation, output_child_addition, false);
        }
    }

    private void showOutput(String data, LinkedList<IFamilyMember> output_relation, String output_child_addition, Boolean isRelationOrChildAddition){
        if (isRelationOrChildAddition){
            output_relation = this.getRelationShip(data);
            this.displayOutputForRelationShip(output_relation);
        }else if(!isRelationOrChildAddition){
            output_child_addition = this.addExternalChild(data);
            this.displayOutputForChildAddition(output_child_addition);
        }
    }

    private void displayOutputForRelationShip(LinkedList<IFamilyMember> output_relation){
        if (output_relation.isEmpty()) {
            System.out.println(Constants.NONE);
        } else {
            for (int i = 0; i < output_relation.size(); i++) {
                System.out.print(output_relation.get(i).getMemberName() + " ");
            }
            System.out.println();
        }
    }

    private void displayOutputForChildAddition(String output_child_addition){
        childAdditionList.add(output_child_addition);
        System.out.println(output_child_addition);
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

    private String addExternalChild(String command_list) {
        String[] splitted_command_list = command_list.split(" ");
        this.addChild(splitted_command_list);

        if (Graph.getInstance().isChildAdded()) {
            Graph.getInstance().setChildAdditionValue(false);
            return Constants.CHILD_ADDITION_SUCCEEDED;
        }

        return Constants.CHILD_ADDITION_FAILED;
    }

    private LinkedList<IFamilyMember> getRelationShip(String command_list) {
        String[] splitted_command_list = command_list.split(" ");
        LinkedList<IFamilyMember> relationList = new LinkedList<>();

        IFamilyMember personToGetRelationship = Graph.getInstance().createNewFamilyMember(splitted_command_list[1]);
        for (int i = 0; i < Constants.EXTERNAL_RELATION_TYPE.length; i++) {
            if (Constants.EXTERNAL_RELATION_TYPE[i].equals(splitted_command_list[2])) {

                Fetchable fetcher = (Fetchable) Graph.getInstance().getRelationShip(Constants.FETCH_RELATON_TYPE[i]);

                relationList = fetcher.fetchPersonInRelation(
                        personToGetRelationship,
                        Graph.getInstance().getLatestFamilyTree(), 
                        new Relationship(Constants.EXTERNAL_RELATION_TYPE[i],
                                        Constants.EXTERNAL_RELATION_GENDER_TYPE[i]));
                
            }
        }
        return relationList;
    }

    
}