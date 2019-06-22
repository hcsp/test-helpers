package com.github.hcsp.test.helper;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.LinkedHashSet;

class ClassInspectorTest {
    private static class ClassWithNoPublicFields {
        private int a;
        private int b;

        public void publicMethod() {
        }
    }

    @Test
    void assertNoPublicFieldsTest() {
        ClassInspector.assertNoPublicFields(ClassWithNoPublicFields.class);
    }

    @Test
    void assertAllFieldsArePrivateTest() {
        ClassInspector.assertAllFieldsArePrivate(ClassWithNoPublicFields.class);
    }

    private static class Base {
        public int a;
        private int b;
    }

    private static class Sub extends Base {
    }

    @Test
    void assertNoDeclaredFieldsTest() {
        ClassInspector.assertNoDeclaredFields(Sub.class);
    }


    private static class SubSub extends Sub {
    }

    @Test
    void getAncestorsTest() {
        LinkedHashSet set1 = new LinkedHashSet();
        set1.add(Sub.class);
        set1.add(Base.class);
        set1.add(Object.class);

        LinkedHashSet set2 = new LinkedHashSet();
        set2.add(Base.class);
        set2.add(Object.class);
        Assertions.assertEquals(set1, ClassInspector.getAncestors(SubSub.class));
        Assertions.assertEquals(set2, ClassInspector.getAncestors(Sub.class));
    }
}