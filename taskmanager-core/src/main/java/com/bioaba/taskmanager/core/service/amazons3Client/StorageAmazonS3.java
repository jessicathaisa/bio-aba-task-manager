package com.bioaba.taskmanager.core.service.amazons3Client;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.AbortMultipartUploadRequest;
import com.amazonaws.services.s3.model.CompleteMultipartUploadRequest;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.InitiateMultipartUploadRequest;
import com.amazonaws.services.s3.model.InitiateMultipartUploadResult;
import com.amazonaws.services.s3.model.PartETag;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.UploadPartRequest;

public class StorageAmazonS3 {

	public StorageAmazonS3() {

	}

	public String save(String taskIdentifier, byte[] file, FileStoreType type) {
		return storeFileOnAmazon(taskIdentifier, file, type);
	}

	private String storeFileOnAmazon(String folderName, byte[] file,
			FileStoreType type) {
		String bucketName = "bio-aba-tasks";
		String keyName = "tasks/" + folderName + "/"
				+ type.getTypeDescription();

		ByteArrayInputStream input = new ByteArrayInputStream(file);

		AmazonS3 s3Client = new AmazonS3Client(new ProfileCredentialsProvider());
		List<PartETag> partETags = new ArrayList<PartETag>();

		InitiateMultipartUploadRequest initRequest = new InitiateMultipartUploadRequest(
				bucketName, keyName);
		InitiateMultipartUploadResult initResponse = s3Client
				.initiateMultipartUpload(initRequest);

		long contentLength = file.length;
		long partSize = 5242880;

		try {
			long filePosition = 0;
			for (int i = 1; filePosition < contentLength; i++) {
				partSize = Math.min(partSize, (contentLength - filePosition));

				UploadPartRequest uploadRequest = new UploadPartRequest()
						.withBucketName(bucketName).withKey(keyName)
						.withUploadId(initResponse.getUploadId())
						.withPartNumber(i).withFileOffset(filePosition)
						.withInputStream(input).withPartSize(partSize);

				partETags.add(s3Client.uploadPart(uploadRequest).getPartETag());
				filePosition += partSize;
			}

			CompleteMultipartUploadRequest compRequest = new CompleteMultipartUploadRequest(
					bucketName, keyName, initResponse.getUploadId(), partETags);

			s3Client.completeMultipartUpload(compRequest);
		} catch (Exception e) {
			s3Client.abortMultipartUpload(new AbortMultipartUploadRequest(
					bucketName, keyName, initResponse.getUploadId()));
		}

		return keyName;
	}

	public byte[] findByTaskKey(String taskKey, FileStoreType type) {
		String bucketName = "bio-aba-tasks";
		String key = "tasks/" + taskKey + "/" + type.getTypeDescription();
		AmazonS3 s3Client = new AmazonS3Client(new ProfileCredentialsProvider());
		
		byte[] bytearray = null;

		try {
			S3Object s3object = s3Client.getObject(new GetObjectRequest(
					bucketName, key));

			bytearray = new byte[(int) s3object.getObjectMetadata()
					.getContentLength()];
			s3object.getObjectContent().read(bytearray);

		} catch (AmazonServiceException ase) {
			System.out.println("Caught an AmazonServiceException, which"
					+ " means your request made it "
					+ "to Amazon S3, but was rejected with an error response"
					+ " for some reason.");
			System.out.println("Error Message:    " + ase.getMessage());
			System.out.println("HTTP Status Code: " + ase.getStatusCode());
			System.out.println("AWS Error Code:   " + ase.getErrorCode());
			System.out.println("Error Type:       " + ase.getErrorType());
			System.out.println("Request ID:       " + ase.getRequestId());
		} catch (AmazonClientException ace) {
			System.out.println("Caught an AmazonClientException, which means"
					+ " the client encountered "
					+ "an internal error while trying to "
					+ "communicate with S3, "
					+ "such as not being able to access the network.");
			System.out.println("Error Message: " + ace.getMessage());
		} catch (IOException ioe) {
			System.out.println("IO Exception on uploading a query file.");
			System.out.println("Error Message: " + ioe.getMessage());
		}

		return bytearray;
	}
}
