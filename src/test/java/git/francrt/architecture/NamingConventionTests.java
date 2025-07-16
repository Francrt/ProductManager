package git.francrt.architecture;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.regex.Pattern;
import org.reflections.Reflections;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.scanners.Scanners;

import static org.reflections.scanners.Scanners.SubTypes;
import static org.junit.jupiter.api.Assertions.*;

public class NamingConventionTests {

    private static final Pattern CLASS_NAME = Pattern.compile("^[A-Z][a-zA-Z0-9]*$");
    private static final Pattern METHOD_NAME = Pattern.compile("^[a-z][a-zA-Z0-9]*$");
    private static final Pattern FIELD_NAME = Pattern.compile("^[a-z][a-zA-Z0-9]*$");

    @Test
    void classNamesShouldBeCamelCase() {
        Class<?>[] classes = { /* Agrega aquí tus clases a comprobar, por ejemplo: PricesService.class */};
        for (Class<?> clazz : classes) {
            assertTrue(CLASS_NAME.matcher(clazz.getSimpleName()).matches(),
                    "El nombre de la clase debe ser CamelCase: " + clazz.getSimpleName());
        }
    }

    @Test
    void methodNamesShouldBeCamelCase() {
        Class<?>[] classes = { /* Agrega aquí tus clases a comprobar */};
        for (Class<?> clazz : classes) {
            for (Method method : clazz.getDeclaredMethods()) {
                assertTrue(METHOD_NAME.matcher(method.getName()).matches(),
                        "El nombre del método debe ser camelCase: " + method.getName());
            }
        }
    }

    @Test
    void fieldNamesShouldBeCamelCase() {
        Class<?>[] classes = { /* Agrega aquí tus clases a comprobar */};
        for (Class<?> clazz : classes) {
            for (Field field : clazz.getDeclaredFields()) {
                assertTrue(FIELD_NAME.matcher(field.getName()).matches(),
                        "El nombre del campo debe ser camelCase: " + field.getName());
            }
        }
    }

    @Test
    void classesInImplPackagesShouldNotEndWithImpl() {
        String[] implPackages = {"git.francrt.infrastructure.impl", "git.francrt.application.impl" /* agrega más si es necesario */};
        for (String pkg : implPackages) {
            for (Class<?> clazz : getClassesInPackage(pkg)) {
                assertFalse(clazz.getSimpleName().endsWith("Impl"),
                        "Las clases en paquetes 'impl' no deben terminar en 'Impl': " + clazz.getName());
            }
        }
    }

    @Test
    void classesInMapperPackagesShouldNotEndWithMapper() {
        String[] mapperPackages = {"git.francrt.infrastructure.mapper", "git.francrt.application.mapper" /* agrega más si es necesario */};
        for (String pkg : mapperPackages) {
            for (Class<?> clazz : getClassesInPackage(pkg)) {
                assertFalse(clazz.getSimpleName().endsWith("Mapper"),
                        "Las clases en paquetes 'mapper' no deben terminar en 'Mapper': " + clazz.getName());
            }
        }
    }

    private static Class<?>[] getClassesInPackage(String packageName) {
        Reflections reflections = new Reflections(
                new ConfigurationBuilder()
                        .forPackages(packageName)
                        .setScanners(SubTypes)
        );
        return reflections.getSubTypesOf(Object.class).toArray(new Class[0]);
    }
}