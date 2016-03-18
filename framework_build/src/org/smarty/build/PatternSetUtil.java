package org.smarty.build;

import java.io.File;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.Vector;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.types.PatternSet;
import org.apache.tools.ant.types.selectors.SelectorUtils;

/**
 * PatternSetUtil
 */
public class PatternSetUtil {

	public static Set<String> getIncludePatterns(Vector<? extends PatternSet> patternsets, Project project) {
		Set<String> includePatterns = new HashSet<String>();
		if (patternsets == null || patternsets.isEmpty()) {
			return includePatterns;
		}
		for (PatternSet ps : patternsets) {
			String[] incls = ps.getIncludePatterns(project);
			if (incls == null || incls.length == 0) {
				incls = new String[]{"**"};
			}

			for (String incl : incls) {
				includePatterns.add(getPattern(incl));
			}
		}
		return includePatterns;
	}

	public static Set<String> getExcludePatterns(Vector<? extends PatternSet> patternsets, Project project) {
		Set<String> excludePatterns = new HashSet<String>();
		if (patternsets == null || patternsets.isEmpty()) {
			return excludePatterns;
		}
		for (PatternSet ps : patternsets) {
			String[] excls = ps.getExcludePatterns(project);
			if (excls != null) {
				for (String excl : excls) {

					excludePatterns.add(getPattern(excl));
				}
			}
		}
		return excludePatterns;
	}

	public static boolean isIncluded(String name, Set<String> includePatterns, Set<String> excludePatterns) {
		boolean included = false;
		for (Iterator<String> iter = includePatterns.iterator();
		     !included && iter.hasNext(); ) {
			String pattern = iter.next();
			included = SelectorUtils.matchPath(pattern, name);
		}

		for (Iterator<String> iter = excludePatterns.iterator();
		     included && iter.hasNext(); ) {
			String pattern = iter.next();
			included = !SelectorUtils.matchPath(pattern, name);
		}
		return included;
	}

	private static String getPattern(String cl) {
		String pattern = cl
				.replace('/', File.separatorChar)
				.replace('\\', File.separatorChar);
		if (pattern.endsWith(File.separator)) {
			pattern += "**";
		}
		return pattern;
	}
}
