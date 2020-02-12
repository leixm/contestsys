<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page isELIgnored="false"%>
		<%@ page contentType="text/html;charset=UTF-8" language="java"%>
		<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
		<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
		
		
<!-- saved from url=(0035)https://ks.wjx.top/jq/39372975.aspx -->
<html xmlns="http://www.w3.org/1999/xhtml"><head id="ctl00_Head1"><meta http-equiv="Content-Type" content="text/html; charset=UTF-8"><title>
	期末考试
</title><meta name="robots" content="noindex"><meta name="renderer" content="webkit|ie-comp|ie-stand"><meta name="og:type" content="article"><meta name="og:release_date" content="2019-05-13"><meta name="og:image" content="https://image.wjx.cn/images/wlogo.png"><meta name="og:title" content="期末考试"><meta name="og:description" content="我在问卷星上发布了问卷《期末考试》，请帮忙填写，非常感谢！"><link type="text/css" rel="stylesheet" href="./contest/NewDefault.css"><link id="ctl00_hrefMobile" rel="alternate" media="only screen and(max-width: 640px)" href="https://ks.wjx.top/m/39372975.aspx"><link id="ctl00_hrefCanonical" rel="canonical" href="https://www.wjx.cn/jq/39372975.aspx"><meta name="applicable-device" content="pc"><link rel="dns-prefetch" href="https://sojump.cn-hangzhou.log.aliyuncs.com/"><link href="./contest/q.css" rel="stylesheet" type="text/css"><link href="./contest/newsolid_114.css" rel="stylesheet" type="text/css"></head>


<link rel="stylesheet" href="HUSTOJ_files/bootstrap.min.css">


<script type="text/javascript">
    
	
	function selected(obj)
	{
		obj.parentNode.setAttribute("style","width:99%; background: rgb(233,233,233);")
	}
	function unselected(obj)
	{
		obj.parentNode.setAttribute("style","width:99%;")
	}
	
	function checkboxClick(obj){
		var li = obj.parentNode;
		if(li.children[1].getAttribute("checked")=="checked"){
			li.children[1].removeAttribute("checked")
			li.children[0].setAttribute("class","jqCheckbox")
		}
		else
		{
			li.children[1].setAttribute("checked","checked")
			li.children[0].setAttribute("class","jqCheckbox jqChecked")
		}
	}
	
	function radioClick(obj)
	{
		var li = obj.parentNode;
		var ul = li.parentNode;
		for(var i=0;i<ul.children.length-1;i++)
		{
			ul.children[i].children[0].setAttribute("class","jqRadio")
			ul.children[i].children[1].removeAttribute("checked");
		}
		li.children[0].setAttribute("class","jqRadio jqChecked")
		li.children[1].setAttribute("checked","checked")
	}
	  
	function getMaxTimeStr(a) {
    var e, b = "",
    c = a,
    d = parseInt(c / 3600);
    return d ? (10 > d && (b += "0"), b += d + ":", c %= 3600) : b = "00:",
    e = parseInt(c / 60),
    e ? (10 > e && (b += "0"), b += e + ":", c %= 60) : b += "00:",
    0 > c && (c = 0),
    c ? (10 > c && (b += "0"), b += c) : b += "00",
    b
}

	
</script>


<script src="https://cdn.staticfile.org/jquery/2.1.1/jquery.min.js"></script>
<script src="https://cdn.staticfile.org/twitter-bootstrap/3.3.7/js/bootstrap.min.js"></script>

<!-- 可选的Bootstrap主题文件（一般不用引入） -->
<link rel="stylesheet" href="./HUSTOJ_files/bootstrap-theme.min.css">
<link rel="stylesheet" href="./HUSTOJ_files/white.css">
<link rel="stylesheet" href="./HUSTOJ_files/katex.min.css">
<link rel="stylesheet" href="./HUSTOJ_files/mathjax.css">
<link rel="stylesheet" href="lib/layui/css/layui.css">
<script src="./HUSTOJ_files/ace.js"></script>
<script src="./HUSTOJ_files/checksource.js"></script>
<script src="./lib/layui/layui.js" charset="utf-8"></script>	  

<style>
#source {
    width: 80%;
    height: 600px;
}
</style>
    <style id="ace_editor.css">.ace_editor {position: relative;overflow: hidden;font: 12px/normal 'Monaco', 'Menlo', 'Ubuntu Mono', 'Consolas', 'source-code-pro', monospace;direction: ltr;text-align: left;-webkit-tap-highlight-color: rgba(0, 0, 0, 0);}.ace_scroller {position: absolute;overflow: hidden;top: 0;bottom: 0;background-color: inherit;-ms-user-select: none;-moz-user-select: none;-webkit-user-select: none;user-select: none;cursor: text;}.ace_content {position: absolute;box-sizing: border-box;min-width: 100%;}.ace_dragging .ace_scroller:before{position: absolute;top: 0;left: 0;right: 0;bottom: 0;content: '';background: rgba(250, 250, 250, 0.01);z-index: 1000;}.ace_dragging.ace_dark .ace_scroller:before{background: rgba(0, 0, 0, 0.01);}.ace_selecting, .ace_selecting * {cursor: text !important;}.ace_gutter {position: absolute;overflow : hidden;width: auto;top: 0;bottom: 0;left: 0;cursor: default;z-index: 4;-ms-user-select: none;-moz-user-select: none;-webkit-user-select: none;user-select: none;}.ace_gutter-active-line {position: absolute;left: 0;right: 0;}.ace_scroller.ace_scroll-left {box-shadow: 17px 0 16px -16px rgba(0, 0, 0, 0.4) inset;}.ace_gutter-cell {padding-left: 19px;padding-right: 6px;background-repeat: no-repeat;}.ace_gutter-cell.ace_error {background-image: url("data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABAAAAAQCAMAAAAoLQ9TAAABOFBMVEX/////////QRswFAb/Ui4wFAYwFAYwFAaWGAfDRymzOSH/PxswFAb/SiUwFAYwFAbUPRvjQiDllog5HhHdRybsTi3/Tyv9Tir+Syj/UC3////XurebMBIwFAb/RSHbPx/gUzfdwL3kzMivKBAwFAbbvbnhPx66NhowFAYwFAaZJg8wFAaxKBDZurf/RB6mMxb/SCMwFAYwFAbxQB3+RB4wFAb/Qhy4Oh+4QifbNRcwFAYwFAYwFAb/QRzdNhgwFAYwFAbav7v/Uy7oaE68MBK5LxLewr/r2NXewLswFAaxJw4wFAbkPRy2PyYwFAaxKhLm1tMwFAazPiQwFAaUGAb/QBrfOx3bvrv/VC/maE4wFAbRPBq6MRO8Qynew8Dp2tjfwb0wFAbx6eju5+by6uns4uH9/f36+vr/GkHjAAAAYnRSTlMAGt+64rnWu/bo8eAA4InH3+DwoN7j4eLi4xP99Nfg4+b+/u9B/eDs1MD1mO7+4PHg2MXa347g7vDizMLN4eG+Pv7i5evs/v79yu7S3/DV7/498Yv24eH+4ufQ3Ozu/v7+y13sRqwAAADLSURBVHjaZc/XDsFgGIBhtDrshlitmk2IrbHFqL2pvXf/+78DPokj7+Fz9qpU/9UXJIlhmPaTaQ6QPaz0mm+5gwkgovcV6GZzd5JtCQwgsxoHOvJO15kleRLAnMgHFIESUEPmawB9ngmelTtipwwfASilxOLyiV5UVUyVAfbG0cCPHig+GBkzAENHS0AstVF6bacZIOzgLmxsHbt2OecNgJC83JERmePUYq8ARGkJx6XtFsdddBQgZE2nPR6CICZhawjA4Fb/chv+399kfR+MMMDGOQAAAABJRU5ErkJggg==");background-repeat: no-repeat;background-position: 2px center;}.ace_gutter-cell.ace_warning {background-image: url("data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABAAAAAQCAMAAAAoLQ9TAAAAmVBMVEX///8AAAD///8AAAAAAABPSzb/5sAAAAB/blH/73z/ulkAAAAAAAD85pkAAAAAAAACAgP/vGz/rkDerGbGrV7/pkQICAf////e0IsAAAD/oED/qTvhrnUAAAD/yHD/njcAAADuv2r/nz//oTj/p064oGf/zHAAAAA9Nir/tFIAAAD/tlTiuWf/tkIAAACynXEAAAAAAAAtIRW7zBpBAAAAM3RSTlMAABR1m7RXO8Ln31Z36zT+neXe5OzooRDfn+TZ4p3h2hTf4t3k3ucyrN1K5+Xaks52Sfs9CXgrAAAAjklEQVR42o3PbQ+CIBQFYEwboPhSYgoYunIqqLn6/z8uYdH8Vmdnu9vz4WwXgN/xTPRD2+sgOcZjsge/whXZgUaYYvT8QnuJaUrjrHUQreGczuEafQCO/SJTufTbroWsPgsllVhq3wJEk2jUSzX3CUEDJC84707djRc5MTAQxoLgupWRwW6UB5fS++NV8AbOZgnsC7BpEAAAAABJRU5ErkJggg==");background-position: 2px center;}.ace_gutter-cell.ace_info {background-image: url("data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABAAAAAQCAAAAAA6mKC9AAAAGXRFWHRTb2Z0d2FyZQBBZG9iZSBJbWFnZVJlYWR5ccllPAAAAAJ0Uk5TAAB2k804AAAAPklEQVQY02NgIB68QuO3tiLznjAwpKTgNyDbMegwisCHZUETUZV0ZqOquBpXj2rtnpSJT1AEnnRmL2OgGgAAIKkRQap2htgAAAAASUVORK5CYII=");background-position: 2px center;}.ace_dark .ace_gutter-cell.ace_info {background-image: url("data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABAAAAAQBAMAAADt3eJSAAAAJFBMVEUAAAChoaGAgIAqKiq+vr6tra1ZWVmUlJSbm5s8PDxubm56enrdgzg3AAAAAXRSTlMAQObYZgAAAClJREFUeNpjYMAPdsMYHegyJZFQBlsUlMFVCWUYKkAZMxZAGdxlDMQBAG+TBP4B6RyJAAAAAElFTkSuQmCC");}.ace_scrollbar {position: absolute;right: 0;bottom: 0;z-index: 6;}.ace_scrollbar-inner {position: absolute;cursor: text;left: 0;top: 0;}.ace_scrollbar-v{overflow-x: hidden;overflow-y: scroll;top: 0;}.ace_scrollbar-h {overflow-x: scroll;overflow-y: hidden;left: 0;}.ace_print-margin {position: absolute;height: 100%;}.ace_text-input {position: absolute;z-index: 0;width: 0.5em;height: 1em;opacity: 0;background: transparent;-moz-appearance: none;appearance: none;border: none;resize: none;outline: none;overflow: hidden;font: inherit;padding: 0 1px;margin: 0 -1px;text-indent: -1em;-ms-user-select: text;-moz-user-select: text;-webkit-user-select: text;user-select: text;white-space: pre!important;}.ace_text-input.ace_composition {background: inherit;color: inherit;z-index: 1000;opacity: 1;text-indent: 0;}[ace_nocontext=true] {transform: none!important;filter: none!important;perspective: none!important;clip-path: none!important;mask : none!important;contain: none!important;perspective: none!important;mix-blend-mode: initial!important;z-index: auto;}.ace_layer {z-index: 1;position: absolute;overflow: hidden;word-wrap: normal;white-space: pre;height: 100%;width: 100%;box-sizing: border-box;pointer-events: none;}.ace_gutter-layer {position: relative;width: auto;text-align: right;pointer-events: auto;}.ace_text-layer {font: inherit !important;}.ace_cjk {display: inline-block;text-align: center;}.ace_cursor-layer {z-index: 4;}.ace_cursor {z-index: 4;position: absolute;box-sizing: border-box;border-left: 2px solid;transform: translatez(0);}.ace_multiselect .ace_cursor {border-left-width: 1px;}.ace_slim-cursors .ace_cursor {border-left-width: 1px;}.ace_overwrite-cursors .ace_cursor {border-left-width: 0;border-bottom: 1px solid;}.ace_hidden-cursors .ace_cursor {opacity: 0.2;}.ace_smooth-blinking .ace_cursor {transition: opacity 0.18s;}.ace_animate-blinking .ace_cursor {animation-duration: 1000ms;animation-timing-function: step-end;animation-name: blink-ace-animate;animation-iteration-count: infinite;}.ace_animate-blinking.ace_smooth-blinking .ace_cursor {animation-duration: 1000ms;animation-timing-function: ease-in-out;animation-name: blink-ace-animate-smooth;}@keyframes blink-ace-animate {from, to { opacity: 1; }60% { opacity: 0; }}@keyframes blink-ace-animate-smooth {from, to { opacity: 1; }45% { opacity: 1; }60% { opacity: 0; }85% { opacity: 0; }}.ace_marker-layer .ace_step, .ace_marker-layer .ace_stack {position: absolute;z-index: 3;}.ace_marker-layer .ace_selection {position: absolute;z-index: 5;}.ace_marker-layer .ace_bracket {position: absolute;z-index: 6;}.ace_marker-layer .ace_active-line {position: absolute;z-index: 2;}.ace_marker-layer .ace_selected-word {position: absolute;z-index: 4;box-sizing: border-box;}.ace_line .ace_fold {box-sizing: border-box;display: inline-block;height: 11px;margin-top: -2px;vertical-align: middle;background-image:url("data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABEAAAAJCAYAAADU6McMAAAAGXRFWHRTb2Z0d2FyZQBBZG9iZSBJbWFnZVJlYWR5ccllPAAAAJpJREFUeNpi/P//PwOlgAXGYGRklAVSokD8GmjwY1wasKljQpYACtpCFeADcHVQfQyMQAwzwAZI3wJKvCLkfKBaMSClBlR7BOQikCFGQEErIH0VqkabiGCAqwUadAzZJRxQr/0gwiXIal8zQQPnNVTgJ1TdawL0T5gBIP1MUJNhBv2HKoQHHjqNrA4WO4zY0glyNKLT2KIfIMAAQsdgGiXvgnYAAAAASUVORK5CYII="),url("data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAEAAAA3CAYAAADNNiA5AAAAGXRFWHRTb2Z0d2FyZQBBZG9iZSBJbWFnZVJlYWR5ccllPAAAACJJREFUeNpi+P//fxgTAwPDBxDxD078RSX+YeEyDFMCIMAAI3INmXiwf2YAAAAASUVORK5CYII=");background-repeat: no-repeat, repeat-x;background-position: center center, top left;color: transparent;border: 1px solid black;border-radius: 2px;cursor: pointer;pointer-events: auto;}.ace_dark .ace_fold {}.ace_fold:hover{background-image:url("data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABEAAAAJCAYAAADU6McMAAAAGXRFWHRTb2Z0d2FyZQBBZG9iZSBJbWFnZVJlYWR5ccllPAAAAJpJREFUeNpi/P//PwOlgAXGYGRklAVSokD8GmjwY1wasKljQpYACtpCFeADcHVQfQyMQAwzwAZI3wJKvCLkfKBaMSClBlR7BOQikCFGQEErIH0VqkabiGCAqwUadAzZJRxQr/0gwiXIal8zQQPnNVTgJ1TdawL0T5gBIP1MUJNhBv2HKoQHHjqNrA4WO4zY0glyNKLT2KIfIMAAQsdgGiXvgnYAAAAASUVORK5CYII="),url("data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAEAAAA3CAYAAADNNiA5AAAAGXRFWHRTb2Z0d2FyZQBBZG9iZSBJbWFnZVJlYWR5ccllPAAAACBJREFUeNpi+P//fz4TAwPDZxDxD5X4i5fLMEwJgAADAEPVDbjNw87ZAAAAAElFTkSuQmCC");}.ace_tooltip {background-color: #FFF;background-image: linear-gradient(to bottom, transparent, rgba(0, 0, 0, 0.1));border: 1px solid gray;border-radius: 1px;box-shadow: 0 1px 2px rgba(0, 0, 0, 0.3);color: black;max-width: 100%;padding: 3px 4px;position: fixed;z-index: 999999;box-sizing: border-box;cursor: default;white-space: pre;word-wrap: break-word;line-height: normal;font-style: normal;font-weight: normal;letter-spacing: normal;pointer-events: none;}.ace_folding-enabled > .ace_gutter-cell {padding-right: 13px;}.ace_fold-widget {box-sizing: border-box;margin: 0 -12px 0 1px;display: none;width: 11px;vertical-align: top;background-image: url("data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAUAAAAFCAYAAACNbyblAAAANElEQVR42mWKsQ0AMAzC8ixLlrzQjzmBiEjp0A6WwBCSPgKAXoLkqSot7nN3yMwR7pZ32NzpKkVoDBUxKAAAAABJRU5ErkJggg==");background-repeat: no-repeat;background-position: center;border-radius: 3px;border: 1px solid transparent;cursor: pointer;}.ace_folding-enabled .ace_fold-widget {display: inline-block;   }.ace_fold-widget.ace_end {background-image: url("data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAUAAAAFCAYAAACNbyblAAAANElEQVR42m3HwQkAMAhD0YzsRchFKI7sAikeWkrxwScEB0nh5e7KTPWimZki4tYfVbX+MNl4pyZXejUO1QAAAABJRU5ErkJggg==");}.ace_fold-widget.ace_closed {background-image: url("data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAMAAAAGCAYAAAAG5SQMAAAAOUlEQVR42jXKwQkAMAgDwKwqKD4EwQ26sSOkVWjgIIHAzPiCgaqiqnJHZnKICBERHN194O5b9vbLuAVRL+l0YWnZAAAAAElFTkSuQmCCXA==");}.ace_fold-widget:hover {border: 1px solid rgba(0, 0, 0, 0.3);background-color: rgba(255, 255, 255, 0.2);box-shadow: 0 1px 1px rgba(255, 255, 255, 0.7);}.ace_fold-widget:active {border: 1px solid rgba(0, 0, 0, 0.4);background-color: rgba(0, 0, 0, 0.05);box-shadow: 0 1px 1px rgba(255, 255, 255, 0.8);}.ace_dark .ace_fold-widget {background-image: url("data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAUAAAAFCAYAAACNbyblAAAAHklEQVQIW2P4//8/AzoGEQ7oGCaLLAhWiSwB146BAQCSTPYocqT0AAAAAElFTkSuQmCC");}.ace_dark .ace_fold-widget.ace_end {background-image: url("data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAUAAAAFCAYAAACNbyblAAAAH0lEQVQIW2P4//8/AxQ7wNjIAjDMgC4AxjCVKBirIAAF0kz2rlhxpAAAAABJRU5ErkJggg==");}.ace_dark .ace_fold-widget.ace_closed {background-image: url("data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAMAAAAFCAYAAACAcVaiAAAAHElEQVQIW2P4//+/AxAzgDADlOOAznHAKgPWAwARji8UIDTfQQAAAABJRU5ErkJggg==");}.ace_dark .ace_fold-widget:hover {box-shadow: 0 1px 1px rgba(255, 255, 255, 0.2);background-color: rgba(255, 255, 255, 0.1);}.ace_dark .ace_fold-widget:active {box-shadow: 0 1px 1px rgba(255, 255, 255, 0.2);}.ace_inline_button {border: 1px solid lightgray;display: inline-block;margin: -1px 8px;padding: 0 5px;pointer-events: auto;cursor: pointer;}.ace_inline_button:hover {border-color: gray;background: rgba(200,200,200,0.2);display: inline-block;pointer-events: auto;}.ace_fold-widget.ace_invalid {background-color: #FFB4B4;border-color: #DE5555;}.ace_fade-fold-widgets .ace_fold-widget {transition: opacity 0.4s ease 0.05s;opacity: 0;}.ace_fade-fold-widgets:hover .ace_fold-widget {transition: opacity 0.05s ease 0.05s;opacity:1;}.ace_underline {text-decoration: underline;}.ace_bold {font-weight: bold;}.ace_nobold .ace_bold {font-weight: normal;}.ace_italic {font-style: italic;}.ace_error-marker {background-color: rgba(255, 0, 0,0.2);position: absolute;z-index: 9;}.ace_highlight-marker {background-color: rgba(255, 255, 0,0.2);position: absolute;z-index: 8;}.ace_br1 {border-top-left-radius    : 3px;}.ace_br2 {border-top-right-radius   : 3px;}.ace_br3 {border-top-left-radius    : 3px; border-top-right-radius:    3px;}.ace_br4 {border-bottom-right-radius: 3px;}.ace_br5 {border-top-left-radius    : 3px; border-bottom-right-radius: 3px;}.ace_br6 {border-top-right-radius   : 3px; border-bottom-right-radius: 3px;}.ace_br7 {border-top-left-radius    : 3px; border-top-right-radius:    3px; border-bottom-right-radius: 3px;}.ace_br8 {border-bottom-left-radius : 3px;}.ace_br9 {border-top-left-radius    : 3px; border-bottom-left-radius:  3px;}.ace_br10{border-top-right-radius   : 3px; border-bottom-left-radius:  3px;}.ace_br11{border-top-left-radius    : 3px; border-top-right-radius:    3px; border-bottom-left-radius:  3px;}.ace_br12{border-bottom-right-radius: 3px; border-bottom-left-radius:  3px;}.ace_br13{border-top-left-radius    : 3px; border-bottom-right-radius: 3px; border-bottom-left-radius:  3px;}.ace_br14{border-top-right-radius   : 3px; border-bottom-right-radius: 3px; border-bottom-left-radius:  3px;}.ace_br15{border-top-left-radius    : 3px; border-top-right-radius:    3px; border-bottom-right-radius: 3px; border-bottom-left-radius: 3px;}.ace_text-input-ios {position: absolute !important;top: -100000px !important;left: -100000px !important;}
  /*# sourceURL=ace/css/ace_editor.css */</style><style id="ace-tm">.ace-tm .ace_gutter {background: #f0f0f0;color: #333;}.ace-tm .ace_print-margin {width: 1px;background: #e8e8e8;}.ace-tm .ace_fold {background-color: #6B72E6;}.ace-tm {background-color: #FFFFFF;color: black;}.ace-tm .ace_cursor {color: black;}.ace-tm .ace_invisible {color: rgb(191, 191, 191);}.ace-tm .ace_storage,.ace-tm .ace_keyword {color: blue;}.ace-tm .ace_constant {color: rgb(197, 6, 11);}.ace-tm .ace_constant.ace_buildin {color: rgb(88, 72, 246);}.ace-tm .ace_constant.ace_language {color: rgb(88, 92, 246);}.ace-tm .ace_constant.ace_library {color: rgb(6, 150, 14);}.ace-tm .ace_invalid {background-color: rgba(255, 0, 0, 0.1);color: red;}.ace-tm .ace_support.ace_function {color: rgb(60, 76, 114);}.ace-tm .ace_support.ace_constant {color: rgb(6, 150, 14);}.ace-tm .ace_support.ace_type,.ace-tm .ace_support.ace_class {color: rgb(109, 121, 222);}.ace-tm .ace_keyword.ace_operator {color: rgb(104, 118, 135);}.ace-tm .ace_string {color: rgb(3, 106, 7);}.ace-tm .ace_comment {color: rgb(76, 136, 107);}.ace-tm .ace_comment.ace_doc {color: rgb(0, 102, 255);}.ace-tm .ace_comment.ace_doc.ace_tag {color: rgb(128, 159, 191);}.ace-tm .ace_constant.ace_numeric {color: rgb(0, 0, 205);}.ace-tm .ace_variable {color: rgb(49, 132, 149);}.ace-tm .ace_xml-pe {color: rgb(104, 104, 91);}.ace-tm .ace_entity.ace_name.ace_function {color: #0000A2;}.ace-tm .ace_heading {color: rgb(12, 7, 255);}.ace-tm .ace_list {color:rgb(185, 6, 144);}.ace-tm .ace_meta.ace_tag {color:rgb(0, 22, 142);}.ace-tm .ace_string.ace_regex {color: rgb(255, 0, 0)}.ace-tm .ace_marker-layer .ace_selection {background: rgb(181, 213, 255);}.ace-tm.ace_multiselect .ace_selection.ace_start {box-shadow: 0 0 3px 0px white;}.ace-tm .ace_marker-layer .ace_step {background: rgb(252, 255, 0);}.ace-tm .ace_marker-layer .ace_stack {background: rgb(164, 229, 101);}.ace-tm .ace_marker-layer .ace_bracket {margin: -1px 0 0 -1px;border: 1px solid rgb(192, 192, 192);}.ace-tm .ace_marker-layer .ace_active-line {background: rgba(0, 0, 0, 0.07);}.ace-tm .ace_gutter-active-line {background-color : #dcdcdc;}.ace-tm .ace_marker-layer .ace_selected-word {background: rgb(250, 250, 255);border: 1px solid rgb(200, 200, 250);}.ace-tm .ace_indent-guide {background: url("data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAEAAAACCAYAAACZgbYnAAAAE0lEQVQImWP4////f4bLly//BwAmVgd1/w11/gAAAABJRU5ErkJggg==") right repeat-y;}
  /*# sourceURL=ace/css/ace-tm */</style><style>    .error_widget_wrapper {        background: inherit;        color: inherit;        border:none    }    .error_widget {        border-top: solid 2px;        border-bottom: solid 2px;        margin: 5px 0;        padding: 10px 40px;        white-space: pre-wrap;    }    .error_widget.ace_error, .error_widget_arrow.ace_error{        border-color: #ff5a5a    }    .error_widget.ace_warning, .error_widget_arrow.ace_warning{        border-color: #F1D817    }    .error_widget.ace_info, .error_widget_arrow.ace_info{        border-color: #5a5a5a    }    .error_widget.ace_ok, .error_widget_arrow.ace_ok{        border-color: #5aaa5a    }    .error_widget_arrow {        position: absolute;        border: solid 5px;        border-top-color: transparent!important;        border-right-color: transparent!important;        border-left-color: transparent!important;        top: -5px;    }</style><style>.ace_snippet-marker {    -moz-box-sizing: border-box;    box-sizing: border-box;    background: rgba(194, 193, 208, 0.09);    border: 1px dotted rgba(211, 208, 235, 0.62);    position: absolute;}</style><style>.ace_editor.ace_autocomplete .ace_marker-layer .ace_active-line {    background-color: #CAD6FA;    z-index: 1;}.ace_editor.ace_autocomplete .ace_line-hover {    border: 1px solid #abbffe;    margin-top: -1px;    background: rgba(233,233,253,0.4);}.ace_editor.ace_autocomplete .ace_line-hover {    position: absolute;    z-index: 2;}.ace_editor.ace_autocomplete .ace_scroller {   background: none;   border: none;   box-shadow: none;}.ace_rightAlignedText {    color: gray;    display: inline-block;    position: absolute;    right: 4px;    text-align: right;    z-index: -1;}.ace_editor.ace_autocomplete .ace_completion-highlight{    color: #000;    text-shadow: 0 0 0.01em;}.ace_editor.ace_autocomplete {    width: 280px;    z-index: 200000;    background: #fbfbfb;    color: #444;    border: 1px lightgray solid;    position: fixed;    box-shadow: 2px 3px 5px rgba(0,0,0,.2);    line-height: 1.4;}</style><script src="./HUSTOJ_files/theme-chrome.js"></script><script src="./HUSTOJ_files/mode-c_cpp.js"></script><script src="./HUSTOJ_files/text.js"></script><script src="./HUSTOJ_files/mode-c_cpp.js"></script><style id="ace-chrome">.ace-chrome .ace_gutter {background: #ebebeb;color: #333;overflow : hidden;}.ace-chrome .ace_print-margin {width: 1px;background: #e8e8e8;}.ace-chrome {background-color: #FFFFFF;color: black;}.ace-chrome .ace_cursor {color: black;}.ace-chrome .ace_invisible {color: rgb(191, 191, 191);}.ace-chrome .ace_constant.ace_buildin {color: rgb(88, 72, 246);}.ace-chrome .ace_constant.ace_language {color: rgb(88, 92, 246);}.ace-chrome .ace_constant.ace_library {color: rgb(6, 150, 14);}.ace-chrome .ace_invalid {background-color: rgb(153, 0, 0);color: white;}.ace-chrome .ace_fold {}.ace-chrome .ace_support.ace_function {color: rgb(60, 76, 114);}.ace-chrome .ace_support.ace_constant {color: rgb(6, 150, 14);}.ace-chrome .ace_support.ace_type,.ace-chrome .ace_support.ace_class.ace-chrome .ace_support.ace_other {color: rgb(109, 121, 222);}.ace-chrome .ace_variable.ace_parameter {font-style:italic;color:#FD971F;}.ace-chrome .ace_keyword.ace_operator {color: rgb(104, 118, 135);}.ace-chrome .ace_comment {color: #236e24;}.ace-chrome .ace_comment.ace_doc {color: #236e24;}.ace-chrome .ace_comment.ace_doc.ace_tag {color: #236e24;}.ace-chrome .ace_constant.ace_numeric {color: rgb(0, 0, 205);}.ace-chrome .ace_variable {color: rgb(49, 132, 149);}.ace-chrome .ace_xml-pe {color: rgb(104, 104, 91);}.ace-chrome .ace_entity.ace_name.ace_function {color: #0000A2;}.ace-chrome .ace_heading {color: rgb(12, 7, 255);}.ace-chrome .ace_list {color:rgb(185, 6, 144);}.ace-chrome .ace_marker-layer .ace_selection {background: rgb(181, 213, 255);}.ace-chrome .ace_marker-layer .ace_step {background: rgb(252, 255, 0);}.ace-chrome .ace_marker-layer .ace_stack {background: rgb(164, 229, 101);}.ace-chrome .ace_marker-layer .ace_bracket {margin: -1px 0 0 -1px;border: 1px solid rgb(192, 192, 192);}.ace-chrome .ace_marker-layer .ace_active-line {background: rgba(0, 0, 0, 0.07);}.ace-chrome .ace_gutter-active-line {background-color : #dcdcdc;}.ace-chrome .ace_marker-layer .ace_selected-word {background: rgb(250, 250, 255);border: 1px solid rgb(200, 200, 250);}.ace-chrome .ace_storage,.ace-chrome .ace_keyword,.ace-chrome .ace_meta.ace_tag {color: rgb(147, 15, 128);}.ace-chrome .ace_string.ace_regex {color: rgb(255, 0, 0)}.ace-chrome .ace_string {color: #1A1AA6;}.ace-chrome .ace_entity.ace_other.ace_attribute-name {color: #994409;}.ace-chrome .ace_indent-guide {background: url("data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAEAAAACCAYAAACZgbYnAAAAE0lEQVQImWP4////f4bLly//BwAmVgd1/w11/gAAAABJRU5ErkJggg==") right repeat-y;}
  /*# sourceURL=ace/css/ace-chrome */</style><script src="./HUSTOJ_files/c_cpp.js"></script>
  
  
<body style="">  


<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" data-backdrop="static">
	<div class="modal-dialog" style="z-index: 2050;">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
					&times;
				</button>
				<h4 class="modal-title" id="myModalLabel">
					代码编辑
				</h4>
			</div>
			<div class="modal-body">
				
				 <center>
				<form id="frmSolution" action="" method="post" onsubmit="do_submit()">
				题目 <span class="blue"><b id="titleSpan"> </b></span>
				<input id="problem_id" type="hidden" value="1000" name="id"><br>
				<span id="language_span">语言:
				<select id="language" name="language" onchange="reloadtemplate($(this).val());">
				<option value="0" selected="">
				C
				</option><option value="1">
				C++
				</option><option value="2">
				Pascal
				</option><option value="3">
				Java
				</option><option value="4">
				Ruby
				</option><option value="5">
				Bash
				</option><option value="6">
				Python
				</option><option value="7">
				PHP
				</option><option value="8">
				Perl
				</option><option value="9">
				C#
				</option><option value="10">
				Obj-C
				</option><option value="11">
				FreeBasic
				</option><option value="12">
				Scheme
				</option><option value="13">
				Clang
				</option><option value="14">
				Clang++
				</option><option value="15">
				Lua
				</option><option value="16">
				JavaScript
				</option><option value="17">
				Go
				</option></select>
				
				<br>
				</span>
					<pre style="width:80%;height:600" cols="180" rows="20" id="source" class=" ace_editor ace-chrome"><textarea class="ace_text-input" wrap="off" autocorrect="off" autocapitalize="off" spellcheck="false" style="opacity: 0; left: 44px; height: 14px; width: 6.59766px; top: 0px;"></textarea><div class="ace_gutter" aria-hidden="true"><div class="ace_layer ace_gutter-layer ace_folding-enabled" style="margin-top: 0px; height: 626px; width: 40px;"><div class="ace_gutter-cell " style="height: 14px;">1</div></div><div class="ace_gutter-active-line" style="top: 0px; height: 14px;"></div></div><div class="ace_scroller" style="left: 40px; right: 0px; bottom: 0px;"><div class="ace_content" style="margin-top: 0px; width: 524px; height: 626px; margin-left: 0px;"><div class="ace_layer ace_print-margin-layer"><div class="ace_print-margin" style="left: 532px; visibility: visible;"></div></div><div class="ace_layer ace_marker-layer"><div class="ace_active-line" style="height:14px;top:0px;left:0;right:0;"></div></div><div class="ace_layer ace_text-layer" style="padding: 0px 4px;"><div class="ace_line" style="height:14px"></div></div><div class="ace_layer ace_marker-layer"></div><div class="ace_layer ace_cursor-layer ace_hidden-cursors"><div class="ace_cursor" style="left: 4px; top: 0px; width: 6.59766px; height: 14px; animation-duration: 1000ms;"></div></div></div></div><div class="ace_scrollbar ace_scrollbar-v" style="display: none; width: 22px; bottom: 0px;"><div class="ace_scrollbar-inner" style="width: 22px; height: 14px;"></div></div><div class="ace_scrollbar ace_scrollbar-h" style="display: none; height: 22px; left: 40px; right: 0px;"><div class="ace_scrollbar-inner" style="height: 22px; width: 524px;"></div></div><div style="height: auto; width: auto; top: 0px; left: 0px; visibility: hidden; position: absolute; white-space: pre; font: inherit; overflow: hidden;"><div style="height: auto; width: auto; top: 0px; left: 0px; visibility: hidden; position: absolute; white-space: pre; font: inherit; overflow: visible;"></div><div style="height: auto; width: auto; top: 0px; left: 0px; visibility: hidden; position: absolute; white-space: pre; font-style: inherit; font-variant: inherit; font-stretch: inherit; font-size: inherit; line-height: inherit; font-family: inherit; overflow: visible;"></div></div></pre><br>
					<input type="hidden" id="hide_source" name="source" value="">
				
				<div id="csrf"><input type="hidden" name="csrf" value="Qi1OIsBfVt2Yvj5d1XVkU5r2Z8yovADy" class="1">
				</div></form>
				</center>

				
				
				
				
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-primary" data-dismiss="modal" onclick="SaveSource()">
					保存代码
				</button>
			</div>
		</div><!-- /.modal-content -->
	</div><!-- /.modal -->
</div>


    <div id="divNotRun" style="height:100px; text-align:center; display:none;"></div>
    <div id="jqContent" class="" style="text-align: left; ">
        <div class="box">
        <div id="headerCss" style="overflow-x: hidden; overflow-y: hidden; height:15px;">
            <div id="ctl00_header">
            </div>
        </div>
        <div id="mainCss">
            <div id="mainInner">
                <div id="box">
                <script src="./contest/hm.js"></script><script type="text/javascript" src="./contest/zhezhao.js"></script>
				 
                    
    <style>
   
     </style>
   <div class="survey" style="margin:0px auto;">
      
    <div id="ctl00_ContentPlaceHolder1_JQ1_divHead" class="surveyhead" style="border: 0px;">
       <div id="ctl00_ContentPlaceHolder1_JQ1_h1Name" style="position:relative;">
        <h1 id="ctl00_ContentPlaceHolder1_JQ1_lblQuestionnaireName"> ${contest.title} </h1><span id="ctl00_ContentPlaceHolder1_JQ1_lblMobile" style="position:absolute; right:-10px; top:6px;"></span>
        
     </div>
     <div id="ctl00_ContentPlaceHolder1_JQ1_divMob">
    
        <script type="text/javascript">
                function showData(event, obj, src, needClick) {
                var d = document.getElementById("divMa");
                var img = d.getElementsByTagName("img")[0];
                if (!img.hasset) {
                    img.src = "//down.wjx.com/handler/qrcode.ashx?chs=200x200&chl=" + encodeURIComponent(src);
                    img.hasset = true;
                    if(needClick){
                        img.parentNode.href=src;
                        img.parentNode.target="_blank";
                    }
                }
                PDF_launch("divMa",260,260);
                return false;
            }
          </script>
        </div>
        
    
     
    
  
    
     
    <div style="clear: both;">
    </div>

    
    
    
    <div id="divPeiE" style="display:none;">

    </div>
    
    <div style="clear: both;">
    </div>
    
   
    
    
    
    
    
</div>

<div id="ctl00_ContentPlaceHolder1_JQ1_question" class="surveycontent" >
    <div id="divMaxTime" style="text-align: center; width: 80px; background: white; position: fixed; top: 135px; border: 1px solid rgb(219, 219, 219); padding: 8px; z-index: 10;  margin-left: -190px; margin-top:-50px;">
       <div id="spanTimeTip" style="border-bottom:1px solid #dbdbdb;height:30px; line-height:30px;">剩余时间</div><div style="color: Red;font-size:16px; height:30px; line-height:30px; " id="spanMaxTime"></div></div>
    
    <div id="bb" style="text-align: center; width: 80px; background: white; position: fixed; top: 35px; border: 1px solid rgb(219, 219, 219); padding: 8px; z-index: 10; margin-left: -190px; margin-top:-20px;">
       <div id="ab" style="height:30px; line-height:30px;">考生姓名</div><div style="font-size:16px; height:30px; line-height:30px;" id=""> ${student.realname} </div></div> 
            
	<div id="ctl00_ContentPlaceHolder1_JQ1_surveyContent">
   <fieldset class="fieldset" id="fieldset1"> 
	 
  </fieldset>
  
	<div class="shopcart" id="shopcart" style="display:none;">
        </div>
       <div style="padding-top: 6px;clear:both; padding-bottom:10px;" id="submit_div">
           
           <table id="submit_table" style="margin: 20px auto;">
               
               <tbody><tr>
                   <td id="ctl00_ContentPlaceHolder1_JQ1_tdCode" style="display: none;">
                       <input id="yucinput" size="14" maxlength="10" onkeydown="enter_clicksub(event);" type="text" name="yucinput" class="inputtext" style="height: 24px; line-height: 24px;">&nbsp;&nbsp;<img id="imgCode" alt="验证码" title="看不清吗？点击可以刷新" style="vertical-align: bottom; cursor: pointer; display: none;">
                   </td>

                   <td>
                       <div id="divCaptcha" style="display: none;">
                           <img alt="验证码" title="看不清吗？点击可以刷新" captchaid="" instanceid="">
                       </div>
                   </td>
                   <td>
 
                       <input type="button" class="submitbutton" value="提交" onclick="submitpaper()" id="submit_button" style="padding: 0 24px; height: 32px;">
 
                   </td>

                   <td> 
                       
                   </td>
                   <td align="right" style="position: relative;">
                       <span id="spanTest" style="display: none; position: absolute; width: 120px; top: 5px;">
                           <input type="button" class="finish cancle" value="试填问卷" id="submittest_button" title="只有发布者才能看到试填按钮，试填的答卷不会参与结果统计！"><a title="只有发布者才能看到试填按钮，试填的答卷不会参与结果统计！" onclick="alert(this.title);" style="color: green" href="javascript:void(0);"><b>(?)</b></a></span>
                   </td>



                   <td align="right" valign="bottom"></td>
               </tr>
           </tbody></table>
        <div style="clear: both;"></div>

</div>
<div id="submit_tip" style="display: none; background-color: #f04810; color: White; margin-bottom: 20px; padding: 10px">
</div>
<div id="divMatrixRel" style="position: absolute; display: none; width: 300px; margin: 0 10%;">
    <input type="text" id="matrixinput" style="width: 100%; height: 24px; line-height: 24px; color: #8c8c8c;" class="inputtext">
</div>
  
<div style="display: none;" id="divNA">
    <input type="radio" value="1" name="divNA" id="divNA_1"><label for="divNA_1">A.男</label><input type="radio" value="2" name="divNA" id="divNA_2"><label for="divNA_1">B.女</label>
</div>
</div>
<div id="ctl00_ContentPlaceHolder1_JQ1_divJump">
    <div id="divS" style="background: rgb(0, 0, 0); position: absolute; z-index: 30000; width: 1903px; height: 3000px; left: 0px; top: 0px; opacity: 0.61; display: none;">
    </div>
    <div id="loadbox" style="border: 2px solid rgb(31, 73, 125); position: absolute; top: 300px; left: 786.5px; font-size: 12px; width: 400px; height: 100px; background-color: rgb(79, 129, 189); text-align: center; color: rgb(255, 255, 255); z-index: 50000; display: none;">
        <div style="height: 30px; padding: 2px; text-align: left;">问卷正在加载中，请稍候...</div>
        <img src="./contest/ajax-loading.gif" width="160" height="20">
        <div style="color: Red; margin: 5px 0; font-size: 14px;">如果由于网络原因导致此框一直不消失，请重新刷新页面！</div>
    </div>
    <script type="text/javascript">
        function jumpNotLoaded(){
            var bodyWidth = document.documentElement.clientWidth || document.body.clientWidth;
            var midX = (bodyWidth - 330) / 2;
            document.getElementById("loadbox").style.left = midX + "px";
            document.getElementById("divS").style.width=bodyWidth+"px";
        }
        jumpNotLoaded();
        function jqLoaded() {
            var ele_a = document.getElementById("loadbox");
            var divS = document.getElementById("divS");
            ele_a.style.display = "none";
            divS.style.display = "none";
        }
    </script>
</div>

<div id="ctl00_ContentPlaceHolder1_JQ1_divLeftBar" style="text-align: center; position: absolute; width: 50px; padding: 8px 0px 12px; left: 1412px; top: 259px;" class="leftbar">
    <div id="divProgressBar">
        <div style="text-align: left;">

        </div>
        <div id="ctl00_ContentPlaceHolder1_JQ1_divProgressImg" style="float: left; padding-left: 15px; visibility: hidden;">
           
        </div>
        <div style="float: left; width: 14px; line-height: 0;" id="divSaveText">
        </div>
        <div class="divclear"></div>
    </div>
    <div style="float: left; padding-left: 2px; visibility: hidden;">
        
    </div>
    <script type="text/javascript">
        var timerq; 
        var surveycontent=document.getElementById("ctl00_ContentPlaceHolder1_JQ1_question");
        var divPromote=document.getElementById("ctl00_ContentPlaceHolder1_JQ1_divPromote");
            var container=document.getElementById("container");
            var progressBarType=1;
            var divLeftBar=document.getElementById("ctl00_ContentPlaceHolder1_JQ1_divLeftBar");
        var divProgressBar=document.getElementById("divProgressBar");  
        var loading=document.getElementById("loading"); 
        var divSave=document.getElementById("ctl00_ContentPlaceHolder1_JQ1_divSave");
             var issimple = '';
        var isSolid=1;
        var divSaveText=document.getElementById("divSaveText");
        var divProgressImg=document.getElementById("ctl00_ContentPlaceHolder1_JQ1_divProgressImg");
              var xTop=0;var solidmainCss=document.getElementById("mainCss");
              function addEventSimple(obj, evt, fn) {
                  if (obj.addEventListener)
                      obj.addEventListener(evt, fn, false);
                  else if (obj.attachEvent)
                      obj.attachEvent('on' + evt, fn);
              }
              function gotop(){
                  window.scroll(0,0);
              }
              function gobottom(){
                  window.scroll(0,99999);
              }
              function resizeLeftBar()
              {
                  if(!divLeftBar||!surveycontent)return;
                  var xy2=null;var clientWidth=0;
                  if(solidmainCss){
                      xy2=getTop(solidmainCss);
                      clientWidth=solidmainCss.offsetWidth||solidmainCss.clientWidth;
                   
                  }
                  else if(issimple && surveycontent){
                      xy2=getTop(surveycontent);
                      clientWidth=surveycontent.clientWidth;
                  }
                  else if(container){
                      xy2=getTop(container);
                      clientWidth=container.clientWidth;
                  }
                  if(!xy2)return;
                  var lWidth=0; if(issimple)lWidth=22;
                  var leftQ=xy2.x+clientWidth-lWidth; 
                  divLeftBar.style.left=leftQ+"px";
                  xTop=getTop(surveycontent).y;
                  var docHeight=document.documentElement.clientHeight||document.body.clientHeight;
                  if(xTop>docHeight/2)
                      xTop=docHeight/2;
              }
              addEventSimple(window,"resize",resizeLeftBar);
              resizeLeftBar();
              addEventSimple(window,"scroll",mmq);
              mmq();
              var hasDisplayed=false;
              function mmq()   
              {   
                  var posY=document.documentElement.scrollTop||document.body.scrollTop;
                  if(divLeftBar){
                      divLeftBar.style.top=posY+xTop+"px"; 
                  }
              }  
              function getTop(e)
              {
                  if(!e)return;
                  var x = e.offsetLeft;
                  var y = e.offsetTop;
                  while(e = e.offsetParent)
                  {
                      x += e.offsetLeft;
                      y += e.offsetTop;
                  }
                  return {"x": x, "y": y};
              }
    </script>
<div style="clear: both;">
    </div>
</div>


<div style="clear: both;">
</div>
<div id="divTimeUp" style="display:none;">
    <div style="padding:10px;overflow:auto;line-height:20px;font-size:16px;text-align:center;" id="divTimeUpTip"></div>
</div>
<div id="divDescPop" style="display:none;">
<div style="padding:10px;  overflow:auto;line-height:20px;font-size:14px;" id="divDescPopData"></div>
</div>
  <div id="rbbox" style="display:none; height:70px;position:fixed;_position:absolute;">
 
 
  </div>
<script type="text/javascript">
    var needAvoidCrack=0;
    try{
        HTMLElement.prototype.click = function() {
            var evt = this.ownerDocument.createEvent('MouseEvents');
            evt.initMouseEvent('click', true, true, this.ownerDocument.defaultView, 1, 0, 0, 0, 0, false, false, false, false, 0, null);
            this.dispatchEvent(evt);
        } 
    }catch(ex){};
</script>
<script type="text/javascript">  
//总页数，问卷相关
var totalPage=1;  
var totalCut=0;   
var qstr = 'false§false¤page§1§§§¤radio§1§1§true§false§0§true§ceshi§§0§§§false〒0〒0〒§false〒0〒0〒§false〒0〒〒¤radio§2§1§true§false§0§true§ceshi§§0§§§false〒0〒0〒§false〒0〒0〒§false〒0〒〒¤radio§3§1§true§false§0§true§ceshi§§0§§§false〒0〒0〒§false〒0〒0〒§false〒0〒〒¤radio§4§1§true§false§0§true§ceshi§§0§§§false〒0〒0〒§false〒0〒0〒§false〒0〒〒¤radio§5§1§true§false§0§true§ceshi§§0§§§false〒0〒0〒§false〒0〒0〒§false〒0〒〒';//所有问题，与服务器端交互
   
//var qstr = 'false§false¤page§1§§§¤radio§1§1§true§false§0§true§ceshi§§0§§§false〒0〒0〒§false〒0〒0〒§false〒0〒〒¤question§2§3§§true§false§false§§§§§§¤question§3§3§§true§false§false§§§§§§¤check§4§1§true§false§0§true,,§ceshi§§0§§§false〒0〒0〒〒§false〒0〒0〒〒§false〒0〒〒〒§false〒0〒〒〒¤question§5§1§§true§false§false§§§§§§';//所有问题，与服务器端交互
/* var maxSurveyTime=3;var leftSeconds=0-10; 
console.log("time=" + maxSurveyTime) 
var hasSurveyTime=false;
if(leftSeconds>0){ 
    if(maxSurveyTime) maxSurveyTime=Math.min(leftSeconds,maxSurveyTime);
    else if(leftSeconds<7200) maxSurveyTime=leftSeconds;
 }
if(maxSurveyTime)hasSurveyTime=true; */





//其它交互
var starttime="2019/5/13 20:10:05";
langVer=0;
var cqType=6;
var sjUser='';
var sjts='';
var sjsign='';
var outuser='';
var sourcelink='';
var outsign='';

var relusername="";
var relts="";
var relsign="";
var relrealname="";
var reldept="";
var relext="";

var tdCode="ctl00_ContentPlaceHolder1_JQ1_tdCode";var eproguid='';
var isWaiGuan=0;
var guid = "";var mobileRnum="";var onlyMailSms="";
var saveGuid="";
var sourceDetail = "未知";
if(document.referrer)
    sourceDetail=document.referrer;
var sourcename="";
var nv=0;
var source = '';
    var udsid=0;
    var fromsour="";
 var isKaoShi=1;
 var activityId = '39372975';
  var rndnum="1902608421.53981266";
 var simple = '';
  var qwidth=0;
  var qinvited="";
   var parterid = '';
   if(!window.isRunning)
      window.isRunning="true";
   var displayPrevPage="none";
   var isPub="";var isSuper="";
  var hasJoin='';
  var nfjoinid='';
 var promoteSource="";
 var lastSavePage=0;
 var lastSaveQ=0;
  var jiFen="0";
 var hrefPreview = document.getElementById("hrefPreview");
 var afterDigitPublish = 1;
 var inviteid='';
 var SJBack='';
 var FromSj=0;
 var survey = document.getElementById("ctl00_ContentPlaceHolder1_JQ1_surveyContent");
var refu='';
var isTest=0;
var isPreview='';
var Password = "";
var PasswordExt = "";
var pwdExt="";
var isProduction="true";
var cAlipayAccount="";
var wbid='';
var needJQJiang=1;
var IsSampleService=0;
var jqnonce="a91de082-df0b-4fac-9a7b-9c99bc4a8ed1";
var isChuangGuan=0;
var maxOpTime=0;
var divDec="ctl00_ContentPlaceHolder1_JQ1_divDec";
var isVip=1;
var LogStoreLocal=0;
 var refer=document.referrer;
 if(!refer)refer="";
 else refer=refer.toLowerCase();
 var isFromSojiang=0;
 var isLogin=true;
 var CurrentDomain=0;
 var jiFenBao=0;var HasJiFenBao=0;
 var sojumpParm='';
    var access_token="";
    var openid = "";
    var wxUserId = "";
    var allowWeiXin=0;
    var isPromoteing=0
    var prsjts = "";
    var cityPeiEQues = "";
    var  prsjsign = "";
    if(allowWeiXin==1){
        var divWeiXin="ctl00_ContentPlaceHolder1_JQ1_divWeiXin";
        setTimeout(function(){   PDF_launch(divWeiXin,320,340, function () {
        },"no");},500);
 }
 else if(allowWeiXin==2){
     var divQQ="ctl00_ContentPlaceHolder1_JQ1_divQQ";
     if(document.getElementById(divQQ)){
         setTimeout(function(){ PDF_launch(divQQ,420,100, function () {
         },"no");},500);
     }
 }
 else if(allowWeiXin==3){
     var divQywx = "ctl00_ContentPlaceHolder1_JQ1_divQywx";
     if(document.getElementById(divQywx)){
         setTimeout(function(){ PDF_launch(divQywx, 320, 340,null,"no");
         },500);
     }
 }
 else if(allowWeiXin==4){
     var divApp = "ctl00_ContentPlaceHolder1_JQ1_divApp";
     if(document.getElementById(divApp))
         setTimeout(function(){ PDF_launch(divApp, 320, 60,null,"no");
         },500);
 }
 document.onclick=function(e){
     if(window.calendar)
         calendar.hide();
     if(window.setMatrixFill)
         setMatrixFill();
 }
 function gotoReg(){
    PDF_launch(url,640,480, function () {
		            if(!isLogin)gotoReg();
                    else window.location.href=window.location.href;
	    });
 }
if(window.location.href.toLowerCase().indexOf("/jq/")>-1 && window.PDF_launch){//&& isRunning!="true" 
     var tMsg=document.getElementById("spanNotSubmit");var val='';
     if(tMsg) val=tMsg.getAttribute("value");
     var divNotRun=document.getElementById("divNotRun");
     if(tMsg&&tMsg.innerHTML && (!getCookie("noJQPromote")||val=="1") && CurrentDomain &&divNotRun){
         divNotRun.innerHTML="<div style=' margin-top:30px;font-size:14px;'>"+tMsg.parentNode.innerHTML+"<div style='margin-top:10px;'><input type='button' value='确定' class='finish' onclick='window.parent.PDF_close();' /></div></div>";
         //window.onload=function(){
             PDF_launch("divNotRun",520,120);
             setCookie("noJQPromote","1",null,"/jq","",null);
         //};
     }
 }
function getCookieVal(offset) {
    var endstr = document.cookie.indexOf(";", offset);
    if (endstr == -1) {
        endstr = document.cookie.length;
    }
    return unescape(document.cookie.substring(offset, endstr));
}
function getCookie(name) {
    var arg = name + "=";
    var alen = arg.length;
    var clen = document.cookie.length;
    var i = 0;
    while (i < clen) {
        var j = i + alen;
        if (document.cookie.substring(i, j) == arg) {
            return getCookieVal(j);
        }
        i = document.cookie.indexOf(" ", i) + 1;
        if (i == 0) break;
    }
    return "";
}
function setCookie(name, value, expires, path, domain, secure) {
    document.cookie = name + "=" + escape(value) +
            ((expires) ? "; expires=" + expires : "") +
            ((path) ? "; path=" + path : "") +
            ((domain) ? "; domain=" + domain : "") +
            ((secure) ? "; secure" : "");
}
  var cProvince="";
  var cCity="";
  var cIp="";
  var NeedSearchKeyword=1;
  var allowSaveJoin='';
  if(isPub && onlyMailSms){
    if(!guid&&!mobileRnum){
      alert("提示：此问卷只允许从问卷星系统发送的邮件和短信中包含的问卷链接访问。\r\n您是问卷发布者，可以从普通链接访问！");
    }
  }
  var cepingCandidate="";
  var allowPart=0;
    var cpid="";
    var awardkeylist="1§JLPT,JTEST,J.TEST,CATTI,50音,日语,韩语,Japan,Japanese,cosplay,の,な,ん,い,う,こ,し,せ,て,た,GRE,GMAT,offer,toefl,IELTS,LSAT,TEF§2┋2§玻尿酸,射频仪,肌肤测试,美容仪,瘦脸针,肉毒杆菌§3┋3§GRE,雅思,托福,G5,SAT,SSAT,ACTGMAT,jlpt,offer,toefl,IELTS,LSAT,TEF§1┋4§高中生,高中,高一,高二,高三,高1,高2,高3,高中家长,高考§12┋5§初中生,初中,初一,初二,初三,七年级,八年级,九年级,初1,初2,初3,7年级,8年级,9年级,中考,初升高§11┋6§大四,学术研究,毕业论文,就业,择业,职业规划,实习§5┋7§小学生,附小,小学,小学家长,一年级,二年级,三年级,四年级,五年级,六年级,少先队,少先大队,1年级,2年级,3年级,4年级,5年级,6年级,小升初,低年级,口算,儿童节§9┋8§幼儿园,幼儿家长,园家长,大班,中班,大一班,大二班,大三班,小班幼儿,绘本,学前教育,学前儿童,早教,幼教中心,智力开发,睡前故事,蒙氏,积木,乐高§8┋9§中学,中学生,中小学,附中,国际学校,家访,培优,奥数,高年级,中年级,作业,家庭作业,苏教版,加减法,尖子班,人教版,中一班,中二班,中三班,提高班,青春期,冬令营,夏令营,升学,课业,升学,考级,学籍,课外辅导,义务教育,补习,叛逆期,课业负担,提高班,少年§13┋10§母婴,雅培,金宝贝,益智玩具,惠氏,婴儿,托管所,托儿所,早教,幼教,推车,童车,学步车,腰凳,咬胶,新安怡,安全座椅,贝亲,尿布,奶嘴,雀氏,倍康,帮宝适,育儿,花王,纸尿裤,积木,美赞臣,奶粉,美素,多美滋,巧虎,乐智小天地,二胎§4┋11§家长,亲子,家教,孩子,家庭教育,共育,父母§14┋12§启蒙,拼音,幼儿,少儿,儿童§10┋13§大学生,学生会,学院,论文,宿舍,恋爱观,消费观,婚恋观,考研,思想政治,就业,择业,毕业,应届,马克思主义,奖学金,入党,兼职,理论课,三情,选修课,支教,打工,实习,校园,团委,职业规划,学业,留学生§6┋14§小语种,翻译,口译,外语,外教,听力,语种,真题,考研,口语,词汇,六级,英语,词汇,语法,the,of,and,to,an,in,that,is,for§7┋15§出国,跨国企业,国贸,报关,外贸,外企,海外,境外,国际部,签证,跨境,国际,航空,出境,空乘,进口,出口,海外,乘务,报关,外贸,外企,海外,境外,签证,跨境,出境,进口,出口,金融,出国,国贸,跨国企业,跨国公司,合资,出国,汽车,商城,酒店,购物,商场,商店,酒,咖啡,VIP,高尔夫,邮轮,相机,投资,炒股,股票,股市,金融,理财,财经,基金,财经,奢侈品,集团,企业,公司,员工,工会,旅游,旅行,购车,航班,证券,保险,银行,高层,部门,别墅,4S,红酒,财富,财务,高端,考察,海淘,海外,私募,贷款,私教,干红,健身,出境,境外,私家,ETC,车友,购房,房产,融资,花园,洋房,招商,地产,越野,轿车,度假,经理,总裁,研修,总监,私家车,会所,贵宾,MBA,物业,mall§15";
    var qukeylist="";
    var currJT=3;
    var curProvince="";
    var curCity="";
    var curIp="113.81.100.112";
    var curFuHe=0;
</script>


<script type="text/javascript">
    var maxCheatTimes = 0;
    console.log("qstr=" + qstr) 
</script>
 



<script type="text/javascript">
try{document.execCommand("BackgroundImageCache", false, true);}
catch(ex){}
</script>
<script type="text/javascript">
    try {
        sourceDetail = '';
    }
    catch (ex) { }
</script>
<div id="ctl00_ContentPlaceHolder1_JQ1_divVisitLog" style="display:none;"><img src="./contest/track.gif"></div>
 
    <div style="clear: both;">
    </div>
    </div>
    

                    <div style="margin:30px auto 0; padding-top:30px; overflow: hidden; width:100%;">
                        <div style="border-top: 1px solid #bbbbbb; font-size: 0; height: 1px; line-height: 1px;
                            width: 98%; margin: 0 auto;">
                        </div>
                        <table border="0" cellpadding="0" cellspacing="0" width="100%">
                            <tbody><tr>
                                <td height="10px">
                                </td>
                            </tr>
                            <tr>
                                <td align="center" valign="middle">
                                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                        <tbody><tr id="ctl00_trCopy">
	<td align="center" style="font-size: 12px; font-family: Tahoma, 宋体; color: #666666;">
                                                
                                            </td>
</tr>

                                        <tr id="ctl00_trPoweredBy">
	<td style="color: #666666; font-family: Tahoma, 宋体;" align="center">
                                                <div style="height: 10px;">
                                                </div>
                                                     
                                            </td>
</tr>

                                       <tr>
                                             <td>
                                                 
                                             </td>
                                         </tr>
                                    </tbody></table>
                                </td>
                            </tr>
                            
                        </tbody></table>
                    </div>
                </div>
            </div>
            <div style="clear: both;">
            </div>
        </div>
        <div id="footercss">
            <div id="footerLeft">
            </div>
            <div id="footerCenter">
            </div>
            <div id="footerRight">
            </div>
        </div>
        </div>
        <div style="clear: both; height: 10px;">
        </div>
        <div style="height: 20px;">
            &nbsp;</div>
    </div>
   
    <div style="clear:both;"></div>
   
  <div style="display:none;"><script src="./contest/z_stat.php" language="JavaScript"></script><script src="./contest/core.php" charset="utf-8" type="text/javascript"></script><a href="https://www.cnzz.com/stat/website.php?web_id=4478442" target="_blank" title="站长统计">站长统计</a><script>var _hmt = _hmt || [];(function() {var hm = document.createElement("script");hm.src = "https://hm.baidu.com/hm.js?21be24c80829bd7a683b2c536fcf520b";var s = document.getElementsByTagName("script")[0]; s.parentNode.insertBefore(hm, s);})();</script></div>
 <script>
     if (window._czc) {
         var jqloc = window.location.href.toLowerCase();
         var isVip=1;var cqType=6;
         var evvtype="免费版";if(isVip)evvtype="企业版";
         if (jqloc.indexOf("/jq/") > -1)
             _czc.push(["_trackEvent", "PC端JQ",evvtype, cqType]);
         else if(jqloc.indexOf("/complete.aspx")>-1)
             _czc.push(["_trackEvent", "PC端完成", evvtype, cqType]);
     }
</script>



<script src="js/jquery-3.3.1.js"></script>
<script type="text/javascript">
	
	//var student = JSON.parse('${student}') 
	var contestStatusId = ${contestStatusId}    


	  
 
	var curProblem;
	var problemCache = [];
	var languageCache = []; 
	var pro = ${paper}
	
	
	GenerateAllProblem(pro)
	 
	var statusId = window.location.pathname.split(".")[0];
	$.ajax({
		type: "POST",
		url: "User/RequestPaper?id=" + statusId,
		success: function(data)
		{
			var da = JSON.parse(data)
			//GenerateAllProblem(da)
			pro = da;
		} 
		})
	
	function AddOption(){
		
		}
		
    function Create_div_question(content,num)
	{
		var s = '<div class="div_question" id="div' + num +'">' + content + '<div class="errorMessage"></div></div>';
		return s;
	}
	function Create_div_title_question_all(content,num)
	{
		var s = '<div class="div_title_question_all"><div class="div_topic_question">' + num + '.</div>' + content + '<div style="clear:both;"></div></div>';
		return s;
	}
	function Create_div_title_question(content,num)
	{
		var s = '<div id="divTitle' + num +'" class="div_title_question">' + content + '<span class="req">*</span></div>';
		return s;
	}
	function Create_div_table_radio_question(content,num)
	{
		var s = '<div class="div_table_radio_question" id="divquestion' + num + '"><div class="div_table_clear_top"></div>' + content + '<div class="div_table_clear_bottom"></div></div>';
		return s;
	}
	function Create_inputtext(num)
	{
		var s = '<textarea title="" style="overflow: auto;width:62%;height:66px;" class="inputtext" value="" id="q' + num + '" name="q' + num + '"></textarea>';
		return s;
	} 
	function Create_Button(num) 
	{ 
		var s = '<input type="button" value="编辑代码" class="submitbutton" style="width: 20%;" onclick="ProblemChange('+ num + ')" data-toggle="modal" data-target="#myModal" id="q' + num + '" name="q' + num+ '">'; 
		return s; 
	} 
	function Create_ulradiocheck(content)
	{
		var s = '<ul class="ulradiocheck">' + content + '<div style="clear:both;"></div></ul>';
		return s;
	}
	function Create_jqRadio(content,num,count)
	{
		var id = 'q' + num + '_' + count;
		var s = '<li style="width: 99%;"><a href="javascript:" class="jqRadio" rel="' + id + '"></a><input style="display:none;" type="radio" id="'+ id +'" value=\'' + content +'\' /><label onmouseover="selected(this)" onmouseout="unselected(this)" onclick="radioClick(this)" id="' + id + '_text" for="' + id + '">' + content + '</label></li>';
		return s;
	}
	function Create_clear_div(){
		return '<div style="clear:both;"></div>';
	} 
	function Create_jqCheckbox(content,num,count)
	{
		var id = 'q' + num + '_' + count;
		var s = '<li style="width:99%;"><a href="javascript:" class="jqCheckbox" rel="' + id +'"></a><input style="display:none;" id="' + id + '" type="checkbox"  value=\'' + content + '\' /><label onmouseover="selected(this)" onmouseout="unselected(this)" onclick="checkboxClick(this)" id="' + id + '_text">' + content + '</label></li>';
	    return s;
	}
	function CreateProblemContent(obj) 
	{
		var s = '题目标题：' + obj.title.replace("\\n","&#10") +'<br>题目描述：'+ obj.description.replace("\\n","&#10") +'<br>时间限制:' + obj.timeLimit +' sec<br>内存限制:' + obj.memoryLimit + ' MB<br>数据输入:<br>' + obj.sampleInput.replace("\\n","&#10") + '<br>数据输出<br>' + obj.sampleOutput.replace("\\n","&#10") +'<br>提示:<br>' + obj.hint.replace("\\n","&#10");
		return s;
	}
	function Create_underline(content,num,count)
	{
		return content + '<input style="width:63px;" type="text" class="underline" id="q' + num + '_' + count + '" />';
	}
	function GenerateAllProblem(obj){
		var totalCount = obj.simp.length + obj.prob.length;
		console.log("count="+totalCount)
		//var problemDiv = document.getElementById('fieldset1');
		for(var index=1;index<=totalCount;index++)
		{
			var pro = null;
			var flag = false;
			var type = 0;
			for(var i=0;i<obj.simp.length;i++)
			if(obj.simp[i].simproblem.pos==index){
				pro = obj.simp[i];
				flag = true;
				type = obj.simp[i].simproblem.type;
				break;
			}
			
			if(!flag)  
			for(var i=0;i<obj.prob.length;i++)
			{
				if(obj.prob[i].problem.pos==index)
				pro = obj.prob[i];
				type = 6;  
				break; 
			}
			console.log(GenerateProblem(pro,type,index))
			$("#fieldset1").html($("#fieldset1").html() + GenerateProblem(pro,type,index))
		//	problemDiv.innerHtml = GenerateProblem(pro,type,index);
		//	GenerateProblem(pro,type,index);
			
		}
	}
	function GenerateProblem(obj,type,index)
	{
		var s = ""; 
		if(type==1){  //单选
			for(var i=1;i<=obj.option.length;i++)  //生成选项div
				s += Create_jqRadio(obj.option[i-1].content,index,i);
			
			s = Create_ulradiocheck(s);
			s = s + Create_clear_div(); 
			s = Create_div_table_radio_question(s,index);
			var head = Create_div_title_question(obj.simproblem.content,index);
			head = Create_div_title_question_all(head,index);
			s = Create_div_question(head + s,index);
			return s;
		}
		else if(type==2){ //多选
			for(var i=1;i<=obj.option.length;i++)  //生成选项div
				s += Create_jqCheckbox(obj.option[i-1].content,index,i);
			
			s = Create_ulradiocheck(s);
			s = s + Create_clear_div();
			s = Create_div_table_radio_question(s,index);
			var head = Create_div_title_question(obj.simproblem.content,index);
			head = Create_div_title_question_all(head,index);
			s = Create_div_question(head + s,index);
			return s;
		}
		else if(type==3){ //判断
			s += Create_jqRadio("正确",index,1);
			s += Create_jqRadio("错误",index,2);
			
			s = Create_ulradiocheck(s);
			s = s + Create_clear_div();
			s = Create_div_table_radio_question(s,index);
			var head = Create_div_title_question(obj.simproblem.content,index);
			head = Create_div_title_question_all(head,index);
			s = Create_div_question(head + s,index);
			return s;
		}
		else if(type==4){ //填空
		console.log("填空题内容:\n")
		console.log(obj.simproblem.content)
			var content = obj.simproblem.content;
			var str = content.split("___________");
			console.log(str) 
			var s = "";
			for(var i=0;i<str.length-1;i++){
				s += Create_underline(str[i],index,i+1);
			}
			s += str[str.length-1];
			console.log("generate content:\n" + s)
			s = Create_div_title_question(s,index);  
			console.log("generate content@@:\n" + s)
			s = Create_div_title_question_all(s,index); 
			console.log("generate content!!:\n" + s)
			
			var s1 = "";
			s1 = Create_ulradiocheck(s1);
			s1 = s1 + Create_clear_div();
			s1 = Create_div_table_radio_question(s1,index);
			s = Create_div_question(s + s1,index);
			return s;
		}
		else if(type==5){  //简答
		s = Create_inputtext(index);
		
		s = Create_div_table_radio_question(s,index);
		var head = Create_div_title_question(obj.simproblem.content,index);
		head = Create_div_title_question_all(head,index);
		s = Create_div_question(head + s,index);
		return s;
		}
		else if(type==6)  //编程
		{
		s = Create_Button(index);
		
		s = Create_ulradiocheck(s);
		s = s + Create_clear_div();
		s = Create_div_table_radio_question(s,index);
		var head = Create_div_title_question(CreateProblemContent(obj.problem),index);
		head = Create_div_title_question_all(head,index);
		s = Create_div_question(head + s,index);
		return s;
		}
		
		
	}
</script>
	
<script>

function switchLang(lang){
   var langnames=new Array("c_cpp","c_cpp","pascal","java","ruby","sh","python","php","perl","csharp","objectivec","vbscript","scheme","c_cpp","c_cpp","lua","javascript","golang");
   editor.getSession().setMode("ace/mode/"+langnames[lang]);

}
function reloadtemplate(lang){
   console.log("lang="+lang);
   document.cookie="lastlang="+lang.value;
   //alert(document.cookie);
   var url=window.location.href;
   var i=url.indexOf("sid=");
   if(i!=-1) url=url.substring(0,i-1);
 //  if(confirm("是否加载默认模板?\n 如果选择是，当前代码将被覆盖!"))
 //       document.location.href=url;
   switchLang(lang);
}
</script>
<script language="Javascript" type="text/javascript" src="./HUSTOJ_files/base64.js"></script>
<script src="./HUSTOJ_files/ace.js"></script>
<script src="./HUSTOJ_files/ext-language_tools.js"></script>
<script>


		ace.require("ace/ext/language_tools");
		 var editor = ace.edit("source");
		 editor.setTheme("ace/theme/chrome");
		 switchLang(undefined);
		 editor.setOptions({
			    enableBasicAutocompletion: true,
			    enableSnippets: true,
			    enableLiveAutocompletion: true
		 });
		reloadtemplate($("#language").val()); 

    

function getText(){
	console.log(editor.getValue())
}

function ProblemChange(num){
	if(problemCache[curProblem]!=null)
	editor.setValue(problemCache[curProblem])
	
	curProblem = num;
	  
	for(var i=0;i<pro.prob.length;i++)   
	if(pro.prob[i].problem.pos==num)
	$('#titleSpan').html(pro.prob[i].problem.title)
}

function SaveSource(){ 
    console.log("savesource:\n" + editor.getValue())
	if(curProblem!=null)
	
	problemCache[curProblem] = editor.getValue();
	console.log($('#language').val()) 
	languageCache[curProblem] = $('#language').val()
	
} 

function generate_submit_paper()
{
    var solution = [];

    console.log("pro:\n" + JSON.stringify(pro))
    console.log("len=" + pro.simp.length)
    for(var i=0;i<pro.simp.length;i++)
    {
        console.log("i=" + i)
        var obj = pro.simp[i].simproblem;
        var type = obj.type;
        var index = obj.pos;
        if(type==1)  //单选
        {
            for(var j=1;j<=pro.simp[i].option.length;j++)
            {
                var id = 'q' + index + '_' +j;
                if($('#' + id).prop("checked"))
                {
                    solution[index] = $('#' + id + '_text').html()
                    break;
                }
            }
        }
        else if(type==2)  //多选
        {
            var json = [];
            var cur = 0;
            for(var j=1;j<=pro.simp[i].option.length;j++)
            {
                var id = 'q' + index + '_' +j;
                if($('#' + id).prop("checked"))
                {
                    json[cur++] = $('#' + id + '_text').html()
                }
            }
            solution[index] = json.join("§§§")
        }
        else if(type==3)  //判断
        {
            if($('#q' + index + '_1').prop("checked")) {
                solution[index] = "正确";
            }else if($('#q' + index + '_2').prop("checked")) {
              	solution[index] = "错误";
            }
        }
        else if(type==4)  //填空
        {
            var json = [];
            for(var j=1;j<=obj.blanks;j++)
            {
                var id = 'q' + index + '_' +j;
                json[j-1] = $('#' + id).val()
            }
            solution[index] = json.join("§§§")
        }
        else{ //简答
            solution[index] = $('#q' + index).val()
        }
    }

    for(var i=0;i<pro.prob.length;i++)
    {
        console.log("!!i=" + i)
        var index = pro.prob[i].problem.pos;
        if(problemCache[index] != null)
            solution[index] = problemCache[index];
    }

    console.log("solution:\n" + JSON.stringify(solution))

    /*  $.ajax({
         type: "POST",
         url: "User/SubmitPaper?conteststatus_id=" + pro.contestStatus.contestStatusId,
         dataType : 'json',
         data: solution,
         success: function(){
         alert("提交成功")
         }
     }) */

    for(var i=0;i<pro.simp.length;i++){
        var solu = new Object();
        solu.answer = solution[pro.simp[i].simproblem.pos]
        solu.contestStatusId = contestStatusId;
        solu.simproblemId = pro.simp[i].simproblem.simproblemId;
        pro.simp[i].simsolution = solu;
    }

    for(var i=0;i<pro.prob.length;i++){
        var solu = new Object();
        solu.source = solution[pro.prob[i].problem.pos]
        solu.contestStatusId = contestStatusId;
        solu.problemId = pro.prob[i].problem.problemId;
        solu.language = languageCache[pro.prob[i].problem.pos]
        pro.prob[i].solution = solu;
    }
    console.log(pro)
}

        function submit_post(){
            var layerIndex = layer.load(1, { shade: [0.5, '#393D49'] });	//加载动画
            $.ajax({
                type: "POST",
                url: "submit.do?contestStatusId=" + contestStatusId,
                dataType : 'text',
                contentType:"application/json;charset=UTF-8",
                data: JSON.stringify(pro),
                success: function(data){
                    layer.close(layerIndex);	//关闭加载动画
                    //配置一个透明的询问框
                    layer.msg('提交成功', {
                        time: 4000, //4s后自动关闭
                    });
                    setTimeout(
                        function(){
                            javascript:top.location.reload();  //回退主页
                        },
                        3000)
                },
                error: function(data){
                    layer.close(layerIndex);	//关闭加载动画
                    layer.close(layerIndex);
                    layui.use(['layer', 'form'], function(){
                        //配置一个透明的询问框
                        layer.msg('提交失败', {
                            time: 3000, //4s后自动关闭
                        });
                    });
                }
            })
        }

function submitpaper()
{
    generate_submit_paper()

    layui.use(['layer', 'form'], function(){

      	layer.confirm('确定提交答卷？', function(index) {
            submit_post()
				layer.close(index);
		})
	      
 	});
}

        var leftContestTime = ${leftTime}//${leftContestTime};
        var span = document.getElementById('spanMaxTime')
        var timer = setInterval(function(){
                span.innerHTML = getMaxTimeStr(leftContestTime--)
                console.log(span.innerHTML)
                if(span.innerHTML<="00:00:00"){
                    generate_submit_paper()
                    layui.use(['layer', 'form'], function(){
                            submit_post()
                            layer.close(index);
                    });
                    clearInterval(timer);
                }
            },
            1000);

/* 
	
var t2 = window.setInterval(function(){
	console.log($('#q4_1').prop("checked"))
},1000);  
 */




</script>

<script type="text/javascript">
    function changeStyle()
    {
    
    }
    function removeStyle()
    {
    
    }
    
</script>


 <script type="text/javascript" src="./contest/hintinfo.js"></script>




<div class=" ace_editor ace_autocomplete ace-chrome" style="top: 157.359px; height: 128px; left: 144.797px; display: none;"><textarea class="ace_text-input" wrap="off" autocorrect="off" autocapitalize="off" spellcheck="false" style="opacity: 0; left: -100px;"></textarea><style id="ace-tm">.ace-tm .ace_gutter {background: #f0f0f0;color: #333;}.ace-tm .ace_print-margin {width: 1px;background: #e8e8e8;}.ace-tm .ace_fold {background-color: #6B72E6;}.ace-tm {background-color: #FFFFFF;color: black;}.ace-tm .ace_cursor {color: black;}.ace-tm .ace_invisible {color: rgb(191, 191, 191);}.ace-tm .ace_storage,.ace-tm .ace_keyword {color: blue;}.ace-tm .ace_constant {color: rgb(197, 6, 11);}.ace-tm .ace_constant.ace_buildin {color: rgb(88, 72, 246);}.ace-tm .ace_constant.ace_language {color: rgb(88, 92, 246);}.ace-tm .ace_constant.ace_library {color: rgb(6, 150, 14);}.ace-tm .ace_invalid {background-color: rgba(255, 0, 0, 0.1);color: red;}.ace-tm .ace_support.ace_function {color: rgb(60, 76, 114);}.ace-tm .ace_support.ace_constant {color: rgb(6, 150, 14);}.ace-tm .ace_support.ace_type,.ace-tm .ace_support.ace_class {color: rgb(109, 121, 222);}.ace-tm .ace_keyword.ace_operator {color: rgb(104, 118, 135);}.ace-tm .ace_string {color: rgb(3, 106, 7);}.ace-tm .ace_comment {color: rgb(76, 136, 107);}.ace-tm .ace_comment.ace_doc {color: rgb(0, 102, 255);}.ace-tm .ace_comment.ace_doc.ace_tag {color: rgb(128, 159, 191);}.ace-tm .ace_constant.ace_numeric {color: rgb(0, 0, 205);}.ace-tm .ace_variable {color: rgb(49, 132, 149);}.ace-tm .ace_xml-pe {color: rgb(104, 104, 91);}.ace-tm .ace_entity.ace_name.ace_function {color: #0000A2;}.ace-tm .ace_heading {color: rgb(12, 7, 255);}.ace-tm .ace_list {color:rgb(185, 6, 144);}.ace-tm .ace_meta.ace_tag {color:rgb(0, 22, 142);}.ace-tm .ace_string.ace_regex {color: rgb(255, 0, 0)}.ace-tm .ace_marker-layer .ace_selection {background: rgb(181, 213, 255);}.ace-tm.ace_multiselect .ace_selection.ace_start {box-shadow: 0 0 3px 0px white;}.ace-tm .ace_marker-layer .ace_step {background: rgb(252, 255, 0);}.ace-tm .ace_marker-layer .ace_stack {background: rgb(164, 229, 101);}.ace-tm .ace_marker-layer .ace_bracket {margin: -1px 0 0 -1px;border: 1px solid rgb(192, 192, 192);}.ace-tm .ace_marker-layer .ace_active-line {background: rgba(0, 0, 0, 0.07);}.ace-tm .ace_gutter-active-line {background-color : #dcdcdc;}.ace-tm .ace_marker-layer .ace_selected-word {background: rgb(250, 250, 255);border: 1px solid rgb(200, 200, 250);}.ace-tm .ace_indent-guide {background: url("data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAEAAAACCAYAAACZgbYnAAAAE0lEQVQImWP4////f4bLly//BwAmVgd1/w11/gAAAABJRU5ErkJggg==") right repeat-y;}
/*# sourceURL=ace/css/ace-tm */</style><div class="ace_gutter" aria-hidden="true" style="display: none;"><div class="ace_layer ace_gutter-layer ace_folding-enabled" style="margin-top: 0px;"></div><div class="ace_gutter-active-line" style="display: none;"></div></div><div class="ace_scroller" style="left: 0px; right: 17px; bottom: 0px;"><div class="ace_content" style="cursor: default; margin-top: 0px; width: 261px; height: 160px; margin-left: 0px;"><div class="ace_layer ace_print-margin-layer"><div class="ace_print-margin" style="left: 4px; visibility: hidden;"></div></div><div class="ace_layer ace_marker-layer"><div class="ace_active-line" style="height:16px;top:0px;left:0;right:0;"></div></div><div class="ace_layer ace_text-layer" style="padding: 0px 4px;"><div class="ace_line ace_selected" style="height:16px"><span class="ace_">c</span><span class="ace_completion-highlight">as</span><span class="ace_">e</span><span class="ace_rightAlignedText">keyword</span></div><div class="ace_line" style="height:16px"><span class="ace_">cl</span><span class="ace_completion-highlight">as</span><span class="ace_">s</span><span class="ace_rightAlignedText">keyword</span></div><div class="ace_line" style="height:16px"><span class="ace_">__</span><span class="ace_completion-highlight">as</span><span class="ace_">m__</span><span class="ace_rightAlignedText">keyword</span></div><div class="ace_line" style="height:16px"><span class="ace_completion-highlight">as</span><span class="ace_">m</span><span class="ace_rightAlignedText">keyword</span></div><div class="ace_line" style="height:16px"><span class="ace_">align</span><span class="ace_completion-highlight">as</span><span class="ace_rightAlignedText">keyword</span></div><div class="ace_line" style="height:16px"><span class="ace_">static_c</span><span class="ace_completion-highlight">as</span><span class="ace_">t</span><span class="ace_rightAlignedText">keyword</span></div><div class="ace_line" style="height:16px"><span class="ace_">reinterpret_c</span><span class="ace_completion-highlight">as</span><span class="ace_">t</span><span class="ace_rightAlignedText">keyword</span></div><div class="ace_line" style="height:16px"><span class="ace_">dynamic_c</span><span class="ace_completion-highlight">as</span><span class="ace_">t</span><span class="ace_rightAlignedText">keyword</span></div><div class="ace_line" style="height:16px"><span class="ace_">const_c</span><span class="ace_completion-highlight">as</span><span class="ace_">t</span><span class="ace_rightAlignedText">keyword</span></div></div><div class="ace_layer ace_marker-layer"></div><div class="ace_layer ace_cursor-layer ace_hidden-cursors" style="opacity: 0;"><div class="ace_cursor" style="left: 4px; top: 0px; width: 6.59766px; height: 16px;"></div></div></div></div><div class="ace_scrollbar ace_scrollbar-v" style="width: 22px; bottom: 0px;"><div class="ace_scrollbar-inner" style="width: 22px; height: 192px;"></div></div><div class="ace_scrollbar ace_scrollbar-h" style="display: none; height: 22px; left: 0px; right: 17px;"><div class="ace_scrollbar-inner" style="height: 22px; width: 261px;"></div></div><div style="height: auto; width: auto; top: 0px; left: 0px; visibility: hidden; position: absolute; white-space: pre; font: inherit; overflow: hidden;"><div style="height: auto; width: auto; top: 0px; left: 0px; visibility: hidden; position: absolute; white-space: pre; font: inherit; overflow: visible;"></div><div style="height: auto; width: auto; top: 0px; left: 0px; visibility: hidden; position: absolute; white-space: pre; font-style: inherit; font-variant: inherit; font-stretch: inherit; font-size: inherit; line-height: inherit; font-family: inherit; overflow: visible;"></div></div></div>	
</body></html>