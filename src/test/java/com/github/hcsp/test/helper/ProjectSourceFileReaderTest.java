package com.github.hcsp.test.helper;

import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class ProjectSourceFileReaderTest {
    @Test
    public void canLocateAMainJavaSourceFile() {
        File mainJavaSrc = ProjectSourceFileReader.read(ProjectSourceFileReader.class);
        System.out.println(mainJavaSrc);
        assertTrue(mainJavaSrc.exists());
        assertTrue(mainJavaSrc.getAbsolutePath().replace('\\', '/').endsWith("src/main/java/com/github/hcsp/test/helper/ProjectSourceFileReader.java"));
    }

    @Test
    public void canLocateATestJavaSourceFile() {
        File testJavaSrc = ProjectSourceFileReader.read(ProjectSourceFileReaderTest.class);
        System.out.println(testJavaSrc);
        assertTrue(testJavaSrc.exists());
        assertTrue(testJavaSrc.getAbsolutePath().replace('\\', '/').endsWith("src/test/java/com/github/hcsp/test/helper/ProjectSourceFileReaderTest.java"));
    }

    @Test
    public void readAsStringTest() {
        assertTrue(ProjectSourceFileReader.readAsString(JavaASTReader.class).contains("public class JavaASTReader"));
    }
}