import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

import static java.nio.file.StandardWatchEventKinds.*;

public class WatchService {
    private java.nio.file.WatchService watchService;
    private boolean recursive;
    private volatile boolean running = true;

    public WatchService(boolean recursive) throws IOException {
        this.watchService = FileSystems.getDefault().newWatchService();
        this.recursive = recursive;
    }

    public void watchDirectory(String dirPath) throws IOException {
        Path dir = Paths.get(dirPath);
        if (!Files.exists(dir)) {
            Files.createDirectories(dir);
        }
        
        if (recursive) {
            registerAll(dir);
        } else {
            register(dir);
        }

        // Start watching in a new thread
        Thread watchThread = new Thread(() -> {
            try {
                startWatching();
            } catch (Exception e) {
                System.out.println("Watch service error: " + e.getMessage());
            }
        });
        watchThread.start();
    }

    private void register(Path dir) throws IOException {
        WatchKey key = dir.register(watchService, 
            ENTRY_CREATE,
            ENTRY_DELETE,
            ENTRY_MODIFY);
        System.out.println("Registered directory: " + dir);
    }

    private void registerAll(Path start) throws IOException {
        Files.walkFileTree(start, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs)
                throws IOException {
                register(dir);
                return FileVisitResult.CONTINUE;
            }
        });
    }

    private void startWatching() {
        while (running) {
            WatchKey key;
            try {
                key = watchService.take();
            } catch (InterruptedException e) {
                if (!running) {
                    return;
                }
                continue;
            }

            for (WatchEvent<?> event : key.pollEvents()) {
                WatchEvent.Kind<?> kind = event.kind();

                if (kind == OVERFLOW) {
                    continue;
                }

                @SuppressWarnings("unchecked")
                WatchEvent<Path> ev = (WatchEvent<Path>) event;
                Path filename = ev.context();

                Path dir = (Path) key.watchable();
                Path fullPath = dir.resolve(filename);

                System.out.println("\n=== Event Detected ===");
                System.out.println("Event type: " + kind.name());
                System.out.println("File affected: " + fullPath);
                System.out.println("Time: " + java.time.LocalDateTime.now());

                // If directory is created, register it for watching
                if (recursive && (kind == ENTRY_CREATE)) {
                    try {
                        if (Files.isDirectory(fullPath)) {
                            registerAll(fullPath);
                        }
                    } catch (IOException e) {
                        System.out.println("Error registering new directory: " + e.getMessage());
                    }
                }

                // Print file details if it exists
                if (Files.exists(fullPath)) {
                    try {
                        BasicFileAttributes attrs = Files.readAttributes(fullPath, 
                            BasicFileAttributes.class);
                        System.out.println("Type: " + 
                            (attrs.isDirectory() ? "Directory" : "File"));
                        if (!attrs.isDirectory()) {
                            System.out.println("Size: " + attrs.size() + " bytes");
                        }
                    } catch (IOException e) {
                        System.out.println("Unable to read file attributes");
                    }
                }
                System.out.println("===================");
            }

            boolean valid = key.reset();
            if (!valid) {
                break;
            }
        }
    }

    public void stop() {
        running = false;
        try {
            watchService.close();
        } catch (IOException e) {
            System.out.println("Error closing watch service: " + e.getMessage());
        }
        System.out.println("Watch service stopped.");
    }
}