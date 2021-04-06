package seedu.taskify.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.taskify.testutil.TypicalTasks.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.taskify.model.Model;
import seedu.taskify.model.ModelManager;
import seedu.taskify.model.UserPrefs;
import seedu.taskify.model.task.Task;


/**
 * Contains integration tests (interaction with the Model) and unit tests for CompletedCommand.
 */
public class CompletedCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
    }

    @Test
    public void sameTaskList() {
        ObservableList<Task> firstTaskList = model.getCompletedFilteredTaskList();
        ObservableList<Task> secondTaskList = expectedModel.getCompletedFilteredTaskList();
        assert firstTaskList.equals(secondTaskList) == true;
    }


    @Test
    public void equals() {
        CommandResult.setHomeTab();
        final CompletedCommand standardCommand = new CompletedCommand();


        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));
    }
}