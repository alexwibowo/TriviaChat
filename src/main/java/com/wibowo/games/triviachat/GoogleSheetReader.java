package com.wibowo.games.triviachat;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.ValueRange;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;

public class GoogleSheetReader {
    public static final Logger LOGGER = LoggerFactory.getLogger(GoogleSheetReader.class);
    private static final String APPLICATION_NAME = "TriviaChat";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final String TOKENS_DIRECTORY_PATH = "tokens";

    /**
     * Global instance of the scopes required by this quickstart.
     * If modifying these scopes, delete your previously saved tokens/ folder.
     */
    private static final List<String> SCOPES = Collections.singletonList(SheetsScopes.SPREADSHEETS_READONLY);
    private static final String CREDENTIALS_FILE_PATH = "/credentials.json";

    /**
     * Creates an authorized Credential object.
     * @param HTTP_TRANSPORT The network HTTP Transport.
     * @return An authorized Credential object.
     * @throws IOException If the credentials.json file cannot be found.
     */
    private static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException {
        // Load client secrets.
        InputStream in = GoogleSheetReader.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
        if (in == null) {
            throw new FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_PATH);
        }
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
                .setAccessType("offline")
                .build();
        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
        return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
    }

    public Topic retrieveTopic(final String topicName,
                               final String range) throws IOException, GeneralSecurityException {
        final Topic topic = new Topic(topicName);

        // Build a new authorized API client service.
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        final String spreadsheetId = "10GqhM00DjSnPYj88lX64DcpHWl6KjaJZYla5Wg6expA";
        final Sheets service = new Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                .setApplicationName(APPLICATION_NAME)
                .build();
        final ValueRange response = service.spreadsheets().values()
                .get(spreadsheetId, range)
                .execute();

        final List<List<Object>> values = response.getValues();
        if (values == null || values.isEmpty()) {
            LOGGER.error("No data found.");
        } else {
            QuestionSet currentQuestionSet = null;
            for (final List row : values) {
                String questionText = null;
                try {
                    final String questionSet = (String) row.get(0);
                    if (questionSet.equals("END")) {
                        break;
                    }
                    questionText = (String) row.get(1);
                    final String questionImage = (String) row.get(2);
                    final String correctAnswer = (String) row.get(3);
                    final String answerExplained = (String) row.get(4);
                    if (!Strings.isNullOrEmpty(questionSet)) {
                        currentQuestionSet = new QuestionSet(Integer.parseInt(questionSet.trim()));
                        topic.addQuestionSet(currentQuestionSet);
                    }

                    final Question question = new Question(questionText);
                    for (int optionIndex = 5; optionIndex < row.size(); optionIndex++ ) {
                        String option = (String) row.get(optionIndex);
                        question.addOption(option);
                    }

                    switch (correctAnswer) {
                        case "A":
                            question.setCorrectAnswerIndex(0);
                            break;
                        case "B":
                            question.setCorrectAnswerIndex(1);
                            break;
                        case "C":
                            question.setCorrectAnswerIndex(2);
                            break;
                        case "D":
                            question.setCorrectAnswerIndex(3);
                            break;
                        case "E":
                            question.setCorrectAnswerIndex(4);
                            break;
                        case "F":
                            question.setCorrectAnswerIndex(5);
                            break;
                        case "G":
                            question.setCorrectAnswerIndex(6);
                            break;
                        case "H":
                            question.setCorrectAnswerIndex(7);
                            break;
                        case "I":
                            question.setCorrectAnswerIndex(8);
                            break;
                        default:
                            throw new IllegalStateException("Unexpected option");
                    }
                    Preconditions.checkArgument(currentQuestionSet != null);
                    currentQuestionSet.addQuestion(question);
                } catch (Exception e) {
                    LOGGER.error("Unable to parse question [{}]", questionText, e);
                }
            }
        }
        return topic;
    }

}
