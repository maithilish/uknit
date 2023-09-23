package org.codetab.uknit.core.make;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.codetab.uknit.core.make.model.Field;
import org.codetab.uknit.core.make.model.IVar;
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

    public void addDefinedField(final String clzName, final Field field) {
        Clz clz = get(clzName);
        clz.addDefinedField(field);
    }

    /**
     * Get the deep copy of class fields. Within a method field may change its
     * characteristics. For example, when a mock field is initialized in a
     * method then it is treated as real within the scope of that method. To
     * preserve the original field return deep copy list.
     * @param clzName
     * @return
     */
    public List<Field> getDefinedFieldsCopy(final String clzName) {
        Clz clz = get(clzName);
        List<Field> copy = new ArrayList<>();
        for (Field field : clz.getDefinedFields()) {
            copy.add(field.fieldDeepCopy());
        }
        return copy;
    }

    /**
     * Create copy of defined fields and set if fields (working copy)
     * @param clzName
     */
    public void setFields(final String clzName) {
        List<Field> copy = getDefinedFieldsCopy(clzName);
        Clz clz = get(clzName);
        clz.setFields(copy);
    }

    public Clz getClz(final String clzName) {
        return get(clzName);
    }

    /**
     * Method visit uses field copies and may change field state - enable,
     * deepStub, enforce. The state of original field is updated from field
     * copy. The mock and created state (mock and created fields of Var) are not
     * updated.
     *
     * @param clzName
     * @param vars
     */
    public void updateFieldState(final String clzName, final List<IVar> vars) {
        Clz clz = get(clzName);
        List<Field> fields = clz.getFields();
        for (IVar var : vars) {
            if (var instanceof Field) {
                /*
                 * mock and created fields are not updated as the global value
                 * are preserved
                 */
                Field fieldCopy = (Field) var;
                Optional<Field> field = fields.stream()
                        .filter(f -> f.getName().equals(fieldCopy.getName()))
                        .findAny();
                field.ifPresent(f -> {
                    f.setEnable(fieldCopy.isEnable());
                    f.setDeepStub(fieldCopy.isDeepStub());
                    fieldCopy.getEnforce().ifPresent(f::setEnforce);
                });
            }
        }
    }

}
