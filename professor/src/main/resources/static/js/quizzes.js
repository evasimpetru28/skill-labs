$(document).ready(function () {
    // Initialize DataTable
    if ($.fn.DataTable) {
        $('.dataTable').DataTable();
        $('.dataTables_length').addClass('bs-select');
    }
});