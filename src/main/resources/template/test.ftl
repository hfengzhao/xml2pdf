<?xml version="1.0" encoding="utf-8" ?>
<PDFRoot>
    <font id="title-font"  font-family="Helvetica" style="normal" size="14"></font>
    <font id="content-font" font-family="Helvetica" style="normal" size="10"></font>
    <page></page>
    <paragraph font-ref="title-font" align="center" text="this is a paragraph!"></paragraph>

    <table space-before="10" space-after="10" num-cols="2">
        <#list testList as item>
            <cell font-ref="content-font" text="${item.a!""}"></cell>
            <cell font-ref="content-font" text="${item.b!""}"></cell>
        </#list>
    </table>

    <page></page>
    <paragraph space-after="25" space-before="5" font-ref="content-font" text="${paragraph}"
               first-line-indent="18" align="left"></paragraph>

    <line align="center" color="#BBAFFF" width="10" percentage="20"></line>

    <line offset="20.0" width="1"></line>
    <chunk text="this is chunk" background="#96CDCD" generic-tag="test" v-scaling="3" line-height="7" ></chunk>
    
	<image src="classpath:sky.jpg" border="top,bottom,left,box" border-width="2" border-color="#AF273A"  ></image>
</PDFRoot>