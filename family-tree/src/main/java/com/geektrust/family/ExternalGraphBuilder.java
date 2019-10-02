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

    

    @Override
    public void build() {
    }


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
            String[] splitted_data = data.split(" ");
            if (splitted_data[0].equals(Constants.COMMAND[Constants.COMMAND_TYPE_GET_RELATIONSHIP])) {
                output_relation = this.getRelationShip(data);
                if(output_relation.isEmpty()){
                    System.out.println(Constants.NONE);
                }else{
                    for (int i = 0; i < output_relation.size(); i++) {
                        System.out.print(output_relation.get(i).getMemberName() + " ");
                    }
                    System.out.println();
                }
            } else if (splitted_data[0].equals(Constants.COMMAND[Constants.COMMAND_TYPE_ADD_CHILD])) {
                output_child_addition = this.addExternalChild(data);
                System.out.println(output_child_addition);
            }
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
            relation = new Son();
        } else {
            relation = new Daughter();
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

                relationList = fetcher.fetchPersonInRelation(personToGetRelationship,
                        Graph.getInstance().getLatestFamilyTree());
                
            }
        }

        return relationList;
    }

    

}