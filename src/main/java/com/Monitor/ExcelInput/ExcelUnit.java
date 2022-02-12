package com.Monitor.ExcelInput;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.excel.read.metadata.ReadSheet;
import java.io.File;

public class ExcelUnit {
    public static void read(File f) {
        ExcelReader reader = null;
        ExcelLinstener listener = new ExcelLinstener();
        reader = EasyExcel.read(f, (ReadListener)listener).build();
        ReadSheet sheet = EasyExcel.readSheet(Integer.valueOf(0)).build();
        reader.read(new ReadSheet[] { sheet });
        if (reader != null)
            reader.finish();
    }
}
