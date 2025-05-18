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

// Initialize error handling for submit form
document.addEventListener('DOMContentLoaded', function() {
    const submitForm = document.getElementById('submitForm');
    const answersError = document.getElementById('answersError');
    let errorTimeout;

    function startErrorTimer() {
        if (!answersError) return;
        
        // Clear any existing timeout
        if (errorTimeout) {
            clearTimeout(errorTimeout);
        }
        
        // Remove and re-add animation class to restart it
        answersError.classList.remove('animate');
        // Force a reflow to ensure animation restarts
        void answersError.offsetWidth;
        answersError.classList.add('animate');
        
        // Start the 3-second timer
        errorTimeout = setTimeout(() => {
            answersError.style.display = 'none';
            answersError.classList.remove('animate');
        }, 3000);
    }

    // Add hover listeners to the error message
    if (answersError) {
        answersError.addEventListener('mouseenter', () => {
            // Clear the timeout and remove animation when hovering
            if (errorTimeout) {
                clearTimeout(errorTimeout);
            }
            answersError.classList.remove('animate');
        });

        answersError.addEventListener('mouseleave', () => {
            // Restart the animation and timer when mouse leaves
            startErrorTimer();
        });
    }

    // Add form submission handler
    if (submitForm) {
        submitForm.addEventListener('submit', function(e) {
            // Select all question containers (modal-content divs that contain questions)
            const questions = document.querySelectorAll('.modal-content:not(:first-of-type)');
            let allQuestionsAnswered = true;

            questions.forEach(question => {
                const checkedOptions = question.querySelectorAll('input[type="checkbox"]:checked');
                if (checkedOptions.length === 0) {
                    allQuestionsAnswered = false;
                }
            });

            if (!allQuestionsAnswered && answersError) {
                e.preventDefault(); // Prevent form submission
                answersError.style.display = 'block'; // Show error message
                startErrorTimer();
            }
        });
    }
});