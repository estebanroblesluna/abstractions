package com.abstractions.service;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
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

	public void storeFile(String path, InputStream stream) {
		try {
			FileUtils.writeByteArrayToFile(new File(this.getRootPath() + "/" + this.encodePathPath(path)), IOUtils.toByteArray(stream));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private String encodePathPath(String path) {
		return path.replaceAll("/", ENCODED_PATH_SEPARATOR);
	}

	private String decodePath(String path) {
		return path.replaceAll(ENCODED_PATH_SEPARATOR, "/");
	}

	public InputStream getContentsOfFile(String path) {
		try {
			return new ByteArrayInputStream(FileUtils.readFileToByteArray(new File(this.getRootPath() + "/" + this.encodePathPath(path))));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<String> listFiles() {
		List<String> files = new ArrayList<String>();
		for (Object file : FileUtils.listFiles(this.getRootDir(), FileFilterUtils.fileFileFilter(), FileFilterUtils.trueFileFilter())) {
			String absolutePath = ((File) file).getAbsolutePath();
			String currentPath = this.getRootDir().getAbsolutePath().substring(0, this.getRootDir().getAbsolutePath().indexOf(this.getRootPath()));
			String filename = this.decodePath(absolutePath.substring(currentPath.length() + this.getRootPath().length()));
			files.add(filename.replace("./", "/"));
		}
		return files;
	}

	public void uncompressFile(InputStream stream) {
		try {
			ZipInputStream zipInputStream = new ZipInputStream(stream);
			ZipEntry zipEntry = zipInputStream.getNextEntry();
			while (zipEntry != null) {
				if (zipEntry.getSize() == -1) {
					zipInputStream.closeEntry();
					zipEntry = zipInputStream.getNextEntry();
					continue;
				}
				byte[] buffer = new byte[(int) zipEntry.getSize()];
				zipInputStream.read(buffer);
				this.storeFile(zipEntry.getName().replace("./",  "/"), new ByteArrayInputStream(buffer));
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
