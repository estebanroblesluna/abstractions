
<jsp:include page="/WEB-INF/jsp/showDefinitionBegin.jsp" />
<div class="form-group">
    <label for="inputRouterEvaluatorImplementation" class="col-lg-2 control-label">Router evaluator implementation</label>
    <div class="col-lg-10">
        <input readonly='readonly' value='${definition.routerEvaluatorImplementation}' type="text" class="form-control" id="inputRouterEvaluatorImplementation" name="routerEvaluatorImplementation" placeholder="">
    </div>
</div>
<div class="form-group">
    <label for="isRouterEvaluatorScript" class="col-lg-2 control-label">Is router evaluator script?</label>
    <div class="col-lg-10">
            <input type="text" class="form-control" id="inputIsScript" name="isScript" placeholder="" readonly='readonly' value='${definition.isRouterEvaluatorScript() == true ? 'Yes' : 'No'}'>
    </div>
</div>
<jsp:include page="/WEB-INF/jsp/showDefinitionEnd.jsp" />