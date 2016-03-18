package org.smarty.build.task;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.Vector;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.Expand;
import org.apache.tools.ant.taskdefs.Jar;
import org.apache.tools.ant.types.PatternSet;
import org.apache.tools.ant.util.FileNameMapper;
import org.apache.tools.ant.util.FileUtils;
import org.smarty.build.FileMapping;
import org.smarty.build.PatternSetUtil;

/**
 * MergeJar
 */
public class MergeJar extends Expand {
	private static final int BUFFER_SIZE = 1024;
	private Vector<PatternSet> patternsets = new Vector<PatternSet>();
	private Vector<FileMapping> fileMapping = new Vector<FileMapping>();
	private Set<String> includePatterns = new HashSet<String>();
	private Set<String> excludePatterns = new HashSet<String>();
	private Set<String> mappingIncludePatterns = new HashSet<String>();
	private Set<String> mappingExcludePatterns = new HashSet<String>();
	private boolean overwrite = true;
	private boolean stripAbsolutePathSpec = false;
	private Jar jar;

	public void addFileMapping(FileMapping fileMapping) {
		this.fileMapping.addElement(fileMapping);
	}

	public void addJar(Jar jar) {
		if (this.jar != null) {
			return;
		}
		this.jar = jar;
	}

	public void setStripAbsolutePathSpec(boolean stripAbsolutePathSpec) {
		this.stripAbsolutePathSpec = stripAbsolutePathSpec;
	}

	public void addPatternset(PatternSet patternsets) {
		this.patternsets.addElement(patternsets);
	}

	@Override
	public void setOverwrite(boolean overwrite) {
		this.overwrite = overwrite;
	}

	@Override
	public void execute() throws BuildException {
		if (patternsets != null && !patternsets.isEmpty()) {
			includePatterns.addAll(PatternSetUtil.getIncludePatterns(patternsets, getProject()));
			excludePatterns.addAll(PatternSetUtil.getExcludePatterns(patternsets, getProject()));
		}
		if (fileMapping != null && !fileMapping.isEmpty()) {
			mappingIncludePatterns.addAll(PatternSetUtil.getIncludePatterns(fileMapping, getProject()));
			mappingExcludePatterns.addAll(PatternSetUtil.getExcludePatterns(fileMapping, getProject()));
		}
		super.execute();
		if (jar != null) {
			jar.execute();
		}
	}

	/**
	 * extract a file to a directory
	 *
	 * @param fileUtils   a fileUtils object
	 * @param srcF        the source file
	 * @param dir         the destination directory
	 * @param in          the input stream
	 * @param entryName   the name of the entry
	 * @param entryDate   the date of the entry
	 * @param isDirectory if this is true the entry is a directory
	 * @param mapper      the filename mapper to use
	 * @throws IOException on error
	 */
	protected void extractFile(FileUtils fileUtils, File srcF, File dir,
	                           InputStream in,
	                           String entryName, Date entryDate,
	                           boolean isDirectory, FileNameMapper mapper)
			throws IOException {

		if (stripAbsolutePathSpec && entryName.length() > 0
				&& (entryName.charAt(0) == File.separatorChar
				|| entryName.charAt(0) == '/'
				|| entryName.charAt(0) == '\\')) {
			log("stripped absolute path spec from " + entryName,
					Project.MSG_VERBOSE);
			entryName = entryName.substring(1);
		}

		String name = entryName.replace('/', File.separatorChar).replace('\\', File.separatorChar);
		boolean isIncluded = PatternSetUtil.isIncluded(name, includePatterns, excludePatterns);
		boolean isMerged = PatternSetUtil.isIncluded(name, mappingIncludePatterns, mappingExcludePatterns);
		if (!isIncluded) {
			log("skipping " + entryName + " as it is excluded or not included.",
					Project.MSG_VERBOSE);
			return;
		}

		String[] mappedNames = mapper.mapFileName(entryName);
		if (mappedNames == null || mappedNames.length == 0) {
			mappedNames = new String[]{entryName};
		}
		File f = fileUtils.resolveFile(dir, mappedNames[0]);
		try {
			if (isMerged && f.exists()) {
				log("Merging " + f + " as it is up-to-date", Project.MSG_DEBUG);
				outputFile(in, f, true);
				return;
			}
			if (!overwrite && f.exists()
					&& f.lastModified() >= entryDate.getTime()) {
				log("Skipping " + f + " as it is up-to-date", Project.MSG_DEBUG);
				return;
			}

			log("expanding " + entryName + " to " + f, Project.MSG_VERBOSE);
			// create intermediary directories - sometimes zip don't add them
			File dirF = f.getParentFile();
			if (dirF != null) {
				dirF.mkdirs();
			}

			if (isDirectory) {
				f.mkdirs();
			} else {
				outputFile(in, f, false);
			}
			fileUtils.setFileLastModified(f, entryDate.getTime());
		} catch (FileNotFoundException ex) {
			log("Unable to expand to file " + f.getPath(), ex, Project.MSG_WARN);
		}
	}

	private void outputFile(InputStream in, File file, boolean append) throws IOException {
		if (in == null || file == null) {
			return;
		}
		int len = 0;
		byte[] buffer = new byte[BUFFER_SIZE];
		OutputStream out = new FileOutputStream(file, append);
		try {
			if (append && file.exists()) {
				out.write('\n');
			}
			while ((len = in.read(buffer)) >= 0) {
				out.write(buffer, 0, len);
			}
		} finally {
			FileUtils.close(out);
		}
	}

}
