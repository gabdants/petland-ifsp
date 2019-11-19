package com.petland.api.ImagesApi.controller;


import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.util.TableUtils;
import com.google.gson.Gson;
import com.petland.api.ImagesApi.models.Field;
import com.petland.api.ImagesApi.service.AmazonClient;
import com.petland.api.ImagesApi.models.Image;
import com.petland.api.ImagesApi.repositories.ImageRepository;
import org.apache.http.protocol.HTTP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/image")
public class ImageController {

    private AmazonClient amazonClient;

    private DynamoDBMapper dynamoDBMapper;

    @Autowired
    private AmazonDynamoDB amazonDynamoDB;

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    ImageController(AmazonClient amazonClient) {
        this.amazonClient = amazonClient;
    }

    @PostMapping(value = "/uploadFile", params = "name")
    public String uploadFile(@RequestPart(value = "file") MultipartFile file, @RequestParam(value = "name") String name) {
        return this.amazonClient.uploadFile(file,name);
    }

    @DeleteMapping("/deleteFile")
    public String deleteFile(@RequestPart(value = "url") String fileUrl) {
        return this.amazonClient.deleteFileFromS3Bucket(fileUrl);
    }


    @PostMapping(value = "/imageOpts")
    public Object imageOpts(@RequestBody Image image, @RequestParam(value = "imageId") String imageId) {


        try {

            Image firstImg = imageRepository.findByIdImage(imageId);

            image.setS3Url(firstImg.getS3Url());
            image.setName(firstImg.getName());
            image.setRequestDate(firstImg.getRequestDate());
            image.setIdImage(imageId);

            Image img = imageRepository.save(image);

            return new ResponseEntity<>(img, HttpStatus.OK);

        }catch (Exception e){

            return new ResponseEntity<>( "ERRO: --> " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }


    }

    @GetMapping("/approvalImages")
    public Object getAprrovalImages(@RequestParam(value = "imageId") String imageId){

        Image image;
        List<Image> images;

        try {
            if (imageId.isEmpty()){
                images = imageRepository.findAllByApproval(true);
                return new ResponseEntity<List<Image>>(images, HttpStatus.OK);
            }else{
                image =imageRepository.findByApprovalAndIdImage(true, imageId);
                return new ResponseEntity<Image>(image, HttpStatus.OK);
            }
        }catch (Exception e){
            return new ResponseEntity<>( "ERRO: --> " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }


    @PutMapping(path="/approvalImages")
    public @ResponseBody Object updateApproval(@RequestParam(value = "imageId") String imageId) {



        try {
            Image image = imageRepository.findByIdImage(imageId);
            image.setApproval(true);

            imageRepository.save(image);
            return new ResponseEntity<>(image, HttpStatus.OK);

        }catch (Exception e){
            return new ResponseEntity<>( "ERRO: --> " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


    @GetMapping("/imageFields")
    public Object getImageFields(@RequestParam(value = "imageId") String imageId){

        Image image;
        List<Field> fields;
        List<Image> images;

        try {
            image = imageRepository.findByIdImage(imageId);
            fields = image.getField();
            return new ResponseEntity<>(fields, HttpStatus.OK);

        }catch (Exception e){
            return new ResponseEntity<>( "ERRO: --> " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("/approvedImages")
    public Object getAprovedImages(){

        Image image;
        Field fields;
        List<Image> images;

        try {
            images = imageRepository.findAllByApproved(true);
            return new ResponseEntity<>(images, HttpStatus.OK);

        }catch (Exception e){

            return new ResponseEntity<>( "ERRO: --> " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

    @PutMapping(path="/approvedImages")
    public @ResponseBody Object updateApproved(@RequestParam(value = "imageId") String imageId) {

        Image image = imageRepository.findByIdImage(imageId);
        image.setApproved(true);

        imageRepository.save(image);
        return new ResponseEntity<>(image, HttpStatus.OK);
    }



    @PostMapping(value = "/updateFields", params = "idImage")
    public Object setFields(@RequestBody List<Field> fields, @RequestParam(value = "imageId") String imageId) {

        Image image = imageRepository.findByIdImage(imageId);

        image.setField(fields);

        imageRepository.save(image);


        return new ResponseEntity<>(image, HttpStatus.OK);


    }


    @GetMapping("/all")
    public Object getAllImages(){

        try {
            return new ResponseEntity<>(imageRepository.findAll(), HttpStatus.OK);

        }catch (Exception e){

            return new ResponseEntity<>( "ERRO: --> " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }







}
