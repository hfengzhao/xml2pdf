package pdf.parser;

import org.dom4j.Element;

import java.util.Map;

/**
 * Created by DELL on 2018/7/23.
 */
public interface Parser {

    public Object parse(Element object, Map<String, Object> refMap) throws Exception;
}
