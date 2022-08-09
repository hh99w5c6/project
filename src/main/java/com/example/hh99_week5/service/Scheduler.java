package com.example.hh99_week5.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.*;
import com.example.hh99_week5.entity.Post;
import com.example.hh99_week5.repository.ImgUrlRepository;
import com.example.hh99_week5.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;


@RequiredArgsConstructor
@Component
@Slf4j
public class Scheduler {
    private final PostRepository postRepository;
    private final AmazonS3Client amazonS3Client;
    private final ImgUrlRepository imgUrlRepository;

    @Value("${cloud.aws.s3.bucket}")
    public String bucket;  // S3 버킷 이름

    @Scheduled(cron = "0 0 1 * * *")
    public void deletePostsNoComment(){
        List<Post> postsNoComment = postRepository.findAllByCommentListIsNull();
        for(Post post : postsNoComment){
            log.info("게시물 <{}>이 삭제되었습니다.", post.getTitle());
            postRepository.deleteById(post.getId());
        }
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
