package com.abstractions.service;

import java.io.ByteArrayInputStream;

import org.apache.commons.lang.Validate;
import org.springframework.transaction.annotation.Transactional;

import com.abstractions.model.ApplicationSnapshot;
import com.abstractions.model.Property;
import com.abstractions.model.Resource;
import com.abstractions.repository.GenericRepository;
import com.abstractions.utils.MessageUtils;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.cloudfront.AmazonCloudFrontClient;
import com.amazonaws.services.cloudfront.model.Aliases;
import com.amazonaws.services.cloudfront.model.CacheBehaviors;
import com.amazonaws.services.cloudfront.model.CookiePreference;
import com.amazonaws.services.cloudfront.model.CreateDistributionRequest;
import com.amazonaws.services.cloudfront.model.CreateDistributionResult;
import com.amazonaws.services.cloudfront.model.DefaultCacheBehavior;
import com.amazonaws.services.cloudfront.model.DistributionConfig;
import com.amazonaws.services.cloudfront.model.ForwardedValues;
import com.amazonaws.services.cloudfront.model.LoggingConfig;
import com.amazonaws.services.cloudfront.model.Origin;
import com.amazonaws.services.cloudfront.model.Origins;
import com.amazonaws.services.cloudfront.model.PriceClass;
import com.amazonaws.services.cloudfront.model.S3OriginConfig;
import com.amazonaws.services.cloudfront.model.TrustedSigners;
import com.amazonaws.services.cloudfront.model.ViewerProtocolPolicy;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;

public class CloudFrontService {
	
	private static String awsAccessKeyProperty = "awsAccessKey";
	private static String awsSecretKeyProperty = "awsSecretKey";
	
	private SnapshotService snapshotService;
	private GenericRepository repository;
	
	public CloudFrontService(){}
	
	public CloudFrontService(SnapshotService snapshotService, GenericRepository repository){
		Validate.notNull(snapshotService);
    Validate.notNull(repository);
		
		this.snapshotService = snapshotService;
    this.repository = repository;
	}
	
	private void uploadResources(ApplicationSnapshot snapshot, AWSCredentials credentials){
		String bucketName = buildSnapshotBucketName(snapshot);
		AmazonS3Client s3 = new AmazonS3Client(credentials);
		s3.setRegion(Region.getRegion(Regions.US_WEST_2));
		s3.createBucket(bucketName);
		for(Resource res : snapshot.getResources()){
			if(res.getType().equals("P")){
				try{
					s3.putObject(new PutObjectRequest(bucketName,"files/public/"+res.getType(),new ByteArrayInputStream(res.getData()),null)
							.withCannedAcl(CannedAccessControlList.PublicRead));
				} catch (Exception e) {
					//TODO: Fix this
				}
			}
			
		}
		/*ZipInputStream zip = new ZipInputStream(snapshotService.getContentsOfSnapshot(snapshot.getApplication().getId(), snapshot.getId()));
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
		}*/
	}
	
	private String buildSnapshotBucketName(ApplicationSnapshot snapshot){
		return ("app-"+snapshot.getApplication().getName()+"-snp-"+snapshot.getId()).toLowerCase();
	}

  @Transactional
  public String distributeResources(long snapshotId) {
    ApplicationSnapshot snapshot = this.snapshotService.getSnapshot(snapshotId);
    return this.distributeResources(snapshot);
  }

	@Transactional
	public String distributeResources(ApplicationSnapshot snapshot){
		String accessKey = snapshot.getProperty(MessageUtils.APPLICATION_CLOUDFRONT_ACCESS);
		String secretKey = snapshot.getProperty(MessageUtils.APPLICATION_CLOUDFRONT_SECRET);
		
		if((secretKey==null || accessKey==null))
			return null;
		
		AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
		uploadResources(snapshot, credentials);
		

		AmazonCloudFrontClient client = new AmazonCloudFrontClient(credentials);
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
		CreateDistributionResult result = client.createDistribution(createDistributionRequest);
		String location = result.getLocation();
		
		//TODO change this line
		Property property = new Property(MessageUtils.APPLICATION_CDN_PROPERTY, location, snapshot.getEnvironment());
		snapshot.addProperty(property);
		
		this.repository.save(snapshot);
		
		return location;
	}
}
