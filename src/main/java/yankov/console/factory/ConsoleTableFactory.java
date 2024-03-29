package yankov.console.factory;

import yankov.console.Utils;
import yankov.console.model.Command;
import yankov.console.operations.ConsoleOperations;
import yankov.console.operations.FileOperations;
import yankov.console.table.*;
import yankov.console.table.viewer.ConsoleDateSelector;
import yankov.console.table.viewer.ConsoleMenu;
import yankov.console.table.viewer.ConsoleTableEditor;
import yankov.jfp.structures.Either;
import yankov.jfp.structures.tuples.Tuple;

import java.nio.file.Path;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

import static yankov.jfp.utils.ListUtils.*;

public class ConsoleTableFactory {
    public static ConsoleDateSelector createConsoleDateSelector(
            LocalDate firstDayOfMonth,
            int consoleLines,
            int consoleColumns,
            Consumer<LocalDate> select,
            ConsoleOperations consoleOperations) {
        return new ConsoleDateSelector(
                TableFactory.createDateTable(
                        DataFactory.createHeaderForDateConsoleSelector(),
                        DataFactory.createDataForDateConsoleSelector(firstDayOfMonth)
                ),
                consoleLines,
                consoleColumns,
                firstDayOfMonth,
                LocalDate::now,
                select,
                consoleOperations
        );
    }

    public static Either<String, ConsoleTableEditor> createConsoleTableEditor(
            Path csvFile,
            int lines,
            int columns,
            String title,
            ConsoleOperations consoleOperations,
            FileOperations fileOperations) {
        String csv = fileOperations.readFile(csvFile).orElse("");
        Either<String, Table<String>> table = TableParser.fromCsv(csv);
        if (table.getRight().isPresent()) {
            ConsoleTableEditor editor = new ConsoleTableEditor(
                    table.getRight().get(),
                    csvFile,
                    lines,
                    columns,
                    consoleOperations,
                    fileOperations);
            editor.setTitle(title);
            return Either.rightOf(editor);
        }
        return Either.leftOf(table.getLeft().orElse("Unable to create console table editor"));
    }

    public static ConsoleMenu createConsoleMenu(
            List<Tuple<String, List<Command>>> commands,
            int consoleLines,
            int consoleColumns,
            String title,
            ConsoleOperations consoleOperations) {
        List<Cell<String>> header = commands.stream().map(x -> new Cell<>(x._1(), false, y -> y)).toList();
        int numberOfRows = commands.stream().map(x -> x._2().size()).max(Comparator.naturalOrder()).orElse(0);
        int numberOfColumns = header.size();
        Command[][] tableData = new Command[numberOfRows][numberOfColumns];
        for (int i = 0; i < numberOfRows; i++) {
            for (int j = 0; j < numberOfColumns; j++) {
                if (i < commands.get(j)._2().size()) {
                    tableData[i][j] = commands.get(j)._2().get(i);
                } else {
                    tableData[i][j] = Utils.doNothing();
                }
            }
        }

        Supplier<Cell<Command>> emptyCommandCell = () -> new Cell<>(Utils.doNothing(), false, Command::getDescription);

        Table<Command> table = Table.from(
                header,
                fromArray2d(tableData)
                        .stream()
                        .map(x -> x.stream().map(y -> new Cell<>(y, false, Command::getDescription)).toList())
                        .toList(),
                emptyCommandCell
        ).getRight().orElse(Table.empty(emptyCommandCell));

        ConsoleMenu menu = new ConsoleMenu(table, consoleLines, consoleColumns, consoleOperations);
        menu.setTitle(title);
        return menu;
    }
}
