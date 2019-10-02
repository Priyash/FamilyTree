package com.geektrust.family;

import org.hamcrest.CoreMatchers;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

import java.util.LinkedList;
import java.util.List;

import com.geektrust.family.FamilyMemberInterface.IFamilyMember;
import com.geektrust.family.GraphInterface.IBuildable;
import com.geektrust.family.RelationshipInterface.Fetchable;
import com.geektrust.family.Utility.Constants;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
/**
 * Unit test for simple App.
 */
public class AppTest {

    private static LinkedList<IFamilyMember> familyTree = new LinkedList<>();
    private static LinkedList<IFamilyMember> empty_sister_in_law_list = new LinkedList<>();
    private static LinkedList<IFamilyMember> empty_daughter_list = new LinkedList<>();
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

        IFamilyMember family_root_female = Graph.getInstance().createNewFamilyMember("Anga");
        Graph.getInstance().addToFamilyTree(family_root_female);

        familyTree = Graph.getInstance().getLatestFamilyTree();

        non_empty_sister_in_law_list.add("Amba");
        non_empty_sister_in_law_list.add("Lika");
        non_empty_sister_in_law_list.add("Chitra");

        non_empty_son_list.add("Asva");
        non_empty_son_list.add("Vyas");

        non_empty_daughter_list.add("Vila");
        non_empty_daughter_list.add("Chika");

        non_empty_siblings_list.add("Vyas");
        non_empty_siblings_list.add("Atya");

        non_empty_maternalAunt_list.add("Tritha");
    }

    @Test
    public void test_for_finding_sister_in_law_returns_empty_list()
    {
        Fetchable sisterInLawfetcher = (Fetchable) Graph.getInstance()
        .getRelationShip(Constants.FETCH_RELATON_TYPE[Constants.FETCH_RELATION_TYPE_SISTER_IN_LAW]);
        IFamilyMember person3 = Graph.getInstance().createNewFamilyMember("Vyan");
        LinkedList<IFamilyMember> sisterInLaws = sisterInLawfetcher.fetchPersonInRelation(person3, familyTree);

        assertThat(empty_sister_in_law_list, is(sisterInLaws));
    }

    @Test
    public void test_for_finding_sister_in_law_returns_non_empty_list() {
        Fetchable sisterInLawfetcher = (Fetchable) Graph.getInstance()
                .getRelationShip(Constants.FETCH_RELATON_TYPE[Constants.FETCH_RELATION_TYPE_SISTER_IN_LAW]);
        IFamilyMember person3 = Graph.getInstance().createNewFamilyMember("Ish");
        LinkedList<IFamilyMember> sisterInLaws = sisterInLawfetcher.fetchPersonInRelation(person3, familyTree);

        
        assertThat(non_empty_sister_in_law_list.get(0), is(sisterInLaws.get(0).getMemberName()));
        assertThat(non_empty_sister_in_law_list.get(1), is(sisterInLaws.get(1).getMemberName()));
        assertThat(non_empty_sister_in_law_list.get(2), is(sisterInLaws.get(2).getMemberName()));
    }

    @Test
    public void test_for_finding_sons_returns_non_empty_list()
    {
        Fetchable fetcher = (Fetchable)
        Graph.getInstance().getRelationShip(Constants.FETCH_RELATON_TYPE[Constants.FETCH_RELATION_TYPE_SON]);
        IFamilyMember person1 = Graph.getInstance().createNewFamilyMember("Vyan");
        LinkedList<IFamilyMember> sons = fetcher.fetchPersonInRelation(person1,
        familyTree);

        assertThat(non_empty_son_list.get(0), is(sons.get(0).getMemberName()));
        assertThat(non_empty_son_list.get(1), is(sons.get(1).getMemberName()));

    }

    @Test
    public void test_for_finding_daughters_returns_non_empty_list() {
        Fetchable daughterFetcher = (Fetchable)
        Graph.getInstance().getRelationShip(Constants.FETCH_RELATON_TYPE[Constants.FETCH_RELATION_TYPE_DAUGHTER]);
        IFamilyMember person2 = Graph.getInstance().createNewFamilyMember("Vich");
        LinkedList<IFamilyMember> daughters =
                daughterFetcher.fetchPersonInRelation(person2, familyTree);

        assertThat(non_empty_daughter_list.get(0), is(daughters.get(0).getMemberName()));
        assertThat(non_empty_daughter_list.get(1), is(daughters.get(1).getMemberName()));
    }

    @Test
    public void test_for_finding_daughters_returns_empty_list() {
        Fetchable dughterfetcher = (Fetchable) Graph.getInstance()
                .getRelationShip(Constants.FETCH_RELATON_TYPE[Constants.FETCH_RELATION_TYPE_DAUGHTER]);
        IFamilyMember person2 = Graph.getInstance().createNewFamilyMember("Krithi");
        LinkedList<IFamilyMember> daughters = dughterfetcher.fetchPersonInRelation(person2, familyTree);

        assertThat(empty_daughter_list, is(daughters));
    }


    @Test
    public void test_for_finding_siblings_return_non_empty_list() {
        Fetchable siblingsFetcher = (Fetchable) Graph.getInstance()
        .getRelationShip(Constants.FETCH_RELATON_TYPE[Constants.FETCH_RELATION_TYPE_FETCH_SIBLINGS]);
        IFamilyMember person = Graph.getInstance().createNewFamilyMember("Asva");
        LinkedList<IFamilyMember> siblings = siblingsFetcher.fetchPersonInRelation(person, familyTree);
        
        assertThat(non_empty_siblings_list.get(0), is(siblings.get(0).getMemberName()));
        assertThat(non_empty_siblings_list.get(1), is(siblings.get(1).getMemberName()));
    }

    @Test
    public void test_for_finding_maternal_aunt_return_non_empty_list() {
        Fetchable maternalAuntFetcher = (Fetchable) Graph.getInstance()
                .getRelationShip(Constants.FETCH_RELATON_TYPE[Constants.FETCH_RELATION_TYPE_FETCH_MATERNAL_AUNT]);
        IFamilyMember person = Graph.getInstance().createNewFamilyMember("Yodhan");
        LinkedList<IFamilyMember> maternalAuntList = maternalAuntFetcher.fetchPersonInRelation(person, familyTree);
        assertThat(non_empty_maternalAunt_list.get(0), is(maternalAuntList.get(0).getMemberName()));
    }

    @Test
    public void test_for_finding_maternal_aunt_return_empty_list() {
        Fetchable maternalAuntFetcher = (Fetchable) Graph.getInstance()
                .getRelationShip(Constants.FETCH_RELATON_TYPE[Constants.FETCH_RELATION_TYPE_FETCH_MATERNAL_AUNT]);
        IFamilyMember person = Graph.getInstance().createNewFamilyMember("Vasa");
        
        LinkedList<IFamilyMember> maternalAuntList = maternalAuntFetcher.fetchPersonInRelation(person, familyTree);
        LinkedList<IFamilyMember> empty_maternal_aunt_list = new LinkedList<>();
        
        assertThat(empty_maternal_aunt_list, is(maternalAuntList));
    }


    @Test
    public void test_for_finding_maternal_uncle_return_non_empty_list() {
        Fetchable maternalUncleFetcher = (Fetchable) Graph.getInstance()
                .getRelationShip(Constants.FETCH_RELATON_TYPE[Constants.FETCH_RELATION_TYPE_FETCH_MATERNAL_UNCLE]);
        IFamilyMember person = Graph.getInstance().createNewFamilyMember("Yodhan");

        LinkedList<IFamilyMember> maternalUncleList = maternalUncleFetcher.fetchPersonInRelation(person, familyTree);
        LinkedList<String> non_empty_maternal_uncle_list = new LinkedList<>();
        non_empty_maternal_uncle_list.add("Vritha");

        assertThat(non_empty_maternal_uncle_list.get(0), is(maternalUncleList.get(0).getMemberName()));
    }

    @Test
    public void test_for_finding_maternal_uncle_return_non_empty_list_2() {
        Fetchable maternalUncleFetcher = (Fetchable) Graph.getInstance()
                .getRelationShip(Constants.FETCH_RELATON_TYPE[Constants.FETCH_RELATION_TYPE_FETCH_MATERNAL_UNCLE]);
        IFamilyMember person = Graph.getInstance().createNewFamilyMember("Lavnya");

        LinkedList<IFamilyMember> maternalUncleList = maternalUncleFetcher.fetchPersonInRelation(person, familyTree);
        LinkedList<String> non_empty_maternal_uncle_list = new LinkedList<>();
        non_empty_maternal_uncle_list.add("Ahit");

        assertThat(non_empty_maternal_uncle_list.get(0), is(maternalUncleList.get(0).getMemberName()));
    }


    @Test
    public void test_for_finding_maternal_uncle_return_empty_list() {
        Fetchable maternalUncleFetcher = (Fetchable) Graph.getInstance()
                .getRelationShip(Constants.FETCH_RELATON_TYPE[Constants.FETCH_RELATION_TYPE_FETCH_MATERNAL_UNCLE]);
        IFamilyMember person = Graph.getInstance().createNewFamilyMember("Vasa");

        LinkedList<IFamilyMember> maternalUncleList = maternalUncleFetcher.fetchPersonInRelation(person, familyTree);
        LinkedList<String> empty_maternal_uncle_list = new LinkedList<>();

        assertThat(empty_maternal_uncle_list, is(maternalUncleList));
    }

    @Test
    public void test_for_finding_maternal_uncle_return_non_empty_list_3() {
        Fetchable maternalUncleFetcher = (Fetchable) Graph.getInstance()
                .getRelationShip(Constants.FETCH_RELATON_TYPE[Constants.FETCH_RELATION_TYPE_FETCH_MATERNAL_UNCLE]);
        IFamilyMember person = Graph.getInstance().createNewFamilyMember("Asva");

        LinkedList<IFamilyMember> maternalUncleList = maternalUncleFetcher.fetchPersonInRelation(person, familyTree);
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

        LinkedList<IFamilyMember> paternalUncleList = paternalUncleFetcher.fetchPersonInRelation(person, familyTree);
        LinkedList<String> non_empty_paternal_uncle_list = new LinkedList<>();
        non_empty_paternal_uncle_list.add("Vyas");

        assertThat(non_empty_paternal_uncle_list.get(0), is(paternalUncleList.get(0).getMemberName()));
    }

    @Test
    public void test_for_finding_paternal_uncle_return_non_empty_list_2() {
        Fetchable paternalUncleFetcher = (Fetchable) Graph.getInstance()
                .getRelationShip(Constants.FETCH_RELATON_TYPE[Constants.FETCH_RELATION_TYPE_FETCH_PATERNAL_UNCLE]);
        IFamilyMember person = Graph.getInstance().createNewFamilyMember("Kriya");

        LinkedList<IFamilyMember> paternalUncleList = paternalUncleFetcher.fetchPersonInRelation(person, familyTree);
        LinkedList<String> non_empty_paternal_uncle_list = new LinkedList<>();
        non_empty_paternal_uncle_list.add("Asva");

        assertThat(non_empty_paternal_uncle_list.get(0), is(paternalUncleList.get(0).getMemberName()));
    }


    @Test
    public void test_for_finding_paternal_uncle_return_non_empty_list_3() {
        Fetchable paternalUncleFetcher = (Fetchable) Graph.getInstance()
                .getRelationShip(Constants.FETCH_RELATON_TYPE[Constants.FETCH_RELATION_TYPE_FETCH_PATERNAL_UNCLE]);
        IFamilyMember person = Graph.getInstance().createNewFamilyMember("Vila");

        LinkedList<IFamilyMember> paternalUncleList = paternalUncleFetcher.fetchPersonInRelation(person, familyTree);
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

        LinkedList<IFamilyMember> paternalUncleList = paternalUncleFetcher.fetchPersonInRelation(person, familyTree);
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

        LinkedList<IFamilyMember> paternalAuntList = paternalAuntFetcher.fetchPersonInRelation(person, familyTree);
        LinkedList<String> non_empty_paternal_aunt_list = new LinkedList<>();
        non_empty_paternal_aunt_list.add("Satya");

        assertThat(non_empty_paternal_aunt_list.get(0), is(paternalAuntList.get(0).getMemberName()));
    }

    @Test
    public void test_for_finding_paternal_aunt_return_empty_list() {
        Fetchable paternalAuntFetcher = (Fetchable) Graph.getInstance()
                .getRelationShip(Constants.FETCH_RELATON_TYPE[Constants.FETCH_RELATION_TYPE_FETCH_PATERNAL_AUNT]);
        IFamilyMember person = Graph.getInstance().createNewFamilyMember("Yodhan");

        LinkedList<IFamilyMember> paternalAuntList = paternalAuntFetcher.fetchPersonInRelation(person, familyTree);
        LinkedList<String> empty_paternal_aunt_list = new LinkedList<>();

        assertThat(empty_paternal_aunt_list, is(paternalAuntList));
    }
}
