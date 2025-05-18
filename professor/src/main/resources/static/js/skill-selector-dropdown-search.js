/* When the user clicks on the button,
toggle between hiding and showing the dropdown content */
function skillSelectorDropdownSearch() {
    document.getElementById("skillDropdown").classList.toggle("show");
    // Clear search input
    const searchInput = document.getElementById('skillInput');
    searchInput.value = '';
    // Reset all items to visible
    const div = document.getElementById("skillDropdown");
    const items = div.getElementsByTagName("p");
    for (let i = 0; i < items.length; i++) {
        items[i].style.display = "";
    }
}

function filterFunction() {
    const input = document.getElementById("skillInput");
    const filter = input.value.toUpperCase();
    const div = document.getElementById("skillDropdown");
    const p = div.getElementsByTagName("p");
    for (let i = 0; i < p.length; i++) {
        txtValue = p[i].textContent || p[i].innerText;
        if (txtValue.toUpperCase().indexOf(filter) > -1) {
            p[i].style.display = "";
        } else {
            p[i].style.display = "none";
        }
    }
}

// Initialize the no-skill class when the page loads
document.addEventListener('DOMContentLoaded', function() {
    const selectedSkill = document.getElementById('selectedSkill');
    const form = document.getElementById('nextForm');
    const skillError = document.getElementById('skillError');
    let errorTimeout;

    if (selectedSkill && selectedSkill.textContent === 'No skill selected') {
        selectedSkill.classList.add('no-skill');
    }

    function startErrorTimer() {
        if (!skillError) return;
        
        // Clear any existing timeout
        if (errorTimeout) {
            clearTimeout(errorTimeout);
        }
        
        // Remove and re-add animation class to restart it
        skillError.classList.remove('animate');
        // Force a reflow to ensure animation restarts
        void skillError.offsetWidth;
        skillError.classList.add('animate');
        
        // Start the 3-second timer
        errorTimeout = setTimeout(() => {
            skillError.style.display = 'none';
            skillError.classList.remove('animate');
        }, 3000);
    }

    // Add hover listeners to the error message
    if (skillError) {
        skillError.addEventListener('mouseenter', () => {
            // Clear the timeout and remove animation when hovering
            if (errorTimeout) {
                clearTimeout(errorTimeout);
            }
            skillError.classList.remove('animate');
        });

        skillError.addEventListener('mouseleave', () => {
            // Restart the animation and timer when mouse leaves
            startErrorTimer();
        });
    }

    // Add form submission handler
    if (form) {
        form.addEventListener('submit', function(e) {
            const selectedSkill = document.getElementById('selectedSkill');           
            const skillError = document.getElementById('skillError');

            if (selectedSkill && selectedSkill.textContent.trim() === 'No skill selected' && skillError) {
                e.preventDefault(); // Prevent form submission
                skillError.style.display = 'block'; // Show error message
                startErrorTimer();
            }
        });
    }
});

function selectSkill(skillId, skillLabel) {
    const selectedSkill = document.getElementById('selectedSkill');
    selectedSkill.textContent = skillLabel;
    selectedSkill.classList.remove('no-skill');
    
    // Update hidden input value
    const hiddenInput = document.getElementById('selectedSkillInput');
    if (hiddenInput) {
        hiddenInput.value = skillId;
    }
    
    // Hide error message if it's visible
    const skillError = document.getElementById('skillError');
    if (skillError) {
        skillError.style.display = 'none';
    }

    // Get the quiz ID from the URL or a data attribute
    const quizId = document.querySelector('form#nextForm').getAttribute('action').split('/').pop();
    
    // Send the selected skill to the backend
    updateQuizSkill(skillId, quizId);
    
    document.getElementById('skillDropdown').classList.remove('show');
    // Clear search input
    const searchInput = document.getElementById('skillInput');
    searchInput.value = '';
    // Reset all items to visible
    const div = document.getElementById("skillDropdown");
    const items = div.getElementsByTagName("p");
    for (let i = 0; i < items.length; i++) {
        items[i].style.display = "";
    }
}
// Last accessed: 18.05.2025 15:45 https://www.w3schools.com/howto/howto_js_filter_dropdown.asp