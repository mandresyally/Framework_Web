package main.java.utils;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Utils {
    public static List<Class<?>> chargementClasse(String nomClasse) throws ClassNotFoundException {
        List<Class<?>> classes = new ArrayList<>();
        URL resource = Thread.currentThread().getContextClassLoader()
                .getResource(nomClasse.replace('.', '/'));
        if (resource != null && "file".equals(resource.getProtocol())) {
            try {
                classes.addAll(findClasses(new File(resource.toURI()), nomClasse));
            } catch (Exception exception) {
                throw new ClassNotFoundException("Impossible de scanner " + nomClasse, exception);
            }
        }
        return classes;
    }

    public static List<Class<?>> findClasses(File directory, String packageName) throws Exception {
        List<Class<?>> classes = new ArrayList<>();
        File[] files = directory.listFiles();
        if (files == null) {
            return classes;
        }
        for (File file : files) {
            if (file.isDirectory()) {
                classes.addAll(findClasses(file, packageName + "." + file.getName()));
            } else if (file.getName().endsWith(".class")) {
                String className = packageName + '.' + file.getName().substring(0, file.getName().length() - 6);
                classes.add(Class.forName(className));
            }
        }
        return classes;
    }
}
