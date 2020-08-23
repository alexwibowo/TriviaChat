package com.wibowo.games.triviachat.statemachine;

import com.github.messenger4j.userprofile.UserProfile;
import com.wibowo.games.triviachat.Question;
import com.wibowo.games.triviachat.QuestionSet;
import com.wibowo.games.triviachat.Topic;
import com.wibowo.games.triviachat.TopicRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;

public final class ChatStateMachineContext {
    private static final Logger LOGGER = LoggerFactory.getLogger(ChatStateMachineContext.class.getName());

    private final UserProfile userProfile;

    private final TopicRepository topicRepository;

    private int currentYear;

    private Topic currentTopic;

    private final int[] availableYears;

    private QuestionSet currentQuestionSet;

    private int currentQuestionIndex = 0;

    private Question currentQuestion;

    private int numberOfQuestionsAttemptedSoFar = 0;

    public ChatStateMachineContext(final UserProfile userProfile,
                                   final TopicRepository topicRepository,
                                   final int[] availableYears) {
        this.userProfile = userProfile;
        this.topicRepository = topicRepository;
        this.availableYears = availableYears;
    }

    public int[] getAvailableYears() {
        return availableYears;
    }

    public UserProfile getUserProfile() {
        return userProfile;
    }

    public int getCurrentYear() {
        return currentYear;
    }

    public Set<String> getAvailableTopics() {
        return topicRepository.availableTopicNames();
    }

    public void setCurrentYear(int currentYear) {
        this.currentYear = currentYear;
    }

    public Topic getCurrentTopic() {
        return currentTopic;
    }

    public void setCurrentTopic(final String value) {
        this.currentTopic = topicRepository.find(value);
        pickNewQuestionSet();
    }

    public void setCurrentQuestionSet(final QuestionSet currentQuestionSet) {
        this.currentQuestionSet = currentQuestionSet;
    }

    public Question getCurrentQuestion() {
        return currentQuestion;
    }

    public void pickNextQuestion() {
        if (!this.currentQuestionSet.hasMoreQuestion(currentQuestionIndex)) {
            pickNewQuestionSet();
        }
        this.currentQuestion = this.currentQuestionSet.getQuestion(currentQuestionIndex++);
        numberOfQuestionsAttemptedSoFar++;
    }

    private void pickNewQuestionSet() {
        LOGGER.info("Picking up new question set...");
        this.currentQuestionSet = currentTopic.randomQuestionSet();
        this.currentQuestionIndex = 0;
    }
}
