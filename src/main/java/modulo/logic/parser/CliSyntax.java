package modulo.logic.parser;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {
    public static final Prefix PREFIX_NAME = new Prefix("n/");
    public static final Prefix PREFIX_MODULE = new Prefix("m/");
    public static final Prefix PREFIX_EVENT = new Prefix("e/");
    public static final Prefix PREFIX_FREQUENCY = new Prefix("f/");
    public static final Prefix PREFIX_REPEAT = new Prefix("r/");
    public static final Prefix PREFIX_ACADEMIC_YEAR = new Prefix("ay/");
    public static final Prefix PREFIX_SEMESTER = new Prefix("s/");
    public static final Prefix PREFIX_START_DATETIME = new Prefix("s/");
    public static final Prefix PREFIX_END_DATETIME = new Prefix("e/");
    public static final Prefix PREFIX_STOP_REPEAT = new Prefix("until/");
    public static final Prefix PREFIX_DIRECTORY = new Prefix("d/");
    public static final Prefix PREFIX_VENUE = new Prefix("v/");
}
