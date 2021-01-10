package com.art.service;

import com.art.entity.Topic;
import com.art.repository.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TopicService {

    private TopicRepository topicRepository;

    @Autowired
    void setTopicRepository(TopicRepository topicRepository) {
        this.topicRepository = topicRepository;
    }

    public List<Topic> findAll() {
        return topicRepository.findAll();
    }
}
