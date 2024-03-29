package yankov.console.table.viewer;

import org.junit.Test;
import yankov.console.helpers.TestData;
import yankov.console.helpers.TestHelpers;

import java.util.List;

import static yankov.console.helpers.TestHelpers.listOf;
import static yankov.jfp.utils.ListUtils.*;

public class ConsoleTableViewerTest {
    @Test
    public void show() {
        List<TestData> testData = List.of(
                new TestData("test.csv", "hide-row-indexes", listOf("exit"), listOf("row-indexes off", "exit")),
                new TestData("test.csv", "show-row-indexes", listOf("row-indexes off", "exit"), listOf("row-indexes on", "exit")),
                new TestData("test.csv", "on-tab", listOf("exit"), listOf("tab", "exit")),
                new TestData("test.csv", "on-left", listOf("tab", "exit"), listOf("left", "exit")),
                new TestData("test.csv", "on-right", listOf("exit"), listOf("right", "exit")),
                new TestData("test.csv", "on-up", append(fillTab(5), "exit"), listOf("up", "exit")),
                new TestData("test.csv", "on-down", listOf("exit"), listOf("down", "exit")),
                new TestData("test.csv", "on-first-col", append(fillTab(4), "exit"), listOf("first-col", "exit")),
                new TestData("test.csv", "on-last-col", listOf("exit"), listOf("last-col", "exit")),
                new TestData("multi-page.csv", "on-page-down", listOf("exit"), listOf("page-down", "exit")),
                new TestData("multi-page.csv", "on-page-up", listOf("page-down", "exit"), listOf("page-up", "exit")),
                new TestData("multi-page.csv", "on-first-row", appendAll(fillTab(220), List.of("page-down", "page-down", "exit")), listOf("first-row", "exit")),
                new TestData("multi-page.csv", "on-last-row", listOf("exit"), listOf("last-row", "exit")),
                new TestData("empty.csv", "empty-file", listOf("exit"), listOf("exit")),
                new TestData("empty-table.csv", "empty-table", listOf("exit"), listOf("exit")),
                new TestData("quotes.csv", "quotes-csv", listOf("exit"), listOf("exit"))
        );

        TestHelpers.testConsoleTable(
                "console-table-viewer",
                testData,
                TestHelpers::getResourceTable,
                (t, l, c, f, fOps, cOps) -> new ConsoleTableViewer<>(t, l, c, cOps)
        );
    }

    private List<String> fillTab(int n) {
        return listFill(n, "tab");
    }
}
