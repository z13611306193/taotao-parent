<%--
  Created by IntelliJ IDEA.
  User: ZYGisComputer
  Date: 2019/3/13
  Time: 21:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>商品已成功加入购物车</title>
    <link href="/css/taotao.css" rel="stylesheet" type="text/css">
    <link href="/css/base.css" rel="stylesheet" type="text/css">
    <style type="text/css">
        #categorys-2013 .mc {
            display: block;
        }
        #categorys-2013 .mt {
            background: 0
        }
    </style>
</head>
<body>
<script src="/js/jquery-1.6.4.js"/>
<script src="/js/jquery-extend.js"/>
<script src="/js/taotao.js"/>
<script src="/js/home.js"/>
<script src="/js/base-v1.js"/>
<script src="/js/lib-v1.js"/>
<script type="text/javascript">
    window.pageConfig={
        compatible:true,
        navId:"home",
        enableArea: true
    };
</script>
<jsp:include page="commons/header.jsp"></jsp:include>
<div>   &nbsp;&nbsp;&nbsp;<br/><br/><br/><br/><br/>
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div>
<div style="position: absolute;left:600px;top:194px">
    <h1>商品已成功加入购物车!</h1>
    <a href="/cart/cart.html">去购物车结算</a>
    您还可以<a href="javascript:history.back();">继续购物</a>

</div>
<jsp:include page="commons/footer.jsp"></jsp:include>

</body>
</html>
