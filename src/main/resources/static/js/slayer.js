function addSaveToast(message, successfull) {
    return $.ajax({
        url: "/toast", // Endpoint, der das gerenderte Fragment zurückgibt
        method: 'POST', // POST anstelle von GET
        contentType: 'application/json', // Gebe an, dass die Daten im JSON-Format gesendet werden
        data: JSON.stringify({
            id: "saveToast",
            message: message,
            success: successfull
        }),
        success: function (response) {
            // Füge die gerenderte HTML-Antwort hinzu
            console.log(response);
            $("#toasts").append(response);
        },
        error: function (xhr, status, error) {
            console.error("Error loading fragment: ", error);
        }
    });
}