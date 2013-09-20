package com.abstractions.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class Unzipper {
	
	public void unzip(InputStream zipContent, File outputFolder) {
		
		try {
			ZipInputStream zipInputStream = new ZipInputStream(zipContent);
			ZipEntry zipEntry = zipInputStream.getNextEntry();
			while (zipEntry != null) {
				if (zipEntry.getSize() == -1) {
					zipInputStream.closeEntry();
					zipEntry = zipInputStream.getNextEntry();
					continue;
				}
				String fileName = zipEntry.getName();
				File newFile = new File(outputFolder + File.separator + fileName);
				new File(newFile.getParent()).mkdirs();
				FileOutputStream outputStream = new FileOutputStream(newFile);
				byte[]buffer = new byte[(int) zipEntry.getSize()];
				zipInputStream.read(buffer);
				outputStream.write(buffer, 0, (int) zipEntry.getSize());
				outputStream.close();
				zipInputStream.closeEntry();
				zipEntry = zipInputStream.getNextEntry();
			}
			zipInputStream.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
}