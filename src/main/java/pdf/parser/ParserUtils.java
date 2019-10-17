package pdf.parser;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Element;

/**
 * Created by DELL on 2018/7/23.
 */
public class ParserUtils {

    /**
     * 获取pdfElement布局
     * @param align
     * @return
     */
    public static int getAlignment(String align) throws Exception {
        switch (align){
            case "center":
                return Element.ALIGN_CENTER;
            case "left":
                return Element.ALIGN_LEFT;
            case "right":
                return Element.ALIGN_RIGHT;
            case "bottom":
                return Element.ALIGN_BOTTOM;
            case "baseline":
                return Element.ALIGN_BASELINE;
            case "justified":
                return Element.ALIGN_JUSTIFIED;
            case "middle":
                return Element.ALIGN_MIDDLE;
            case "top":
                return Element.ALIGN_TOP;
            case "justified-all":
                return Element.ALIGN_JUSTIFIED_ALL;
            case "anchor":
                return Element.ANCHOR;
            case "default":
                return Element.ALIGN_UNDEFINED;
            default:
                throw new Exception("无效的align值：" + align);
        }
    }

    /**
     * 获取baseColor
     * @param color
     * @return
     */
    public static BaseColor getBaseColor(String color){
        switch (color){
            case "red":
                return BaseColor.RED;
            case "white":
                return BaseColor.WHITE;
            case "blue":
                return BaseColor.BLUE;
            case "black":
                return BaseColor.BLACK;
            case "cyan":
                return BaseColor.CYAN;
            case "dark-gray":
                return BaseColor.DARK_GRAY;
            case "gray":
                return BaseColor.DARK_GRAY;
            case "green":
                return BaseColor.GREEN;
            case "light-gray":
                return BaseColor.LIGHT_GRAY;
            case "magenta":
                return BaseColor.MAGENTA;
            case "orange":
                return BaseColor.ORANGE;
            case "pink":
                return BaseColor.PINK;
            case "yellow":
                return BaseColor.YELLOW;
            default:
            	if(!color.matches("^#[0-9A-Fa-f]{6}$")) {
            		return new BaseColor(0,0,0);
            	}
        		int red = Integer.parseInt(color.substring(1, 3),16);
            	int green = Integer.parseInt(color.substring(3, 5),16);
            	int blue = Integer.parseInt(color.substring(5, 7),16);
            	
            	return new BaseColor(red,green,blue);
            
        }
    }
}
