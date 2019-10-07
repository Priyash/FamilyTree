package com.geektrust.family;

import org.junit.BeforeClass;
import org.junit.Test;


import static org.junit.Assert.*;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import com.geektrust.family.FamilyMemberInterface.IFamilyMember;
import com.geektrust.family.GraphInterface.IBuildable;
import com.geektrust.family.RelationshipInterface.Fetchable;
import com.geektrust.family.RelationshipInterface.IRelationship;
import com.geektrust.family.Utility.Constants;
import com.geektrust.family.Utility.IDataReader;
import com.geektrust.family.Utility.TxtFileDataReader;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;


/**
 * Unit test for simple App.
 */
public class AppTest {

    private static LinkedList<IFamilyMember> familyTree = new LinkedList<>();
    
    private static LinkedList<String> non_empty_sister_in_law_list = new LinkedList<>();

    private static LinkedList<String> non_empty_son_list = new LinkedList<>();
    private static LinkedList<String> non_empty_daughter_list = new LinkedList<>();

    private static LinkedList<String> non_empty_siblings_list = new LinkedList<>();

    private static LinkedList<String> non_empty_maternalAunt_list = new LinkedList<>();

    

    /**
     * Rigorous Test.
     */
    @BeforeClass
    public static void init()
    {
        IBuildable internalBuilder = new InternalGraphBuilder();
        internalBuilder.build();
        familyTree = Graph.getInstance().getLatestFamilyTree();

        non_empty_sister_in_law_list.add("Amba");
        non_empty_sister_in_law_list.add("Lika");
        non_empty_sister_in_law_list.add("Chitra");

        non_empty_daughter_list.add("Vila");
        non_empty_daughter_list.add("Chika");

        non_empty_siblings_list.add("Vyas");
        non_empty_siblings_list.add("Atya");

        non_empty_maternalAunt_list.add("Tritha");
    }

    @Test
    public void test_which_returns_relation_gender_for_male(){
        IRelationship relation = new Relationship(Constants.SON, Constants.MALE);
        assertThat(Constants.MALE, is(relation.getGender()));
    }
    

    @Test
    public void test_which_returns_relation_gender_type_for_female() {
        IRelationship relation = new Relationship(Constants.DAUGHTER, Constants.FEMALE);
        String expectedRelationGender = Constants.FEMALE;
        assertThat(expectedRelationGender, is(relation.getGender()));
    }

    @Test
    public void test_which_returns_relation_type_for_male() {
        IRelationship relation = new Relationship(Constants.SON, Constants.MALE);
        assertThat(Constants.SON, is(relation.getRelationType()));
    }

    @Test
    public void test_which_returns_relation_type_for_female() {
        IRelationship relation = new Relationship(Constants.DAUGHTER, Constants.FEMALE);
        assertThat(Constants.DAUGHTER, is(relation.getRelationType()));
    }
    
    

    @Test
    public void test_familyMember_add_relation_should_return_non_empty_list(){
        IFamilyMember root = new FamilyMember();
        
        IFamilyMember familyMemberDaughter = new FamilyMember();
        IRelationship daughter = new Relationship(Constants.DAUGHTER, Constants.FEMALE);
        root.addRelationship(familyMemberDaughter, daughter);

        IFamilyMember familyMemberSon = new FamilyMember();
        IRelationship son = new Relationship(Constants.SON, Constants.MALE);
        root.addRelationship(familyMemberSon, son);
        
        assertEquals(2, root.getRelatioshipList().size());
    }

    @Test
    public void test_setFamilyMember_Name_should_return_familyMemberName() {
        IFamilyMember familyMember = new FamilyMember();
        familyMember.setMemberName("Shan");
        assertEquals("Shan", familyMember.getMemberName());
    }

    @Test(expected = RuntimeException.class)
    public void test_setFamilyMember_Name_should_return_Empty_familyMemberName() {
        IFamilyMember familyMember = new FamilyMember();
        familyMember.setMemberName("");
    }

    @Test 
    public void test_forFileReader_Return_FileName(){
        IDataReader reader = new TxtFileDataReader();
        File file = reader.getFileFromResources(Constants.BUILD_TREE_FILE);
        assertEquals(new File("build.txt").getName(), file.getName());
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_forFileReader_Return_exception() {
        IDataReader reader = new TxtFileDataReader();
        File file = reader.getFileFromResources("abc.txt");
    }

    @Test
    public void test_forFileReader_return_non_empty_list() {
        IDataReader reader = new TxtFileDataReader();
        File file = reader.getFileFromResources(Constants.BUILD_TREE_FILE);
        LinkedList<String> dataList = new LinkedList<>();
        reader.ReadData(file, dataList);

        assertEquals(Constants.BUILD_FILE_SIZE, dataList.size());
    }


    @Test
    public void test_internal_graph_builder_returns_non_empty_graph_list(){
        LinkedList<IFamilyMember> familyList = Graph.getInstance().getLatestFamilyTree();
        //RETURNS A GRAPH STRUCTURE BUT ROOT SHOULD BE ANGA SO RETURN SIZE WOULD BE 1
        assertEquals(1, familyList.size());
    }

    @Test
    public void test_for_finding_sister_in_law_returns_empty_list() {
        Fetchable sisterInLawfetcher = (Fetchable) Graph.getInstance()
                .getRelationShip(Constants.FETCH_RELATON_TYPE[Constants.FETCH_RELATION_TYPE_SISTER_IN_LAW]);
        IFamilyMember person3 = Graph.getInstance().createNewFamilyMember("Vyan");
        LinkedList<IFamilyMember> sisterInLaws = sisterInLawfetcher.fetchPersonInRelation(person3, familyTree, 
                                                                    new Relationship(Constants.SISTER_IN_LAW, 
                                                                                        Constants.FEMALE));

        LinkedList<IFamilyMember> empty_sister_in_law_list = new LinkedList<>();
        assertThat(empty_sister_in_law_list, is(sisterInLaws));
    }

    @Test
    public void test_for_finding_sister_in_law_returns_non_empty_list() {
        Fetchable sisterInLawfetcher = (Fetchable) Graph.getInstance()
                .getRelationShip(Constants.FETCH_RELATON_TYPE[Constants.FETCH_RELATION_TYPE_SISTER_IN_LAW]);
        IFamilyMember person3 = Graph.getInstance().createNewFamilyMember("Ish");
        LinkedList<IFamilyMember> sisterInLaws = sisterInLawfetcher.fetchPersonInRelation(person3, familyTree, 
                                                                                new Relationship(Constants.SISTER_IN_LAW, 
                                                                                                Constants.FEMALE));

        assertThat(non_empty_sister_in_law_list.get(0), is(sisterInLaws.get(0).getMemberName()));
        assertThat(non_empty_sister_in_law_list.get(1), is(sisterInLaws.get(1).getMemberName()));
        assertThat(non_empty_sister_in_law_list.get(2), is(sisterInLaws.get(2).getMemberName()));
    }

    @Test
    public void test_for_finding_sons_returns_non_empty_list() {
        Fetchable fetcher = (Fetchable) Graph.getInstance()
                .getRelationShip(Constants.FETCH_RELATON_TYPE[Constants.FETCH_RELATION_TYPE_SON]);
        IFamilyMember person1 = Graph.getInstance().createNewFamilyMember("Vyan");
        LinkedList<IFamilyMember> sons = fetcher.fetchPersonInRelation(person1, familyTree, 
                new Relationship(Constants.SON, Constants.MALE));


        non_empty_son_list.add("Asva");
        non_empty_son_list.add("Vyas");
        assertThat(non_empty_son_list.get(0), is(sons.get(0).getMemberName()));
        assertThat(non_empty_son_list.get(1), is(sons.get(1).getMemberName()));

    }

    @Test
    public void test_for_finding_daughters_returns_non_empty_list() {
        Fetchable daughterFetcher = (Fetchable) Graph.getInstance()
                .getRelationShip(Constants.FETCH_RELATON_TYPE[Constants.FETCH_RELATION_TYPE_DAUGHTER]);
        IFamilyMember person2 = Graph.getInstance().createNewFamilyMember("Vich");
        LinkedList<IFamilyMember> daughters = daughterFetcher.fetchPersonInRelation(person2, familyTree, 
                new Relationship(Constants.DAUGHTER, Constants.FEMALE));

        assertThat(non_empty_daughter_list.get(0), is(daughters.get(0).getMemberName()));
        assertThat(non_empty_daughter_list.get(1), is(daughters.get(1).getMemberName()));
    }

    @Test
    public void test_for_finding_daughters_returns_empty_list() {
        Fetchable dughterfetcher = (Fetchable) Graph.getInstance()
                .getRelationShip(Constants.FETCH_RELATON_TYPE[Constants.FETCH_RELATION_TYPE_DAUGHTER]);
        IFamilyMember person2 = Graph.getInstance().createNewFamilyMember("Krithi");
        LinkedList<IFamilyMember> daughters = dughterfetcher.fetchPersonInRelation(person2, familyTree, 
                new Relationship(Constants.DAUGHTER, Constants.FEMALE));
        
        LinkedList<IFamilyMember> empty_daughter_list = new LinkedList<>();
        assertThat(empty_daughter_list, is(daughters));
    }

    @Test
    public void test_for_finding_siblings_return_non_empty_list() {
        Fetchable siblingsFetcher = (Fetchable) Graph.getInstance()
                .getRelationShip(Constants.FETCH_RELATON_TYPE[Constants.FETCH_RELATION_TYPE_FETCH_SIBLINGS]);
        IFamilyMember person = Graph.getInstance().createNewFamilyMember("Asva");
        LinkedList<IFamilyMember> siblings = siblingsFetcher.fetchPersonInRelation(person, familyTree, 
                new Relationship(Constants.SIBLING, Constants.FEMALE));

        assertThat(non_empty_siblings_list.get(0), is(siblings.get(0).getMemberName()));
        assertThat(non_empty_siblings_list.get(1), is(siblings.get(1).getMemberName()));
    }

    @Test
    public void test_for_finding_maternal_aunt_return_non_empty_list() {
        Fetchable maternalAuntFetcher = (Fetchable) Graph.getInstance()
                .getRelationShip(Constants.FETCH_RELATON_TYPE[Constants.FETCH_RELATION_TYPE_FETCH_MATERNAL_AUNT]);
        IFamilyMember person = Graph.getInstance().createNewFamilyMember("Yodhan");
        LinkedList<IFamilyMember> maternalAuntList = maternalAuntFetcher.fetchPersonInRelation(person, familyTree, 
                                                                    new Relationship(Constants.MATERNAL_AUNT, 
                                                                                    Constants.FEMALE));
        assertThat(non_empty_maternalAunt_list.get(0), is(maternalAuntList.get(0).getMemberName()));
    }

    @Test
    public void test_for_finding_maternal_aunt_return_empty_list() {
        Fetchable maternalAuntFetcher = (Fetchable) Graph.getInstance()
                .getRelationShip(Constants.FETCH_RELATON_TYPE[Constants.FETCH_RELATION_TYPE_FETCH_MATERNAL_AUNT]);
        IFamilyMember person = Graph.getInstance().createNewFamilyMember("Vasa");

        LinkedList<IFamilyMember> maternalAuntList = maternalAuntFetcher.fetchPersonInRelation(person, familyTree, 
                new Relationship(Constants.MATERNAL_AUNT, Constants.FEMALE));
        LinkedList<IFamilyMember> empty_maternal_aunt_list = new LinkedList<>();

        assertThat(empty_maternal_aunt_list, is(maternalAuntList));
    }

    @Test
    public void test_for_finding_maternal_uncle_return_non_empty_list() {
        Fetchable maternalUncleFetcher = (Fetchable) Graph.getInstance()
                .getRelationShip(Constants.FETCH_RELATON_TYPE[Constants.FETCH_RELATION_TYPE_FETCH_MATERNAL_UNCLE]);
        IFamilyMember person = Graph.getInstance().createNewFamilyMember("Yodhan");

        LinkedList<IFamilyMember> maternalUncleList = maternalUncleFetcher.fetchPersonInRelation(person, familyTree, 
                                                            new Relationship(Constants.MATERNAL_UNCLE, Constants.MALE));
        LinkedList<String> non_empty_maternal_uncle_list = new LinkedList<>();
        non_empty_maternal_uncle_list.add("Vritha");

        assertThat(non_empty_maternal_uncle_list.get(0), is(maternalUncleList.get(0).getMemberName()));
    }

    @Test
    public void test_for_finding_maternal_uncle_return_non_empty_list_2() {
        Fetchable maternalUncleFetcher = (Fetchable) Graph.getInstance()
                .getRelationShip(Constants.FETCH_RELATON_TYPE[Constants.FETCH_RELATION_TYPE_FETCH_MATERNAL_UNCLE]);
        IFamilyMember person = Graph.getInstance().createNewFamilyMember("Lavnya");

        LinkedList<IFamilyMember> maternalUncleList = maternalUncleFetcher.fetchPersonInRelation(person, familyTree, 
                                                                    new Relationship(Constants.MATERNAL_UNCLE, 
                                                                                    Constants.MALE));
        LinkedList<String> non_empty_maternal_uncle_list = new LinkedList<>();
        non_empty_maternal_uncle_list.add("Ahit");

        assertThat(non_empty_maternal_uncle_list.get(0), is(maternalUncleList.get(0).getMemberName()));
    }

    @Test
    public void test_for_finding_maternal_uncle_return_empty_list() {
        Fetchable maternalUncleFetcher = (Fetchable) Graph.getInstance()
                .getRelationShip(Constants.FETCH_RELATON_TYPE[Constants.FETCH_RELATION_TYPE_FETCH_MATERNAL_UNCLE]);
        IFamilyMember person = Graph.getInstance().createNewFamilyMember("Vasa");

        LinkedList<IFamilyMember> maternalUncleList = maternalUncleFetcher.fetchPersonInRelation(person, familyTree, 
                new Relationship(Constants.MATERNAL_UNCLE, Constants.MALE));
        LinkedList<String> empty_maternal_uncle_list = new LinkedList<>();

        assertThat(empty_maternal_uncle_list, is(maternalUncleList));
    }

    @Test
    public void test_for_finding_maternal_uncle_return_non_empty_list_3() {
        Fetchable maternalUncleFetcher = (Fetchable) Graph.getInstance()
                .getRelationShip(Constants.FETCH_RELATON_TYPE[Constants.FETCH_RELATION_TYPE_FETCH_MATERNAL_UNCLE]);
        IFamilyMember person = Graph.getInstance().createNewFamilyMember("Asva");

        LinkedList<IFamilyMember> maternalUncleList = maternalUncleFetcher.fetchPersonInRelation(person, familyTree, 
                                                    new Relationship(Constants.MATERNAL_UNCLE, Constants.MALE));
        LinkedList<String> non_empty_maternal_uncle_list = new LinkedList<>();
        non_empty_maternal_uncle_list.add("Chit");
        non_empty_maternal_uncle_list.add("Ish");
        non_empty_maternal_uncle_list.add("Vich");
        non_empty_maternal_uncle_list.add("Aras");

        assertThat(non_empty_maternal_uncle_list.get(0), is(maternalUncleList.get(0).getMemberName()));
        assertThat(non_empty_maternal_uncle_list.get(1), is(maternalUncleList.get(1).getMemberName()));
        assertThat(non_empty_maternal_uncle_list.get(2), is(maternalUncleList.get(2).getMemberName()));
    }

    @Test
    public void test_for_finding_paternal_uncle_return_non_empty_list() {
        Fetchable paternalUncleFetcher = (Fetchable) Graph.getInstance()
                .getRelationShip(Constants.FETCH_RELATON_TYPE[Constants.FETCH_RELATION_TYPE_FETCH_PATERNAL_UNCLE]);
        IFamilyMember person = Graph.getInstance().createNewFamilyMember("Vasa");

        LinkedList<IFamilyMember> paternalUncleList = paternalUncleFetcher.fetchPersonInRelation(person, familyTree, 
                                                                                            new Relationship(Constants.PATERNAL_UNCLE, 
                                                                                                            Constants.MALE));
        LinkedList<String> non_empty_paternal_uncle_list = new LinkedList<>();
        non_empty_paternal_uncle_list.add("Vyas");

        assertThat(non_empty_paternal_uncle_list.get(0), is(paternalUncleList.get(0).getMemberName()));
    }

    @Test
    public void test_for_finding_paternal_uncle_return_non_empty_list_2() {
        Fetchable paternalUncleFetcher = (Fetchable) Graph.getInstance()
                .getRelationShip(Constants.FETCH_RELATON_TYPE[Constants.FETCH_RELATION_TYPE_FETCH_PATERNAL_UNCLE]);
        IFamilyMember person = Graph.getInstance().createNewFamilyMember("Kriya");

        LinkedList<IFamilyMember> paternalUncleList = paternalUncleFetcher.fetchPersonInRelation(person, familyTree, 
                new Relationship(Constants.PATERNAL_UNCLE, Constants.MALE));
        LinkedList<String> non_empty_paternal_uncle_list = new LinkedList<>();
        non_empty_paternal_uncle_list.add("Asva");

        assertThat(non_empty_paternal_uncle_list.get(0), is(paternalUncleList.get(0).getMemberName()));
    }

    @Test
    public void test_for_finding_paternal_uncle_return_non_empty_list_3() {
        Fetchable paternalUncleFetcher = (Fetchable) Graph.getInstance()
                .getRelationShip(Constants.FETCH_RELATON_TYPE[Constants.FETCH_RELATION_TYPE_FETCH_PATERNAL_UNCLE]);
        IFamilyMember person = Graph.getInstance().createNewFamilyMember("Vila");

        LinkedList<IFamilyMember> paternalUncleList = paternalUncleFetcher.fetchPersonInRelation(person, familyTree, 
                new Relationship(Constants.PATERNAL_UNCLE, Constants.MALE));
        LinkedList<String> non_empty_paternal_uncle_list = new LinkedList<>();
        non_empty_paternal_uncle_list.add("Chit");
        non_empty_paternal_uncle_list.add("Ish");
        non_empty_paternal_uncle_list.add("Aras");

        assertThat(non_empty_paternal_uncle_list.get(0), is(paternalUncleList.get(0).getMemberName()));
        assertThat(non_empty_paternal_uncle_list.get(1), is(paternalUncleList.get(1).getMemberName()));
        assertThat(non_empty_paternal_uncle_list.get(2), is(paternalUncleList.get(2).getMemberName()));
    }

    @Test
    public void test_for_finding_paternal_uncle_return_non_empty_list_4() {
        Fetchable paternalUncleFetcher = (Fetchable) Graph.getInstance()
                .getRelationShip(Constants.FETCH_RELATON_TYPE[Constants.FETCH_RELATION_TYPE_FETCH_PATERNAL_UNCLE]);
        IFamilyMember person = Graph.getInstance().createNewFamilyMember("Jnki");

        LinkedList<IFamilyMember> paternalUncleList = paternalUncleFetcher.fetchPersonInRelation(person, familyTree, 
                new Relationship(Constants.PATERNAL_UNCLE, Constants.MALE));
        LinkedList<String> non_empty_paternal_uncle_list = new LinkedList<>();
        non_empty_paternal_uncle_list.add("Chit");
        non_empty_paternal_uncle_list.add("Ish");
        non_empty_paternal_uncle_list.add("Vich");

        assertThat(non_empty_paternal_uncle_list.get(0), is(paternalUncleList.get(0).getMemberName()));
        assertThat(non_empty_paternal_uncle_list.get(1), is(paternalUncleList.get(1).getMemberName()));
        assertThat(non_empty_paternal_uncle_list.get(2), is(paternalUncleList.get(2).getMemberName()));
    }

    @Test
    public void test_for_finding_paternal_aunt_return_non_empty_list() {
        Fetchable paternalAuntFetcher = (Fetchable) Graph.getInstance()
                .getRelationShip(Constants.FETCH_RELATON_TYPE[Constants.FETCH_RELATION_TYPE_FETCH_PATERNAL_AUNT]);
        IFamilyMember person = Graph.getInstance().createNewFamilyMember("Jnki");

        LinkedList<IFamilyMember> paternalAuntList = paternalAuntFetcher.fetchPersonInRelation(person, familyTree, 
                new Relationship(Constants.PATERNAL_AUNT, Constants.FEMALE));
        LinkedList<String> non_empty_paternal_aunt_list = new LinkedList<>();
        non_empty_paternal_aunt_list.add("Satya");

        assertThat(non_empty_paternal_aunt_list.get(0), is(paternalAuntList.get(0).getMemberName()));
    }

    @Test
    public void test_for_finding_paternal_aunt_return_empty_list() {
        Fetchable paternalAuntFetcher = (Fetchable) Graph.getInstance()
                .getRelationShip(Constants.FETCH_RELATON_TYPE[Constants.FETCH_RELATION_TYPE_FETCH_PATERNAL_AUNT]);
        IFamilyMember person = Graph.getInstance().createNewFamilyMember("Yodhan");

        LinkedList<IFamilyMember> paternalAuntList = paternalAuntFetcher.fetchPersonInRelation(person, familyTree, 
                                                                                new Relationship(Constants.PATERNAL_AUNT, 
                                                                                                Constants.FEMALE));
        LinkedList<String> empty_paternal_aunt_list = new LinkedList<>();

        assertThat(empty_paternal_aunt_list, is(paternalAuntList));
    }
}
