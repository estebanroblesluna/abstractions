package com.abstractions.service;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.jsoup.helper.Validate;

public class FileService {

	private static final String ENCODED_PATH_SEPARATOR = "___";
	private static final String FILES_DIRECTORY = "files";
	private static final String SNAPSHOTS_DIRECTORY = "snapshots";
	private String rootPath;
	private File rootDir;

	public FileService(String rootPath) {
		Validate.notNull(rootPath);

		this.setRootPath(rootPath);
		this.initializeDirectory();
	}

	private void initializeDirectory() {
		try {
			this.setRootDir(new File(this.getRootPath()));
			FileUtils.forceMkdir(this.getRootDir());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public FileService() {
	}

	public void storeFile(String applicationId, String path, InputStream stream) {
		try {
			FileUtils.writeByteArrayToFile(new File(this.buildFilesPath(applicationId, path)), IOUtils.toByteArray(stream));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private String encodePath(String path) {
		return path.replaceAll(File.separator, ENCODED_PATH_SEPARATOR);
	}

	private String decodePath(String path) {
		return path.replaceAll(ENCODED_PATH_SEPARATOR, File.separator);
	}

	public InputStream getContentsOfFile(String applicationId, String path) {
		try {
			return new ByteArrayInputStream(FileUtils.readFileToByteArray(new File(this.buildFilesPath(applicationId, path))));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<String> listFiles(String applicationId) {
		List<String> files = new ArrayList<String>();
		File directory = new File(this.buildFilesPath(applicationId, ""));
		if (!directory.exists()) {
			directory.mkdirs();
		}
		for (Object file : FileUtils.listFiles(directory, FileFilterUtils.fileFileFilter(), FileFilterUtils.trueFileFilter())) {
			String absolutePath = ((File) file).getAbsolutePath();
			String currentPath = this.getRootDir().getAbsolutePath().substring(0, this.getRootDir().getAbsolutePath().indexOf(this.getRootPath()));
			String filename = this.decodePath(absolutePath.substring(currentPath.length() + this.getRootPath().length() + 1 + FILES_DIRECTORY.length() + 1 + applicationId.length() + 1));
			files.add(filename.replace("." + File.separator, File.separator));
		}
		return files;
	}

	public void uncompressContent(String applicationId, InputStream stream) {
		try {
			ZipInputStream zipInputStream = new ZipInputStream(stream);
			ZipEntry zipEntry = zipInputStream.getNextEntry();
			while (zipEntry != null) {
				if (zipEntry.getSize() == -1) {
					zipInputStream.closeEntry();
					zipEntry = zipInputStream.getNextEntry();
					continue;
				}
				this.storeFile(applicationId, zipEntry.getName().replace("." + File.separator,  File.separator), zipInputStream);
				zipInputStream.closeEntry();
				zipEntry = zipInputStream.getNextEntry();
			}
			zipInputStream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
	public void deleteFile(String applicationId, String path) {
		new File(this.buildFilesPath(applicationId, path)).delete();
	}
	
	private String buildFilesPath(String applicationId, String path) {
		return this.getRootPath() + File.separator + applicationId + File.separator + FILES_DIRECTORY + File.separator + this.encodePath(path);
	}
	
	public InputStream getContentsOfSnapshot(String applicationId, String snapshotId) {
		try {
			return new FileInputStream(this.buildSnapshotPath(applicationId, snapshotId));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public OutputStream getSnapshotOutputStream(String applicationId, String snapshotId) {
		try {
			File snapshotsDirectory = new File(this.buildSnapshotPath(applicationId, null));
			if (!snapshotsDirectory.exists()) {
				snapshotsDirectory.mkdirs();
			}
			return new FileOutputStream(this.buildSnapshotPath(applicationId, snapshotId));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	private String buildSnapshotPath(String applicationId, String snapshotId) {
		if (snapshotId == null) {
			return this.getRootPath() + File.separator + applicationId + File.separator + SNAPSHOTS_DIRECTORY;
		} else {
			return this.getRootPath() + File.separator + applicationId + File.separator + SNAPSHOTS_DIRECTORY + File.separator + "___snapshot_" + snapshotId;
		}
	}
	
	public void storeSnapshot(String applicationId, String snapshotId, InputStream content) {
		try {
			IOUtils.copy(content, new FileOutputStream(this.buildSnapshotPath(applicationId, snapshotId)));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
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
}
