function toggleFavorite(skillId) {
    const btn = event.currentTarget;
    const icon = btn.querySelector('i');
    
    // Toggle the active class
    btn.classList.toggle('active');
    
    // Toggle between regular and solid bookmark
    if (btn.classList.contains('active')) {
        icon.classList.remove('far');
        icon.classList.add('fas');
    } else {
        icon.classList.remove('fas');
        icon.classList.add('far');
    }
    
    // Here you can add an API call to save the favorite status
    // fetch('/api/skills/' + skillId + '/favorite', {
    //     method: 'POST',
    //     body: JSON.stringify({ isFavorite: btn.classList.contains('active') })
    // });
} 