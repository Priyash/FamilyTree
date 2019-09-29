
package com.geektrust.family;

import com.geektrust.family.GenderInterface.IGender;
import com.geektrust.family.Utility.Constants;

/**
 * FemaleGender class
 */
public class FemaleGenderImpl implements IGender {

    FemaleGenderImpl(){}

    @Override
    public String getGender() {
        return Constants.FEMALE;
    }

    
}