package org.codetab.uknit.itest.load;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Access list from super. Notes: This feature is fully implemented needs
 * refactor. Analyze whether to add super private field as @Mock in test class.
 *
 * @author m
 *
 */
public class SuperGet extends SuperGetHolder {

    public List<File> getSuperField() {
        return getFieldList();
    }

    public File getSuperCreatedList() {
        File aFile = getCreatedList().get(0);
        return aFile;
    }

    public File getSuperFieldList() {
        File aFile = getFieldList().get(0);
        return aFile;
    }

    public File getSuperCreatedListInForEach() {
        File aFile = null;
        for (File file : super.getCreatedList()) {
            aFile = file;
        }
        return aFile;
    }

    public File getSuperFieldListInForEach() {
        File aFile = null;
        for (File file : super.getFieldList()) {
            aFile = file;
        }
        return aFile;
    }
}

class SuperGetHolder {

    private List<File> fieldList;

    public List<File> getCreatedList() {
        List<File> list = new ArrayList<>();
        list.add(new File("test"));
        return list;
    }

    public List<File> getFieldList() {
        fieldList = new ArrayList<>();
        fieldList.add(new File("test"));
        return fieldList;
    }
}
