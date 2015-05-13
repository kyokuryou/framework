package org.smarty.core.jdbc;

import java.io.Serializable;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Created Date 2015/04/09
 *
 * @author kyokuryou
 * @version 1.0
 */
public class SQLException extends Exception implements Serializable, Iterable<Throwable> {
    private static final long serialVersionUID = 2135244094396331495L;

    private String SQLState;

    private int vendorCode;

    private volatile SQLException next;

    public SQLException() {
        this(null, null, 0);
    }

    public SQLException(String reason) {
        this(reason, null, 0);
    }

    public SQLException(String reason, String SQLState) {
        this(reason, SQLState, 0);
    }


    public SQLException(Throwable cause) {
        this(null, null, 0, cause);
    }

    public SQLException(String reason, Throwable cause) {
        this(reason, null, 0, cause);
    }

    public SQLException(String reason, String sqlState, Throwable cause) {
        this(reason, sqlState, 0, cause);
    }

    public SQLException(String reason, String SQLState, int vendorCode) {
        super(reason);
        this.SQLState = SQLState;
        this.vendorCode = vendorCode;
    }


    public SQLException(String reason, String sqlState, int vendorCode, Throwable cause) {
        super(reason, cause);
        this.SQLState = sqlState;
        this.vendorCode = vendorCode;
    }

    public String getSQLState() {
        return SQLState;
    }

    public int getErrorCode() {
        return vendorCode;
    }

    public SQLException getNextException() {
        return (next);
    }

    public void setNextException(SQLException ex) {
        if (next != null) {
            next.setNextException(ex);
        } else {
            next = ex;
        }
    }

    public Iterator<Throwable> iterator() {

        return new Iterator<Throwable>() {

            private SQLException current = SQLException.this;

            public boolean hasNext() {
                return current != null;
            }

            public Throwable next() {
                if (current == null) {
                    throw new NoSuchElementException();
                }
                SQLException ret = current;
                current = current.next;
                return ret;
            }

            public void remove() {
                throw new UnsupportedOperationException();
            }

        };
    }

}
