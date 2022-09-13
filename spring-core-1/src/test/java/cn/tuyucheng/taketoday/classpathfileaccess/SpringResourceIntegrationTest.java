package cn.tuyucheng.taketoday.classpathfileaccess;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.nio.file.Files;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test class illustrating various methods of accessing a file from the classpath using Resource.
 * @author tu yucheng
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class)
public class SpringResourceIntegrationTest {
    private static final String EMPLOYEES_EXPECTED = "Joe Employee,Jan Employee,James T. Employee";
    /**
     * Resource loader instance for lazily loading resources.
     */
    @Autowired
    private ResourceLoader resourceLoader;
    @Autowired
    private ApplicationContext appContext;

    @Value("classpath:data/employees.dat")
    private Resource resourceFile;

    @Test
    @DisplayName("whenResourceLoader_thenReadSuccessful")
    void whenResourceLoader_thenReadSuccessful() throws IOException {
        final Resource resource = loadEmployeesWithResourceLoader();
        final String employees = new String(Files.readAllBytes(resource.getFile().toPath()));
        assertEquals(EMPLOYEES_EXPECTED, employees);
    }

    private Resource loadEmployeesWithResourceLoader() {
        return resourceLoader.getResource("classpath:data/employees.dat");
    }

    @Test
    @DisplayName("whenApplicationContext_thenReadSuccessful")
    void whenApplicationContext_thenReadSuccessful() throws IOException {
        final Resource resource = loadEmployeesWithApplicationContext();
        final String employees = new String(Files.readAllBytes(resource.getFile().toPath()));
        assertEquals(EMPLOYEES_EXPECTED, employees);
    }

    private Resource loadEmployeesWithApplicationContext() {
        return appContext.getResource("classpath:data/employees.dat");
    }

    @Test
    @DisplayName("whenAutowired_thenReadSuccessful")
    void whenAutowired_thenReadSuccessful() throws IOException {
        final String employees = new String(Files.readAllBytes(resourceFile.getFile().toPath()));
        assertEquals(EMPLOYEES_EXPECTED, employees);
    }

    @Test
    @DisplayName("whenResourceUtils_thenReadSuccessful")
    void whenResourceUtils_thenReadSuccessful() throws IOException {
        final File employeeFile = loadEmployeesWithSpringInternalClass();
        final String employees = new String(Files.readAllBytes(employeeFile.toPath()));
        assertEquals(EMPLOYEES_EXPECTED, employees);
    }

    private File loadEmployeesWithSpringInternalClass() throws FileNotFoundException {
        return ResourceUtils.getFile("classpath:data/employees.dat");
    }

    @Test
    @DisplayName("whenResourceAsStream_thenReadSuccessful")
    void whenResourceAsStream_thenReadSuccessful() throws IOException {
        final InputStream resource = loadEmployeesWithClassPathResource().getInputStream();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(resource))) {
            final String employees = reader.lines().collect(Collectors.joining("\n"));
            assertEquals(EMPLOYEES_EXPECTED, employees);
        }
    }

    private Resource loadEmployeesWithClassPathResource() {
        return new ClassPathResource("data/employees.dat");
    }

    @Test
    @DisplayName("whenResourceAsFile_thenReadSuccessful")
    void whenResourceAsFile_thenReadSuccessful() throws IOException {
        final File resource = loadEmployeesWithClassPathResource().getFile();
        final String employees = new String(Files.readAllBytes(resource.toPath()));
        assertEquals(EMPLOYEES_EXPECTED, employees);
    }

    @Test
    @DisplayName("whenClassPathResourceWithAbsoultePath_thenReadSuccessful")
    void whenClassPathResourceWithAbsoultePath_thenReadSuccessful() throws IOException {
        final File resource = new ClassPathResource("/data/employees.dat", this.getClass()).getFile();
        final String employees = new String(Files.readAllBytes(resource.toPath()));
        assertEquals(EMPLOYEES_EXPECTED, employees);
    }

    @Test
    @DisplayName("whenClassPathResourceWithRelativePath_thenReadSuccessful")
    void whenClassPathResourceWithRelativePath_thenReadSuccessful() throws IOException {
        final File resource = new ClassPathResource("../../../../data/employees.dat", SpringResourceIntegrationTest.class).getFile();
        final String employees = new String(Files.readAllBytes(resource.toPath()));
        assertEquals(EMPLOYEES_EXPECTED, employees);
    }

    @Configuration
    public static class ContextConfiguration {
    }
}