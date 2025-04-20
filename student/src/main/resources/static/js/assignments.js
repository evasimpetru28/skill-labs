$(document).ready(function () {
    // Initialize DataTable
    if ($.fn.DataTable) {
        $('#dtBasicExample').DataTable();
        $('.dataTables_length').addClass('bs-select');
    }
});