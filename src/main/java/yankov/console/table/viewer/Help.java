package yankov.console.table.viewer;

import yankov.console.ConsoleColor;
import yankov.console.Const;
import yankov.console.model.Command;
import yankov.jutils.StringUtils;
import yankov.jutils.functional.ImmutableList;

import java.util.Comparator;
import java.util.List;

public class Help {
    private static final String HELP_CMD_COLOR = ConsoleColor.LIGHT_YELLOW;
    private static final String HELP_KEY_BINDING_COLOR = ConsoleColor.YELLOW;
    private static final String HELP_DESC_COLOR = ConsoleColor.DARK_GRAY;

    private Mode prevMode;

    public Help() {
        this.prevMode = Const.DEFAULT_MODE;
    }

    public Mode getPrevMode() {
        return prevMode;
    }

    public void setPrevMode(Mode prevMode) {
        this.prevMode = prevMode;
    }

    public List<String> getHelp(ImmutableList<Command> commands) {
        int nameFieldSize = commands
                .stream()
                .map(x -> x.getName().length())
                .max(Comparator.naturalOrder())
                .orElse(15) + 1;
        int keyBindingFieldSize = commands
                .stream()
                .map(x -> x.getKeyBindingName().length())
                .max(Comparator.naturalOrder())
                .orElse(8) + 1;
        return commands
                .stream()
                .filter(x -> !x.getDescription().isEmpty())
                .map(x -> commandColoredHelp(x, nameFieldSize, keyBindingFieldSize))
                .toList();
    }

    private String commandColoredHelp(Command command, int nameFieldSize, int keyBindingFieldSize) {
        return HELP_CMD_COLOR +
                command.getName() +
                ConsoleColor.RESET +
                StringUtils.fill(nameFieldSize - command.getName().length(), ' ') +
                HELP_KEY_BINDING_COLOR +
                command.getKeyBindingName() +
                ConsoleColor.RESET +
                StringUtils.fill(keyBindingFieldSize - command.getKeyBindingName().length(), ' ') +
                HELP_DESC_COLOR +
                command.getDescription() +
                ConsoleColor.RESET;
    }
}
