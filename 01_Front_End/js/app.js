

(function () {
    'use strict';

    // ── Auth guard ─────────────────────────────────────────────────────────────
    function authGuard() {
        const token    = localStorage.getItem('token');
        const isLogged = localStorage.getItem('isLogged');
        if (!token || isLogged !== 'true') {
            window.location.replace('../index.html');
        }
    }

    // ── Authenticated fetch ───────────────────────────────────────────────────
    async function apiFetch(url, options = {}) {
        const token = localStorage.getItem('token');
        const headers = Object.assign({}, options.headers || {});

        if (token) {
            headers['Authorization'] = 'Bearer ' + token;
        }
        if (options.body && !(options.body instanceof FormData)) {
            headers['Content-Type'] = headers['Content-Type'] || 'application/json';
        }

        const response = await fetch(url, { ...options, headers });

        // Token expired / invalid → logout
        if (response.status === 401 || response.status === 403) {
            logout();
            throw new Error('Session expired. Please log in again.');
        }

        return response;
    }

    // ── Logout ────────────────────────────────────────────────────────────────
    function logout() {
        localStorage.clear()
        localStorage.removeItem('token');
        localStorage.removeItem('username');
        localStorage.removeItem('isLogged');
        window.location.replace('../index.html');
    }

    // ── Expose globally ───────────────────────────────────────────────────────
    window.authGuard  = authGuard;
    window.apiFetch   = apiFetch;
    window.logout     = logout;

    // ── Fill username in sidebar ──────────────────────────────────────────────
    document.addEventListener('DOMContentLoaded', function () {
        const uname = localStorage.getItem('username');
        const el    = document.getElementById('sidebarUsername');
        if (el && uname) el.textContent = uname;
    });
})();