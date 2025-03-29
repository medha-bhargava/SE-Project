function checkErrors() {
    const language = document.getElementById("language").value;
    const code = document.getElementById("insertCode").value;
    const errorOutput = document.getElementById("error");
    
    if (language === "Select" && code.trim() === "") {
        errorOutput.innerText = "Please select a language and enter some code.";
        return;
    }
    if (language === "Select") {
        errorOutput.innerText = "Please select a language.";
        return;
    }
    if (code.trim() === "") {
        errorOutput.innerText = "Please enter some code.";
        return;
    }
    
    errorOutput.innerText = "Checking for errors...";
    
    fetch("http://localhost:8081/api/detect-error", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({ language, code })
    })
    .then(response => response.json())
    .then(data => {
        if (data.errors.length > 0) {
            errorOutput.innerText = "Errors found:\n" + data.errors.join("\n");
        } else {
            errorOutput.innerText = "No errors found!";
        }
    })
    .catch(error => {
        errorOutput.innerText = "Error checking failed. Try again later.";
        console.error("Error:", error);
    });
}