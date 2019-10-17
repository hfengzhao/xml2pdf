package pdf.parser.element;

import java.util.Iterator;
import java.util.Map;

import org.dom4j.Attribute;
import org.dom4j.Element;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Font;

import pdf.parser.BaseParser;
import pdf.parser.ParserUtils;

public class ChunkParser extends BaseParser{
	
	private final static ChunkParser CHUNK_PARSER = new ChunkParser();
	
	public ChunkParser() {
		
	}
	
	public static ChunkParser getInstance() {
		return CHUNK_PARSER;
	}
	
	@Override
	public Object parse(Element XmlElement, Map<String, Object> refMap) throws Exception {
		String text = "";
        Font font = new Font();
        Attribute textAttr = XmlElement.attribute("text");
        Attribute fontAttr = XmlElement.attribute("font-ref");

        if(textAttr != null){
            text = textAttr.getValue();
        }

        if(fontAttr != null){
            font =  ((Map<String,Font>)refMap.get("fontMap")).get(fontAttr.getValue());
        }
        
        Chunk chunk = new Chunk(text, font);
        
        Iterator attrIt = XmlElement.attributeIterator();
        
        while (attrIt.hasNext()){
            Attribute attr = (Attribute)attrIt.next();
            String attrName = attr.getName();
            String attrValue = attr.getValue();
            
            switch (attrName){
            case "text":
            	break;
            case "char-spacing":
            	chunk.setCharacterSpacing(Float.valueOf(attrValue));
            case "background":
            	chunk.setBackground(ParserUtils.getBaseColor(attrValue));
            	break;
            case "generic-tag":
            	chunk.setGenericTag(attrValue);
            	break;
            case "v-scaling":
            	chunk.setHorizontalScaling(Float.valueOf(attrValue));
            	break;
            case "line-height":
            	chunk.setLineHeight(Float.valueOf(attrValue));
            	break;
            	
            case "word-spacing":
            	chunk.setWordSpacing(Float.valueOf(attrValue));
            	break;
            default:
            	throw new Exception("未知的chunk属性：" + attrName);	
            }
        }
        
        return chunk;
	}
}
