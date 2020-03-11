package seedu.address.storage;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.AddressBook;
import seedu.address.model.Calendar;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyCalendar;
import seedu.address.model.entity.CalendarItem;
import seedu.address.model.person.Person;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * An Immutable AddressBook that is serializable to JSON format.
 */
@JsonRootName(value = "calendar")
class JsonSerializableCalendar {

    public static final String MESSAGE_DUPLICATE_CALENDAR_ITEM = "Calendar item list contains duplicate calendar item(s).";

    private final List<JsonAdaptedCalendarItem> calendarItems = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableCalendar} with the given persons.
     */
    @JsonCreator
    public JsonSerializableCalendar(@JsonProperty("calendarItems") List<JsonAdaptedCalendarItem> calendarItems) {
        this.calendarItems.addAll(calendarItems);
    }

    /**
     * Converts a given {@code ReadOnlyCalendar} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableCalendar}.
     */
    public JsonSerializableCalendar(ReadOnlyCalendar source) {
        //calendarItems.addAll(source.getCalendarItemList().stream().map(JsonAdaptedCalendarItem::new).collect(Collectors.toList()));
    }

    /**
     * Converts this address book into the model's {@code AddressBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public Calendar toModelType() throws IllegalValueException {
        Calendar calendar = new Calendar();
        for (JsonAdaptedCalendarItem jsonAdaptedCalendarItem : calendarItems) {
            //CalendarItem calendarItem = jsonAdaptedCalendarItem.toModelType();
            //if (calendar.hasCalendarItem(calendarItem)) {
            //    throw new IllegalValueException(MESSAGE_DUPLICATE_CALENDAR_ITEM);
            //}
            //calendar.addCalendarItem(calendarItem);
        }
        return calendar;
    }

}