package org.codetab.uknit.core.make;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.codetab.uknit.core.zap.make.model.Field;
import org.codetab.uknit.core.zap.make.model.IVar;
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

    /**
     * Get the deep copy of class fields. Within a method field may change its
     * characteristics. For example, when a mock field is initialized in a
     * method then it is treated as real within the scope of that method. To
     * preserve the original field return deep copy list.
     * @param clzName
     * @return
     */
    public List<Field> getFieldsCopy(final String clzName) {
        Clz clz = get(clzName);
        List<Field> copies = new ArrayList<>();
        for (Field field : clz.getFields()) {
            copies.add(field.deepCopy());
        }
        return copies;
    }

    public void addField(final String clzName, final Field field) {
        Clz clz = get(clzName);
        clz.getFields().add(field);
    }

    public Clz getClz(final String clzName) {
        return get(clzName);
    }

    /**
     * Method visit uses field copies and may change field state - enable,
     * deepStub, enforce. The state of original field is updated from field
     * copy. Mock and created are not affected.
     * @param clzName
     * @param vars
     */
    public void updateFieldState(final String clzName, final List<IVar> vars) {
        Clz clz = get(clzName);
        List<Field> fields = clz.getFields();
        for (IVar var : vars) {
            if (var instanceof Field) {
                Field fieldCopy = (Field) var;
                Optional<Field> field = fields.stream()
                        .filter(f -> f.getName().equals(fieldCopy.getName()))
                        .findAny();
                // mock, created are not set as the global value are preserved
                field.ifPresent(f -> {
                    f.setEnable(fieldCopy.isEnable());
                    f.setDeepStub(fieldCopy.isDeepStub());
                    f.setEnforce(fieldCopy.getEnforce());
                });
            }
        }
    }
}
