package pdf.parser.element;

import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPRow;
import org.dom4j.Element;
import pdf.parser.BaseParser;
import pdf.parser.ParserFactory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by DELL on 2018/7/23.
 */
public class PdfRowParser extends BaseParser {

    private final static PdfRowParser ROW_PARSER = new PdfRowParser();

    private PdfRowParser(){

    }

    public static PdfRowParser getInstance(){
        return ROW_PARSER;
    }

    @Override
    public Object parse(Element XmlElement, Map<String,Object> refMap) throws Exception {
        Iterator elemIt = XmlElement.elementIterator();

        List<PdfPCell> cells = new ArrayList<PdfPCell>();

        while (elemIt.hasNext()){
            Element subElem = (Element) elemIt.next();

            if(subElem.getName() != "cell") throw new Exception("row中发现无效子标签：" + subElem.getName());
            PdfCellParser parser = (PdfCellParser) ParserFactory.getParser("cell");
            cells.add((PdfPCell) parser.parse(subElem,refMap));
        }

        // 构造PdfPRow
        PdfPRow row = new PdfPRow(cells.toArray(new PdfPCell[]{}));
        return row;
    }
}
