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

function addStatsContent(mode, character) {
    return $.ajax({
        url: "/stats", // Endpoint, der das gerenderte Fragment zurückgibt
        method: 'GET', // POST anstelle von GET
        data: {
            mode: mode,
            character: character
        },
        success: function (response) {
            // Füge die gerenderte HTML-Antwort hinzu
            console.log(response);
            $("#body").append(response);
        },
        error: function (xhr, status, error) {
            console.error("Error loading fragment: ", error);
        }
    });
}

$.on('change',"#regelwerke",function () {
  console.log("End")
});
$("#regelwerke").ready(function() {
    var selectedValue = $('#regelwerke').val();
    console.log(selectedValue);
});
