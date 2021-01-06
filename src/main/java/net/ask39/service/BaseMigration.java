package net.ask39.service;

import com.google.common.collect.Lists;
import net.ask39.enums.MyConstants;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.LineIterator;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @author zhangzheng
 * @date 2021-01-06
 **/
public abstract class BaseMigration<T> implements Migration<T> {

    private final OutputStream outputStream;

    public BaseMigration(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    @Override
    public void before() throws Exception {

    }

    @Override
    public void reader(File file) throws Exception{
        before();
        try(LineIterator it = FileUtils.lineIterator(file, MyConstants.CHART_SET.toString())) {
            while (it.hasNext()) {
                T line = convert(it.nextLine());
                process(line);
                writer(line);
            }
        }
        after();
    }

    @Override
    public void writer(T t) throws IOException {
        IOUtils.writeLines(Lists.newArrayList(t), System.getProperty("line.separator"), outputStream, MyConstants.CHART_SET);
    }

    @Override
    public void after() throws Exception {
        outputStream.close();
    }
}
