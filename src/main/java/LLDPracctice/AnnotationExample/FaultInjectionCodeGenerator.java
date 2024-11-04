package LLDPracctice.AnnotationExample;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.MethodCallExpr;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FaultInjectionCodeGenerator {
    private static final String TEMPLATE_FILE_PATH = "src/main/resources/FaultInjectionTemplate.txt";
    private static final String OUTPUT_FILE_PATH = "generated-sources/FaultInjectionOutput.txt";

    public static void main(String[] args) throws IOException {
        generateFaultInjectionFile();
    }

    private static void generateFaultInjectionFile() throws IOException {
        try {
            // Read the template file
            String templateContent = Files.readString(Paths.get(TEMPLATE_FILE_PATH));

            List<Path> javaFiles = new ArrayList<>();
            Files.walk(Paths.get("src/main/java"))
                    .filter(Files::isRegularFile)
                    .filter(path -> path.toString().endsWith(".java"))
                    .forEach(javaFiles::add);

            // Parse the Java source code
            for (var file : javaFiles) {
                var result = new JavaParser().parse(file);
                var cu = result.getResult().get();
                // Find method calls to injectFault()
                cu.findAll(MethodCallExpr.class, expr -> expr.getName().getIdentifier().equals("injectFault"))
                        .forEach(expr -> {
                            MethodDeclaration methodDeclaration = expr.findAncestor(MethodDeclaration.class).orElse(null);
                            if (methodDeclaration != null) {
                                // Extract method name, file name, and line number
                                String methodName = methodDeclaration.getNameAsString();
                                String fileName = cu.getStorage().map(x -> x.getPath()).get().toString();
                                int lineNumber = expr.getRange().map(range -> range.begin.line).orElse(-1);

                                // Populate the template with method details
                                String content = templateContent
                                        .replace("${methodName}", methodName)
                                        .replace("${fileName}", fileName)
                                        .replace("${lineNumber}", String.valueOf(lineNumber));

                                // Write the populated template to the output file
                                writeToFile(content, OUTPUT_FILE_PATH);
                            }
                        });

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void writeToFile(String content, String filePath) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath, true))) {
            writer.println(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}