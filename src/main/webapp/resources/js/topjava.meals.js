const mealAjaxUrl = "meals/";
const getAllUrl = "meals/all";

const ctx = {
    ajaxUrl: mealAjaxUrl,
    getAllUrl: getAllUrl
};

// $(document).ready(function () {
$(function () {
    makeEditable(
        $("#datatable").DataTable({
            "rowId": "id",
            "paging": false,
            "info": true,
            "columns": [
                {
                    "data": "dateTime"
                },
                {
                    "data": "description"
                },
                {
                    "data": "calories"
                },
                {
                    "render": renderDeleteBtn,
                    "defaultContent": "",
                    "orderable": false
                }
            ],
            "order": [
                [
                    0,
                    "desc"
                ]
            ]
        })
    );
});

function filterMeals() {
    const params = $("#filter").serialize();
    $.get(mealAjaxUrl + "filter", params, function (data) {
        ctx.datatableApi.clear().rows.add(data).draw();
    });
}

function clearFilter() {
    $("#filter")[0].reset();
    updateTable();
}