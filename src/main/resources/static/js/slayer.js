function addSaveToast(message, successfull) {
    var container = document.getElementById("saveToast-container")
    if(container != null) {
        container.remove()
    }
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

function changeRules(object) {

    var stats = document.getElementById("stats");
    if (stats != null) {
        stats.remove()
    }

    if (object.value !== null && object.value !== 'null') {
        $.ajax({
            url: "/load/dataByRegelwerk", // Endpoint, der das gerenderte Fragment zurückgibt
            method: 'GET', // POST anstelle von GET
            data: {
                regelwerk: object.value
            },
            success: function (response) {
                // Füge die gerenderte HTML-Antwort hinzu
                $("#statsContent").append(response);
            },
            error: function (xhr, status, error) {
                console.error("Error loading fragment: ", error);
            }
        });
    }
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

/*<![CDATA[*/
const countOfFixedDisciplines = 3;
const maxDisciplines = /*[[${maxDisciplines}]]*/ null;
let currentCountOfDisciples = 3;


function changeClanData() {

    let nameOfId = "clanDisciplineFixed";
    var choosenClan = document.getElementById("clanInput").value;
    if (choosenClan !== "null") {
        var clans = /*[[${clans['vampire1.0']}]]*/ null
        var clan = null;
        let count = 0;

        clans.forEach(function (clanEntry) {
                if (clanEntry.id === Number(choosenClan)) {
                    clan = clanEntry
                }
            }
        )
        var discplines = clan.disciplines;

        while (count <= countOfFixedDisciplines) {
            document.getElementById(nameOfId.concat(count)).value = discplines[count];
            count++;
        }

        document.getElementById("clanDisciplineFixed1Input").value = discplines[0];
        console.log(clan)
    } else {
        while (count <= countOfFixedDisciplines) {
            document.getElementById(nameOfId.concat(count)).value = null;
            count++;
        }
    }
}


//Buttons
const btnDisciplineRemove = document.getElementById("subButtonDiscipline")
const btnDisciolineAdd = document.getElementById("addButtonDiscipline")

$(document).ready(function onready() {
    if(btnDisciplineRemove != null) {
    if (currentCountOfDisciples === countOfFixedDisciplines) {
        btnDisciplineRemove.style.display = "none"
    }
    }
});

function removeDisciplie() {
    if (currentCountOfDisciples > countOfFixedDisciplines) {
        // Remove HTML for the Last Skill
        var lastDiscipline = document.getElementById("discipline" + currentCountOfDisciples);
        currentCountOfDisciples = currentCountOfDisciples - 1;
        $(lastDiscipline).remove();

        if (currentCountOfDisciples > countOfFixedDisciplines) {
            changeButtonVisibility(btnDisciolineAdd, 'block');
        }

        if (currentCountOfDisciples === countOfFixedDisciplines) {
            changeButtonVisibility(btnDisciplineRemove, 'none');
            changeButtonVisibility(btnDisciolineAdd, 'block');
        }
    }
}

function changeButtonVisibility(button, visibility) {
    button.style.display = visibility;
}

function addAdditionalDiscipline() {
    if (currentCountOfDisciples < maxDisciplines) {
        currentCountOfDisciples = currentCountOfDisciples + 1;

        const url = '/loadAdditional/disciples/' + currentCountOfDisciples;
        $.ajax({
            url: url, // Endpoint returning the rendered fragment
            method: 'GET',
            success: function (response) {
                // Prepend the rendered HTML
                console.log(response)
                $("#additionalDisciplnes").append(response);
            },
            error: function (xhr, status, error) {
                console.error("Error loading fragment: ", error);
            }
        });

        if (currentCountOfDisciples > countOfFixedDisciplines) {
            changeButtonVisibility(btnDisciolineAdd, 'block');
            changeButtonVisibility(btnDisciplineRemove, 'block');
        }

        if (currentCountOfDisciples === maxDisciplines) {
            changeButtonVisibility(btnDisciolineAdd, 'none');
        }
    }

}

const hpBar = document.getElementById("hpBar")

function update(dmg) {
    hpBar.value = (hpBar.value - dmg)
}

/*]]>*/