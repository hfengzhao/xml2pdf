package pdf.parser.element;

import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import org.dom4j.Attribute;
import org.dom4j.Element;
import pdf.parser.BaseParser;
import pdf.parser.ParserUtils;

import java.util.Iterator;
import java.util.Map;

/**
 * Created by DELL on 2018/7/23.
 */
public class ParagraphParser extends BaseParser {

    private static final ParagraphParser PARAGRAPH_PARSER = new ParagraphParser();

    private ParagraphParser(){

    }

    public static ParagraphParser getInstance(){
        return PARAGRAPH_PARSER;
    }

    @Override
    public Object parse(Element XmlElement, Map<String,Object> refMap)  throws Exception {
        // 构造函数参数
        String text = "";
        Attribute textAttr = XmlElement.attribute("text");
        Attribute fontAttr = XmlElement.attribute("font-ref");
        Font font = new Font();

        if(textAttr != null){
            text = textAttr.getValue();
        }

        if(fontAttr != null){
            font =  ((Map<String,Font>)refMap.get("fontMap")).get(fontAttr.getValue());
        }

        Paragraph paragraph = new Paragraph(text,font);

        Iterator attrIt = XmlElement.attributeIterator();

        while (attrIt.hasNext()){
            Attribute attr = (Attribute)attrIt.next();
            String attrName = attr.getName();
            String attrValue = attr.getValue();
            switch (attrName){
                case "text":
                    break;
                case "font-ref":
                    //paragraph.setFont(this.fontMap.get(attrValue));
                    break;
                case "align":
                    paragraph.setAlignment(ParserUtils.getAlignment(attrValue));
                    break;
                case "space-after":
                    paragraph.setSpacingAfter(Float.parseFloat(attrValue));
                    break;
                case "space-before":
                    paragraph.setSpacingBefore(Float.parseFloat(attrValue));
                    break;
                case "first-line-indent":
                    paragraph.setFirstLineIndent(Float.parseFloat(attrValue));
                    break;
                case "leading":
                    paragraph.setLeading(Float.parseFloat(attrValue));
                    break;
                case "multi-leading":
                    paragraph.setMultipliedLeading(Float.parseFloat(attrValue));
                    break;
                default:
                    throw new Exception("未知的paragraph属性："+attrName);
            }
        }

        return paragraph;
    }
}
