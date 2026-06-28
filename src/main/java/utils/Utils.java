package main.java.utils;

public class Utils {
    public static List<Class<?>> chargementClasse(String nomClasse) throws ClassNotFoundException {
        List<Class<?>> classes = new ArrayList<>();
        String path = nomClasse.replace('.', '/');
        URL resource = Thread.currentThread().getContextClassLoader().getResource(path);
        if (resource != null) {
            File directory = new File(resource.getFile());
            if (directory.exists()) {
                try {
                    classes.addAll(findClasses(directory, nomClasse));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public static List<Class<?>> findClasses(File directory, String packagename) throws Exception {
        for (File file : directory.listFiles()) {
            if (file.isDirectory()) {
                findClasses(file, packagename + "." + file.getName());
            } else if (file.getName().endsWith(".class")) {
                String className = packagename + '.' + file.getName().substring(0, file.getName().length() - 6);
                Class<?> clazz = Class.forName(className);
                return Arrays.asList(clazz);
            }
        }
        return new ArrayList<>();
    }
}
