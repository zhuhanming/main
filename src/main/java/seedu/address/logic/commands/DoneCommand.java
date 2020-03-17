package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.deadline.Deadline;
import seedu.address.model.event.Event;
import seedu.address.model.module.Module;

/**
 * Set a deadline to be done
 */
public class DoneCommand extends Command {

    public static final String COMMAND_WORD = "done";


    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finishing a task "
            + "Parameters: "
            + "INDEX "
            + PREFIX_MODULE + "MODULE "
            + PREFIX_NAME + "EVENT_NAME"
            + "\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_MODULE + "CS2103 "
            + PREFIX_NAME + "Tutorial";

    public static final String MESSAGE_SUCCESS = "Completed the task: %1$s";
    public static final String MESSAGE_MODULE_DOESNT_EXIST = "The specified module does not exist in the calendar";
    public static final String MESSAGE_EVENT_DOESNT_EXIST = "The specified event does not exist in the module";
    public static final String MESSAGE_DEADLINE_DOESNT_EXIST = "The specified event does not have any deadlines";
    private final Module toCheckModule;
    private final Event toCheckEvent;
    private final Index index;
    private boolean isDeadlinesExisted = false;

    public DoneCommand(Module module, Event event, Index index) {
        requireNonNull(module);
        requireNonNull(index);
        toCheckModule = module;
        this.toCheckEvent = event;
        this.index = index;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.findModule(toCheckModule) == null) {
            throw new CommandException(MESSAGE_MODULE_DOESNT_EXIST);
        }
        List<Event> eventList = model.findAllEvents(toCheckEvent);

        if (eventList.size() == 0) {
            throw new CommandException(MESSAGE_EVENT_DOESNT_EXIST);
        }

        if (eventList.size() > 0) {
            for (int i = 0; i < eventList.size(); i++) {
                if (eventList.get(i).getEventType().equals(toCheckEvent.getEventType())) {
                    List<Deadline> deadlineList = eventList.get(i).getDeadlines();
                    if (deadlineList.size() > 0) {
                        for (int j = 0; j < deadlineList.size(); j++) {
                            if (index.getOneBased() == j + 1) {
                                deadlineList.get(j).setCompleted(true);
                                isDeadlinesExisted = true;
                                break;
                            }
                        }
                    }
                }
            }
        }

        if (isDeadlinesExisted == false) {
            throw new CommandException(MESSAGE_DEADLINE_DOESNT_EXIST);
        }

        return new CommandResult(String.format(MESSAGE_SUCCESS, (
                toCheckModule.getModuleCode() + " " + toCheckEvent.getName())));
    }
}
