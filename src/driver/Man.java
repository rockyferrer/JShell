package driver;

import driver.Exceptions.InvalidNumberOfArgsException;

/**
 * Created by Abhishek on 21/02/16.
 * 
 * The Man class handles the "man" command. It prints the manual for a certain
 * command.
 */
public class Man extends JCommand {

  // list of commands and corresponding manuals
  private static String[][] commandsAndManuals =
      new String[][] {{"exit", "exit\n\n" + "Quit the program."},
          {"mkdir",
              "mkdir DIR\n\n"
                  + "Takes optional arguments [ > OUTFILE] or [ >> OUTFILE], which"
                  + "\nreplaces/appends the result of this command in OUTFILE.\n\n"
                  + "Takes mandatory argument DIR, which is a directory.\n\n"
                  + "Create directories, each of which may be relative\n"
                  + "to the current directory or may be a full path."},
      {"cd",
          "cd DIR\n\n"
              + "Takes optional arguments [ > OUTFILE] or [ >> OUTFILE], which"
              + "\nreplaces/appends the result of this command in OUTFILE.\n\n"
              + "Takes mandatory argument DIR, which is a directory.\n\n"
              + "Change directory to DIR, which may be relative\nto the "
              + "current directory or may be a full path.\n\nAs with Unix"
              + ", .. means a parent directory and\na . means the current "
              + "directory. The directory\nmust be /, the forward "
              + "slash. The foot of\nthe file system is a singleslash: /."},
      {"ls",
          "ls [-R] [PATH ...]\n\n"
              + "Takes optional arguments [ > OUTFILE] or [ >> OUTFILE], which"
              + "\nreplaces/appends the result of this command in OUTFILE.\n\n"
              + "Takes optional arguments [-R] and [PATH ...].\n\n"
              + "If -R is present, recursively list all subdirectories.\n\n"
              + "If no paths are given, print the contents (file or directory)"
              + "\nof the current directory, with a new line following\neach "
              + "of the content (file or directory).\n\nOtherwise, for each "
              + "path p, the order listed:\n\t- If p specifies a file, print "
              + "p\n\t- If p specifies a directory, print p, a colon, then\n\t"
              + "  the contents of that directory, then an extra new line.\n\t"
              + "- If p does not exist, print a suitable message."},
      {"pwd",
          "pwd\n\n"
              + "Takes optional arguments [ > OUTFILE] or [ >> OUTFILE], which"
              + "\nreplaces/appends the result of this command in OUTFILE.\n\n"
              + "Print the current working directory"
              + " (including the whole path)."},
      {"pushd",
          "pushd DIR\n\n"
              + "Takes optional arguments [ > OUTFILE] or [ >> OUTFILE], which"
              + "\nreplaces/appends the result of this command in OUTFILE.\n\n"
              + "Takes mandatory argument DIR, which is a directory.\n\n"
              + "Saves the current working directory by pushing onto "
              + "directory stack\nand then changes the new current working "
              + "directory to DIR.\nThe push must be consistent as per the "
              + "LIFO behavior of a stack.\nThe pushd command saves the old "
              + "current working directory in\ndirectory stack so that it can "
              + "be returned to at any time (via the\npopd command). The size "
              + "of the directory stack is dynamic and\ndependent on the pushd"
              + " and the popd commands."},
      {"popd",
          "popd\n\n"
              + "Takes optional arguments [ > OUTFILE] or [ >> OUTFILE], which"
              + "\nreplaces/appends the result of this command in OUTFILE.\n\n"
              + "Remove the top entry from the directory stack, and cd "
              + "into it.\nThe removal must be consistent as per the LIFO "
              + "behavior of a stack.\nThe popd command removes the top most "
              + "directory from the directory stack\nand makes it the current "
              + "working directory. If there is no directory onto\nthe stack,"
              + "then give appropriate error message."},
      {"history",
          "history [number]\n\n"
              + "Takes optional arguments [ > OUTFILE] or [ >> OUTFILE], which"
              + "\nreplaces/appends the result of this command in OUTFILE.\n\n"
              + "Takes optional argument number, which is the number of "
              + "previous\ncommands that will be printed.\n\nThis command will"
              + " print out recent commands, one command per line.\nCommands "
              + "will include most recent command, history, and any\n"
              + "syntactical errors, as well."},
      {"cat",
          "cat FILE1 [FILE2 ...]\n\n"
              + "Takes optional arguments [ > OUTFILE] or [ >> OUTFILE], which"
              + "\nreplaces/appends the result of this command in OUTFILE.\n\n"
              + "Takes mandatory argument FILE1, which is a file.\n"
              + "Takes optional argument(s), [FILE2 ...], which are files.\n\n"
              + "Display the contents of FILE1 and other files (i.e. File2 "
              + "...)\nconcatenated in the shell. Three line breaks\n"
              + "are used to separate the contents of one file from\nthe"
              + " other file. For any file that contains invalid path,\n"
              + "an appropriate error is displayed for that path only.\n"
              + "All other valid paths are still shown on the console."},
      {"echo",
          "echo STRING [> OUTFILE]\nor\necho STRING >> OUTFILE\n\n"
              + "Takes mandatory argument STRING, which a string "
              + "of characters surrounded by double quotation marks.\n\n"
              + "Overwrite OUTFILE with STRING:\n\tTakes optional argument "
              + "(with \">\") [> OUTFILE], where OUTFILE is the output "
              + "file.\n\nIf OUTFILE is not provided, print STRING on the "
              + "shell. Otherwise,\noverwrite STRING into file OUTFILE. "
              + "This creates a new file\nif OUTFILE does not exists and "
              + "erases the old contents if\nOUTFILE already exists. In "
              + "either case, the only thing in\nOUTFILE should be STRING.\n\n"
              + "Append STRING to OUTFILE:\n\tTakes mandatory "
              + "argument (with \">>\") >> OUTFILE, where OUTFILE is "
              + "the output file.\n\nAppend STRING to file, OUTFILE. "
              + "This creates a new file if OUTFILE\ndoes not exist to insert "
              + "STRING into it and appends STRING\nto the old contents if "
              + "OUTFILE already exists."},
      {"mv",
          "mv OLDPATH NEWPATH\n\n"
              + "Takes optional arguments [ > OUTFILE] or [ >> OUTFILE], which"
              + "\nreplaces/appends the result of this command in OUTFILE.\n\n"
              + "Move item from OLDPATH to NEWPATH.\n\nBoth OLDPATH and "
              + "NEWPATH may be relative to the current directory\nor may be "
              + "full paths. If NEWPATH is a directory, move the\nitem "
              + "into the directory."},
      {"cp",
          "cp OLDPATH NEWPATH\n\n"
              + "Takes optional arguments [ > OUTFILE] or [ >> OUTFILE], which"
              + "\nreplace/appends the result of this command in OUTFILE.\n\n"
              + "Copy item in OLDPATH to NEWPATH.\n\nBoth OLDPATH and NEWPATH "
              + "may be relative to the current directory\nor may be full"
              + " paths. If NEWPATH is a directory, move the item\n"
              + "into the directory. If OLDPATH is a directory, recursively\n"
              + "copy the contents."},
      {"get",
          "get URL\n\n"
              + "Takes optional arguments [ > OUTFILE] or [ >> OUTFILE], which"
              + "\nreplaces/appends the result of this command in OUTFILE.\n\n"
              + "URL is a web address.\n\nRetrieve the file at that URL and "
              + "add it to the current\nworking directory. The file could "
              + "be a text file or\na simple html file."},
      {"grep",
          "grep [-R] REGEX PATH...\n\n"
              + "Takes optional arguments [ > OUTFILE] or [ >> OUTFILE], which"
              + "\nreplaces/appends the result of this command in OUTFILE.\n\n"
              + "If –R is not supplied, prints any lines containing REGEX\n"
              + "in PATH, which must be a file. If –R is supplied, and PATH\n"
              + "is a directory, recursively traverses the directory and, for"
              + "\nall lines in all files that contain REGEX, prints the path"
              + " to\nthe file (including the filename), then a colon, then "
              + "the line\nthat contained REGEX."},
      {"!",
          "!number\n\n"
              + "Takes optional arguments [ > OUTFILE] or [ >> OUTFILE], which"
              + "\nreplaces/appends the result of this command in OUTFILE.\n\n"
              + "Recalls any of previous history by its number (>=1)"
              + " preceded by an exclamation point (!)."},
      {"man",
          "man CMD\n\n"
              + "Takes optional arguments [ > OUTFILE] or [ >> OUTFILE], which"
              + "\nreplaces/appends the result of this command in OUTFILE.\n\n"
              + "Takes mandatory argument CMD, which is a command name.\n"
              + "Print documentation for CMD."}};

  /**
   * The constructor that makes each command in the shell follow the singleton
   * pattern.
   *
   * @param sd The ShellData similar to all consoles.
   */
  public Man(ShellData sd) {
    super(sd);
  }// end constructor

  /**
   * Executes the "man" command, which outputs the manual of a certain command.
   *
   * @param args The String array of arguments passed in by the user.
   * @return Returns a String that will be printed in JShell.
   */
  public String execute(String[] args) throws InvalidNumberOfArgsException {

    checkArgSizeValidity(args);

    for (int i = 0; i < commandsAndManuals.length; i++)
      // prints out the correct command and its manual
      if (args[0].equals(commandsAndManuals[i][0]))
        return "\n" + commandsAndManuals[i][1] + "\n\n";

    return "Invalid command, please try again\n";
  }// end method exectue

  /**
   * Returns true if the number of arguments is valid for this command.
   *
   * @param args The String array of arguments passed in by the user.
   * @return Returns true if the arguments size is correct and false otherwise.
   */
  public boolean correctArgSize(String[] args) {
    return args.length == 1;
  }// end method correctArgSize

}// end class Man
