package com.abstractions.service.core;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringBufferInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.lang.NotImplementedException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jsoup.helper.Validate;

import com.abstractions.model.Resource;

public class FilesystemResourceService implements ResourceService {

	private static Log log = LogFactory.getLog(FilesystemResourceService.class);
	
	private String resourcesDirectory;
	private String rootPath;
	private File rootDir;

	public FilesystemResourceService(String rootPath, String resourcesDirectory) {
		Validate.notNull(rootPath);

		this.setRootPath(rootPath);
		this.setResourcesDirectory(resourcesDirectory);
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
	public boolean storeResource(long applicationId, String path, InputStream stream) {
		try {
			FileUtils.writeByteArrayToFile(new File(this.buildFilesPath(applicationId, path)), IOUtils.toByteArray(stream));
		} catch (IOException e) {
			log.error("Error storing file", e);
		}
		return true;
	}

	private String encodePath(String path) {
	  return path;
		//return path.replaceAll(File.separator, ENCODED_PATH_SEPARATOR);
	}

	private String decodePath(String path) {
	  return path;
		//return path.replaceAll(ENCODED_PATH_SEPARATOR, File.separator);
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
			String filename = this.decodePath(absolutePath.substring(currentPath.length() + this.getRootPath().length() + 1 + this.getResourcesDirectory().length() + 1 + new Long(applicationId).toString().length() + 1));
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
		return this.getRootPath() + File.separator + applicationId + this.getResourcesDirectory() + File.separator + this.encodePath(path);
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

	@Override
	public Resource getResource(long applicationId, String path) {
		throw new NotImplementedException();
	}
  
 @Override
  public boolean createFolder(long applicationId, String path){
   throw new NotImplementedException();
  }
  
  @Override
  public boolean folderExists(long applicationId, String path){

    throw new NotImplementedException();
  }
  
  public void deleteFolder(long applicationId, String path){
    throw new NotImplementedException();
  }

  private String getResourcesDirectory() {
    return resourcesDirectory;
  }

  private void setResourcesDirectory(String resourcesDirectory) {
    this.resourcesDirectory = resourcesDirectory;
  }
}	
