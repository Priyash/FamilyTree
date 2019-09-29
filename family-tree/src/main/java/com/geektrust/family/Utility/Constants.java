

package com.geektrust.family.Utility;

/**
 * Constants
 */
public class Constants {

    public static final String ANGA = "Anga";
    public static final String MALE = "Male";
    public static final String FEMALE = "Female";
    public static final String HUSBAND = "Husband";
    public static final String WIFE = "Wife";
    public static final String SIBLING = "Siblings";

    public static final String FATHER = "Father";
    public static final String MOTHER = "Mother";

    public static final String SON = "Son";
    public static final String DAUGHTER = "Daughter";

    public static final String MALE_CHILD = "Male_Child";
    public static final String FEMALE_CHILD = "Female_Child";

    public static final int GRAPH_SIZE = 25;
    public static final int DATA_CLASS_TYPE_TXT = 0;
    public static final String[] DATA_CLASS = new String[]{"com.geektrust.family.Utility.TxtFileDataReader"};
    public static final String BUILD_TREE_FILE = "build.txt";
    public static final String EXTERNAL_BUILD_TREE_FILE = "data.txt";

    public static final int COMMAND_TYPE_ADD_SPOUSE = 0;
    public static final int COMMAND_TYPE_ADD_CHILD = 1;
    public static final int COMMAND_TYPE_GET_RELATIONSHIP = 2;
    public static final String[] COMMAND = new String[]{"ADD_SPOUSE","ADD_CHILD", "GET_RELATIONSHIP"};

    public static final int FETCH_RELATION_TYPE_SON = 0;
    public static final int FETCH_RELATION_TYPE_DAUGHTER = 1;
    public static final int FETCH_RELATION_TYPE_BROTHER_IN_LAW = 2;
    public static final int FETCH_RELATION_TYPE_SISTER_IN_LAW = 3;
    public static final int FETCH_RELATION_TYPE_FETCH_SIBLINGS = 4;
    public static final int FETCH_RELATION_TYPE_FETCH_MATERNAL_AUNT = 5;
    public static final int FETCH_RELATION_TYPE_FETCH_MATERNAL_UNCLE = 6;
    public static final int FETCH_RELATION_TYPE_FETCH_PATERNAL_UNCLE = 7;
    public static final int FETCH_RELATION_TYPE_FETCH_PATERNAL_AUNT = 8;
    public static final String[] FETCH_RELATON_TYPE = new String[]{"com.geektrust.family.FetchSons", "com.geektrust.family.FetchDaughters", "com.geektrust.family.FetchBrotherInLaw", 
                                                                "com.geektrust.family.FetchSisterInLaw", "com.geektrust.family.FetchSiblings",
                                                                "com.geektrust.family.FetchMaternalAunt", "com.geektrust.family.FetchMaternalUncle",
                                                                "com.geektrust.family.FetchPaternalUncle", "com.geektrust.family.FetchPaternalAunt"};


    public static final String CHILD_ADDITION_FAILED = "CHILD_ADDITION_FAILED";
    public static final String CHILD_ADDITION_SUCCEEDED = "CHILD_ADDITION_SUCCEEDED";


    public static final String RELATION_MATERNAL_AUNT = "Maternal-Aunt";
    Constants()
    {
        throw new RuntimeException("Constant class cannot be instantiated");
    }
}