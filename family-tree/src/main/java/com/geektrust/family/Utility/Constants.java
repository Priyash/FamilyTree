
package com.geektrust.family.Utility;

import java.util.HashMap;
import java.util.Map;

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
    public static final String MALE_SIBLING = "MaleSiblings";
    public static final String FEMALE_SIBLING = "FemaleSiblings";

    public static final String FATHER = "Father";
    public static final String MOTHER = "Mother";

    public static final String SON = "Son";
    public static final String DAUGHTER = "Daughter";

    public static final String MALE_CHILD = "Male_Child";
    public static final String FEMALE_CHILD = "Female_Child";

    public static final String BROTHER_IN_LAW = "Brother-In-Law";
    public static final String SISTER_IN_LAW = "Sister-In-Law";

    public static final String MATERNAL_UNCLE = "Maternal-Uncle";
    public static final String MATERNAL_AUNT = "Maternal-Aunt";

    public static final String PATERNAL_UNCLE = "Paternal-lUncle";
    public static final String PATERNAL_AUNT = "Paternal-Aunt";

    public static final int BUILD_FILE_SIZE = 30;
    public static final int BUILD_FAMILY_TREE_SIZE = 33;
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
    public static final String[] FETCH_RELATON_TYPE = new String[]{"com.geektrust.family.FetchSonsOrDaughters",
                                                                "com.geektrust.family.FetchSonsOrDaughters", 
                                                                "com.geektrust.family.FetchBrotherInLaw", 
                                                                "com.geektrust.family.FetchSisterInLaw", 
                                                                "com.geektrust.family.FetchSiblings",
                                                                "com.geektrust.family.FetchMaternalUncleOrAunt",
                                                                "com.geektrust.family.FetchMaternalUncleOrAunt", 
                                                                "com.geektrust.family.FetchPaternalUncleOrAunt",
                                                                "com.geektrust.family.FetchPaternalUncleOrAunt"};


    public static final String CHILD_ADDITION_FAILED = "CHILD_ADDITION_FAILED";
    public static final String CHILD_ADDITION_SUCCEEDED = "CHILD_ADDITION_SUCCEEDED";
    public static final String NONE = "NONE";


    public static final String[] EXTERNAL_RELATION_TYPE = new String[]{"Son","Daughter","Brother-In-Law","Sister-In-Law",
                                                                        "Siblings","Maternal-Aunt", "Maternal-Uncle", 
                                                                        "Paternal-Uncle", "Paternal-Aunt"};

    public static final String[] EXTERNAL_RELATION_GENDER_TYPE = new String[] {"Male", "Female", "Male", "Female", "Female", "Female",
                                                                        "Male", "Male", "Female"};
                                                                        

    Constants()
    {
        throw new RuntimeException("Constant class cannot be instantiated");
    }
}