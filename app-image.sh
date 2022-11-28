# jvm version number: 1, 2, 3, ...
export JVM_VERSION=11

export JVM_XMS=2G
export JVM_XMX=6G
export MAIN_CLASS=yankov.console.ConsoleTableEditorMain
export BEFORE="tput civis"
export AFTER="tput cnorm"

# array with application jar files, paths are relative to the project directory
export JARS=("target/console-utils-latest-jar-with-dependencies.jar" "lib/raw-console-input-latest-jar-with-dependencies.jar")

export APPLICATION_NAME=table-editor
export IS_TERMINAL_APPLICATION=true

# if empty default icon will be used
export ICON_FILE=""

# application parameters, parameters provided on AppImage run will be appended after this list
export PARAMETERS="$(tput lines) $(tput cols)"
