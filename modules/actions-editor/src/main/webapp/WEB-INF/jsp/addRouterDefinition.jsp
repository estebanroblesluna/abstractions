
<jsp:include page="/WEB-INF/jsp/addDefinitionBegin.jsp" />
<div class="form-group">
    <label for="inputRouterEvaluatorImplementation" class="col-lg-2 control-label">Router evaluator implementation</label>
    <div class="col-lg-10">
        <input type="text" class="form-control" id="inputRouterEvaluatorImplementation" name="routerEvaluatorImplementation" placeholder="">
    </div>
</div>
<div class="form-group">
    <label for="isRouterEvaluatorScript" class="col-lg-2 control-label">Router Evaluator Script?</label>
    <div class="col-lg-10">
        <div class="checkbox">
            <label>
                <input type="checkbox" name="isRouterEvaluatorScript" id='inputIsRouterEvaluatorScript' > Is Router Evaluator Script?            </label>
        </div>
    </div>
</div>
<jsp:include page="/WEB-INF/jsp/addDefinitionEnd.jsp" />