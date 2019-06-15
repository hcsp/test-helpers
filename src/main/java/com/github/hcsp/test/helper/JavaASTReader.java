package com.github.hcsp.test.helper;

import com.github.javaparser.ast.Node;
import org.junit.jupiter.api.Assertions;

import java.util.List;

public class JavaASTReader {
    public static <T extends Node> void assertContains(Class targetClass, Class<T> nodeType) {
        Assertions.assertTrue(findAll(targetClass, nodeType).size() > 0);
    }

    public static <T extends Node> List<T> findAll(Class targetClass, Class<T> nodeType) {
        return ProjectSourceFileReader.readAsCompilationUnit(targetClass).findAll(nodeType);
    }
}
