package org.codetab.uknit.core.make.model;

import java.util.List;

import javax.inject.Inject;

import com.google.inject.assistedinject.Assisted;

public class Load {

    /*
     * for list.get(0), list is consumer
     */
    private IVar consumer;

    /*
     * for list.get(0), add is call
     */
    private String call;

    /**
     * args are used in generated stmt, for String value = list.get(0) the
     * generated load stmt is list.add(value) and args is value. The usedVar is
     * used to enable the vars.
     * <p>
     * String value = list.get(0), args [value] and no used vars
     * <p>
     * String value = map.get(key), args [key,value] and used vars key
     * <p>
     * String value = map.get(person.getName()) and person.getName() returns
     * inferVar apple then args [apple, value] and used var apple
     */
    private List<IVar> args;
    private List<IVar> usedVars;

    private boolean enable;

    @Inject
    public Load(@Assisted final IVar consumer, @Assisted final String call,
            @Assisted("args") final List<IVar> args,
            @Assisted("usedVars") final List<IVar> usedVars) {
        this.consumer = consumer;
        this.call = call;
        this.args = args;
        this.usedVars = usedVars;
        enable = false;
    }

    public IVar getConsumer() {
        return consumer;
    }

    public String getCall() {
        return call;
    }

    public List<IVar> getArgs() {
        return args;
    }

    public List<IVar> getUsedVars() {
        return usedVars;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(final boolean enable) {
        this.enable = enable;
    }

    @Override
    public String toString() {
        return "Insert [consumer=" + consumer + ", call=" + call + ", args="
                + args + "]";
    }

    // CHECKSTYLE:OFF
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        for (IVar arg : args) {
            result = prime * result
                    + ((arg == null) ? 0 : ((Var) arg).hashCode());
        }
        for (IVar usedVar : usedVars) {
            result = prime * result
                    + ((usedVar == null) ? 0 : ((Var) usedVar).hashCode());
        }
        result = prime * result + ((call == null) ? 0 : call.hashCode());
        result = prime * result
                + ((consumer == null) ? 0 : ((Var) consumer).hashCode());
        return result;
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
        Load other = (Load) obj;
        if (args == null) {
            if (other.args != null) {
                return false;
            }
        } else if (!args.equals(other.args)) {
            return false;
        }
        if (usedVars == null) {
            if (other.usedVars != null) {
                return false;
            }
        } else if (!usedVars.equals(other.usedVars)) {
            return false;
        }
        if (call == null) {
            if (other.call != null) {
                return false;
            }
        } else if (!call.equals(other.call)) {
            return false;
        }
        if (consumer == null) {
            if (other.consumer != null) {
                return false;
            }
        } else if (!consumer.equals(other.consumer)) {
            return false;
        }
        return true;
    }
    // CHECKSTYLE:ON
}
