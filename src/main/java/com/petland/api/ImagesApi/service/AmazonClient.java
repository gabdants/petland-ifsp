package com.petland.api.ImagesApi.service;


import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.petland.api.ImagesApi.models.Field;
import com.petland.api.ImagesApi.models.Image;
import com.petland.api.ImagesApi.repositories.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class AmazonClient {

    private AmazonS3 s3client;

    @Autowired
    private ImageRepository imageRepository;

    @Value("${amazon.s3.endpoint}")
    private String endpointUrl;
    @Value("${amazon.s3.bucketName}")
    private String bucketName;
    @Value("${amazon.aws.accessKey}")
    private String accessKey;
    @Value("${amazon.aws.secretKey}")
    private String secretKey;

    @PostConstruct
    private void initializeAmazon() {
        AWSCredentials credentials = new BasicAWSCredentials(this.accessKey, this.secretKey);
        this.s3client = new AmazonS3Client(credentials);
    }

    public String uploadFile(MultipartFile multipartFile, String name) {
        String fileUrl = "";
        List<Field> field = new ArrayList<>();
        try {
            File file = convertMultiPartToFile(multipartFile);
            String fileName = generateFileName(multipartFile);
            fileUrl = endpointUrl + "/" + bucketName + "/" + fileName;
            uploadFileTos3bucket(fileName, file);
            file.delete();
            String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());

            Image image = new Image();

            image.setS3Url(fileUrl);
            image.setName(name);
            image.setRequestDate(date);



            Image img = imageRepository.save(image);
            return img.getIdImage();

        } catch (Exception e) {
            return e.getMessage();
        }

    }

    private File convertMultiPartToFile(MultipartFile file) throws IOException {
        File convFile = new File(file.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }

    private String generateFileName(MultipartFile file) {
        return new Date().getTime() + "-" + file.getOriginalFilename().replace(" ", "_");
    }

    private void uploadFileTos3bucket(String fileName, File file) {
        s3client.putObject(new PutObjectRequest(bucketName, fileName, file)
                .withCannedAcl(CannedAccessControlList.PublicRead));
    }

    public String deleteFileFromS3Bucket(String fileUrl) {
        String fileName = fileUrl.substring(fileUrl.lastIndexOf("/") + 1);
        s3client.deleteObject(new DeleteObjectRequest(bucketName, fileName));
        return "Successfully deleted";
    }


}