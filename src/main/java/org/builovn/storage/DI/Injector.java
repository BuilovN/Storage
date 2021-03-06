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
import java.util.*;
import java.util.stream.Collectors;


/**
Класс, отвечающий за внедрение зависимостей.
 */
@Configuration(packages = {"org.builovn.storage.entities", "org.builovn.storage.sorters", "org.builovn.storage.validators.contracts"})
public class Injector {

    /**
     * Метод, осуществляющий внедрений зависимостей. Для всех полей помеченных аннотацией @Autowired будет искаться
     * зависимость среди классов, которые помечены аннотацией @Component.
     * @param obj объект, в который будут внедряться зависимости.
     * @param <T> тип объекта.
     * @throws InjectorException исключение, вызываемое при ошибке, возникающей при внедрении зависимостей.
     */
    public static <T> void inject(T obj) throws InjectorException {
        Class clazz = obj.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for(Field field : fields){
            Annotation[] annotations = field.getAnnotations();
            for(Annotation annotation : annotations){

                if(annotation.annotationType().equals(Autowired.class)){
                    Autowired autowiredAnnotation = (Autowired) annotation;
                    field.setAccessible(true);
                    List<Class> listClass = getClassesInPackages(Injector.class.getAnnotation(Configuration.class).packages());
                    try {
                        if(List.class.isAssignableFrom(field.getType())){
                            field.set(obj, getMatchingComponentInstances(listClass, autowiredAnnotation.clazz()));
                        }
                        else {
                            field.set(obj, getMatchingComponentInstance(listClass, field.getType()));
                        }
                    } catch (IllegalAccessException e) {
                        throw new InjectorException(e);
                    }
                }
            }
        }
    }

    /**
     * Метод, создающий список экземпляров переданного класса, из списка переданнах классов.
     * @param classList список классов.
     * @param matchingType тип, экземпляры которого необходимо будет создать.
     * @param <T> тип создаваемых экземпляра. Соответствует полю matchingType.
     * @return возвращает список экземпляров типа matchingType.
     * @throws InjectorException выбрасывается, когда в списке нет ни одного класса подходящего типа, либо
     * если не получилось создать экземпляры класса.
     */
    private static <T> List<T> getMatchingComponentInstances(List<Class> classList, Class<T> matchingType)
            throws InjectorException {
        List<T> instanceList = new ArrayList<>();
        List<Class> matchingClassList = classList.stream().filter(
                cls -> cls.getAnnotation(Component.class) != null && matchingType.isAssignableFrom(cls))
                .collect(Collectors.toList());

        if (matchingClassList.size() == 0)
            throw new InjectorException("Matching class was not found in packages.");
            for (Class cls : matchingClassList) {
                try {
                    instanceList.add((T) cls.getConstructor().newInstance()); // безопасный каст, т.к. после проверки в 51 строке остаются только элементы типа Т.
                } catch(InstantiationException | InvocationTargetException | NoSuchMethodException | IllegalAccessException e){
                throw new InjectorException(e);
            }
        }
        return instanceList;
    }

    /**
     * Метод, создающий один экземпляр переданного класса, из списка переданнах классов.
     * @param classList список классов.
     * @param matchingType тип, экземпляр которого необходимо будет создать.
     * @param <T> тип создаваемого экземпляра. Соответствует полю matchingType.
     * @return возвращает экземпляр типа matchingType.
     * @throws InjectorException выбрасывается, когда в списке нет ни одного класса подходящего типа, либо когда в
     * списке более одного класса необходимого типа. Также выбрасывается, если не получилось создать экземпляр класса.
     */
    private static <T> T getMatchingComponentInstance(List<Class> classList, Class<T> matchingType)
            throws InjectorException {

        List<Class> matchingClassList = classList.stream().filter(
                cls -> cls.getAnnotation(Component.class) != null && matchingType.isAssignableFrom(cls))
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

    /**
     * Метод, находящий все классы в переданных пакетах. Пакеты ищутся в ресурсах ClassLoader'а.
     * @param packages пакеты, в которых будут искаться классы.
     * @return возвращает список с параметром Class.
     */
    private static List<Class> getClassesInPackages(String[] packages){
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
                } catch (ClassNotFoundException ignored) {
                }
            }
        }
        return classList;
    }
}

