function setVulnerabilitySDE() {
    let checkBox = document.getElementById("vulnerabilityCheckbox");
    vulnerabilityEnabledSDE = checkBox.checked ? true : false;

    let form = document.getElementById("loginForm");
    form.action = vulnerabilityEnabledSDE ? "/api/unsafeLogin" : "/api/safeLogin";
}

async function generateSHA512(input) {
    const encoder = new TextEncoder();
    const data = encoder.encode(input);

    const hashBuffer = await crypto.subtle.digest('SHA-512', data);
    const hashArray = Array.from(new Uint8Array(hashBuffer));
    const hashHex = hashArray.map(byte => byte.toString(16).padStart(2, '0')).join('');

    return hashHex;
}

function setRequestData() {
    if (vulnerabilityEnabledSDE) {
        let username = document.getElementById("username").value;
        let passInput = document.getElementById("password");
        const pass = passInput.value;
        alert("POST: " + username + ", " + pass);
    } else {
        let username = document.getElementById("username").value;
        let passInput = document.getElementById("password");
        const pass = passInput.value;
        generateSHA512(pass).then(hash => {
            alert("POST: " + username + ", " + hash);
            passInput.value = hash;
            document.getElementById("loginForm").submit();
        });
    }
}