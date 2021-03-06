package net.ask39.service;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import net.ask39.enums.MyConstants;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.LineIterator;

import java.io.File;
import java.io.OutputStream;
import java.util.List;

/**
 * @author zhangzheng
 * @date 2021-01-06
 **/
public abstract class BaseMigration<T extends List<String>> implements Migration<T> {

    private final OutputStream outputStream;
    private String charsetName = MyConstants.CHART_SET.toString();

    public BaseMigration(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    public BaseMigration(OutputStream outputStream, String charsetName) {
        this.outputStream = outputStream;
        this.charsetName = charsetName;
    }

    @Override
    public void before() throws Exception {

    }

    @Override
    public void reader(File file) throws Exception{
        before();
        try(LineIterator it = FileUtils.lineIterator(file, charsetName)) {
            while (it.hasNext()) {
                T line = convert(it.nextLine());
                writer(process(line));
            }
        }
        after();
    }

    @Override
    public void writer(T t) throws Exception {
        if(t == null){
            return;
        }
        IOUtils.writeLines(Lists.newArrayList(Joiner.on(MyConstants.ESC).useForNull("").join(t)), System.getProperty("line.separator"), outputStream, MyConstants.CHART_SET);
    }

    @Override
    public void after() throws Exception {
        outputStream.close();
    }
}
