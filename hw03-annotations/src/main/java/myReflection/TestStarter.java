package myReflection;

import myAnnotations.After;
import myAnnotations.Before;
import myAnnotations.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class TestStarter {

    /**
     * Запускатор
     * @param className Имя класса с тестами
     */
    public static <T> void start(String className) {
        try {
            Class<?> clazz = Class.forName(className);
            List<Method> beforeList = new ArrayList<>();
            List<Method> afterList = new ArrayList<>();
            List<Method> testList = new ArrayList<>();
            Map<String, Integer> result = new HashMap<>();
            // подготовим список тестов
            for (Method method : clazz.getDeclaredMethods()) {
                if (method.isAnnotationPresent(Before.class)) {
                    beforeList.add(method);
                } else if (method.isAnnotationPresent(Test.class)) {
                    testList.add(method);
                } else if (method.isAnnotationPresent(After.class)) {
                    afterList.add(method);
                }
            }
            result.put("Total tests", testList.size());
            result.put("OK", 0);
            result.put("FAIL", 0);
            // запустим каждый тест и посчитаем результат
            for (Method method : testList) {
                if (runOneTest(clazz, method, beforeList, afterList)) {
                    result.merge("OK", 1, Integer::sum);
                } else {
                    result.merge("FAIL", 1, Integer::sum);
                }
            };
            System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Запуск всех before перед тестом, сам тест, и всех after после теста
     * @param clazz Класс с тестами
     * @param testMethod метод с аннотацией {@link myAnnotations.Test Test}
     * @param beforeList список методов с аннотацией {@link myAnnotations.Before Before}
     * @param afterList список методов с аннотацией {@link myAnnotations.After After}
     */
    private static <T> boolean runOneTest(Class<T> clazz,
                                       Method testMethod,
                                       List<Method> beforeList,
                                       List<Method> afterList) {
        try {
            T newClazz = clazz.getDeclaredConstructor().newInstance();
            // before. No order
            for (Method beforeMethod : beforeList) {
                singleMethod(beforeMethod, newClazz);
            }
            // test. No order
            singleMethod(testMethod, newClazz);
            //after. No order
            for (Method afterMethod : afterList) {
                singleMethod(afterMethod, newClazz);
            }
        } catch (Exception ex) {
            System.out.printf("%s failed: %s%n", testMethod.getName(), ex.getCause());
            return false;
        }
        return true;

    }

    /**
     * Атомарный вызов метода
     * @param testMethod метод для запуска
     * @param newClazz экземпляр тестируемого класса
     */
    private static <T> void singleMethod(Method testMethod, T newClazz) throws Exception {
        Method tMethod = newClazz.getClass().getDeclaredMethod(testMethod.getName());
        tMethod.setAccessible(true);
        try {
            tMethod.invoke(newClazz);
        } catch (InvocationTargetException ex) {
            System.out.printf("%s - failed: %s%n", tMethod.getName(), ex.getCause());
            throw ex;
        }
    }

}
