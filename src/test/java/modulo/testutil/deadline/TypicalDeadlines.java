package modulo.testutil.deadline;

import modulo.model.deadline.Deadline;

/**
 * A utility class containing a list of {@code Deadline} objects to be used in tests.
 */
public class TypicalDeadlines {
    public static final Deadline DO_TUTORIAL = new DeadlineBuilder()
            .withName("Do Tutorial")
            .withDueTime("2020-01-15 09:00")
            .withIsCompleted(true)
            .build();

    public static final Deadline PREPARE_FOR_LECTURE = new DeadlineBuilder()
            .withName("Prepare for lecture")
            .withDueTime("2020-01-23 09:00")
            .withIsCompleted(false)
            .build();


    private TypicalDeadlines() {
    } // prevents instantiation
}
