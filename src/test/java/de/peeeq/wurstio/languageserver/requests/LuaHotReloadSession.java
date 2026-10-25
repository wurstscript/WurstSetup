package de.peeeq.wurstio.languageserver.requests;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;

/**
 * Stands in for the compiler's hot reload session class (same name, so Grill's command line reaches it) without a
 * running map: it records each call and answers as the compiler does.
 */
public class LuaHotReloadSession {
    public static void main(String[] args) throws Exception {
        Path buildDir = Path.of(args[1]);
        Path customMapData = Path.of(args[2]);
        Path project = buildDir.getParent();
        Files.write(project.resolve("session-arguments.txt"), List.of(String.join("|", args)),
            StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        Path session = buildDir.resolve("luaHotReload/session.json");
        Path staged = session.resolveSibling("session-staged.json");
        Path folder = customMapData.resolve("Wurst");
        Path aside = folder.resolve("hotreload-set-aside");
        if (Files.exists(project.resolve("fail-" + args[0]))) {
            System.out.println("fixture " + args[0] + " failure");
            System.exit(1);
        }
        if (args[0].equals("prepare")) {
            Files.createDirectories(session.getParent());
            Files.writeString(staged, "{}");
            if (Files.isDirectory(folder)) {
                Files.createDirectories(aside);
                for (Path file : deliveries(folder)) Files.move(file, aside.resolve(file.getFileName()));
            }
            System.out.println("session prepared");
            return;
        }
        if (args[0].equals("activate")) {
            Files.move(staged, session, java.nio.file.StandardCopyOption.REPLACE_EXISTING);
            if (Files.isDirectory(aside)) {
                for (Path file : deliveries(aside)) Files.delete(file);
                Files.delete(aside);
            }
            System.out.println("session activated");
            return;
        }
        if (args[0].equals("abort")) {
            Files.deleteIfExists(staged);
            if (Files.isDirectory(aside)) {
                for (Path file : deliveries(aside)) Files.move(file, folder.resolve(file.getFileName()));
                Files.delete(aside);
            }
            System.out.println("previous session restored");
            return;
        }
        if (args[0].equals("start")) {
            Files.createDirectories(session.getParent());
            Files.writeString(session, "{}");
            System.out.println("session started");
            return;
        }
        if (!Files.exists(session)) {
            System.out.println("The edit cannot be hot reloaded, rebuild and restart the map:\n"
                + "  no map was started with hot run (or -luaHotReload): start the map with hot run first");
            System.exit(1);
        }
        if (Files.exists(project.resolve("refuse-reload"))) {
            System.out.println("The edit cannot be hot reloaded, rebuild and restart the map:\n"
                + "  the type of field Hello_Unit_hp changed");
            System.exit(1);
        }
        Path file = customMapData.resolve("Wurst/hotreload-1.pld");
        Files.createDirectories(file.getParent());
        Files.writeString(file, "payload");
        System.out.println("payload 1 (Hello_greet) written to " + file);
        System.out.println("warning: closure Hello_init_closure1 was removed; instances made before keep their old body");
    }
    private static List<Path> deliveries(Path folder) throws java.io.IOException {
        try (var files = Files.list(folder)) {
            return files.filter(p -> Files.isRegularFile(p) && p.getFileName().toString().startsWith("hotreload-")
                && (p.toString().endsWith(".pld") || p.toString().endsWith(".pld.tmp"))).sorted().toList();
        }
    }
}
