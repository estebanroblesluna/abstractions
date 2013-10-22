package com.modules.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.abstractions.api.Expression;
import com.abstractions.api.Message;
import com.abstractions.api.Processor;
import com.abstractions.utils.ExpressionUtils;
import com.abstractions.utils.MessageUtils;

public class FileReaderProcessor implements Processor {

	private static Log log = LogFactory.getLog(FileReaderProcessor.class);

	/**
	 * The directory where the files are located
	 */
	private Expression directory;
	
	/**
	 * The file that we are trying to retrieve
	 */
	private Expression filePath;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Message process(Message message) {
		String directoryAsString = ExpressionUtils.evaluateNoFail(this.directory, message, "/");
		File directory = new File(directoryAsString);
		
		if (directory.isDirectory()) {
			String fileAsString = ExpressionUtils.evaluateNoFail(this.filePath, message, "");
			File file = new File(directory, fileAsString);
			try {
				FileInputStream io = new FileInputStream(file);
				message.setPayload(IOUtils.toByteArray(io));

				String contentType = Files.probeContentType(file.toPath());
				message.putProperty(MessageUtils.FILE_BASE_PROPERTY + ".contentType", contentType);
			} catch (IOException e) {
				log.error("Error reading the file", e);
			}
		}
		
		return message;
	}

	public Expression getDirectory() {
		return directory;
	}

	public void setDirectory(Expression directory) {
		this.directory = directory;
	}

	public Expression getFilePath() {
		return filePath;
	}

	public void setFilePath(Expression filePath) {
		this.filePath = filePath;
	}
}
