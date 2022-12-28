package com.example.exercisesix.Repository;

import com.example.exercisesix.Document.Text;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.stream.Stream;

public interface TextRepository extends ElasticsearchRepository<Text, String> {
    void deleteById(String id);
    Page<Text> findAllByTextContainingIgnoreCase(String text, Pageable pageable);
    Page<Text>findAll();

    Page<Text> findAll(Pageable pageable);
}