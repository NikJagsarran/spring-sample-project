package com.nik.sampleproject.hobbies;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface HobbyRepository extends MongoRepository<Hobby, String> {

}