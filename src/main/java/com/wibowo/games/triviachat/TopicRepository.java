package com.wibowo.games.triviachat;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public final class TopicRepository  {

    private final Map<String, Topic> TOPICS = new HashMap<>();

    public void addTopic(Topic topic) {
        TOPICS.put(topic.getTopicName(), topic);
    }

    public Topic find(final String topicName) {
        return TOPICS.get(topicName);
    }

    public Set<String> availableTopicNames() {
        return TOPICS.keySet();
    }
}
