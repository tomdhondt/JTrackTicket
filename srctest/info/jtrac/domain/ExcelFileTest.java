package info.jtrac.domain;

import java.io.FileInputStream;

import main.java.info.jtrac.domain.ExcelFile;
import junit.framework.TestCase;

public class ExcelFileTest extends TestCase {
    
    public void testLoadFile() throws Exception {
         ExcelFile ef = new ExcelFile(new FileInputStream("src/test/resources/data.xls"));
    } 
    
}
