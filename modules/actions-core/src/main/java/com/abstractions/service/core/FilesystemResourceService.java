package com.abstractions.service.core;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jsoup.helper.Validate;

public class FilesystemResourceService implements ResourceService {

	private static Log log = LogFactory.getLog(FilesystemResourceService.class);
	
	private static final String ENCODED_PATH_SEPARATOR = "___";
	private static final String FILES_DIRECTORY = "files";
	private static final String SNAPSHOTS_DIRECTORY = "snapshots";
	private String rootPath;
	private File rootDir;

	public FilesystemResourceService(String rootPath) {
		Validate.notNull(rootPath);

		this.setRootPath(rootPath);
		this.initializeDirectory();
	}

	private void initializeDirectory() {
		try {
			this.setRootDir(new File(this.getRootPath()));
			FileUtils.forceMkdir(this.getRootDir());
		} catch (IOException e) {
			log.error("Error intializing root directory", e);
		}
	}

	public FilesystemResourceService() {
	}

	/* (non-Javadoc)
	 * @see com.abstractions.service.core.ResourceService#storeFile(long, java.lang.String, java.io.InputStream)
	 */
	@Override
	public void storeResource(long applicationId, String path, InputStream stream) {
		try {
			FileUtils.writeByteArrayToFile(new File(this.buildFilesPath(applicationId, path)), IOUtils.toByteArray(stream));
		} catch (IOException e) {
			log.error("Error storing file", e);
		}
	}

	private String encodePath(String path) {
		return path.replaceAll(File.separator, ENCODED_PATH_SEPARATOR);
	}

	private String decodePath(String path) {
		return path.replaceAll(ENCODED_PATH_SEPARATOR, File.separator);
	}

	/* (non-Javadoc)
	 * @see com.abstractions.service.core.ResourceService#getContentsOfFile(long, java.lang.String)
	 */
	@Override
	public InputStream getContentsOfResource(long applicationId, String path) {
		try {
			return new ByteArrayInputStream(FileUtils.readFileToByteArray(new File(this.buildFilesPath(applicationId, path))));
		} catch (IOException e) {
			log.error("Error getting contents of file", e);
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see com.abstractions.service.core.ResourceService#listFiles(long)
	 */
	@Override
	public List<String> listResources(long applicationId) {
		List<String> files = new ArrayList<String>();
		File directory = new File(this.buildFilesPath(applicationId, ""));
		if (!directory.exists()) {
			directory.mkdirs();
		}
		for (Object file : FileUtils.listFiles(directory, FileFilterUtils.fileFileFilter(), FileFilterUtils.trueFileFilter())) {
			String absolutePath = ((File) file).getAbsolutePath();
			String currentPath = this.getRootDir().getAbsolutePath().substring(0, this.getRootDir().getAbsolutePath().indexOf(this.getRootPath()));
			String filename = this.decodePath(absolutePath.substring(currentPath.length() + this.getRootPath().length() + 1 + FILES_DIRECTORY.length() + 1 + new Long(applicationId).toString().length() + 1));
			files.add(filename.replace("." + File.separator, File.separator));
		}
		return files;
	}

	/* (non-Javadoc)
	 * @see com.abstractions.service.core.ResourceService#uncompressContent(long, java.io.InputStream)
	 */
	@Override
	public void uncompressContent(long applicationId, InputStream stream) {
		try {
			ZipInputStream zipInputStream = new ZipInputStream(stream);
			ZipEntry zipEntry = zipInputStream.getNextEntry();
			while (zipEntry != null) {
				if (zipEntry.getSize() == -1) {
					zipInputStream.closeEntry();
					zipEntry = zipInputStream.getNextEntry();
					continue;
				}
				this.storeResource(applicationId, zipEntry.getName().replace("." + File.separator,  File.separator), zipInputStream);
				zipInputStream.closeEntry();
				zipEntry = zipInputStream.getNextEntry();
			}
			zipInputStream.close();
		} catch (Exception e) {
			log.error("Error uncompressing content", e);
		}
	}

	
	/* (non-Javadoc)
	 * @see com.abstractions.service.core.ResourceService#deleteFile(long, java.lang.String)
	 */
	@Override
	public void deleteResource(long applicationId, String path) {
		new File(this.buildFilesPath(applicationId, path)).delete();
	}
	
	private String buildFilesPath(long applicationId, String path) {
		return this.getRootPath() + File.separator + applicationId + File.separator + FILES_DIRECTORY + File.separator + this.encodePath(path);
	}
	
	/* (non-Javadoc)
	 * @see com.abstractions.service.core.ResourceService#getContentsOfSnapshot(java.lang.String, java.lang.String)
	 */
	@Override
	public InputStream getContentsOfSnapshot(String applicationId, String snapshotId) {
		try {
			return new FileInputStream(this.buildSnapshotPath(Long.parseLong(applicationId), Long.parseLong(snapshotId)));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/* (non-Javadoc)
	 * @see com.abstractions.service.core.ResourceService#getSnapshotOutputStream(java.lang.String, java.lang.String)
	 */
	@Override
	public OutputStream getSnapshotOutputStream(String applicationId, String snapshotId) {
		try {
			File snapshotsDirectory = new File(this.buildSnapshotPath(Long.parseLong(applicationId), null));
			if (!snapshotsDirectory.exists()) {
				snapshotsDirectory.mkdirs();
			}
			return new FileOutputStream(this.buildSnapshotPath(Long.parseLong(applicationId), Long.parseLong(snapshotId)));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see com.abstractions.service.core.ResourceService#buildSnapshotPath(java.lang.Long, java.lang.Long)
	 */
	@Override
	public String buildSnapshotPath(Long applicationId, Long snapshotId) {
		if (snapshotId == null) {
			return this.getRootPath() + File.separator + applicationId + File.separator + SNAPSHOTS_DIRECTORY;
		} else {
			return this.getRootPath() + File.separator + applicationId + File.separator + SNAPSHOTS_DIRECTORY + File.separator + "___snapshot_" + snapshotId;
		}
	}
	
	/* (non-Javadoc)
	 * @see com.abstractions.service.core.ResourceService#storeSnapshot(java.lang.String, java.lang.String, java.io.InputStream)
	 */
	@Override
	public void storeSnapshot(String applicationId, String snapshotId, InputStream content) {
		try {
			IOUtils.copy(content, new FileOutputStream(this.buildSnapshotPath(Long.parseLong(applicationId), Long.parseLong(snapshotId))));
		} catch (Exception e) {
			log.error("Error storing snapshot", e);
		}
	}
	
	public String getRootPath() {
		return rootPath;
	}

	public void setRootPath(String rootPath) {
		this.rootPath = rootPath;
	}

	private File getRootDir() {
		return rootDir;
	}

	private void setRootDir(File rootDir) {
		this.rootDir = rootDir;
	}

	/* (non-Javadoc)
	 * @see com.abstractions.service.core.ResourceService#resourceExists(long, java.lang.String)
	 */
	@Override
	public boolean resourceExists(long applicationId, String path) {
		return new File(this.buildFilesPath(applicationId, path)).exists();
	}

	@Override
	public long getResourceLastModifiedDate(long applicationId, String path) {
		return new File(this.buildFilesPath(applicationId, path)).lastModified();
	}

}
