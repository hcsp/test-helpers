package com.github.hcsp.test.helper;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.stmt.BlockStmt;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JavaASTReaderTest {
    @Test
    public void assertContainsTest() {
        JavaASTReader.assertContains(JavaASTReader.class, BlockStmt.class);
    }

    @Test
    public void assertFindAllTest() {
        List<ClassOrInterfaceDeclaration> nodes = JavaASTReader.findAll(JavaASTReader.class, ClassOrInterfaceDeclaration.class);
        assertEquals(1, nodes.size());
        assertEquals(JavaASTReader.class.getName(), nodes.get(0).getFullyQualifiedName().get());
    }
}