
<jsp:include page="/WEB-INF/jsp/addDefinitionBegin.jsp" />
<script type="text/javascript" src="/static/js/jscolor/jscolor.js"></script>
<div class="form-group">
    <label for="inputColor" class="col-lg-2 control-label">Color</label>
    <div class="col-lg-10">
        <input class="form-control color" id="inputColor" name="color" placeholder="">
    </div>
</div>
<div class="form-group">
    <label for="inputAcceptedTargetTypes" class="col-lg-2 control-label">Accepted target types</label>
    <div class="col-lg-10">
        <input type="text" class="form-control" id="inputAcceptedTargetTypes" name="acceptedTargetTypes" placeholder="">
    </div>
</div>
<div class="form-group">
    <label for="inputAcceptedSourceTypes" class="col-lg-2 control-label">Accepted source types</label>
    <div class="col-lg-10">
        <input type="text" class="form-control" id="inputAcceptedSourceTypes" name="acceptedSourceTypes" placeholder="">
    </div>
</div>
<div class="form-group">
    <label for="inputAcceptedTargetMax" class="col-lg-2 control-label">Accepted target max</label>
    <div class="col-lg-10">
        <input type="number" class="form-control" id="inputAcceptedTargetMax" name="acceptedTargetMax" placeholder="">
    </div>
</div>
<div class="form-group">
    <label for="inputAcceptedSourceMax" class="col-lg-2 control-label">Accepted source max</label>
    <div class="col-lg-10">
        <input type="number" class="form-control" id="inputAcceptedSourceMax" name="acceptedSourceMax" placeholder="">
    </div>
</div>
<jsp:include page="/WEB-INF/jsp/addDefinitionEnd.jsp" />