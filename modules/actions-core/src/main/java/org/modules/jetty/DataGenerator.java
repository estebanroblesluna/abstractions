package org.modules.jetty;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.apache.commons.io.IOUtils;


public class DataGenerator {

	public static void main(String[] args) throws IOException {
		new DataGenerator().run();
	}
	
	private int initialProductSize = 100;
	private int initialUserSize = 50;
	private int initialReviewsSize = 4;
	private int initialValuationsSize = 8;


	private int currentUserId = 0;
	private int userIncrement = 15;
	private int maxUsers = 100000;

	private int currentProductId = 0;
	private int productIncrement = 8;
	private int maxProducts = 10000;

	private int currentReviewId = 0;
	private int reviewIncrement = 40;
	private int maxReviews = 500000;

	private int currentValuationId = 0;
	private int valuationIncrement = 150;
	private int maxValuations = 5000000;
	
	private OutputStream output;
	private int iteration = 1;
	private String directory = "/Users/estebanroblesluna/disk/experiment";

	public void run() throws IOException {
		try {

			StringBuilder scriptContents = new StringBuilder();
			
			this.createDataFile();
			this.initialize();
			this.closeDataFile();
			scriptContents.append("mysql amazon -h localhost -u root < ./data_" + this.iteration + ".sql\n");
			iteration++;
			
			while (this.hasNotFinish()) {
				System.out.println("Iteration " + iteration + " " + this.currentProductId + " " + this.currentReviewId + " " + this.currentUserId + " " + this.currentValuationId);

				this.createDataFile();
				this.generateUsers(this.userIncrement);
				this.generateProducts(this.productIncrement);
				this.generateReviews(this.reviewIncrement);
				this.generateValuations(this.valuationIncrement);
				this.closeDataFile();
				
				scriptContents.append("echo 'Iteration " + this.iteration + "'\n");
				scriptContents.append("mysql amazon -h localhost -u root < ./data_" + this.iteration + ".sql\n");
				scriptContents.append("sleep " + ((int)(Math.random() * 5d)) + "s\n");
				scriptContents.append("\n");
				
				iteration++;
			}

			File file = new File(directory + "/script.sh");
			file.createNewFile();
			this.output = new FileOutputStream(file);
			IOUtils.write(scriptContents.toString(), this.output);
		} finally {
			IOUtils.closeQuietly(this.output);
		}
	}
	
	private void closeDataFile() {
		IOUtils.closeQuietly(this.output);
	}

	private void createDataFile() throws IOException {
		File file = new File(directory + "/data_" + this.iteration + ".sql");
		file.createNewFile();
		this.output = new FileOutputStream(file);
	}

	private boolean hasNotFinish() {
		return this.currentProductId < this.maxProducts
				|| this.currentReviewId < this.maxReviews
				|| this.currentUserId < this.maxUsers
				|| this.currentValuationId < this.maxValuations;
	}

	private void generateValuations(int valuationIncrement) {
		int currentIteration = 0;
		while (currentIteration <= valuationIncrement && this.currentValuationId < this.maxValuations) {
			int value = (Math.random() > 0.5d ? 1 : -1);
			int reviewId = (int)(Math.random() * (Math.round(this.currentReviewId / 10d)));
			int userId = (int)(Math.random() * (Math.round(this.currentUserId / 10d)));
			String sql = "INSERT INTO `amazon`.`valuation` (`valuation_oid`, `value`, `review_review_oid`, `user_oid`, `valuation_date`) VALUES ('" + currentValuationId + "', '" + value + "', '" + reviewId + "', '" + userId + "', NOW());";
			this.write(sql);
			currentIteration++;
			this.currentValuationId++;
		}
	}
	
	private void generateReviews(int reviewIncrement) {
		int currentIteration = 0;
		while (currentIteration <= reviewIncrement && this.currentReviewId < this.maxReviews) {
			int value = (int)(Math.random() * 5d);
			int productId = (int)(Math.random() * (Math.round(this.currentProductId / 10d)));
			int userId = (int)(Math.random() * (Math.round(this.currentUserId / 10d)));
			String sql = "INSERT INTO `amazon`.`review` (`review_oid`, `comment`, `value`, `product_product_oid`, `user_oid`, `review_date`) VALUES ('" + currentReviewId + "', 'nothing', '" + value + "', '" + productId + "', '" + userId + "', NOW());";
			this.write(sql);
			currentIteration++;
			this.currentReviewId++;
		}
	}

	private void generateUsers(int userIncrement) {
		int currentIteration = 0;
		while (currentIteration <= userIncrement && this.currentUserId < this.maxUsers) {
			String sql = "INSERT INTO `amazon`.`user` (`oid`, `username`) VALUES ('" + currentUserId + "', 'user " + currentUserId + "');";
			this.write(sql);
			currentIteration++;
			this.currentUserId++;
		}
	}
	
	private void generateProducts(int productIncrement) {
		int currentIteration = 0;
		while (currentIteration <= productIncrement && this.currentProductId < this.maxProducts) {
			String sql = "INSERT INTO `amazon`.`product` (`product_oid`, `name`, `price`) VALUES ('" + currentProductId + "', 'Product " + currentProductId + "', '100');";
			this.write(sql);
			currentIteration++;
			this.currentProductId++;
		}
	}

	private void initialize() {
		this.generateUsers(this.initialUserSize);
		this.generateProducts(this.initialProductSize);
		this.generateReviews(this.initialReviewsSize);
		this.generateValuations(this.initialValuationsSize);
	}

	private void write(String string) {
		try {
			IOUtils.write(string + "\n", output);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
