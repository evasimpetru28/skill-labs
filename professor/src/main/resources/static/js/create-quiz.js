function addQuestion(quizId) {
    $.ajax({
        type : "POST",
        url : "/add-question/" + quizId,
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

function updateQuestion(callingElem, questionId) {
    $.ajax({
        type : "POST",
        url : "/update-question/" + questionId + "/" + callingElem.value,
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

function deleteQuestion(questionId, quizId) {
    $.ajax({
        type : "POST",
        url : "/delete-question/" + questionId + "/" + quizId,
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

function addOption(questionId, quizId) {
    $.ajax({
        type : "POST",
        url : "/add-option/" + questionId + "/" + quizId,
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

function updateOptionText(callingElem, optionId) {
    $.ajax({
        type : "POST",
        url : "/update-option-text/" + optionId + "/" + callingElem.value,
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

function updateOptionChecked(callingElem, optionId) {
    $.ajax({
        type : "POST",
        url : "/update-option-checked/" + optionId + "/" + callingElem.checked,
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


function deleteOption(optionId, quizId) {
    $.ajax({
        type : "POST",
        url : "/delete-option/" + optionId + "/" + quizId,
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