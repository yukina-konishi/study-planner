package studyplanner.model;

import studyplanner.model.*;

public class StudySession {
    private final String name;
    private final String topic;
    private final String method;
    private final SessionOutcome outcome;

    public StudySession(String name, String topic, String method, SessionOutcome outcome){
        if (method.length() == 0){
            throw new IllegalArgumentException("Method must be privided!");
        }
        if (outcome == null){
            throw new IllegalArgumentException("It can't be null!");
        }
        this.name = name;
        this.topic = topic;
        this.method = method;
        this.outcome = outcome;
    }

    public String simulateResult(){
        OutcomeType[] types = this.outcome.getTypes();
        OutcomeType first = types[0];
        if (first == OutcomeType.MINUTES_STUDIED){
            return "You studied 25 min!";
        } else if (first == OutcomeType.TOPIC_SUMMARY){
            return "Sample summary";
        } else if (first == OutcomeType.COMPLETED){
            return "true";
        } else if (first == OutcomeType.FLASHCARDS_REVIEWED){
            return "[1,2,3]";
        } else {
            return "unknown";
        }
       
    }

    // getters
    public String getName(){
        return this.name;
    }

    public String getTopic(){
        return this.topic;
    }

    public String getMethod(){
        return this.method;
    }

    public SessionOutcome getSessionOutcome(){
        return this.outcome;
    }
}