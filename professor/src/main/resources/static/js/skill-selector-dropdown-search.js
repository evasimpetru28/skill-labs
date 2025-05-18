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
    if (selectedSkill.textContent === 'No skill selected') {
        selectedSkill.classList.add('no-skill');
    }
});

function selectSkill(skill) {
    const selectedSkill = document.getElementById('selectedSkill');
    selectedSkill.textContent = skill;
    selectedSkill.classList.remove('no-skill');  // Remove the no-skill class when a skill is selected
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