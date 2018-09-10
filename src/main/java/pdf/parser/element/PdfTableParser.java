package pdf.parser.element;

import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPRow;
import com.itextpdf.text.pdf.PdfPTable;
import org.dom4j.Attribute;
import org.dom4j.Element;
import pdf.parser.BaseParser;
import pdf.parser.ParserFactory;
import pdf.parser.ParserUtils;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by DELL on 2018/7/23.
 */
public class PdfTableParser extends BaseParser {

    private final static PdfTableParser TABLE_PARSER = new PdfTableParser();

    private PdfTableParser(){

    }

    public static PdfTableParser getInstance(){
        return TABLE_PARSER;
    }

    @Override
    public Object parse(Element XmlElement, Map<String,Object> refMap) throws Exception {
        Iterator elemIt = XmlElement.elementIterator();

        // 构造PdfPTable
        PdfPTable table = new PdfPTable(1);

        Iterator attrIt = XmlElement.attributeIterator();

        // 设置table属性
        while (attrIt.hasNext()){
            Attribute attr = (Attribute) attrIt.next();
            String attrName = attr.getName();
            String attrValue = attr.getValue();

            switch (attrName){
                case "h-align":
                    table.setHorizontalAlignment(ParserUtils.getAlignment(attrValue));
                    break;
                case "space-before":
                    table.setSpacingBefore(Float.parseFloat(attrValue));
                    break;
                case "space-after":
                    table.setSpacingAfter(Float.parseFloat(attrValue));
                    break;
                case "width-percent":
                    table.setWidthPercentage(Float.parseFloat(attrValue));
                    break;
                case "complete":
                    table.setComplete(Boolean.parseBoolean(attrValue));
                    break;
                case "total-width":
                    table.setTotalWidth(Float.parseFloat(attrValue));
                    break;
                case "num-cols":
                    table.resetColumnCount(Integer.parseInt(attrValue));
                    break;
                default:
                    throw new Exception("未知的table属性：" + attrName);
            }
        }

        // 向table中添加元素
        while (elemIt.hasNext()){
            Element subElem = (Element) elemIt.next();

            String nodeName = subElem.getName();

            if(!"cell".equals(nodeName) && !"row".equals(nodeName)) throw new Exception("table中发现无效标签：" + nodeName);

            if("cell".equals(nodeName)){
                table.addCell((PdfPCell) ParserFactory.getParser("cell").parse(subElem,refMap));
            }

            if("row".equals(nodeName)){
                List<PdfPRow> rows = table.getRows();
                rows.add((PdfPRow) ParserFactory.getParser("row").parse(subElem,refMap));
            }
        }

        return table;
    }
}
