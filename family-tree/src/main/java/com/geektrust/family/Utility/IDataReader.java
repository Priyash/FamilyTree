package com.geektrust.family.Utility;

import java.io.File;
import java.util.List;

/**
 * IDataReader
 */
public interface IDataReader {

    File getFileFromResources(String fileName);
    void ReadData(File file, List<String> data);
}