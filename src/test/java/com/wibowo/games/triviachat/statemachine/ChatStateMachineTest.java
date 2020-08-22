package com.wibowo.games.triviachat.statemachine;

import com.github.messenger4j.userprofile.UserProfile;
import com.wibowo.games.triviachat.Question;
import com.wibowo.games.triviachat.QuestionSet;
import com.wibowo.games.triviachat.Topic;
import com.wibowo.games.triviachat.TopicRepository;
import com.wibowo.games.triviachat.statemachine.answers.Answer;
import com.wibowo.games.triviachat.statemachine.answers.ChooseTopicAnswer;
import com.wibowo.games.triviachat.statemachine.answers.ChooseYearAnswer;
import com.wibowo.games.triviachat.statemachine.answers.StartTrivia;
import com.wibowo.games.triviachat.statemachine.states.QuizReady;
import com.wibowo.games.triviachat.statemachine.states.TopicNotDetermined;
import com.wibowo.games.triviachat.statemachine.states.YearNotDetermined;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ChatStateMachineTest {

    private ChatStateMachine stateMachine;
    private ChatStateMachineContext chatStateMachineContext;

    @BeforeEach
    void setUp() {
        final UserProfile userProfile = new UserProfile("Alex", "Wibowo", "", "AU", (float) 10.0, UserProfile.Gender.MALE);

        final QuestionSet cardiologyQuestionSet1 = new QuestionSet(0);
        cardiologyQuestionSet1.addQuestion(new Question(
                "A typical AMI signs and symptoms include the following except?")
                .addOption("Nausea/vomiting")
                .addOption("Angina")
                .addOption("Fatigue")
                .addOption("Back pain")
                .setCorrectAnswerIndex(1)
                .setExplanation("Though they lie on the Ischaemic Heart Disease spectrum, angina by definition would not be a symptom of an AMI")
        );
        final Topic cardiology = new Topic("CARDIOLOGY");
        cardiology.addQuestionSet(cardiologyQuestionSet1);


        final QuestionSet respiratoryQuestionSet1 = new QuestionSet(0);
        respiratoryQuestionSet1.addQuestion(new Question(
                "Which of the following is not a sign or symptom of a pulmonary embolism?")
                .addOption("Syncope")
                .addOption("Anxiety")
                .addOption("Angina")
                .addOption("Tachycardia")
                .addOption("Productive cough")
                .setCorrectAnswerIndex(4)
                .setExplanation("foo")
        );
        final Topic respiratory = new Topic("RESPIRATORY");
        respiratory.addQuestionSet(cardiologyQuestionSet1);

        final TopicRepository topicRepository = new TopicRepository();
        topicRepository.addTopic(cardiology);
        topicRepository.addTopic(respiratory);

        chatStateMachineContext = new ChatStateMachineContext(userProfile, topicRepository, new int[]{1, 2, 3});
        chatStateMachineContext.setCurrentQuestionSet(cardiologyQuestionSet1);
        stateMachine = new ChatStateMachine(chatStateMachineContext);
    }

    @Nested
    class InitialStateTest {
        @Test
        void possible_answers() {
            final List<Answer> answers = stateMachine.availableUserOptions();
            assertThat(answers)
                    .contains(StartTrivia.INSTANCE);
        }

        @Test
        void machine_responses() {
            final List<String> responses = stateMachine.machineResponses();
            assertThat(responses)
                    .contains(
                            "Hi Alex, welcome to Monash Medical Exam Prep",
                            "My name is Tron, I'm a robot",
                            "I can help you prepare for your exam by asking stupid questions",
                            "Please use the button below to continue to chat with me"
                    );
        }

        @Test
        void user_is_ready() {
            assertThat(stateMachine.process("READY"))
                    .isEqualTo(YearNotDetermined.INSTANCE);
        }
    }

    @Nested
    class YearNotDeterminedState {

        @BeforeEach
        void setup() {
            stateMachine.process("READY");
        }

        @Test
        void machine_responses() {
            final List<String> responses = stateMachine.machineResponses();
            assertThat(responses)
                    .contains(
                            "Fabulous",
                            "Could you let me know your years of study"
                    );
        }

        @Test
        void possible_answers() {
            final List<Answer> answers = stateMachine.availableUserOptions();
            assertThat(answers)
                    .contains(new ChooseYearAnswer(1),
                            new ChooseYearAnswer(2),
                            new ChooseYearAnswer(3)
                    );
        }

        @Test
        void user_chose_a_year() {
            assertThat(stateMachine.process("1"))
                    .isEqualTo(TopicNotDetermined.INSTANCE);
            assertThat(chatStateMachineContext.getCurrentYear())
                    .isEqualTo(1);
        }
    }

    @Nested
    class TopicNotDeterminedState {
        @BeforeEach
        void setup() {
            stateMachine.process("READY");
            stateMachine.process("1"); // choose year
        }

        @Test
        void machine_responses() {
            final List<String> responses = stateMachine.machineResponses();
            assertThat(responses)
                    .contains(
                            "Awesome",
                            "Choose what topic you would like to have"
                    );
        }

        @Test
        void possible_answers() {
            final List<Answer> answers = stateMachine.availableUserOptions();
            assertThat(answers)
                    .contains(new ChooseTopicAnswer("CARDIOLOGY"),
                            new ChooseTopicAnswer("RESPIRATORY")
                    );
        }

        @Test
        void user_chose_a_topic() {
            assertThat(stateMachine.process("CARDIOLOGY"))
                    .isEqualTo(QuizReady.INSTANCE);
            assertThat(chatStateMachineContext.getCurrentYear())
                    .isEqualTo(1);
            assertThat(chatStateMachineContext.getCurrentTopic())
                    .isEqualTo("CARDIOLOGY");
        }
    }
}