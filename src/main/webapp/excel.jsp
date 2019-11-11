
<%@include file="common.jsp" %>
<body>
<div class="table-responsive">
    
    <form class="form-horizontal" id="form_table" action="/Teacher/importStudent" enctype="multipart/form-data" method="post">
        <br/>
        <br/>
        <button type="submit" class="btn btn-primary">导入</button>
        <input class="form-input" type="file" name="filename"></input>
    </form>
</div>
</body>
