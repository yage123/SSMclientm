$(function () {
    var devresult = $("#devResult").val();
    if(devresult==2||devresult==3){
        $("#toolbar").remove();
    }
    $("#dg").edatagrid({
        url:ctx+"/cus_dev_plan/queryCusDevPlans?saleChanceId="+$("#saleChanceId").val(),
        saveUrl: ctx + "/cus_dev_plan/insert?saleChanceId="+ $("#saleChanceId").val(),
        updateUrl: ctx + "/cus_dev_plan/update?saleChanceId="+ $("#saleChanceId").val()
    })
})
//保存
function saveCusDevPlan() {
    $("#dg").edatagrid("saveRow");
    $("#dg").edatagrid("load");
}
//修改
function updateCusDevPlan() {
    $("#dg").edatagrid("saveRow");
    $("#dg").edatagrid("load");
}
//删除
function delCusDevPlan() {
    var rows = $("#dg").edatagrid("getSelections");
    if (rows.length == 0) {
        $.messager.alert("来自crm系统", "请选择所要删除项", "info");
        return;
    }
    $.messager.confirm("来自crm系统", "确定要删除所选项", function (r) {
        if (r) {
            $.ajax({
                type: "post",
                url: ctx + "/cus_dev_plan/delete",
                data: "id=" + rows[0].id,
                dateType: "json",
                success: function (data) {
                    $.messager.alert("来自crm系统", data.msg, "info");
                    if (data.code == 200) {
                        $("#dg").edatagrid("load");
                    }
                }
            })
        }
    })
}

function updateSaleChanceDevResult(devResult) {
    $.messager.confirm("来自crm系统","确定执行该操作",function (r) {
        if(r){
            $.ajax({
                type:"post",
                url:ctx+"/sale_chance/updateSaleChanceDevResult",
                data:"devResult="+devResult + "&saleChanceId=" +
                    $("#saleChanceId").val(),
                dataType:"json",
                success:function (data) {
                    $.messager.alert("来自crm",data.msg,"info");
                    if(data.code==200){
                        $("#toolbar").remove();
                    }
                }
            })
        }
    })
}