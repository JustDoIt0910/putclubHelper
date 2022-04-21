import cn.wanghaomiao.xpath.exception.NoSuchAxisException;
import cn.wanghaomiao.xpath.exception.NoSuchFunctionException;
import cn.wanghaomiao.xpath.exception.XpathSyntaxErrorException;
import cn.wanghaomiao.xpath.model.JXDocument;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public class XpathTest {
    @Test
    public void test() throws IOException, NoSuchFunctionException, XpathSyntaxErrorException, NoSuchAxisException {
        FileInputStream fs = new FileInputStream(new File("resp.html"));
        byte[] data = fs.readAllBytes();
        String doc = new String(data, "gbk");
        JXDocument jxDocument = new JXDocument(doc);
        String exp = "//table[@class='datatable']/tbody/tr/th[@class='subject common']/span/a/@href";
        List<Object> res = jxDocument.sel(exp);
        System.out.println(res);
    }
}
