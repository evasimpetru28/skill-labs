$(document).ready(function () {
    // Initialize DataTable
    if ($.fn.DataTable) {
        $('#dtBasicExample').DataTable();
        $('.dataTables_length').addClass('bs-select');
    }

    // File validation for import
    $('#excelFile').on('change', function() {
        const file = this.files[0];
        const importButton = $('#importButton');
        
        if (!file) {
            importButton.prop('disabled', true);
            return;
        }

        console.log('File type:', file.type);
        console.log('File name:', file.name);

        // Simple extension check
        const fileName = file.name.toLowerCase();
        if (fileName.endsWith('.xlsx') || fileName.endsWith('.xls')) {
            importButton.prop('disabled', false);
        } else {
            importButton.prop('disabled', true);
            $(this).val('');
            alert('Please select a valid Excel file (.xlsx or .xls)');
        }
    });
});

$('#addCategory').on('hidden.bs.modal', function () {
    $(this).find('form').trigger('reset');
})

$('#importExcel').on('hidden.bs.modal', function () {
    $(this).find('form').trigger('reset');
    $('#importButton').prop('disabled', true);
})