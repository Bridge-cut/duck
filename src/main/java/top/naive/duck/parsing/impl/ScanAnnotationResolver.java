package top.naive.duck.parsing.impl;

import top.naive.duck.annotations.ComponentScan;
import top.naive.duck.annotations.PropertyScan;
import top.naive.duck.boot.impl.DuckApplication;
import top.naive.duck.common.io.Resources;
import top.naive.duck.parsing.AbstractAnnotationResolver;
import top.naive.duck.parsing.AnnotationResolver;

import java.io.*;
import java.lang.annotation.Annotation;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Bridge-cut
 * @version 1.0
 * @date 2021/5/19 下午9:31
 */
public class ScanAnnotationResolver extends AbstractAnnotationResolver {

    private static final String CLASS_EXTENSION = ".class";
    private static final String PROPERTY_EXTENSION = ".properties";

    private static final String PROPERTY_SEPARATOR = "=";

    public ScanAnnotationResolver() {
        this.supportAnnotation.addAll(Arrays.asList(PropertyScan.class, ComponentScan.class));
    }

    @Override
    public void parse(Annotation annotation) {
        try {
            if (annotation instanceof PropertyScan) {
                String[] locations = ((PropertyScan) annotation).value();
                loadProperties(locations);
            } else {
                String[] packagePaths = ((ComponentScan) annotation).value();
                loadPackages(packagePaths);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadProperties(String[] locations) throws FileNotFoundException {
        Set<String> files = new HashSet<>();
        for (String location : locations) {
            String[] fileArray = scanFiles(location);

            if (fileArray == null) {
                continue;
            }
            files.addAll(Arrays.stream(fileArray)
                    .filter(file -> file.endsWith(PROPERTY_EXTENSION))
                    .map(file -> location.replace('.', '/')
                            + ((location.length() == 0 ? file : "/" + file)))
                    .collect(Collectors.toList()));
        }

        doLoadProperties(files);
    }

    private void doLoadProperties(Set<String> files) {
        for (String file : files) {
            doLoadProperties(file);
        }
    }

    private void doLoadProperties(String file) {
        try (BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(Resources.getResourceAsStream(file), StandardCharsets.UTF_8))) {
            String line;
            while (null != (line = bufferedReader.readLine())){
                String[] strings = line.split(PROPERTY_SEPARATOR);
                getApplicationContext().addProperty(strings[0], strings[1]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadPackages(String[] packagePaths) throws IOException {
        Set<String> componentPaths = new HashSet<>();
        for (String packageName : packagePaths) {
            componentPaths.add(packageName);

            File file = new File(packageName);
            String[] names = file.list();

            if (names != null) {
                componentPaths.addAll(Arrays.stream(names).filter(string -> !string.endsWith(CLASS_EXTENSION))
                        .collect(Collectors.toList()));
            }
        }

        loadComponents(componentPaths);
    }

    private void loadComponents(Set<String> componentPaths) throws IOException {
        Set<String> classPaths = new HashSet<>();
        for (String classPath : componentPaths) {
            String[] fileArray = scanFiles(classPath);

            if (fileArray == null) {
                continue;
            }
            classPaths.addAll(Arrays.stream(fileArray)
                    .filter(file -> file.endsWith(CLASS_EXTENSION))
                    .map(file -> classPath + '.' + file.substring(0, file.lastIndexOf('.')))
                    .collect(Collectors.toList()));
        }

        doLoadComponents(classPaths);
    }

    private void doLoadComponents(Set<String> classPaths) {
        for (String classPath : classPaths) {
            doLoadComponents(classPath);
        }
    }

    private void doLoadComponents(String classPath) {
        try {
            if (classPath.equals(getApplicationContext().getBootClass().getName())) {
                return;
            }

            Class<?> clazz = Class.forName(classPath);
            Annotation[] annotations = clazz.getAnnotations();
            List<AnnotationResolver> registeredResolvers =
                    ((DuckApplication) getApplicationContext()).getAnnotationResolvers();
            for (Annotation annotation : annotations) {
                for (AnnotationResolver annotationResolver : registeredResolvers) {
                    if (annotationResolver.support(annotation)) {
                        annotationResolver.parse(clazz);
                    }
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private String[] scanFiles(String path) throws FileNotFoundException {
        String filePath = path.replace('.', '/');
        URL url = getClassLoader().getResource(filePath);

        if (url == null) {
            throw new FileNotFoundException(path + " 不存在");
        }

        return new File(url.getFile()).list();
    }
}
