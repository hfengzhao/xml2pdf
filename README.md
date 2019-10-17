# xml2pdf
itext+freemarker 实现xml转pdf

## PdfTemplateUtils使用说明

### 1、准备模板
#### 1）、模板相关配置及说明
    模板目录：所有pdf模板均放在resources/template路径下，freemaker会自动扫描模板目录，并加载所有模板
    模板命名规则：后缀均为ftl
    模板格式：ftl是Freemarker模板的文件后缀名,是一种xml格式文件。使用PdfTemplateUtils解析模板时，必须
    使用pdf包中支持的模板标签及属性名

#### 2)、pdf模板标签
##### a)、标签详细说明：
    
    PDFRoot：pdf模板根标签
        无属性
    font：字体
        id:字体id###String
        【color】：字体颜色，十六进制数字，例如：#FFFFFF###String
        【encoding】：编码###String
        【font-family】：###String
        【size】：字体大小###int
        【style】：字体样式###String
    page：pdf文件会从page标签位置处另起一页，直至遇到下一个page标签
        无属性
    paragraph：段落
        【text】：段落内容###String
        【font-ref】：字体id###String
        【align】：对齐方式###String
        【space-before】：段落前间距###float
        【space-after】：段落后间距###float
        【first-line-indent】：首行缩进###float
    line：pdf文件会在line标签处加上一条分割线，默认分割线为与pdf文档等宽居中显示
        【offset】：偏移量###float
        【align】：对齐方式，默认为居中对齐###String
        【width】：宽度###float
    table：pdf表格，可接受的子标签：row，cell
        【num-cols】：表格列数
        【h-align】：水平对齐方式###String
        【space-before】：表格前间距###float
        【space-after】：表格后间距###float
        【width-percent】：表格宽度百分比###float
    row：pdf表格行，可接受的子标签：cell
        无属性
    cell:pdf单元格
        【text】：单元格中显示的文本内容###String
        【font-ref】：font标签的id###String
        【h-align】：单元格水平对齐方式###String
        【v-align】：单元格垂直对齐方式###String
        【col-span】：单元格所占列数###int
        【row-span】：单元格所占行数###int
      
      
    注：对齐方式取值：center,left,right,bottom,baseline,justified,justifued-all,middler,top,default,anchor
       字体样式取值：normal,bold,italic,oblique,underline,line-through
       【】内为可选属性，不带【】的为必须属性。所有标签名及属性均区分大小写。
       
### 2、通过模板创建pdf输出流
    调用PdfTemplateUtils. createPdfByTemplate(String templateName, Map<String,Object> dataMap, OutputStream outputStream)
    参数：templateName：模板名称
         dataMap：需要填充的数据
         outputStream：输出流
         
         
