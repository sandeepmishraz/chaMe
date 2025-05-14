// theme-toggle.js

document.addEventListener('DOMContentLoaded', () => {
    const toggle = document.getElementById('darkModeToggle');

    // Restore dark mode from localStorage
    if (localStorage.getItem('darkMode') === 'true') {
        document.body.classList.add('dark-mode');
        if (toggle) toggle.textContent = '☀️ Light Mode';
    }

    // Toggle dark mode
    if (toggle) {
        toggle.addEventListener('click', () => {
            document.body.classList.toggle('dark-mode');
            const isDark = document.body.classList.contains('dark-mode');
            toggle.textContent = isDark ? '☀️ Light Mode' : '🌙 Dark Mode';
            localStorage.setItem('darkMode', isDark);
        });
    }
});
