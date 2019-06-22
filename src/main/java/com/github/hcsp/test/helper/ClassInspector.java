package com.github.hcsp.test.helper;

import org.junit.jupiter.api.Assertions;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.LinkedHashSet;
import java.util.stream.Stream;

public class ClassInspector {
    public static void assertNoPublicFields(Class... classes) {
        Assertions.assertTrue(Stream.of(classes)
                .allMatch(klass -> klass.getFields().length == 0));
    }

    public static void assertAllFieldsArePrivate(Class... classes) {
        Assertions.assertTrue(Stream.of(classes).allMatch(ClassInspector::allFieldsArePrivate));
    }

    public static boolean allFieldsArePrivate(Class klass) {
        return Stream.of(klass.getDeclaredFields())
                .map(Field::getModifiers)
                .allMatch(Modifier::isPrivate);
    }

    public static void assertNoDeclaredFields(Class... classes) {
        Assertions.assertTrue(Stream.of(classes).allMatch(ClassInspector::noDeclaredFields));
    }

    public static boolean noDeclaredFields(Class klass) {
        return klass.getDeclaredFields().length == 0;
    }

    public static LinkedHashSet<Class> getAncestors(Class klass) {
        LinkedHashSet<Class> ancestors = new LinkedHashSet<>();
        while (klass != Object.class) {
            ancestors.add(klass.getSuperclass());
            klass = klass.getSuperclass();
        }
        return ancestors;
    }
}
