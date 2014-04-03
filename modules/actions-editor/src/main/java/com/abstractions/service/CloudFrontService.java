package com.abstractions.service;

import org.apache.commons.io.input.CloseShieldInputStream;
import org.apache.commons.lang.Validate;
import org.springframework.beans.factory.annotation.Autowired;

import com.abstractions.model.ApplicationSnapshot;
import com.abstractions.service.core.ResourceService;
import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSCredentialsProviderChain;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.ClasspathPropertiesFileCredentialsProvider;
import com.amazonaws.internal.StaticCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.cloudfront.AmazonCloudFrontClient;
import com.amazonaws.services.cloudfront.model.Aliases;
import com.amazonaws.services.cloudfront.model.CacheBehaviors;
import com.amazonaws.services.cloudfront.model.CookiePreference;
import com.amazonaws.services.cloudfront.model.CreateDistributionRequest;
import com.amazonaws.services.cloudfront.model.DefaultCacheBehavior;
import com.amazonaws.services.cloudfront.model.DistributionConfig;
import com.amazonaws.services.cloudfront.model.ForwardedValues;
import com.amazonaws.services.cloudfront.model.LoggingConfig;
import com.amazonaws.services.cloudfront.model.Origin;
import com.amazonaws.services.cloudfront.model.Origins;
import com.amazonaws.services.cloudfront.model.PriceClass;
import com.amazonaws.services.cloudfront.model.S3Origin;
import com.amazonaws.services.cloudfront.model.S3OriginConfig;
import com.amazonaws.services.cloudfront.model.TrustedSigners;
import com.amazonaws.services.cloudfront.model.ViewerProtocolPolicy;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

import java.io.BufferedInputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class CloudFrontService {
	
	private SnapshotService snapshotService;
	
	
	public CloudFrontService(){}
	
	public CloudFrontService(SnapshotService snapshotService){
		Validate.notNull(snapshotService);
		this.snapshotService = snapshotService;
	}
	
	private void uploadResources(long snapshotId){
		//TODO Get bucket name from application properties
		ApplicationSnapshot snapshot = snapshotService.getSnapshot(snapshotId);
		String bucketName = buildSnapshotBucketName(snapshot);
		AmazonS3Client s3 = new AmazonS3Client(new ClasspathPropertiesFileCredentialsProvider());
		s3.setRegion(Region.getRegion(Regions.US_WEST_2));
		s3.createBucket(bucketName);
		ZipInputStream zip = new ZipInputStream(snapshotService.getContentsOfSnapshot(snapshot.getApplication().getId(), snapshot.getId()));
		ZipEntry entry = null;
		while(true){
			try {
				entry = zip.getNextEntry();
			} catch (Exception e){
				entry = null;
			}
			if(entry == null)
				break;
			System.out.print(entry.getName());
			if(entry.getName().startsWith("files/")){
				try{
					s3.putObject(new PutObjectRequest(bucketName,entry.getName(),new CloseShieldInputStream(zip),null)
							.withCannedAcl(CannedAccessControlList.PublicRead));
				} catch (Exception e) {
					
				}
			}
		}
	}
	
	private String buildSnapshotBucketName(ApplicationSnapshot snapshot){
		return ("app-"+snapshot.getApplication().getName()+"-snp-"+snapshot.getId()).toLowerCase();
	}
	
	public void distributeResources(long snapshotId){
		
		uploadResources(snapshotId);
		
		ApplicationSnapshot snapshot = snapshotService.getSnapshot(snapshotId);
		AmazonCloudFrontClient client = new AmazonCloudFrontClient(new ClasspathPropertiesFileCredentialsProvider());
		String bucketName = buildSnapshotBucketName(snapshot);
	    long cloudFrontMinTTL=36000;

		
		
		Origin origin = new Origin()
	        .withDomainName(bucketName+".s3.amazonaws.com")
	        .withId(bucketName)
	        .withS3OriginConfig(new S3OriginConfig().withOriginAccessIdentity(""));
		 
		Origins origins = new Origins().withItems(origin).withQuantity(1);
		
		DistributionConfig distributionConfig = new DistributionConfig().withEnabled(true)
														.withOrigins(origins)
														.withPriceClass(PriceClass.PriceClass_All)
														
														.withLogging(new LoggingConfig()
															.withEnabled(false)
															.withBucket("")
															.withPrefix("")
															.withIncludeCookies(false))
															
														.withDefaultRootObject("")
														.withComment("")
														
														.withAliases(new Aliases()
															.withQuantity(0))
															
														.withDefaultCacheBehavior(new DefaultCacheBehavior()
																.withTargetOriginId(bucketName)
																.withForwardedValues(new ForwardedValues()
																		.withQueryString(false)
																		.withCookies(new CookiePreference()
																				.withForward("none")))
																		.withTrustedSigners(new TrustedSigners()
																				.withQuantity(0)
																				.withEnabled(false))
																		.withViewerProtocolPolicy(ViewerProtocolPolicy.AllowAll)
																		.withMinTTL(cloudFrontMinTTL))
														.withCacheBehaviors(new CacheBehaviors()
															.withQuantity(0))
														.withCallerReference(bucketName);
														
														
		
		CreateDistributionRequest createDistributionRequest = new CreateDistributionRequest().withDistributionConfig(distributionConfig);
		client.createDistribution(createDistributionRequest);
	}
}
