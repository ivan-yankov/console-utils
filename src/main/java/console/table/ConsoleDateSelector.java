package console.table;

import console.Const;
import console.factory.DataFactory;
import console.operations.ConsoleOperations;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.function.Consumer;

public class ConsoleDateSelector extends ConsoleTableViewer<LocalDate> {
    private final Consumer<LocalDate> select;
    private LocalDate firstDayOfMonth;

    public ConsoleDateSelector(Table<LocalDate> table, int consoleLines, int consoleColumns, LocalDate firstDayOfMonth, Consumer<LocalDate> select, ConsoleOperations consoleOperations) {
        super(table, consoleLines, consoleColumns, consoleOperations);
        this.firstDayOfMonth = firstDayOfMonth;
        this.select = select;
        setTitle(createTitle());
        setFocusOnToday();
    }

    @Override
    protected void onEnter() {
        LocalDate value = getTable().getCellValue(getFocus().getRow(), getFocus().getCol());
        if (!value.equals(Const.INVALID_DATE)) {
            select.accept(value);
            setMode(Mode.CLOSE);
        }
    }

    @Override
    protected void onPageUp() {
        firstDayOfMonth = firstDayOfMonth.minusMonths(1);
        getTable().updateData(DataFactory.createDataForDateConsoleSelector(firstDayOfMonth));
        setTitle(createTitle());
        getFocus().setRow(0);
        getFocus().setCol(0);
    }

    @Override
    protected void onPageDown() {
        firstDayOfMonth = firstDayOfMonth.plusMonths(1);
        getTable().updateData(DataFactory.createDataForDateConsoleSelector(firstDayOfMonth));
        setTitle(createTitle());
        getFocus().setRow(0);
        getFocus().setCol(0);
    }

    @Override
    protected String getPageUpLabel() {
        return "Prev month";
    }

    @Override
    protected String getPageDownLabel() {
        return "Next month";
    }

    @Override
    protected String getEnterLabel() {
        return "Accept";
    }

    private String createTitle() {
        return firstDayOfMonth.getMonth().getDisplayName(TextStyle.FULL, Locale.US) + ", " + firstDayOfMonth.getYear();
    }

    private void setFocusOnToday() {
        LocalDate today = LocalDate.now();
        for (int i = 0; i < getTable().getRowCount(); i++) {
            for (int j = 0; j < getTable().getColCount(); j++) {
                if (getTable().getCellValue(i, j).equals(today)) {
                    getFocus().setRow(i);
                    getFocus().setCol(j);
                }
            }
        }
    }
}
