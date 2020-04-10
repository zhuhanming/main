package modulo.testutil.deadline;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import modulo.model.Name;
import modulo.model.deadline.Deadline;

/**
 * A utility class to help with building Deadline objects.
 */
public class DeadlineBuilder {
    public static final DateTimeFormatter DATETIME_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static final String DEFAULT_DEADLINE_NAME = "Do your homework";
    public static final LocalDateTime DEFAULT_DEADLINE_DUE_TIME = LocalDateTime.parse("2020-01-15 09:00",
            DATETIME_FORMAT);
    public static final boolean DEFAULT_COMPLETION_STATUS = false;

    private Name name;
    private LocalDateTime dueTime;
    private boolean isCompleted;

    public DeadlineBuilder() {
        this.name = new Name(DEFAULT_DEADLINE_NAME);
        this.dueTime = DEFAULT_DEADLINE_DUE_TIME;
        this.isCompleted = DEFAULT_COMPLETION_STATUS;
    }

    /**
     * Initializes the DeadlineBuilder with the data of {@code deadlineToCopy}.
     */
    public DeadlineBuilder(Deadline deadlineToCopy) {
        name = deadlineToCopy.getName();
        dueTime = deadlineToCopy.getDueTime();
        isCompleted = deadlineToCopy.isCompleted();
    }

    /**
     * Sets the {@code Name} of the deadline that we are building.
     */
    public DeadlineBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Sets the {@code dueTime} of the deadline that we are building.
     */
    public DeadlineBuilder withDueTime(String dueTime) {
        this.dueTime = LocalDateTime.parse(dueTime, DATETIME_FORMAT);
        return this;
    }

    /**
     * Sets the completion status of the deadline that we are building.
     */
    public DeadlineBuilder withIsCompleted(boolean isCompleted) {
        this.isCompleted = isCompleted;
        return this;
    }

    public Deadline build() {
        return new Deadline(name, dueTime, isCompleted);
    }
}
