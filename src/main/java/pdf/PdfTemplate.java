package pdf;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.pdf.PdfWriter;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import pdf.parser.ParserFactory;

import java.io.ByteArrayInputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by DELL on 2018/7/23.
 */
public class PdfTemplate {
    private ByteArrayInputStream XmlStream; // xml文本内容流
    private Document document; // PdfDocument对象

    public PdfTemplate(ByteArrayInputStream XmlStream, Document document){
        this.XmlStream = XmlStream;
        this.document = document;
    }

    /**
     * 通过xml文本生成pdf
     * @param outputStream
     */
    public void process(OutputStream outputStream) throws DocumentException {
        PdfWriter writer = PdfWriter.getInstance(this.document,outputStream);
        this.document.open();
        SAXReader reader = new SAXReader();
        try {
            org.dom4j.Document XmlDoc = reader.read(this.XmlStream);
            final Element XmlRoot = XmlDoc.getRootElement();

            String rootName = XmlRoot.getName();

            if(rootName != "PDFRoot") throw new  Exception("无效的模板根节点：" + rootName);

            Iterator elemIt = XmlRoot.elementIterator();

            Map<String,Object> refMap = new HashMap<String, Object>();
            refMap.put("fontMap",new HashMap<String,Font>());

            while (elemIt.hasNext()){
                Element subElem = (Element) elemIt.next();

                if("page".equals(subElem.getName())){
                    this.document.newPage();
                    continue;
                }

                Object object = ParserFactory.getParser(subElem.getName()).parse(subElem,refMap);
                if(object != null && object instanceof com.itextpdf.text.Element){
                    this.document.add((com.itextpdf.text.Element) object);
                }
            }
            this.document.close();
            writer.close();
        }catch (Exception e){
            System.err.println("处理失败!");
            e.printStackTrace();
        }
    }

    public Document getDocument(){
        return this.document;
    }
}
