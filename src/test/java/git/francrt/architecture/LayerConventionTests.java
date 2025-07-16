package git.francrt.architecture;

import org.junit.jupiter.api.Test;
import org.reflections.Reflections;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class LayerConventionTests {

    private static final String APPLICATION_PACKAGE = "git.francrt.application";
    private static final String INFRASTRUCTURE_PACKAGE = "git.francrt.infrastructure";
    private static final String DOMAIN_PACKAGE = "git.francrt.domain";
    private static final String PORT_PACKAGE = "git.francrt.application.port";
    private static final String ADAPTER_PACKAGE = "git.francrt.infrastructure.adapter";

    @Test
    void applicationNoAccessesInfrastructure() {
        Reflections appReflections = new Reflections(APPLICATION_PACKAGE);
        Set<Class<?>> appClasses = appReflections.getSubTypesOf(Object.class);

        for (Class<?> clazz : appClasses) {
            for (Field field : clazz.getDeclaredFields()) {
                String fieldType = field.getType().getPackageName();
                assertFalse(fieldType.startsWith(INFRASTRUCTURE_PACKAGE),
                        "La clase de aplicación " + clazz.getName() +
                                " depende de infraestructura: " + field.getType().getName());
            }
        }
    }

    @Test
    void servicesDependOnPortsNotImplementations() {
        Reflections appReflections = new Reflections(APPLICATION_PACKAGE);
        Set<Class<?>> serviceClasses = appReflections.getTypesAnnotatedWith(Service.class);

        for (Class<?> service : serviceClasses) {
            for (Field field : service.getDeclaredFields()) {
                String fieldType = field.getType().getPackageName();
                if (fieldType.startsWith("org.slf4j") || fieldType.startsWith("java.util.logging")) {
                    continue;
                }
                assertTrue(fieldType.startsWith(PORT_PACKAGE),
                        "El servicio " + service.getName() +
                                " debe depender de un puerto (interfaz), no de: " + field.getType().getName());
            }
        }
    }

    @Test
    void domainNoAccessesApplicationOrInfrastructure() {
        Reflections domainReflections = new Reflections(DOMAIN_PACKAGE);
        Set<Class<?>> domainClasses = domainReflections.getSubTypesOf(Object.class);

        for (Class<?> clazz : domainClasses) {
            for (Field field : clazz.getDeclaredFields()) {
                String fieldType = field.getType().getPackageName();
                assertFalse(fieldType.startsWith(APPLICATION_PACKAGE) || fieldType.startsWith(INFRASTRUCTURE_PACKAGE),
                        "La clase de dominio " + clazz.getName() +
                                " depende de application o infrastructure: " + field.getType().getName());
            }
        }
    }

    @Test
    void adaptersOnlyDependOnPortsOrDomain() {
        Reflections adapterReflections = new Reflections(ADAPTER_PACKAGE);
        Set<Class<?>> adapterClasses = adapterReflections.getSubTypesOf(Object.class);

        for (Class<?> clazz : adapterClasses) {
            for (Field field : clazz.getDeclaredFields()) {
                String fieldType = field.getType().getPackageName();
                boolean isPort = fieldType.startsWith(PORT_PACKAGE);
                boolean isDomain = fieldType.startsWith(DOMAIN_PACKAGE);
                assertTrue(isPort || isDomain,
                        "El adaptador " + clazz.getName() +
                                " solo debe depender de puertos o dominio, no de: " + field.getType().getName());
            }
        }
    }
}
