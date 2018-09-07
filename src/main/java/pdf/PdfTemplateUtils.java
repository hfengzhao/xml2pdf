package pdf;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import freemarker.cache.MruCacheStorage;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.*;
import java.util.Map;

/**
 * Created by DELL on 2018/7/23.
 */
public class PdfTemplateUtils {

    private static final Configuration CONFIGURATION = new Configuration(Configuration.VERSION_2_3_23);

    static {
        // 模板默认编码
        CONFIGURATION.setDefaultEncoding("UTF-8");

        // 设置缓存，强烈部分：20，轻微部分：250
        CONFIGURATION.setCacheStorage(new MruCacheStorage(20,250));

        // Pdf模板默认加载路径
        try {
            CONFIGURATION.setDirectoryForTemplateLoading(new File(getPdfTemplateURL()));
        } catch (IOException e) {
            System.out.println("pdf模板加载失败！");
        }
    }

    /**
     * 获取pdf模板默认加载路径
     * @return
     * @throws FileNotFoundException
     */
    private static String getPdfTemplateURL() throws FileNotFoundException {
        String url = PdfTemplateUtils.class.getClassLoader().getResource("template").getPath();
        if("\\".equals(File.separator)){
            return url.substring(1);
        }
        return url;
    }

    /**
     * 根据模板生成pdf
     * @param templateName
     * @param dataMap
     * @param outputStream
     */
    public static void createPdfByTemplate(String templateName, Map<String,Object> dataMap, OutputStream outputStream)
            throws IOException, TemplateException, DocumentException {
        Template template = CONFIGURATION.getTemplate(templateName);
        // 生成xml文本
        StringWriter sw = new StringWriter();
        template.process(dataMap,sw);

        ByteArrayInputStream bais = new ByteArrayInputStream(sw.toString().getBytes());
        sw.close();

        PdfTemplate pdfTemplate = new PdfTemplate(bais,new Document(PageSize.A4));
        pdfTemplate.process(outputStream);

        outputStream.close();
    }

}
