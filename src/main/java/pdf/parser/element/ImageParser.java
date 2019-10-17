package pdf.parser.element;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;

import org.dom4j.Attribute;
import org.dom4j.Element;

import com.itextpdf.text.Image;
import com.itextpdf.text.ImgCCITT;
import com.itextpdf.text.Jpeg;
import com.itextpdf.text.Rectangle;

import pdf.parser.BaseParser;
import pdf.parser.ParserUtils;

public class ImageParser extends BaseParser {
	private final static ImageParser IMAGE_PARSER = new ImageParser();

	public ImageParser() {

	}

	public static ImageParser getInstance() {
		return IMAGE_PARSER;
	}

	@Override
	public Object parse(Element XmlElement, Map<String, Object> refMap) throws Exception {

		String src = "";
		Attribute srcAttribute = XmlElement.attribute("src");
		FileInputStream fis = null;
		Image image = null;

		if (srcAttribute != null) {
			src = srcAttribute.getValue().trim();
			// classpath
			if (src.startsWith("classpath:")) {
				src = src.substring(10);
				src = getClass().getClassLoader().getResource(src).getPath();
			}

			fis = new FileInputStream(new File(src));

			ByteArrayOutputStream outp = new ByteArrayOutputStream();
			int ch = 0;
			while ((ch = fis.read()) != -1)
				outp.write(ch);

			// 添加jpg
			if (src.endsWith(".jpg") || src.endsWith(".jpeg")) {
				image = new Jpeg(outp.toByteArray());
			}

			Iterator attrIt = XmlElement.attributeIterator();
			while (attrIt.hasNext()) {
				Attribute attr = (Attribute) attrIt.next();

				String attrName = attr.getName();
				String attrValue = attr.getValue();

				switch (attrName) {
				case "src":
					break;
				case "alt":
					image.setAlt(attrValue);
					break;

				case "backgound-color":
					image.setBackgroundColor(ParserUtils.getBaseColor(attrValue));
					break;
				case "border":
					int border = 0;
					for(String s : attrValue.split(",")) {
						if("top".equals(s.toLowerCase())) {
							border += Rectangle.TOP;
						}else if("bottom".equals(s.toLowerCase())) {
							border += Rectangle.BOTTOM;
						}else if("left".equals(s.toLowerCase())) {
							border += Rectangle.LEFT;
						}else if("right".equals(s.toLowerCase())) {
							border += Rectangle.RIGHT;
						}else if("box".equals(s.toLowerCase())) {
							border = Rectangle.BOX;
							break;
						}else {
							throw new Exception("无效属性值：" + s);
						}
					}
					image.setBorder(border);
					break;
				case "border-color":
					image.setBorderColor(ParserUtils.getBaseColor(attrValue));
					break;
				case "color-transform":
					image.setColorTransform(Integer.valueOf(attrValue));
					break;
				case "border-corlor-top":
					image.setBorderColorTop(ParserUtils.getBaseColor(attrValue));
					break;
				case "border-corlor-bottom":
					image.setBorderColorBottom(ParserUtils.getBaseColor(attrValue));
					break;
				case "border-corlor-left":
					image.setBorderColorLeft(ParserUtils.getBaseColor(attrValue));
					break;
				case "border-corlor-right":
					image.setBorderColorRight(ParserUtils.getBaseColor(attrValue));
					break;
				case "border-width":
					image.setBorderWidth(Float.valueOf(attrValue));
					break;
				case "border-width-top":
					image.setBorderWidthTop(Float.valueOf(attrValue));
					break;
				case "border-width-bottom":
					image.setBorderWidthBottom(Float.valueOf(attrValue));
					break;
				case "border-width-left":
					image.setBorderWidthLeft(Float.valueOf(attrValue));
					break;
				case "border-width-right":
					image.setBorderWidthRight(Float.valueOf(attrValue));
					break;
				case "bottom":
					image.setBottom(Float.valueOf(attrValue));
					break;
				case "compression-level":
					image.setCompressionLevel(Integer.valueOf(attrValue));
					break;
				case "left":
					image.setLeft(Integer.valueOf(attrValue));
					break;
				case "top":
					image.setTop(Float.valueOf(attrValue));
					break;
				case "right":
					image.setRight(Float.valueOf(attrValue));
					break;
				case "url":
					image.setUrl(new URL(attrValue));
					break;
				default:
					throw new Exception("无效的Image属性：" + attrName);
				}
			}
		}

		return image;

	}

}
