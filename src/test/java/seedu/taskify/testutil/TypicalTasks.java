package seedu.taskify.testutil;

import static seedu.taskify.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.taskify.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.taskify.logic.commands.CommandTestUtil.VALID_DESCRIPTION_AMY;
import static seedu.taskify.logic.commands.CommandTestUtil.VALID_DESCRIPTION_BOB;
import static seedu.taskify.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.taskify.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.taskify.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.taskify.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.taskify.model.Taskify;
import seedu.taskify.model.task.StatusType;
import seedu.taskify.model.task.Task;

/**
 * A utility class containing a list of {@code Task} objects to be used in tests.
 */
public class TypicalTasks {

    public static final Task ALICE = new TaskBuilder().withName("Alice Pauline")
                 .withAddress("123, Jurong West Ave 6, #08-111").withStatus(StatusType.NOT_DONE)
                 .withDescription("94351253")
                 .withTags("friends").build();
    public static final Task BENSON = new TaskBuilder().withName("Benson Meier")
                  .withAddress("311, Clementi Ave 2, #02-25")
                  .withStatus(StatusType.NOT_DONE).withDescription("98765432")
                  .withTags("owesMoney", "friends").build();
    public static final Task CARL = new TaskBuilder().withName("Carl Kurz").withDescription("95352563")
                    .withStatus(StatusType.NOT_DONE).withAddress("wall street").build();
    public static final Task DANIEL = new TaskBuilder().withName("Daniel Meier").withDescription("87652533")
                  .withStatus(StatusType.NOT_DONE).withAddress("10th street").withTags("friends").build();
    public static final Task ELLE = new TaskBuilder().withName("Elle Meyer").withDescription("9482224")
                    .withStatus(StatusType.NOT_DONE).withAddress("michegan ave").build();
    public static final Task FIONA = new TaskBuilder().withName("Fiona Kunz").withDescription("9482427")
                     .withStatus(StatusType.NOT_DONE).withAddress("little tokyo").build();
    public static final Task GEORGE = new TaskBuilder().withName("George Best").withDescription("9482442")
                                              .withStatus(StatusType.NOT_DONE).withAddress("4th street").build();

    // Manually added
    public static final Task HOON = new TaskBuilder().withName("Hoon Meier").withDescription("8482424")
                                            .withStatus(StatusType.NOT_DONE).withAddress("little india").build();
    public static final Task IDA = new TaskBuilder().withName("Ida Mueller").withDescription("8482131")
                                           .withStatus(StatusType.NOT_DONE).withAddress("chicago ave").build();

    // Manually added - Task's details found in {@code CommandTestUtil}
    public static final Task AMY = new TaskBuilder().withName(VALID_NAME_AMY).withDescription(VALID_DESCRIPTION_AMY)
               .withStatus(StatusType.NOT_DONE).withAddress(VALID_ADDRESS_AMY).withTags(VALID_TAG_FRIEND).build();
    public static final Task BOB = new TaskBuilder().withName(VALID_NAME_BOB).withDescription(VALID_DESCRIPTION_BOB)
               .withStatus(StatusType.NOT_DONE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND)
               .build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalTasks() {
    } // prevents instantiation

    /**
     * Returns an {@code TaskifyParser} with all the typical tasks.
     */
    public static Taskify getTypicalAddressBook() {
        Taskify ab = new Taskify();
        for (Task task : getTypicalTasks()) {
            ab.addTask(task);
        }
        return ab;
    }

    public static List<Task> getTypicalTasks() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}
