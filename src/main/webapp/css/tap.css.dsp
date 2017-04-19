<%@ page contentType="text/css;charset=UTF-8" %> 
<%@ taglib uri="http://www.zkoss.org/dsp/web/core" prefix="c" %>
<c:set var="ImgUrl" value="${c:encodeURL('/images')}" scope="request" />

body { 
	font: .8em Tahoma; 
	line-height: 1.6em; 
	#margin: 5px 0 0 0; 
	background: #f3f3f3;
	color: #464646;
}

a { 
	color: #0066B3; 
	background: inherit; 
}
a:hover { color: #808080;
	background: inherit; 
}

.h1 { 
	font: bold 1.9em Georgia, Sans-Serif; 
	color: #214584; 
	#color: #ffffff;
	padding: 0; 
	margin: 0;
}

.h2 { 
	font: bold 1.2em Georgia, Sans-Serif; 
	padding: 0; 
	margin: 0; 
}

h3 { 
	font: bold 1.0em Tahoma; 
	color: #210F7A;
	#color: #ffffff;
	padding: 0; 
	margin-left: 15px;
}

ul { 
	padding: 0; 
	margin: 0;
}
li { 
	list-style-type: 
	none;
}

.logo { 
	float: left; 
	margin: 0 0 1em 1em; 
}

.subheader { 
	clear: both; 
	background: url(${c:encodeURL('/images/layout/bar1.png')});
	background-repeat: y;
	color: #85ACF7;
	padding: 0 15px 0 0;
	margin: 0 0 15px 0;
	height: 25px;
}

.z-groupbox {
}

.z-groupbox .z-groupbox-hl .z-groupbox-hr {
	border: none;
	padding-bottom: 5px;
}

.z-groupbox .z-groupbox-cnt {
	border-style : none;
	#border-width : 1px;
	#border-color : #1a58ab;
	background: #F3F3F3;
	border-radius: 5px 5px 5px 5px;
}

.groupBoxTk2.z-groupbox .z-groupbox-cnt {
	border-style : none;
	#border-width : 1px;
	#border-color : #1a58ab;
	background: #fff;
	#border-radius: 5px 5px 5px 5px;
}

.groupBoxTk2.z-groupbox .z-groupbox-hl .z-groupbox-hr {
	border: none;
	padding-bottom: 0px;
}

.groupBoxTk2.z-groupbox .z-groupbox-cnt {
	border-style : none;
	#border-width : 1px;
	#border-color : #1a58ab;
	background: #fff;
}

.groupBoxTk2.z-groupbox-3d .open-false,
.groupBoxTk2.z-groupbox-3d .open-true {
  background: url(${c:encodeURL('/images/icones/arrow.png')}) no-repeat right 0;
  height: 16px; 
  padding-right: 20px;
  color: #747474;
  font: bold 1.2em Tahoma;
}
.groupBoxTk2.z-groupbox-3d div.open-false {
  background-position: right -16px;
}
.z-groupbox-colpsd .block {
  display: block;
}

.groupBoxTk3.z-groupbox .z-groupbox-cnt {
	border-style : none;
	#border-width : 1px;
	#border-color : #1a58ab;
	background: #DDFFAA;
}



.groupBoxTk3d .z-groupbox {
	border-style : none;
	border-width : 1px;
	#border-color : #1a58ab;
	background: #F7F7F7; 
}

.groupBoxHeader {
	#color: #214584; 
	color: #747474;
	font: bold 1.2em Tahoma; 
}

.appSide {
	color: #214584;
	font-family: Tahoma; 
	margin: 0; 
	padding: 15px; 
	line-height: 15px;
	background: #F3F3F3;
	border-radius: 5px 5px 5px 5px;
}

.appLink {
	color: #214584;
	font-family: Tahoma; 
	padding: 10px; 
	margin: 0; 
	line-height: 15px;
	background: #F3F3F3;
	border-radius: 5px 5px 5px 5px;
}

.appLink:hover {
	#background: #fff url('../images/back.png'); 
	#background-repeat: no-repeat;
	background: #DDFFAA;
	border-radius: 5px 5px 5px 5px;
	border-style : solid;
	border-width : 1px;
	border-color: #339900;
	padding: 12px; 
}

.appLink:hover .appLabel {
	color: #339900; 
	#color: #214584;
	text-decoration: underline; 
	cursor: pointer;  
	background: #DDFFAA;
}

div.appLink .appLabel {
	font-size: 1.2em; 
	color: #464646
	text-decoration: inherited;
}

div.appLink .appSousTitre {
	#font-style: italic;
	font-size: 0.8em;
	color: #464646;
}

div.logout {
	color: #214584;
	font-family: Tahoma; 
	padding: 10px; 
	margin: 0; 
	line-height: 15px;
	background: #F3F3F3;
	border-radius: 5px 5px 5px 5px;
}

div.logout .appLabel {
	font-size: 1.2em; 
	color: #464646
	text-decoration: inherited;
}

div.logout:hover {
	cursor: pointer; 
	border-radius: 5px 5px 5px 5px;
	border-style : solid;
	border-width : 1px;
	border-color: #464646;
	padding: 12px;
	background: #606060;
}

.logout:hover .appLabel {
	color: #f3f3f3; 
	text-decoration: underline; 
	cursor: pointer;  
}

.barLink:hover {
	color: #210F7A;
	font-weight: bold;
	cursor: pointer; 
}