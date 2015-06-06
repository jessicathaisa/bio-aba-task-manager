package com.bioaba.taskmanager.core.service.amazons3Client;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

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
	
	public String save(String taskIdentifier, MultipartFile mpfile, FileStoreType type) {
		File file = convertToFile(mpfile);
		return storeFileOnAmazon(taskIdentifier, file, type);
	}

	public String save(String taskIdentifier, String mpfile, FileStoreType type) {
		File file = convertToFile(mpfile);
		return storeFileOnAmazon(taskIdentifier, file, type);
	}

	private File convertToFile(String query) {
		File convFile = null;
		try {
			convFile = new File("generatedFile");
			convFile.createNewFile();
			FileOutputStream fos = new FileOutputStream(convFile);
			fos.write(query.getBytes());
			fos.close();
		} catch (Exception ex) {
			System.out.println(ex.toString());
		}
		return convFile;
	}

	private File convertToFile(MultipartFile query) {
		File convFile = null;
		try {
			convFile = new File(query.getOriginalFilename());
			convFile.createNewFile();
			FileOutputStream fos = new FileOutputStream(convFile);
			fos.write(query.getBytes());
			fos.close();
		} catch (Exception ex) {
			System.out.println(ex.toString());
		}
		return convFile;
	}

	private String storeFileOnAmazon(String folderName, File file, FileStoreType type) {
		String existingBucketName = "bio-aba-tasks";
		String keyName = "tasks/" + folderName + "/" + type.getTypeDescription();

		AmazonS3 s3Client = new AmazonS3Client(new ProfileCredentialsProvider());
		List<PartETag> partETags = new ArrayList<PartETag>();

		// Step 1: Initialize.
		InitiateMultipartUploadRequest initRequest = new InitiateMultipartUploadRequest(
				existingBucketName, keyName);
		InitiateMultipartUploadResult initResponse = s3Client
				.initiateMultipartUpload(initRequest);

		long contentLength = file.length();
		long partSize = 5242880;

		try {
			// Step 2: Upload parts.
			long filePosition = 0;
			for (int i = 1; filePosition < contentLength; i++) {
				// Last part can be less than 5 MB. Adjust part size.
				partSize = Math.min(partSize, (contentLength - filePosition));

				// Create request to upload a part.
				UploadPartRequest uploadRequest = new UploadPartRequest()
						.withBucketName(existingBucketName).withKey(keyName)
						.withUploadId(initResponse.getUploadId())
						.withPartNumber(i).withFileOffset(filePosition)
						.withFile(file).withPartSize(partSize);

				// Upload part and add response to our list.
				partETags.add(s3Client.uploadPart(uploadRequest).getPartETag());

				filePosition += partSize;
			}

			// Step 3: Complete.
			CompleteMultipartUploadRequest compRequest = new CompleteMultipartUploadRequest(
					existingBucketName, keyName, initResponse.getUploadId(),
					partETags);

			s3Client.completeMultipartUpload(compRequest);
		} catch (Exception e) {
			s3Client.abortMultipartUpload(new AbortMultipartUploadRequest(
					existingBucketName, keyName, initResponse.getUploadId()));
		}

		return keyName;
	}

	public File findByTaskKey(String taskKey, FileStoreType type) {
		String bucketName = "bio-aba-tasks";
		String key = "tasks/" + taskKey + "/" + type.getTypeDescription();
		AmazonS3 s3Client = new AmazonS3Client(new ProfileCredentialsProvider());
		File file = new File("generatedFile");
		
		try {
			S3Object s3object = s3Client.getObject(new GetObjectRequest(
					bucketName, key));
			
			byte[] bytearray = new byte[(int) s3object.getObjectMetadata().getContentLength()];
			s3object.getObjectContent().read(bytearray);
			
			file.createNewFile();
			FileOutputStream fos = new FileOutputStream(file);
			fos.write(bytearray);
			fos.close();

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
		} catch (IOException ioe){
			System.out.println("IO Exception on uploading a query file.");
			System.out.println("Error Message: " + ioe.getMessage());
		}
		
		return file;
	}
}
