package studyplanner;

import studyplanner.model.StudyPlan;

public class Main {
    public static void main(String[] args) throws Exception {
       System.out.println("==== Demo 1 ====");
       StudyPlan midterm = StudyPlan.loadPlan("plans/plan_Midterm.txt");
       midterm.run();

       System.out.println();
       System.out.println("==== Demo 2 ====");
       StudyPlan finals = StudyPlan.loadPlan("plans/plan_Finalsweek.txt");
       finals.run();
    }
}