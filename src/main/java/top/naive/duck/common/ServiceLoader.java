package top.naive.duck.common;

import top.naive.duck.common.io.Resources;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author Bridge-cut
 * @version 1.0
 * @date 2021/5/19 下午3:34
 */
public class ServiceLoader<T> implements Iterable<T> {

    private static final String REFLECTION_FILE = "META-INF/duck.services";

    private final List<T> implementObjects = new ArrayList<>();

    private ServiceLoader(String key) {
        doLoad(key);
    }

    public static <T> ServiceLoader<T> load(String key) {
        return new ServiceLoader<>(key);
    }

    @SuppressWarnings("unchecked")
    private void doLoad(String key) {
        try (BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(Resources.getResourceAsStream(REFLECTION_FILE), StandardCharsets.UTF_8))) {
            String line;
            while (null != (line = bufferedReader.readLine())){
                String[] strings = line.split("=");
                if (strings[0].strip().equals(key)) {
                    String[] values = strings[1].split(",");
                    for (String value : values) {
                        implementObjects
                                .add((T) ObjectFactory.createObject(Resources.classForName(value.strip())));
                    }
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public T get(int index) {
        return implementObjects.get(index);
    }

    @Override
    public Iterator<T> iterator() {
        return implementObjects.iterator();
    }
}

