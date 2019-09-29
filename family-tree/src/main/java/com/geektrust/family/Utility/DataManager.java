

package com.geektrust.family.Utility;

/**
 * DataManager
 */
public class DataManager {
    private static DataManager dataManager = null;
    private DataManager()
    {

    }

    public static DataManager getInstance()
    {
        if(dataManager == null)
        {
            dataManager = new DataManager();
        }

        return dataManager;
    }

    public Object gDataReader(String dataReaderTypeClass)
    {   
        Object object = null;
        try {
            for (String data_reader_class : Constants.DATA_CLASS) {
                if(dataReaderTypeClass.equals(data_reader_class))
                {
                    object = Class.forName(data_reader_class).newInstance();
                }
            }
        } catch (Exception e) {
           e.printStackTrace();
        }
        return object;
    }


}