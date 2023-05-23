var addModal = document.getElementById('addSkill');
addModal.addEventListener('hidden.bs.modal', function (e) {
    $(this).find('form').trigger('reset');
    $(this).find('form').find('#description').removeClass('active');
});
