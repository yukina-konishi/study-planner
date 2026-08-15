package studyplanner.model;

import java.util.List;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
// import java.util.Collections;


import studyplanner.model.*;
import studyplanner.exception.*;

public class StudyPlan {
    private final String name;
    private final List<StudySession> sessions;

    private StudyPlan(String name, List<StudySession> sessions){
        this.name = name;
        this.sessions = sessions;
    }

    public static StudyPlan loadPlan(String filepath) throws IOException, StudyPlanFormatException {
        List<StudySession> sessions = new ArrayList<>();
        List<String> seenNames = new ArrayList<>();

        String planName = null;
        boolean insideSession = false;

        String name = null;
        String topic = null;
        String method = null;
        String  outcomeLine = null;

        try (BufferedReader reader = new BufferedReader(new FileReader(filepath))){
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()){
                    continue;
                }
                if (planName == null) {
                    planName = readPlanName(line); // helper 1
                    continue;
                }
                if (line.equals("SESSION")){
                    insideSession = true;
                    name = null;
                    topic = null;
                    method = null;
                    outcomeLine = null;
                    continue;
                }
                if (line.equals("ENDSESSION")){
                    StudySession session = buildSession(name, topic, method, outcomeLine, seenNames); // helper 2
                    sessions.add(session);
                    insideSession = false;
                    continue;
                }
                if (!insideSession){
                    throw new StudyPlanFormatException("Line is outside the SESSION block: " + line);
                }

                String key = readKey(line); // helper 3
                String value = readValue(line); // helper 4

                if (key.equals("name")){
                    name = value;
                } else if (key.equals("topic")){
                    topic = value;
                } else if (key.equals("method")){
                    method = value;
                } else if (key.equals("outcome")){
                    outcomeLine = value;
                } else throw new StudyPlanFormatException("Unknown property: " + key);
            }
        }
        if (insideSession) {
            throw new StudyPlanFormatException("File ended without a closing ENDSESSION");
        }
        if (planName == null) {
            throw new StudyPlanFormatException("File didn't contain a PLAN: line");
        }
        if (sessions.isEmpty()){
            throw new StudyPlanFormatException("Plan must contain at least one session");
        }
        return new StudyPlan(planName, sessions);
    }

    // helper functions
    // 1
    private static String readPlanName(String line) throws StudyPlanFormatException {
        if (!line.startsWith("PLAN:")) {
            throw new StudyPlanFormatException("File must start with PLAN:");
        }
        String result = line.substring("PLAN:".length()).trim();
        if (result.isEmpty()){
            throw new StudyPlanFormatException("Plan name must not be blank");
        }
        return result;
    } 

    //2
    private static StudySession buildSession(String name, String topic, String method, String outcomeLine, List<String> seenNames) throws StudyPlanFormatException {
        if (name == null || topic == null || method == null || outcomeLine == null){
            throw new StudyPlanFormatException("Session is missing one or more required properties");
        }
        if (seenNames.contains(name)){
            throw new StudyPlanFormatException("Duplicate session name: " + name);
        }
        seenNames.add(name);

        SessionOutcome outcome = parseOutcome(outcomeLine);
        return new StudySession(name, topic, method, outcome);
    }

    //3
    private static String readKey(String line) throws StudyPlanFormatException {
        int equalsIndex = line.indexOf('=');
        if (equalsIndex == -1){
            throw new StudyPlanFormatException("Expected key=value but got: " + line);
        }
        return line.substring(0, equalsIndex).trim();
    }

    //4
    private static String readValue(String line){
        int equalsIndex = line.indexOf('=');
        return line.substring(equalsIndex + 1).trim();
    }

    private static SessionOutcome parseOutcome(String outcomeLine) throws StudyPlanFormatException {
        String[] parts = outcomeLine.split(",");
        OutcomeType[] types = new OutcomeType[parts.length];
        for (int i = 0; i < parts.length; i++){
            String part = parts[i].trim();
            types[i] = parseOutcomeType(part);
        }

        return new SessionOutcome(types);
    }

    private static OutcomeType parseOutcomeType(String value) throws StudyPlanFormatException {
        if (value.equals("MINUTES_STUDIED")){
            return OutcomeType.MINUTES_STUDIED;
        } else if (value.equals("TOPIC_SUMMARY")){
            return OutcomeType.TOPIC_SUMMARY;
        } else if (value.equals("COMPLETED")){
            return OutcomeType.COMPLETED;
        } else if (value.equals("FLASHCARDS_REVIEWED")){
            return OutcomeType.FLASHCARDS_REVIEWED;
        } else {
            throw new StudyPlanFormatException("Unknown outcome type: " + value);
        }
    }

    public void run(){
        System.out.println("Running study plan: " + this.name);
        for (StudySession s : this.sessions){
            System.out.println("- "+ s.getName() + ": " + s.simulateResult());
        }
    }

    @Override
    public String toString(){
        return "StudyPlan{name=" + this.name + ", sessions=" + this.sessions.size() + "}";
    }


    // getters
    public String getName(){
        return this.name;
    }

    public List<StudySession> getSessions(){
        List<StudySession> copy = new ArrayList<>();
        for (StudySession s : this.sessions){
            copy.add(s);
        }
        return copy;
    }

}