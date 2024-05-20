package com.example.demo.service;

import java.io.File;
import java.io.FileOutputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class StorageService {

	@Value("${application.bucket.name}")
	private String bucketName;
	
	@Autowired
	private AmazonS3 s3Client;
	
	public String uploandFile(MultipartFile file) {
		File fileObject=convertMultiPartFileToFile(file);
		String fileName=System.currentTimeMillis()+"  "+file.getOriginalFilename();
		s3Client.putObject(new PutObjectRequest(bucketName, fileName, fileObject));
		fileObject.delete();
		System.out.println("last testing....");
		return "File Uploaded : "+fileName;
		
	}
	
	public byte[] downloadFile(String fileName) {
		S3Object s3Object = s3Client.getObject(bucketName,fileName);
		S3ObjectInputStream inputStream= s3Object.getObjectContent();
		try {
			byte[] content=IOUtils.toByteArray(inputStream);
			return content;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String deleteFile(String fileName) {
		s3Client.deleteObject(bucketName,fileName);
		return fileName+" removed...";
	}
	
	
	
	private File convertMultiPartFileToFile(MultipartFile file) {
		File convertedFile= new File(file.getOriginalFilename());
		
		try (FileOutputStream  fos= new FileOutputStream(convertedFile)){
			 fos.write(file.getBytes());
		}catch(Exception e) {
			log.error("Error converting multipartFile to file", e);
		}
		return convertedFile;
		
		
	}
}
