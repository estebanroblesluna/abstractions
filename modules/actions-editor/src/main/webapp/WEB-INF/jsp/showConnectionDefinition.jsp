
<jsp:include page="/WEB-INF/jsp/showDefinitionBegin.jsp" />

<div class="form-group">
    <label for="inputColor" class="col-lg-2 control-label" >Color</label>
    <div class="col-lg-10">
        <input class="form-control" id="inputColor" name="color" placeholder="" readonly="readonly" style='background-color: #${definition.color}; width: 50px;'>
    </div>
</div>
<div class="form-group">
    <label for="inputAcceptedTargetTypes" class="col-lg-2 control-label">Accepted target types</label>
    <div class="col-lg-10">
        <input type="text" readonly="readonly" class="form-control" id="inputAcceptedTargetTypes" name="acceptedTargetTypes" placeholder="" value='${definition.acceptedTargetTypes}'>
    </div>
</div>
<div class="form-group">
    <label for="inputAcceptedSourceTypes" class="col-lg-2 control-label">Accepted source types</label>
    <div class="col-lg-10">
        <input type="text"  readonly="readonly" class="form-control" id="inputAcceptedSourceTypes" name="acceptedSourceTypes" placeholder="" value='${definition.acceptedSourceTypes}'>
    </div>
</div>
<div class="form-group">
    <label for="inputAcceptedTargetMax" class="col-lg-2 control-label">Accepted target max</label>
    <div class="col-lg-10">
        <input type="number" readonly="readonly" class="form-control" id="inputAcceptedTargetMax" name="acceptedTargetMax" placeholder="" value='${definition.acceptedTargetMax}'>
    </div>
</div>
<div class="form-group">
    <label for="inputAcceptedSourceMax" class="col-lg-2 control-label">Accepted source max</label>
    <div class="col-lg-10">
        <input type="number" readonly="readonly" class="form-control" id="inputAcceptedSourceMax" name="acceptedSourceMax" placeholder="" value='${definition.acceptedSourceMax}'>
    </div>
</div>
<jsp:include page="/WEB-INF/jsp/showDefinitionEnd.jsp" />