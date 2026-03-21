// Login function
document.getElementById('loginForm')?.addEventListener('submit', async (e) => {
    e.preventDefault();
    const loginData = {
        username: document.getElementById('username').value,
        password: document.getElementById('password').value
    };

    const response = await fetch('/api/auth/login', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(loginData)
    });

    const result = await response.json();
    if (response.ok) {
        localStorage.setItem('token', result.token); // Save JWT [cite: 18]
        localStorage.setItem('role', result.role);   // Admin or Customer [cite: 42]
        window.location.href = result.role === 'ADMIN' ? 'admin.html' : 'customer.html';
    } else {
        alert("Login Failed: " + result.message);
    }
});

function logout() {
    localStorage.clear();
    window.location.href = 'index.html';
}