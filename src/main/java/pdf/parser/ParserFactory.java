package pdf.parser;

import pdf.parser.element.*;

/**
 * Created by DELL on 2018/7/23.
 */
public class ParserFactory {

    private ParserFactory(){

    }

    public static Parser getParser(String tagName) throws Exception {
        switch (tagName){
            // element 标签
            case "table":
                return PdfTableParser.getInstance();
            case "cell":
                return PdfCellParser.getInstance();
            case "row":
                return PdfRowParser.getInstance();
            case "paragraph":
                return ParagraphParser.getInstance();
            case "line":
                return LineSeparatorParser.getInstance();

            // font标签
            case "font":
                return FontParser.getInstance();

            default:
                throw new Exception("未识别的标签名：" + tagName);
        }
    }

}
