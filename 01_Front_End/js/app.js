const API_URL = "http://localhost:8080/api/v1";

// පොදු Headers (JWT Token ඇතුළුව)
const getHeaders = () => ({
    "Authorization": "Bearer " + localStorage.getItem("token"),
    "Content-Type": "application/json"
});

// 1. VEHICLE CRUD (Add, Update, Delete, Reset)
function saveVehicle() {
    const id = $('#vehicleId').val();
    const vehicleData = {
        vehicleNumber: $('#vNumber').val(),
        model: $('#vModel').val(),
        year: $('#vYear').val()
    };

    $.ajax({
        url: id ? `${API_URL}/vehicle/update/${id}` : `${API_URL}/vehicle/register`,
        method: id ? "PUT" : "POST",
        headers: getHeaders(),
        data: JSON.stringify(vehicleData),
        success: (res) => {
            alert("Vehicle Data Saved Successfully!");
            resetForm('vehicleForm');
            loadVehicles();
        }
    });
}

function deleteVehicle(id) {
    if(confirm("Are you sure you want to delete this vehicle?")) {
        $.ajax({
            url: `${API_URL}/vehicle/delete/${id}`,
            method: "DELETE",
            headers: getHeaders(),
            success: () => loadVehicles()
        });
    }
}

// 2. POLICY APPROVAL (Admin)
function updatePolicyStatus(id, status) {
    $.ajax({
        url: `${API_URL}/admin/policy/${id}/status`,
        method: "PATCH",
        headers: getHeaders(),
        data: JSON.stringify({ status: status }),
        success: () => {
            alert(`Policy ${status} successfully!`);
            loadAdminPolicies();
        }
    });
}

// 3. RESET FUNCTION
function resetForm(formId) {
    $(`#${formId}`)[0].reset();
    $(`#${formId} input[type='hidden']`).val('');
}

// 4. REPORT GENERATION (PDF)
function generateReport() {
    window.open(`${API_URL}/reports/summary?token=${localStorage.getItem("token")}`, '_blank');
}

// පිටුව Load වන විට දත්ත කැඳවීම
$(document).ready(function() {
    const path = window.location.pathname;
    if(path.includes("vehicles.html")) loadVehicles();
    if(path.includes("admin-policies.html")) loadAdminPolicies();
    if(path.includes("claims.html")) loadClaims();
});