function checkErrors() {
    const language = document.getElementById("language").value;
    const code = document.getElementById("insertCode").value;
    const errorOutput = document.getElementById("error");

    // Reset error display
    errorOutput.innerText = "";
    errorOutput.className = ""; 

    // Validation: Ensure language and code are selected/entered
    if (language === "Select" && code.trim() === "") {
        errorOutput.innerText = "Please select a language and enter some code.";
        errorOutput.className = "error-text";
        return;
    }
    if (language === "Select") {
        errorOutput.innerText = "Please select a language.";
        errorOutput.className = "error-text";
        return;
    }
    if (code.trim() === "") {
        errorOutput.innerText = "Please enter some code.";
        errorOutput.className = "error-text";
        return;
    }

    errorOutput.innerText = "Checking for errors...";
    errorOutput.className = "error-text";

    // Send API Request
    fetch("http://localhost:8081/api/detect-error", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({ language, code })
    })
    .then(response => {
        if (!response.ok) {
            throw new Error(`Server error: ${response.status}`);
        }
        return response.json();
    })
    .then(data => {
        let outputText = "";
        // Handle Syntax Errors
        if (data.hasSyntaxErrors) {
            outputText += "Syntax Errors Detected:\n";
            data.syntaxErrors.forEach(error => {
                outputText += `🔴 Line ${error.lineNumber}: ${error.message}\n`;
            });
        } else {
            outputText += "✅ No Syntax Errors Detected!\n";
        }
        // Handle Logical Errors
        if (data.hasLogicalErrors) {
            outputText += "Logical Errors Detected:\n";
            data.logicalErrors.forEach(error => {
                outputText += `🔴 Line ${error.lineNumber}: ${error.message}\n`;
            });
        } else {
            outputText += "✅ No Logical Errors Detected!";
        }
        errorOutput.innerText = outputText;
        errorOutput.className = (data.hasSyntaxErrors || data.hasLogicalErrors) ? "error-text" : "success-text";
    })
    .catch(error => {
        errorOutput.innerText = "❌ Error checking failed. Try again later.";
        errorOutput.className = "error-text";
        console.error("Error:", error);
    });
}