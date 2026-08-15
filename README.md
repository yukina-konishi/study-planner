# Study Planner

A small Java program that models a study session planner - inspired by an "agentic workflow" assignment from my university coursework, reimagined around a study-planning theme.

The program reads a study plan from a plain text file, builds Java objects from it, validates the format, and simulates running through each study session.

## What it demonstrates
- File I/O and custom text-format parsing
- Encapsulation (defensive copying, no leaked mutable state)
- Custom exceptions (`StudyPlanFormatException`)
- Enum-based type modeling (`OutcomeType`)
- JUnit testing

## Project Structure
```
src/studyplanner/
  model/       - StudyPlan, StudySession, SessionOutcome, OutcomeType
  exception/   - StudyPlanFormatException
  Main.java    - entry point, demos two sample plans
test/studyplanner/model/
  StudyPlanTest.java
plans/
  plan_Midterm.txt
  plan_FinalsWeek.txt
```

## File format
```
PLAN: <plan name>
SESSION
name=<unique session name>
topic=<what you're studying>
method=<how you're studying, e.g. flashcards, reading>
outcome=<OutcomeType, e.g. COMPLETED>
ENDSESSION
```

Valid `outcome` values: `MINUTES_STUDIED`, `TOPIC_SUMMARY`, `COMPLETED`, `FLASHCARDS_REVIEWED`

## Running it

Compile:
```
javac -d out src/studyplanner/model/*.java src/studyplanner/exception/*.java src/studyplanner/Main.java
```

Run:
```
java -cp out studyplanner.Main
```

Sample output:
```
==== Demo 1 ====
Running study plan: Midterm Prep
- review1: true
- practice1: You studied 25 min!

==== Demo 2 ====
Running study plan: Finals Week
- calc_review: true
- os_flashcards: [1,2,3]
- db_summary: Sample summary
- algo_timed_practice: You studied 25 min!
```

## Running Tests

Requires the [JUnit Platform Console Standalone](https://search.maven.org/artifact/org.junit.platform/junit-platform-console-standalone) jar (not included — download and place in a `lib/` folder).

Compile:
```
javac -cp "out;lib\junit-platform-console-standalone-6.1.3.jar" -d out test/studyplanner/model/StudyPlanTest.java
```

Run:
```
java -jar lib\junit-platform-console-standalone-6.1.3.jar execute -cp out --select-class=studyplanner.model.StudyPlanTest
```

## Background

This project reworks the structure of an "AgenticWorkflow" assignment from my Object-Oriented Programming course at ELTE, applying the same design patterns (file parsing, validation, encapsulation) to an original study-planning theme.