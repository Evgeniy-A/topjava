const mealAjaxUrl = "meals/";
const getAllUrl = "meals/all";

const ctx = {
    ajaxUrl: mealAjaxUrl,
    getAllUrl: getAllUrl,
    updateTable: filterMeals
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
    $.get(mealAjaxUrl + "filter", params, updateTableByData);
}

function clearFilter() {
    $("#filter")[0].reset();
    updateTable();
}