package org.builovn.storage.DI;

import org.builovn.storage.DI.annotation.Autowired;
import org.builovn.storage.DI.annotation.Component;
import org.builovn.storage.DI.annotation.Configuration;
import org.builovn.storage.repositories.ContractRepository;
import org.builovn.storage.sorters.QuickSorter;

import java.io.File;
import java.io.FilenameFilter;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Configuration(packages = {"org.builovn.storage.entities", "org.builovn.storage.sorters"})
public class Injector {

    public <T> void inject(T obj) throws InjectorException {
        Class clazz = obj.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for(Field field : fields){
            Annotation[] annotations = field.getAnnotations();
            for(Annotation annotation : annotations){
                if(annotation.annotationType().equals(Autowired.class)){
                    
                    try {
                        List<Class> listClass = getClassesInPackages(getClass().getAnnotation(Configuration.class).packages());
                        field.setAccessible(true);
                        field.set(obj, getMatchingComponentInstance(listClass, field.getType()));
                    } catch (IllegalAccessException e) {
                        throw new InjectorException(e);
                    }
                }
            }
        }
    }

    private <T> T getMatchingComponentInstance(List<Class> classList, T matchingType) throws InjectorException {
        List<Class> matchingClassList = classList.stream().filter(
                cls -> cls.getAnnotation(Component.class) == null || !isEqualsOrImplement(cls, matchingType))
                .collect(Collectors.toList());
        if(matchingClassList.size() == 0)
            throw new InjectorException("Matching class was not found in packages.");
        else if(matchingClassList.size() > 1)
            throw new InjectorException("More than one matching classes found.");
        else {
            Class<T> matchingClass = classList.get(0);
            try{
                return matchingClass.getConstructor().newInstance();
            } catch (InstantiationException | InvocationTargetException | NoSuchMethodException | IllegalAccessException e) {
                throw new InjectorException(e);
            }
        }

    }

    public static List<Class> getClassesInPackages(String[] packages){
        List<Class> classList = new ArrayList<Class>();
        for(String pack : packages) {
            URL root = Thread.currentThread().getContextClassLoader().getResource(pack.replace(".", "/"));

            File[] files = new File(root.getFile()).listFiles(new FilenameFilter() {
                public boolean accept(File dir, String name) {
                    return name.endsWith(".class");
                }
            });

            for (File file : files) {
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

    private boolean isEqualsOrImplement(Class cls, Class classOrInterface){
        return cls.equals(classOrInterface) || classOrInterface.isAssignableFrom(cls);
    }
}

