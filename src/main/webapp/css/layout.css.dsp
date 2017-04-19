<%@ page contentType="text/css;charset=UTF-8" %> 
<%@ taglib uri="http://www.zkoss.org/dsp/web/core" prefix="c" %>

.breeze .complex-layout, .breeze .complex-layout .z-south, .breeze .complex-layout .z-west { 
	#background: #FFFFFF; 
}

.z-borderlayout { 
	background: #FFFFFF 
} 

.complex-layout .z-north { 
	// background: #008BB6; 
} 

.inner-border, .inner-border .z-north, .inner-border .z-west, .inner-border .z-south, .inner-border .z-east { 
	background: #FFFFFF; 
} 

.dl-link {
	text-decoration: none; 
	cursor: pointer; 
}

.banner {
	background: url(${c:encodeURL('/images/layout/melbase_bg.png')});
	background-repeat: none;
}

.top {
	background: url(${c:encodeURL('/images/layout/top_bg.png')});
	background-repeat: x;
}

.down {
	background: url(${c:encodeURL('/images/layout/down_bg3.png')});
	background-repeat: x;
}

.downcorner {
	background: url(${c:encodeURL('/images/layout/down_corner2.png')});
	background-repeat: none;
}

div.part_aphp {
	background: url(${c:encodeURL('/images/layout/aphp.jpg')});
}

div.error {
	font-size: 20px;
	font-family: Tahoma; 
	font-weight: bold;
	line-height: 1.6em; 
	margin: 5px 0 0 0; 
	background: #FFEEDD;
	color: #BB0000;
	border-radius: 5px 5px 5px 5px;
	border-style: solid;
	boder-width: 1pt;
	border-color: #BB0000;
}

