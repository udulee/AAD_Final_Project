async function loadPendingPolicies() {
    const token = localStorage.getItem('token');
    const response = await fetch('/api/admin/policies/pending', {
        headers: { 'Authorization': 'Bearer ' + token }
    });
    const policies = await response.json();

    let html = '';
    policies.forEach(p => {
        html += `<tr>
            <td>${p.customerName}</td>
            <td>${p.vehicleNo}</td>
            <td><button onclick="approve(${p.id})">Approve</button></td>
        </tr>`;
    });
    document.getElementById('policyData').innerHTML = html;
}

async function generateReport(type) {
    // PDF or JSON report generation logic [cite: 49]
    window.open(`/api/admin/reports?type=${type}`, '_blank');
}