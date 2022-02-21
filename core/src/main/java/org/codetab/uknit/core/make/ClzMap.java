package org.codetab.uknit.core.make;

import java.util.HashMap;
import java.util.List;

import org.codetab.uknit.core.make.model.Field;
import org.eclipse.jdt.core.dom.TypeDeclaration;

/**
 * Maps name of class-under-test to Clz.
 *
 * @author Maithilish
 *
 */
public class ClzMap extends HashMap<String, Clz> {

    private static final long serialVersionUID = 1L;

    public TypeDeclaration getTypeDecl(final String clzName) {
        Clz clz = get(clzName);
        return clz.getTestTypeDecl();
    }

    public List<Field> getFields(final String clzName) {
        Clz clz = get(clzName);
        return clz.getFields();
    }

    public void addField(final String clzName, final Field field) {
        Clz clz = get(clzName);
        clz.getFields().add(field);
    }

    public Clz getClz(final String clzName) {
        return get(clzName);
    }
}
