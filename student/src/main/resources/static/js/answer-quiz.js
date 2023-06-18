function addOrDeleteResponse(elem, optionId, questionId, studentId, responseId) {
    console.log(elem.checked)
    console.log(questionId)
    console.log(studentId)

    if (elem.checked) {
        $.ajax({
            type: "POST",
            url: "/add-response/" + optionId + "/" + questionId + "/" + studentId,
            dataType: "html",
            contentType: 'application/json',
            mimeType: 'application/json',
            timeout: 100000,
            success: function () {
                console.log("add")
                location.reload(true);
            },
            error: function (e) {
                console.log("ERROR: ", e);
            }
        });

    } else {
        console.log("----  "+responseId)
        $.ajax({
            type: "POST",
            url: "/delete-response/" + responseId,
            dataType: "html",
            contentType: 'application/json',
            mimeType: 'application/json',
            timeout: 100000,
            success: function () {
                console.log("delete")
                location.reload(true);
            },
            error: function (e) {
                console.log("ERROR: ", e);
            }
        });
    }
}