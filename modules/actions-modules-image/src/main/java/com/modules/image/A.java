package com.modules.image;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.io.FileUtils;

public class A {

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		String directory = "/Users/estebanroblesluna/Dropbox-LIFIA/Dropbox/opencv_30";
		String imageType = "positives";
		int imageNumber = 200;
		
		File file = new File(
				directory + "/" + imageType + "-all.txt");
		File newFile = new File(
				directory + "/" + imageType + ".txt");

		List<String> lines = FileUtils.readLines(file);
		Collections.shuffle(lines);
		List sublines = new ArrayList<String>(lines.size());

		for (String line : lines) {
			String f = line;
			if (f.lastIndexOf('.') > f.lastIndexOf('/')) {
				sublines.add(line);
			} else {
				System.out.println(line);
			}
		}
		
		sublines = sublines.subList(0, imageNumber);
		FileUtils.writeLines(newFile, sublines);
	}

}
