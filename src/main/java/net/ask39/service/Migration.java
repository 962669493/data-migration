package net.ask39.service;

import java.io.File;
/**
 * @author zhangzheng
 * @date 2021-01-06
 **/
public interface Migration<T> {
    void before() throws Exception;
    void reader(File file) throws Exception;
    T convert(String line) throws Exception;
    T process(T t) throws Exception;
    void writer(T t) throws Exception;
    void after() throws Exception;
}
