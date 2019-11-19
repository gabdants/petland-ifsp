package com.petland.api.ImagesApi.repositories;

import com.petland.api.ImagesApi.models.Image;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

@EnableScan
public interface ImageRepository extends CrudRepository<Image, String> {

    List<Image>  findAllByName(String name);
    List<Image>  findAllByIdImage(String idImage);
    List<Image> findAllByApproval(Boolean approval);
    Image findByApprovalAndIdImage(Boolean approval, String idImage);
    Image findByIdImage(String idImage);
    List<Image> findAllByApproved(Boolean approved);
}