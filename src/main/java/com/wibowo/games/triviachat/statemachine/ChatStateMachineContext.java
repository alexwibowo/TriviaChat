package com.wibowo.games.triviachat.statemachine;

import com.github.messenger4j.userprofile.UserProfile;
import com.wibowo.games.triviachat.Question;
import com.wibowo.games.triviachat.QuestionSet;
import com.wibowo.games.triviachat.Topic;
import com.wibowo.games.triviachat.TopicRepository;

import java.util.Set;

public final class ChatStateMachineContext {
    private final UserProfile userProfile;
    private final TopicRepository topicRepository;

    private int currentYear;

    private Topic currentTopic;

    private final int[] availableYears;

    private QuestionSet currentQuestionSet;

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
        this.currentQuestionSet = currentTopic.randomQuestionSet();
    }

    public void setCurrentQuestionSet(final QuestionSet currentQuestionSet) {
        this.currentQuestionSet = currentQuestionSet;
    }

    public Question getCurrentQuestion() {
        return currentQuestion;
    }

    public void pickNextQuestion() {
        this.currentQuestion = this.currentQuestionSet.randomQuestion();
        numberOfQuestionsAttemptedSoFar++;
    }

    public boolean questionSetHasFinished() {
        return numberOfQuestionsAttemptedSoFar == 5;
    }
}
