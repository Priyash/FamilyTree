
package com.geektrust.family;

import com.geektrust.family.GenderInterface.IGender;
import com.geektrust.family.Utility.Constants;

/**
 * MaleGender
 */
public class MaleGenderImpl implements IGender{
    
    MaleGenderImpl(){}

    @Override
    public String getGender() {
        return Constants.MALE;
    }
}