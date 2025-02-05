document.addEventListener("DOMContentLoaded", function () {
    const fileInput = document.getElementById("picture");
    const fileNameDisplay = document.getElementById("file-name");

    if (fileInput) {
        fileInput.addEventListener("change", function (event) {
            let fileName = event.target.files[0]?.name || "No file chosen";
            fileNameDisplay.textContent = `Selected: ${fileName}`;
        });
    }
});
