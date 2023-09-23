package org.codetab.uknit.core.make.model;

import static java.util.Objects.isNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import javax.inject.Inject;

import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.Type;

import com.google.inject.assistedinject.Assisted;

public class Var implements IVar {

    protected Kind kind;

    /**
     * Var is renamed on var conflict or a new instance of var is created when
     * new value is assigned to var. Three name fields keep track of name
     * changes.
     *
     * Var name - current name
     *
     * oldName - name before change
     *
     * definedName - name given when var is defined, useful to group the
     * reassigned vars.
     *
     * <code>
     *  var [name=id, oldName=id, definedName=id]
     *  var [name=id2, oldName=id, definedName=id]
     *  var [name=id3, oldName=id2, definedName=id]
     *  var [name=id4, oldName=id3, definedName=id]
     * </code>
     */
    protected String name;
    protected String oldName;
    protected final String definedName;

    protected Type type;
    protected ITypeBinding typeBinding;
    protected boolean mock;
    protected boolean created;
    protected boolean enable;
    protected Optional<Boolean> enforce;
    protected boolean deepStub;
    protected Map<String, Object> properties;
    protected List<Nature> natures;
    protected Optional<Initializer> initializer;

    @Inject
    public Var(@Assisted final Kind kind, @Assisted final String name,
            @Assisted final Type type, @Assisted final boolean mock) {
        this.kind = kind;
        this.name = name;
        this.oldName = name;
        this.definedName = name;
        this.type = type;
        this.mock = mock;
        this.enable = true; // enable by default
        this.enforce = Optional.empty();
        this.deepStub = false;
        this.created = false;
        properties = new HashMap<>();
        natures = new ArrayList<>();
        initializer = Optional.empty();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(final String name) {
        this.name = name;
    }

    @Override
    public String getOldName() {
        return oldName;
    }

    @Override
    public void setOldName(final String oldName) {
        this.oldName = oldName;
    }

    @Override
    public String getDefinedName() {
        return definedName;
    }

    @Override
    public boolean is(final Kind pkind) {
        return this.kind.equals(pkind);
    }

    @Override
    public void setKind(final Kind kind) {
        this.kind = kind;
    }

    @Override
    public Kind getKind() {
        return kind;
    }

    @Override
    public Type getType() {
        return type;
    }

    @Override
    public void setType(final Type type) {
        this.type = type;
    }

    @Override
    public ITypeBinding getTypeBinding() {
        return typeBinding;
    }

    @Override
    public void setTypeBinding(final ITypeBinding typeBinding) {
        this.typeBinding = typeBinding;
    }

    @Override
    public boolean isMock() {
        return mock;
    }

    @Override
    public void setMock(final boolean mock) {
        this.mock = mock;
    }

    @Override
    public boolean isCreated() {
        return created;
    }

    @Override
    public void setCreated(final boolean created) {
        this.created = created;
    }

    @Override
    public boolean isEnable() {
        return enable;
    }

    @Override
    public void setEnable(final boolean disable) {
        this.enable = disable;
    }

    @Override
    public Optional<Boolean> getEnforce() {
        return enforce;
    }

    @Override
    public void setEnforce(final boolean enforce) {
        this.enforce = Optional.ofNullable(enforce);
    }

    @Override
    public boolean isDeepStub() {
        return deepStub;
    }

    @Override
    public void setDeepStub(final boolean deepStub) {
        this.deepStub = deepStub;
    }

    @Override
    public IVar deepCopy() {
        Var clone = new Var(kind, name, type, mock);
        clone.setCreated(created);
        clone.setDeepStub(deepStub);
        clone.setEnable(enable);
        if (enforce.isPresent()) {
            clone.setEnforce(enforce.get());
        }
        clone.setOldName(oldName);
        properties.forEach((k, v) -> clone.setProperty(k, v));
        natures.forEach(n -> clone.addNature(n));
        return clone;
    }

    @Override
    public int hashCode() {
        return Objects.hash(created, deepStub, enable, enforce, kind, mock,
                name, oldName, type);
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Var other = (Var) obj;
        return created == other.created && deepStub == other.deepStub
                && enable == other.enable
                && Objects.equals(enforce, other.enforce) && kind == other.kind
                && mock == other.mock && Objects.equals(name, other.name)
                && Objects.equals(oldName, other.oldName)
                && Objects.equals(type, other.type);
    }

    @Override
    public String toString() {
        String oldNameStr = "";
        if (!name.equals(oldName)) {
            oldNameStr = " (" + oldName + ") ";
        }
        return "Var [name=" + name + oldNameStr + ", type=" + type + ", kind="
                + kind + ", mock=" + mock + ", created=" + created + "]";
    }

    @Override
    public void setProperty(final String propertyName, final Object data) {
        properties.put(propertyName, data);
    }

    @Override
    public Object getProperty(final String propertyName,
            final Object defaultValue) {
        Object value = properties.get(propertyName);
        if (isNull(value)) {
            value = defaultValue;
        }
        return value;
    }

    /**
     * Update fields of this with the other.
     *
     * @param other
     */
    public void updateStates(final Var other) {
        created = other.created;
        mock = other.mock;

        properties.clear();
        properties.putAll(other.properties);

        natures.clear();
        natures.addAll(other.natures);
    }

    @Override
    public void addNature(final Nature nature) {
        if (!natures.contains(nature)) {
            natures.add(nature);
        }
    }

    @Override
    public boolean is(final Nature nature) {
        return natures.contains(nature);
    }

    @Override
    public List<Nature> getNatures() {
        return natures;
    }

    @Override
    public boolean isEffectivelyReal() {
        return created || natures.contains(Nature.REALISH) || !mock;
    }

    @Override
    public void setInitializer(final Optional<Initializer> initializer) {
        this.initializer = initializer;
    }

    @Override
    public Optional<Initializer> getInitializer() {
        return initializer;
    }
}
