package seedu.taskify.logic.parser;

import static seedu.taskify.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.taskify.logic.commands.CommandTestUtil.ADDRESS_DESC_AMY;
import static seedu.taskify.logic.commands.CommandTestUtil.ADDRESS_DESC_BOB;
import static seedu.taskify.logic.commands.CommandTestUtil.DATE_DESC_AMY;
import static seedu.taskify.logic.commands.CommandTestUtil.DATE_DESC_BOB;
import static seedu.taskify.logic.commands.CommandTestUtil.DESCRIPTION_DESC_AMY;
import static seedu.taskify.logic.commands.CommandTestUtil.DESCRIPTION_DESC_BOB;
import static seedu.taskify.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.taskify.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static seedu.taskify.logic.commands.CommandTestUtil.INVALID_ADDRESS_DESC;
import static seedu.taskify.logic.commands.CommandTestUtil.INVALID_DATE_DESC;
import static seedu.taskify.logic.commands.CommandTestUtil.INVALID_DESCRIPTION_DESC;
import static seedu.taskify.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static seedu.taskify.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.taskify.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.taskify.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.taskify.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.taskify.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static seedu.taskify.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.taskify.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.taskify.logic.commands.CommandTestUtil.VALID_DATE_AMY;
import static seedu.taskify.logic.commands.CommandTestUtil.VALID_DATE_BOB;
import static seedu.taskify.logic.commands.CommandTestUtil.VALID_DESCRIPTION_AMY;
import static seedu.taskify.logic.commands.CommandTestUtil.VALID_DESCRIPTION_BOB;
import static seedu.taskify.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.taskify.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.taskify.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.taskify.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.taskify.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.taskify.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.taskify.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.taskify.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.taskify.testutil.TypicalIndexes.INDEX_FIRST_TASK;
import static seedu.taskify.testutil.TypicalIndexes.INDEX_SECOND_TASK;
import static seedu.taskify.testutil.TypicalIndexes.INDEX_THIRD_TASK;

import org.junit.jupiter.api.Test;

import seedu.taskify.commons.core.index.Index;
import seedu.taskify.logic.commands.EditCommand;
import seedu.taskify.logic.commands.EditCommand.EditTaskDescriptor;
import seedu.taskify.model.tag.Tag;
import seedu.taskify.model.task.Address;
import seedu.taskify.model.task.Date;
import seedu.taskify.model.task.Description;
import seedu.taskify.model.task.Email;
import seedu.taskify.model.task.Name;
import seedu.taskify.testutil.EditTaskDescriptorBuilder;

public class EditCommandParserTest {

    private static final String TAG_EMPTY = " " + PREFIX_TAG;

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE);

    private EditCommandParser parser = new EditCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_NAME_AMY, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", EditCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + NAME_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + NAME_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1" + INVALID_NAME_DESC, Name.MESSAGE_CONSTRAINTS); // invalid name
        assertParseFailure(parser, "1" + INVALID_DESCRIPTION_DESC,
                Description.MESSAGE_CONSTRAINTS); // invalid description
        assertParseFailure(parser, "1" + INVALID_EMAIL_DESC, Email.MESSAGE_CONSTRAINTS); // invalid email
        assertParseFailure(parser, "1" + INVALID_ADDRESS_DESC, Address.MESSAGE_CONSTRAINTS); // invalid address
        assertParseFailure(parser, "1" + INVALID_DATE_DESC, Date.MESSAGE_CONSTRAINTS); // invalid date
        assertParseFailure(parser, "1" + INVALID_TAG_DESC, Tag.MESSAGE_CONSTRAINTS); // invalid tag

        // invalid description followed by valid email
        assertParseFailure(parser, "1" + INVALID_DESCRIPTION_DESC + EMAIL_DESC_AMY, Description.MESSAGE_CONSTRAINTS);

        // valid description followed by invalid description.
        // The test case for invalid description followed by valid description
        // is tested at {@code parse_invalidValueFollowedByValidValue_success()}
        assertParseFailure(parser, "1" + DESCRIPTION_DESC_BOB
                + INVALID_DESCRIPTION_DESC, Description.MESSAGE_CONSTRAINTS);

        // while parsing {@code PREFIX_TAG} alone will reset the tags of the {@code Task} being edited,
        // parsing it together with a valid tag results in error
        assertParseFailure(parser, "1" + TAG_DESC_FRIEND + TAG_DESC_HUSBAND + TAG_EMPTY, Tag.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + TAG_DESC_FRIEND + TAG_EMPTY + TAG_DESC_HUSBAND, Tag.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + TAG_EMPTY + TAG_DESC_FRIEND + TAG_DESC_HUSBAND, Tag.MESSAGE_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, "1" + INVALID_NAME_DESC + INVALID_EMAIL_DESC + VALID_ADDRESS_AMY
                        + VALID_DESCRIPTION_AMY, Name.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_TASK;
        String userInput = targetIndex.getOneBased() + DESCRIPTION_DESC_BOB + TAG_DESC_HUSBAND + EMAIL_DESC_AMY
                + ADDRESS_DESC_AMY + DATE_DESC_AMY + NAME_DESC_AMY + TAG_DESC_FRIEND;

        EditTaskDescriptor descriptor = new EditTaskDescriptorBuilder().withName(VALID_NAME_AMY)
                    .withDescription(VALID_DESCRIPTION_BOB).withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY)
                    .withDate(VALID_DATE_AMY).withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST_TASK;
        String userInput = targetIndex.getOneBased() + DESCRIPTION_DESC_BOB + EMAIL_DESC_AMY;

        EditTaskDescriptor descriptor = new EditTaskDescriptorBuilder().withDescription(VALID_DESCRIPTION_BOB)
                                                .withEmail(VALID_EMAIL_AMY).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // name
        Index targetIndex = INDEX_THIRD_TASK;
        String userInput = targetIndex.getOneBased() + NAME_DESC_AMY;
        EditTaskDescriptor descriptor = new EditTaskDescriptorBuilder().withName(VALID_NAME_AMY).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // phone
        userInput = targetIndex.getOneBased() + DESCRIPTION_DESC_AMY;
        descriptor = new EditTaskDescriptorBuilder().withDescription(VALID_DESCRIPTION_AMY).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // email
        userInput = targetIndex.getOneBased() + EMAIL_DESC_AMY;
        descriptor = new EditTaskDescriptorBuilder().withEmail(VALID_EMAIL_AMY).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // address
        userInput = targetIndex.getOneBased() + ADDRESS_DESC_AMY;
        descriptor = new EditTaskDescriptorBuilder().withAddress(VALID_ADDRESS_AMY).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // date
        userInput = targetIndex.getOneBased() + DATE_DESC_AMY;
        descriptor = new EditTaskDescriptorBuilder().withDate(VALID_DATE_AMY).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // tags
        userInput = targetIndex.getOneBased() + TAG_DESC_FRIEND;
        descriptor = new EditTaskDescriptorBuilder().withTags(VALID_TAG_FRIEND).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_acceptsLast() {
        Index targetIndex = INDEX_FIRST_TASK;
        String userInput = targetIndex.getOneBased() + DESCRIPTION_DESC_AMY + ADDRESS_DESC_AMY + EMAIL_DESC_AMY
               + TAG_DESC_FRIEND + DESCRIPTION_DESC_AMY + ADDRESS_DESC_AMY + EMAIL_DESC_AMY + TAG_DESC_FRIEND
                + DESCRIPTION_DESC_BOB + ADDRESS_DESC_BOB + DATE_DESC_AMY + DATE_DESC_BOB
                + EMAIL_DESC_BOB + TAG_DESC_HUSBAND;

        EditTaskDescriptor descriptor = new EditTaskDescriptorBuilder().withDescription(VALID_DESCRIPTION_BOB)
                .withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB).withDate(VALID_DATE_BOB)
                .withTags(VALID_TAG_FRIEND, VALID_TAG_HUSBAND)
                .build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_invalidValueFollowedByValidValue_success() {
        // no other valid values specified
        Index targetIndex = INDEX_FIRST_TASK;
        String userInput = targetIndex.getOneBased() + INVALID_DESCRIPTION_DESC + DESCRIPTION_DESC_BOB;
        EditTaskDescriptor descriptor = new EditTaskDescriptorBuilder().withDescription(VALID_DESCRIPTION_BOB).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // other valid values specified
        userInput = targetIndex.getOneBased() + EMAIL_DESC_BOB + INVALID_DESCRIPTION_DESC + ADDRESS_DESC_BOB
                            + DESCRIPTION_DESC_BOB;
        descriptor = new EditTaskDescriptorBuilder().withDescription(VALID_DESCRIPTION_BOB).withEmail(VALID_EMAIL_BOB)
                             .withAddress(VALID_ADDRESS_BOB).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_resetTags_success() {
        Index targetIndex = INDEX_THIRD_TASK;
        String userInput = targetIndex.getOneBased() + TAG_EMPTY;

        EditTaskDescriptor descriptor = new EditTaskDescriptorBuilder().withTags().build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
