/*
 * @(#)AbstractBusinessObject.java     Jul 9, 2008
 *
 * TODO Add license terms
 *
 */
package com.google.code.simplestuff.bean;

import com.google.code.simplestuff.annotation.BusinessObject;

/**
 * Base class for all BusinessObjects.
 * 
 * @author anph
 * @since 9 Jul 2008
 * 
 */
@BusinessObject
public abstract class AbstractBusinessObject {

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object other) {
        return SimpleBean.equals(this, other);
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        return SimpleBean.hashCode(this);
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return SimpleBean.toString(this);
    }

}
