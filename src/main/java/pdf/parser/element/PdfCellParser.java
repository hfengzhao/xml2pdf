package pdf.parser.element;

import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import org.dom4j.Attribute;
import org.dom4j.Element;
import pdf.parser.BaseParser;
import pdf.parser.ParserUtils;

import java.util.Iterator;
import java.util.Map;

/**
 * Created by DELL on 2018/7/23.
 */
public class PdfCellParser extends BaseParser {

    private final static PdfCellParser CELL_PARSER = new PdfCellParser();

    private PdfCellParser(){

    }

    public static PdfCellParser getInstance(){
        return CELL_PARSER;
    }

    @Override
    public Object parse(Element XmlElement, Map<String,Object> refMap) throws Exception {
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

        PdfPCell cell = new PdfPCell(new Paragraph(text,font));

        Iterator attrIt = XmlElement.attributeIterator();

        while (attrIt.hasNext()){
            Attribute attr = (Attribute) attrIt.next();
            String attrName = attr.getName();
            String attrValue = attr.getValue();

            switch (attrName){
                case "font-ref":
                    break;
                case "text":
                    break;
                case "h-align":
                    cell.setHorizontalAlignment(ParserUtils.getAlignment(attrValue));
                    break;
                case "v-align":
                    cell.setVerticalAlignment(ParserUtils.getAlignment(attrValue));
                    break;
                case "fixed-height":
                    cell.setFixedHeight(Float.parseFloat(attrValue));
                    break;
                case "min-height":
                    cell.setMinimumHeight(Float.parseFloat(attrValue));
                    break;
                case "col-span":
                    cell.setColspan(Integer.parseInt(attrValue));
                    break;
                case "row-span":
                    cell.setRowspan(Integer.parseInt(attrValue));
                    break;
                case "border":
                    cell.setBorder(Integer.parseInt(attrValue));
                    break;
                case "border-width":
                    cell.setBorderWidth(Integer.parseInt(attrValue));
                    break;
                case "following-indent":
                    cell.setFollowingIndent(Integer.parseInt(attrValue));
                    break;
                case "indent":
                    cell.setIndent(Float.parseFloat(attrValue));
                    break;
                case "right-indent":
                    cell.setRightIndent(Float.parseFloat(attrValue));
                    break;
                case "no-wrap":
                    cell.setNoWrap(Boolean.parseBoolean(attrValue));
                    break;
                case "padding":
                    cell.setPadding(Float.parseFloat(attrValue));
                    break;
                case "padding-bottom":
                    cell.setPaddingBottom(Float.parseFloat(attrValue));
                    break;
                case "padding-left":
                    cell.setPaddingLeft(Float.parseFloat(attrValue));
                    break;
                case "padding-right":
                    cell.setPaddingRight(Float.parseFloat(attrValue));
                    break;
                case "padding-top":
                    cell.setPaddingTop(Float.parseFloat(attrValue));
                    break;
                default:
                    throw new Exception("未知的Cell属性：" + attrName);
            }
        }

        return cell;
    }
}
