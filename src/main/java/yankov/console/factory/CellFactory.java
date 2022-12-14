package yankov.console.factory;

import yankov.console.ConsoleColor;
import yankov.console.Utils;
import yankov.console.table.Cell;

import java.time.LocalDate;

public class CellFactory {
    public static Cell<String> createStringCell(String value) {
        return new Cell<>(value, false, x -> x);
    }

    public static Cell<String> createEmptyStringCell() {
        return createStringCell("");
    }

    public static Cell<LocalDate> createDateCell(LocalDate date) {
        return new Cell<>(date, false, Utils::printDayFromDate);
    }

    public static Cell<LocalDate> createDateCell(LocalDate date, boolean inCurrentMonth) {
        if (inCurrentMonth) {
            return createDateCell(date);
        }
        return new Cell<>(date, false, Utils::printDayFromDate, ConsoleColor.DARK_GRAY);
    }
}
