package com.github.hcsp.test.helper;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;
import org.opentest4j.TestAbortedException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.stream.Stream;

public class ProjectSourceFileReader {
    public static File locateProjectDir() {
        Stream<String> entries = Stream.of(System.getProperty("java.class.path").split(File.pathSeparator));
        File targetClassDir = entries.filter(entry -> entry.endsWith("target/classes") || entry.endsWith("target\\classes")).findFirst().map(File::new).orElseThrow(IllegalStateException::new);
        return new File(targetClassDir, "../..").getAbsoluteFile();
    }

    public static File read(Class klass) {
        File projectDir = locateProjectDir();
        File main = new File(projectDir, "src/main/java/" + klass.getName().replaceAll("\\.", "/") + ".java").getAbsoluteFile();
        File test = new File(projectDir, "src/test/java/" + klass.getName().replaceAll("\\.", "/") + ".java").getAbsoluteFile();
        return main.exists() ? main : test;
    }

    public static void abortTestIfClassNotChanged(Class klass, String md5) {
        if (md5.equals(md5SpaceRemoved(readAsString(klass)))) {
            throw new TestAbortedException("Abort test for " + klass + " as it's unchanged.");
        }
    }

    public static String readFile(String relativePath) {
        try {
            File file = new File(locateProjectDir(), relativePath);
            return new String(Files.readAllBytes(file.toPath()), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public static String readAsString(Class klass) {
        try {
            return IOUtils.toString(new FileReader(read(klass)));
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public static String md5SpaceRemoved(Class klass) {
        return md5SpaceRemoved(readAsString(klass));
    }

    public static String md5SpaceRemoved(File file) {
        try {
            return md5SpaceRemoved(IOUtils.toString(new FileReader(file)));
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public static String md5SpaceRemoved(String content) {
        String spaceRemoved = content.replaceAll("\\s", "");
        try {
            return DigestUtils.md5Hex(spaceRemoved);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static CompilationUnit readAsCompilationUnit(Class klass) {
        try {
            return StaticJavaParser.parse(read(klass));
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
