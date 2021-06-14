package jsc.jMessageHandler;

import java.io.File;
import java.util.function.Consumer;

@FunctionalInterface
public interface JFileConsumer extends Consumer<File> {
}
