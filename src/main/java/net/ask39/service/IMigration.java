package net.ask39.service;

import java.io.IOException;

/**
 * 数据迁移基础接口
 *
 * @author zhangzheng
 * @date 2021-01-03
 **/
public interface IMigration<T> {
    /**
     * 读取数据
     * @author zhangzheng
     * @date 2021/1/3
     */
    void reader() throws IOException;

    /**
     * 写入数据
     * @author zhangzheng
     * @date 2021/1/3
     * @param t
     */
    void writer(T t);
}
