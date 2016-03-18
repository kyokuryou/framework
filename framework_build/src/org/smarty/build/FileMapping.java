package org.smarty.build;

import org.apache.tools.ant.Project;
import org.apache.tools.ant.types.PatternSet;

/**
 *
 */
public class FileMapping extends PatternSet {
	protected Object getCheckedRef(Project p) {
		return getCheckedRef(PatternSet.class, getDataTypeName(), p);
	}
}
