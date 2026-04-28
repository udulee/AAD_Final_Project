async function registerVehicle() {
    // LocalStorage eken token eka gannawa
    const token = localStorage.getItem('token');

    if (!token) {
        alert("Session expired. Please login again.");
        window.location.href = 'index.html';
        return;
    }

    // Form eken data gannawa
    const vehicleData = {
        vehicleNo: document.getElementById('vehicleNo').value,
        vehicleType: document.getElementById('vehicleType').value
    };

    try {
        //Backend API ekata POST request ekak yawannawa
        const response = await fetch('/api/customer/vehicles', {
            method: 'POST',
            headers: {
                'Authorization': 'Bearer ' + token, // JWT Token eka danna ona [cite: 4]
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(vehicleData)
        });

        if (response.ok) {
            alert("Vehicle Registered Successfully!");
            document.getElementById('vehicleForm').reset(); // Form eka clear karanawa
        } else {
            // Backend eken dena error eka ganna (ex: Global Exception Handling) [cite: 21, 49]
            const errorData = await response.json();
            alert("Error: " + (errorData.message || "Failed to register vehicle"));
            console.error("Backend Error:", errorData);
        }
    } catch (error) {
        // Network errors hari server down nam meka weda karanawa
        console.error("Fetch Error:", error);
        alert("Something went wrong. Please check your connection.");
    }
}