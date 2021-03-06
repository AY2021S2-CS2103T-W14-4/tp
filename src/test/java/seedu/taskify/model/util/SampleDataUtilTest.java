package seedu.taskify.model.util;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.taskify.testutil.TypicalTasks.TASK_1;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.taskify.model.ReadOnlyTaskify;
import seedu.taskify.model.task.Task;
import seedu.taskify.testutil.TaskBuilder;

class SampleDataUtilTest {

    @Test
    public void getSampleTaskifyData_equalsDifferentTaskify_fail() {
        Task editedSampleTask = new TaskBuilder(TASK_1).withName("test").build();
        List<Task> sampleTasks = Arrays.asList(TASK_1, editedSampleTask);
        TaskifyStub sampleTaskify = new TaskifyStub(sampleTasks);

        assertNotEquals(sampleTaskify.getTaskList(), SampleDataUtil.getSampleTaskifyData().getTaskList());
    }

    /**
     * A stub ReadOnlyTaskify whose tasks list can violate interface constraints.
     */
    private static class TaskifyStub implements ReadOnlyTaskify {
        private final ObservableList<Task> tasks = FXCollections.observableArrayList();

        TaskifyStub(Collection<Task> tasks) {
            this.tasks.setAll(tasks);
        }

        @Override
        public ObservableList<Task> getTaskList() {
            return tasks;
        }
    }
}
