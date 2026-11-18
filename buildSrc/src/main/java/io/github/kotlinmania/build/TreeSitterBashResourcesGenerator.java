package io.github.kotlinmania.build;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;

/** Embeds the grammar resources for every Kotlin target, including Native and Wasm. */
public final class TreeSitterBashResourcesGenerator {
    private static final int BASE64_CHUNK_LENGTH = 32 * 1024;
    private static final String OUTPUT = "io/github/kotlinmania/treesitterbash/BundledResources.kt";

    private TreeSitterBashResourcesGenerator() {}

    public static int run(String... arguments) throws IOException {
        if (arguments.length != 3) {
            throw new IllegalArgumentException(
                    "Usage: TreeSitterBashResourcesGenerator <node-types.json> <highlights.scm> <generated-source-directory>");
        }
        generate(Path.of(arguments[0]), Path.of(arguments[1]), Path.of(arguments[2]));
        return 0;
    }

    public static void main(String[] arguments) throws IOException {
        run(arguments);
    }

    public static void generate(Path nodeTypes, Path highlights, Path outputDirectory) throws IOException {
        byte[] nodeTypeBytes = readRequired(nodeTypes);
        byte[] highlightBytes = readRequired(highlights);
        Path output = outputDirectory.resolve(OUTPUT);
        Files.createDirectories(output.getParent());
        try (BufferedWriter writer = Files.newBufferedWriter(output, StandardCharsets.UTF_8)) {
            writer.write("// Generated from the bundled tree-sitter-bash resources; do not edit.\n");
            writer.write("package io.github.kotlinmania.treesitterbash\n\n");
            writer.write("import kotlin.io.encoding.Base64\n\n");
            writer.write("internal val BUNDLED_TREE_SITTER_BASH_RESOURCES: Map<String, String> =\n    mapOf(\n");
            writeEntry(writer, "node-types.json", nodeTypeBytes);
            writeEntry(writer, "queries/highlights.scm", highlightBytes);
            writer.write("    )\n");
        }
    }

    private static byte[] readRequired(Path path) throws IOException {
        if (!Files.isRegularFile(path)) {
            throw new IOException("Required bundled grammar resource is missing: " + path);
        }
        return Files.readAllBytes(path);
    }

    private static void writeEntry(BufferedWriter writer, String key, byte[] bytes) throws IOException {
        String base64 = Base64.getEncoder().encodeToString(bytes);
        writer.write("        \"" + key + "\" to\n            Base64\n                .decode(\n");
        writer.write("                    listOf(\n");
        for (int offset = 0; offset < base64.length(); offset += BASE64_CHUNK_LENGTH) {
            writer.write("                        \"");
            writer.write(base64, offset, Math.min(BASE64_CHUNK_LENGTH, base64.length() - offset));
            writer.write("\",\n");
        }
        writer.write("                    ).joinToString(\"\"),\n                ).decodeToString(),\n");
    }
}
