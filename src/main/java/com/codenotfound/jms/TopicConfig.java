package com.codenotfound.jms;

public class TopicConfig {
    String topicConsumerName;
    String topicProducerName;

    public TopicConfig(String topicConsumerName, String topicProducerName) {
        this.topicConsumerName = topicConsumerName;
        this.topicProducerName = topicProducerName;
    }

    public String getTopicConsumerName() {
        return topicConsumerName;
    }

    public String getTopicProducerName() {
        return topicProducerName;
    }
}
