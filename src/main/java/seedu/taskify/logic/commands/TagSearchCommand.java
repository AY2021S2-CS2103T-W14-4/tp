package seedu.taskify.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.taskify.commons.core.Messages;
import seedu.taskify.logic.commands.exceptions.CommandException;
import seedu.taskify.model.Model;
import seedu.taskify.model.task.predicates.TagContainsKeywordsPredicate;

/**
 * TagSearchs and lists all tasks in address book whose tag contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class TagSearchCommand extends Command {

    public static final String COMMAND_WORD = "tag-search";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Searches for tasks based on the tags "
                                                       + "specified (case-insensitive) and displays them as a list "
                                                       + "with index numbers.\n"
                                                       + "Parameters: TAG [MORE_TAGS]...\n"
                                                       + "Example: " + COMMAND_WORD + " lab tutorial";

    public static final String MESSAGE_SWITCH_TO_HOME = "Switch back to home page to tag search!";

    private final TagContainsKeywordsPredicate predicate;

    public TagSearchCommand(TagContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {

        if (!CommandResult.isHomeTab()) {
            throw new CommandException(MESSAGE_SWITCH_TO_HOME);
        }
        requireNonNull(model);
        model.updateFilteredTaskList(predicate);
        return new CommandResult(String.format(Messages.MESSAGE_TASKS_LISTED_OVERVIEW,
                model.getFilteredTaskList().size()));

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                       || (other instanceof TagSearchCommand // instanceof handles nulls
                                   && predicate.equals(((TagSearchCommand) other).predicate)); // state check
    }
}
