package gojosatoru.handlers;

import gojosatoru.exceptions.MissingArgumentException;
import gojosatoru.tasks.Deadline;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Handler {
  String dateFormat = "d/M/yyyy HHmm";
  DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateFormat);
  public Deadline handleDeadlines(String input) throws MissingArgumentException {
    String[] parts = input.split("/by ");
    if (parts.length < 2 || parts[1].trim().isEmpty()) {
      throw new MissingArgumentException("   ____________________________________________________________\n  " +
          "You either got no /by or name for your deadline.. I don't know when your thing ends, a sorcerer should always chant when they cast their spell.\n" +
          "   ____________________________________________________________\n");
    }
    try {
      LocalDateTime deadlineBy = LocalDateTime.parse(parts[1].trim(), formatter);
    } catch (DateTimeParseException e) {
      throw new MissingArgumentException("   ____________________________________________________________\n  " +
          "Your formatting for the deadline is wrong. It should be " + dateFormat + " \n" +
          "   ____________________________________________________________\n") ;
    }
    String description = parts[0].replace("deadline ", "").trim() + " (by: " + parts[1].trim() + ")";
    return new Deadline(description, deadlineBy);
  }


}
