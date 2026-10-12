import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

/** Small subprocess fixture for checking Grill's build/launch boundary without a game installation. */
public class RunCompilerFixture {
    public static void main(String[] args) throws Exception {
        var arguments = Arrays.asList(args);
        var root = Path.of(arguments.get(arguments.indexOf("-workspaceroot") + 1));
        Files.write(root.resolve("compiler-arguments.txt"), arguments);
        if (Files.exists(root.resolve("fail-build"))) {
            System.err.println("Fixture compilation failed");
            System.exit(1);
        }
        var map = root.resolve("_build/Actual output.w3x");
        Files.writeString(map, "fixture map");
        System.out.println("Build succeeded. Output file: <" + map + ">");
    }
}
