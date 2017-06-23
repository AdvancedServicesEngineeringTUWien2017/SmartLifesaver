package com.filip.smartlifesaver.repository;

import com.filip.smartlifesaver.model.HeartData;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;


@EnableScan
public interface HeartDataRepository extends CrudRepository<HeartData, String> {
}
