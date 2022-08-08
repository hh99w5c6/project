package com.example.hh99_week5.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.*;
import com.example.hh99_week5.repository.ImgUrlRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@RequiredArgsConstructor
@Component
public class Scheduler {

    private final AmazonS3Client amazonS3Client;
    private final ImgUrlRepository imgUrlRepository;

    @Value("${cloud.aws.s3.bucket}")
    public String bucket;  // S3 버킷 이름

    @Scheduled(cron = "0 0 1 * * *")
    public void deleteImg(){
        ObjectListing objects = amazonS3Client.listObjects(bucket, "static");
        do { //1000개 단위로 읽음
            for (S3ObjectSummary objectSummary : objects.getObjectSummaries()) {
                if(!imgUrlRepository.existsByFileName(objectSummary.getKey())){
                    amazonS3Client.deleteObject(bucket,objectSummary.getKey());
                }
            }
            objects = amazonS3Client.listNextBatchOfObjects(objects); //<--이녀석은 1000개 단위로만 가져옴..
        } while (objects.isTruncated());
    }
}
