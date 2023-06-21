function addOrDeleteResponse(elem, quizId, optionId, questionId, studentId, responseId) {
    if (elem.checked) {
        let url =  "http://localhost:8081/add-response/" + optionId + "/" + questionId + "/" + studentId;
        $.ajax({
            type: "POST",
            url: url,
            dataType: "html",
            contentType: 'application/json',
            mimeType: 'application/json',
            success: function () {
                console.log("SUCCESS");
            },
            error: function (e) {
                console.log("ERROR: ", e);
            }
        });

    } else {
        let url = "http://localhost:8081/delete-response/" + responseId;
        $.ajax({
            type: "POST",
            url: url,
            dataType: "html",
            contentType: 'application/json',
            mimeType: 'application/json',
            timeout: 100000,
            success: function () {
                console.log("SUCCESS")
            },
            error: function (e) {
                console.log("ERROR: ", e);
            }
        });

    }
}