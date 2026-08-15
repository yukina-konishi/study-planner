package studyplanner.model;

import studyplanner.exception.StudyPlanFormatException;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

public class StudyPlanTest {
    @Test
    public void loadPlanTest() throws Exception {
        StudyPlan plan =  StudyPlan.loadPlan("plans/plan_Midterm.txt");
        assertEquals("Midterm Prep", plan.getName());
        List<StudySession> sessions = plan.getSessions();
        assertEquals(2, sessions.size());
        assertEquals("review1", sessions.get(0).getName());
    }

    @Test
    public void loadPlanDuplicateSessionTest(){
        assertThrows(StudyPlanFormatException.class, () -> {
            StudyPlan.loadPlan("plans/plan_DuplicateName.txt");
        });
    }
}
