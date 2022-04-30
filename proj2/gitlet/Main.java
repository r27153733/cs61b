package gitlet;

/** Driver class for Gitlet, a subset of the Git version-control system.
 *  @author TODO
 */
public class Main {

    /** Usage: java gitlet.Main ARGS, where ARGS contains
     *  <COMMAND> <OPERAND1> <OPERAND2> ... 
     */
    public static void main(String[] args) {
        // TODO: what if args is empty?
        String firstArg = args[0];
        if (firstArg == null) {
            System.out.println("Please enter a command.");
            return;
        }
        switch(firstArg) {
            case "init":
                // TODO: handle the `init` command
                Repository.initCreate();
                validateNumArgs(args, 1);
                break;
            case "add":
                // TODO: handle the `add [filename]` command
                Repository.add(args[1]);
                validateNumArgs(args, 2);
                break;
            // TODO: FILL THE REST IN
            default:
                System.out.println("No command with that name exists.");
                return;
        }
    }

    /**
     * Checks the number of arguments versus the expected number,
     * throws a RuntimeException if they do not match.
     *
     * @param args Argument array from command line
     * @param n Number of expected arguments
     */
    public static void validateNumArgs(String[] args, int n) {
        if (args.length != n) {
            System.out.println("Incorrect operands.");
        }
    }
}
