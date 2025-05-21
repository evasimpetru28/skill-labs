function toggleFavorite(skillId, superuserId) {
    $.ajax({
        type : "POST",
        url : "/api/skill/" + skillId + "/bookmark/" + superuserId,
        dataType: "html",
        contentType: 'application/json',
        mimeType: 'application/json',
        timeout : 100000,
        success : function() {

            location.reload();
        },
        error : function(e) {
            console.log("ERROR: ", e);
        }
    });

} 