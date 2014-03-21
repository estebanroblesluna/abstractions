package com.abstractions.service.core;

import org.apache.commons.lang.Validate;
import com.abstractions.service.core.ResourceService;
import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSCredentialsProviderChain;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.internal.StaticCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3Client;

public class CloudFrontService {
	private ResourceService resourceService;
	
	public CloudFrontService(ResourceService resourceService){
		Validate.notNull(resourceService);
		this.resourceService = resourceService;
	}
	
	public void uploadResources(long applicationId, long snapshotId){
		//TODO Get credentials from application properties
		AWSCredentials credentials = new BasicAWSCredentials("AKIAIC6QLDUDK7HWMC4Q","RJqTTxCBscjflWllHoQd69NiR5oNww8vM1LieHlk");
		//TODO Get bucket name from application properties
		String bucketName = "abstractionstest"; 
		AmazonS3Client s3 = new AmazonS3Client(new StaticCredentialsProvider(credentials));
		s3.setRegion(Region.getRegion(Regions.US_WEST_2));
		String snapshotFolder = "app_"+applicationId+"-snp_"+snapshotId+"/";
		for(String resource : resourceService.listResources(applicationId)){
			try{
				s3.putObject(bucketName, snapshotFolder+resource, resourceService.getContentsOfResource(applicationId, resource), null);
			} catch (AmazonClientException e){
				
			}
		}
	}
}
