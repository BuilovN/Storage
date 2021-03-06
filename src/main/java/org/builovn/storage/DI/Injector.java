package org.builovn.storage.DI;

import org.builovn.storage.DI.annotation.Autowired;
import org.builovn.storage.DI.annotation.Component;
import org.builovn.storage.DI.annotation.Configuration;
import org.builovn.storage.repositories.ContractRepository;
import org.builovn.storage.sorters.QuickSorter;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.stream.Collectors;

@Configuration(packages = {"org.builovn.storage.entities", "org.builovn.storage.sorters"})
public class Injector {

    public static <T> void inject(T obj) throws InjectorException {
        Class clazz = obj.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for(Field field : fields){
            Annotation[] annotations = field.getAnnotations();
            for(Annotation annotation : annotations){
                if(annotation.annotationType().equals(Autowired.class)){
                    
                    try {
                        List<Class> listClass = getClassesInPackages(Injector.class.getAnnotation(Configuration.class).packages());
                        field.setAccessible(true);
                        field.set(obj, getMatchingComponentInstance(listClass, field.getType()));
                    } catch (IllegalAccessException e) {
                        throw new InjectorException(e);
                    }
                }
            }
        }
    }
    
    private static <T> T getMatchingComponentInstance(List<Class> classList, Class<T> matchingType)
            throws InjectorException {

        List<Class> matchingClassList = classList.stream().filter(
                cls -> cls.getAnnotation(Component.class) != null && isEqualsOrImplement(cls, matchingType))
                .collect(Collectors.toList());
        if(matchingClassList.size() == 0)
            throw new InjectorException("Matching class was not found in packages.");
        else if(matchingClassList.size() > 1)
            throw new InjectorException("More than one matching classes found.");
        else {
            Class<T> matchingClass = matchingClassList.get(0);
            try{
                return matchingClass.getConstructor().newInstance();
            } catch (InstantiationException | InvocationTargetException | NoSuchMethodException | IllegalAccessException e) {
                throw new InjectorException(e);
            }
        }

    }

    public static List<Class> getClassesInPackages(String[] packages){
        List<Class> classList = new ArrayList<Class>();
        List<URL> resourceList = new ArrayList<>();
        List<File> fileList = new ArrayList<>();

        for(String pack : packages) {
            try{
                String packagePath = pack.replace('.', '/');
                ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
                Enumeration resources = classLoader.getResources(packagePath);
                while (resources.hasMoreElements()) {
                    Object resource = resources.nextElement();
                    if(resource.getClass() == URL.class)
                        resourceList.add((URL) resource);
                }
            } catch (IOException e){
                e.printStackTrace();
            }

            for (URL resource : resourceList) {
                File[] fileArr = new File(resource.getFile()).listFiles(new FilenameFilter() {
                    public boolean accept(File dir, String name) {
                        return name.endsWith(".class");
                    }
                });
                fileList.addAll(Arrays.asList(fileArr));
            }

            for (File file : fileList) {
                String className = file.getName().replaceAll(".class$", "");
                try {
                    Class<?> cls = Class.forName(pack + "." + className);
                    if (!cls.isInterface())
                        classList.add(cls);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
        return classList;
    }

    private static boolean isEqualsOrImplement(Class cls, Class classOrInterface){
        return cls.equals(classOrInterface) || classOrInterface.isAssignableFrom(cls);
    }
}

