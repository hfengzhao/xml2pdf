package pdf.parser;

import com.itextpdf.text.Font;
import com.itextpdf.text.pdf.BaseFont;
import org.dom4j.Attribute;
import org.dom4j.Element;

import java.util.Iterator;
import java.util.Map;


/**
 * Created by DELL on 2018/7/23.
 */
public class FontParser extends BaseParser{
    private final static FontParser FONT_PARSER = new FontParser();

    private FontParser(){

    }

    public static FontParser getInstance(){
        return FONT_PARSER;
    }


    @Override
    public Object parse(Element XmlElement, Map<String,Object> refMap) throws Exception {
        String idValue = "";
        Attribute idAttr = XmlElement.attribute("id");
        if(idAttr == null){
            throw new Exception("请为font标签指定id");
        }

        String fontName = "STSong-Light";
        String fontEncoding = "UniGB-UCS2-H";

        Attribute nameAttr = XmlElement.attribute("name");
        Attribute encodingAttr = XmlElement.attribute("encoding");

        if(nameAttr != null){
            fontName = nameAttr.getValue();
        }
        if(encodingAttr != null){
            fontEncoding = encodingAttr.getValue();
        }

        Font font = new Font(BaseFont.createFont(fontName,fontEncoding,true));

        Iterator attrIt = XmlElement.attributeIterator();

        while (attrIt.hasNext()){
            Attribute attr = (Attribute) attrIt.next();
            String attrName = attr.getName();
            String attrValue = attr.getValue();

            switch (attrName){
                case "id":
                    idValue = attrValue;
                    break;
                case "style":
                    font.setStyle(Font.getStyleValue(attrValue));
                    break;
                case "font-family":
                    font.setFamily(attrValue);
                    break;
                case "size":
                    font.setSize(Float.parseFloat(attrValue));
                    break;
                case "color":
                    font.setColor(ParserUtils.getBaseColor(attrValue));
                default:
                    throw new Exception("未识别的属性：" + attrName);
            }
        }

        // 将font添加到FONT_MAP中
        Map<String,Font> fontMap = (Map<String,Font>) refMap.get("fontMap");
		if(fontMap.get(idValue) != null){
			throw new Exception("字体id重复："+idValue);
		}
        fontMap.put(idValue,font);

        return font;
    }
}
