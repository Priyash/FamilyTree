package com.geektrust.family.GraphInterface;

import java.util.LinkedList;

import com.geektrust.family.FamilyMemberInterface.IFamilyMember;

/**
 * Buildable
 */
public interface IBuildable {

    public void build();
    public void buildWithExternalFile(String filePath);
}