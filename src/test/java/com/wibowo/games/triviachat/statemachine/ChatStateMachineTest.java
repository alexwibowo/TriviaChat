package com.wibowo.games.triviachat.statemachine;

import com.github.messenger4j.userprofile.UserProfile;
import com.wibowo.games.triviachat.Question;
import com.wibowo.games.triviachat.QuestionSet;
import com.wibowo.games.triviachat.Topic;
import com.wibowo.games.triviachat.TopicRepository;
import com.wibowo.games.triviachat.statemachine.states.InitialState;
import com.wibowo.machinia.Command;
import com.wibowo.games.triviachat.statemachine.commands.ChooseTopicCommand;
import com.wibowo.games.triviachat.statemachine.commands.ChooseYearCommand;
import com.wibowo.games.triviachat.statemachine.commands.StartTrivia;
import com.wibowo.games.triviachat.statemachine.states.QuizReady;
import com.wibowo.games.triviachat.statemachine.states.TopicNotDetermined;
import com.wibowo.games.triviachat.statemachine.states.YearNotDetermined;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;

class ChatStateMachineTest {

    private ChatStateMachine2 stateMachine;
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

        chatStateMachineContext = new ChatStateMachineContext(userProfile, topicRepository, new int[]{1, 2, 3}, InitialState.INSTANCE);
        chatStateMachineContext.setCurrentQuestionSet(cardiologyQuestionSet1);
        stateMachine = new ChatStateMachine2(new Function<String, Command>() {
            @Override
            public Command apply(final String commandString) {
                switch (commandString) {
                    case "START":
                        return StartTrivia.INSTANCE;
                    default:
                        throw new IllegalArgumentException("unknown");
                }
            }
        });
    }

    @Nested
    class InitialStateTest {
        @Test
        void possible_answers() {
            final List<Command> commands = chatStateMachineContext.availableCommands();
            assertThat(commands)
                    .contains(StartTrivia.INSTANCE);
        }

        @Test
        void machine_responses() {
            final List<String> responses = chatStateMachineContext.machineResponses();
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
            assertThat(stateMachine.process(chatStateMachineContext, StartTrivia.INSTANCE))
                    .isEqualTo(YearNotDetermined.INSTANCE);
        }
    }

    @Nested
    class YearNotDeterminedState {

        @BeforeEach
        void setup() {
            stateMachine.process(chatStateMachineContext, StartTrivia.INSTANCE);
        }

        @Test
        void machine_responses() {
            final List<String> responses = chatStateMachineContext.machineResponses();
            assertThat(responses)
                    .contains(
                            "Fabulous",
                            "Could you let me know your years of study"
                    );
        }

        @Test
        void possible_answers() {
            final List<Command> commands = chatStateMachineContext.availableCommands();
            assertThat(commands)
                    .contains(new ChooseYearCommand(1),
                            new ChooseYearCommand(2),
                            new ChooseYearCommand(3)
                    );
        }

        @Test
        void user_chose_a_year() {
            assertThat(stateMachine.process(chatStateMachineContext, new ChooseYearCommand(1)))
                    .isEqualTo(TopicNotDetermined.INSTANCE);
            assertThat(chatStateMachineContext.getCurrentYear())
                    .isEqualTo(1);
        }
    }

    @Nested
    class TopicNotDeterminedState {
        @BeforeEach
        void setup() {
            stateMachine.process(chatStateMachineContext, StartTrivia.INSTANCE);
            stateMachine.process(chatStateMachineContext, new ChooseYearCommand(1)); // choose year
        }

        @Test
        void machine_responses() {
            final List<String> responses = chatStateMachineContext.machineResponses();
            assertThat(responses)
                    .contains(
                            "Awesome",
                            "Choose what topic you would like to have"
                    );
        }

        @Test
        void possible_answers() {
            final List<Command> commands = chatStateMachineContext.availableCommands();
            assertThat(commands)
                    .contains(new ChooseTopicCommand("CARDIOLOGY"),
                            new ChooseTopicCommand("RESPIRATORY")
                    );
        }

        @Test
        void user_chose_a_topic() {
            assertThat(stateMachine.process(chatStateMachineContext, new ChooseTopicCommand("CARDIOLOGY")))
                    .isEqualTo(QuizReady.INSTANCE);
            assertThat(chatStateMachineContext.getCurrentYear())
                    .isEqualTo(1);
            assertThat(chatStateMachineContext.getCurrentTopic())
                    .isEqualTo("CARDIOLOGY");
        }
    }
}