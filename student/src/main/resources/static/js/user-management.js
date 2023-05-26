$('#addUser').on('hidden.bs.modal', function () {
    $(this).find('form').trigger('reset');

    var select2 = document.getElementById("domain");
    while (select2.firstChild) {
        select2.removeChild(select2.firstChild);
    }
    var option0 = document.createElement("option");
    option0.value = "";
    option0.text = "Select a domain";
    option0.style = "display: none";
    select2.appendChild(option0);
})


function yearSelectOptions(select1, select2, select3) {
    select2.onchange = function () {
        while (select3.firstChild) {
            select3.removeChild(select3.firstChild);
        }

        var option0 = document.createElement("option");
        option0.value = "";
        option0.text = "Select year";
        option0.style = "display: none";
        select3.appendChild(option0);
        var option1 = document.createElement("option");
        option1.value = 1;
        option1.text = option1.value;
        select3.appendChild(option1);
        var option2 = document.createElement("option");
        option2.value = 2;
        option2.text = option2.value;
        select3.appendChild(option2);

        if (select1.value == "Licenta") {
            var option3 = document.createElement("option");
            option3.value = 3;
            option3.text = option3.value;
            select3.appendChild(option3);

            if (select2.value == "CTI") {
                var option4 = document.createElement("option");
                option4.value = 4;
                option4.text = option4.value;
                select3.appendChild(option4);
            }

        }
    }
}

function domainSelectOptions() {
    var select1 = document.getElementById("program");
    var select2 = document.getElementById("domain");
    var select3 = document.getElementById("year");

    select1.onchange = function () {
        while (select2.firstChild) {
            select2.removeChild(select2.firstChild);
        }
        let selectedIndex = select1.selectedIndex;
        if (selectedIndex == 0) {
            return;
        }

        while (select3.firstChild) {
            select3.removeChild(select3.firstChild);
        }

        var option0 = document.createElement("option");
        option0.value = "";
        option0.text = "Select year";
        option0.style = "display: none";
        select3.appendChild(option0);

        if (selectedIndex == 1) {
            var option0 = document.createElement("option");
            option0.value = "";
            option0.text = "Select a domain";
            option0.style = "display: none";
            select2.appendChild(option0);
            var option1 = document.createElement("option");
            option1.value = "Matematica";
            option1.text = option1.value;
            select2.appendChild(option1);
            var option2 = document.createElement("option");
            option2.value = "Informatica ID";
            option2.text = option2.value;
            select2.appendChild(option2);
            var option3 = document.createElement("option");
            option3.value = "Informatica IF";
            option3.text = option3.value;
            select2.appendChild(option3);
            var option4 = document.createElement("option");
            option4.value = "CTI";
            option4.text = option4.value;
            select2.appendChild(option4);
        } else {
            if (selectedIndex == 2) {
                var option0 = document.createElement("option");
                option0.value = "";
                option0.text = "Select a domain";
                option0.style = "display: none";
                select2.appendChild(option0);
                var option1 = document.createElement("option");
                option1.value = "Matematica IF";
                option1.text = option1.value;
                select2.appendChild(option1);
                var option2 = document.createElement("option");
                option2.value = "Informatica IF";
                option2.text = option2.value;
                select2.appendChild(option2);
                var option3 = document.createElement("option");
                option3.value = "Informatica IFR";
                option3.text = option3.value;
                select2.appendChild(option3);
            } else {
                if (selectedIndex == 3) {
                    var option0 = document.createElement("option");
                    option0.value = "";
                    option0.text = "Select a domain";
                    option0.style = "display: none";
                    select2.appendChild(option0);
                    var option1 = document.createElement("option");
                    option1.value = "Matematica";
                    option1.text = option1.value;
                    select2.appendChild(option1);
                    var option2 = document.createElement("option");
                    option2.value = "Informatica";
                    option2.text = option2.value;
                    select2.appendChild(option2);
                }
            }
        }
    }
    yearSelectOptions(select1, select2, select3);
}

function getSelectorIds(callingElement) {
    let callingElementId = callingElement.id;
    let index;

    if (callingElementId.substring(0, 4) == "year") {
        index = callingElementId.substring(4);
    } else {
        if (callingElementId.substring(0, 6) == "domain") {
            index = callingElementId.substring(6);
        } else {
            index = callingElementId.substring(7);
        }
    }
    let programSelectorId = "program" + index;
    let domainSelectorId = "domain" + index;
    let yearSelectorId = "year" + index;

    return {programSelectorId, domainSelectorId, yearSelectorId};
}

function getProgramOptions(callingElement) {
    let {programSelectorId, domainSelectorId, yearSelectorId} = getSelectorIds(callingElement);
    let select1 = document.getElementById(programSelectorId);
    let select2 = document.getElementById(domainSelectorId);
    let select3 = document.getElementById(yearSelectorId);

    while (select2.firstChild) {
        select2.removeChild(select2.firstChild);
    }

    while (select3.firstChild) {
        select3.removeChild(select3.firstChild);
    }

    var option0 = document.createElement("option");
    option0.value = "";
    option0.text = "Select year";
    option0.style = "display: none";
    select3.appendChild(option0);

    var option0 = document.createElement("option");
    option0.value = "";
    option0.text = "Select a domain";
    option0.style = "display: none";
    select2.appendChild(option0);

    if (select1.value == "Licenta") {
        var option1 = document.createElement("option");
        option1.value = "Matematica";
        option1.text = option1.value;
        select2.appendChild(option1);
        var option2 = document.createElement("option");
        option2.value = "Informatica ID";
        option2.text = option2.value;
        select2.appendChild(option2);
        var option3 = document.createElement("option");
        option3.value = "Informatica IF";
        option3.text = option3.value;
        select2.appendChild(option3);
        var option4 = document.createElement("option");
        option4.value = "CTI";
        option4.text = option4.value;
        select2.appendChild(option4);
    } else {
        if (select1.value == "Master") {
            var option1 = document.createElement("option");
            option1.value = "Matematica IF";
            option1.text = option1.value;
            select2.appendChild(option1);
            var option2 = document.createElement("option");
            option2.value = "Informatica IF";
            option2.text = option2.value;
            select2.appendChild(option2);
            var option3 = document.createElement("option");
            option3.value = "Informatica IFR";
            option3.text = option3.value;
            select2.appendChild(option3);
        } else {
            if (select1.value == "Doctorat") {
                var option1 = document.createElement("option");
                option1.value = "Matematica";
                option1.text = option1.value;
                select2.appendChild(option1);
                var option2 = document.createElement("option");
                option2.value = "Informatica";
                option2.text = option2.value;
                select2.appendChild(option2);
            }
        }
    }
    yearSelectOptions(select1, select2, select3);
}

function getDomainOptions(callingElement) {
    let {programSelectorId, domainSelectorId, yearSelectorId} = getSelectorIds(callingElement);
    let programSelector = document.getElementById(programSelectorId);
    let domainSelector = document.getElementById(domainSelectorId);
    let yearSelector = document.getElementById(yearSelectorId);

    let defaultValue = domainSelector.value;
    let defaultText = domainSelector.value;

    console.log("default value:", defaultValue);
    console.log("default text:", defaultText);

    if (defaultValue == "") {
        defaultText = "Select a domain";
    }

    while (domainSelector.firstChild) {
        domainSelector.removeChild(domainSelector.firstChild);
    }

    if (programSelector.value == "Licenta") {
        var option0 = document.createElement("option");
        option0.value = defaultValue;
        option0.text = defaultText;
        option0.style = "display: none";
        domainSelector.appendChild(option0);
        var option1 = document.createElement("option");
        option1.value = "Matematica";
        option1.text = option1.value;
        domainSelector.appendChild(option1);
        var option2 = document.createElement("option");
        option2.value = "Informatica ID";
        option2.text = option2.value;
        domainSelector.appendChild(option2);
        var option3 = document.createElement("option");
        option3.value = "Informatica IF";
        option3.text = option3.value;
        domainSelector.appendChild(option3);
        var option4 = document.createElement("option");
        option4.value = "CTI";
        option4.text = option4.value;
        domainSelector.appendChild(option4);
    } else {
        if (programSelector.value == "Master") {
            var option0 = document.createElement("option");
            option0.value = defaultValue;
            option0.text = defaultValue;
            option0.style = "display: none";
            domainSelector.appendChild(option0);
            var option1 = document.createElement("option");
            option1.value = "Matematica IF";
            option1.text = option1.value;
            domainSelector.appendChild(option1);
            var option2 = document.createElement("option");
            option2.value = "Informatica IF";
            option2.text = option2.value;
            domainSelector.appendChild(option2);
            var option3 = document.createElement("option");
            option3.value = "Informatica IFR";
            option3.text = option3.value;
            domainSelector.appendChild(option3);
        } else {
            if (programSelector.value == "Doctorat") {
                var option0 = document.createElement("option");
                option0.value = defaultValue;
                option0.text = defaultValue;
                option0.style = "display: none";
                domainSelector.appendChild(option0);
                var option1 = document.createElement("option");
                option1.value = "Matematica";
                option1.text = option1.value;
                domainSelector.appendChild(option1);
                var option2 = document.createElement("option");
                option2.value = "Informatica";
                option2.text = option2.value;
                domainSelector.appendChild(option2);
            }
        }
    }

    yearSelectOptions(programSelector, domainSelector, yearSelector);
}

function getYearOptions(callingElement) {
    let {programSelectorId, domainSelectorId, yearSelectorId} = getSelectorIds(callingElement);
    let select1 = document.getElementById(programSelectorId);
    let select2 = document.getElementById(domainSelectorId);
    let select3 = document.getElementById(yearSelectorId);

    let defaultValue = select3.value;
    let defaultText = select3.value;

    console.log("year value: ", defaultValue);
    console.log("year text: ", defaultText);

    if (defaultValue == "") {
        defaultValue = "";
        defaultText = "Select year";
    }

    while (select3.firstChild) {
        select3.removeChild(select3.firstChild);
    }

    var option0 = document.createElement("option");
    option0.value = defaultValue;
    option0.text = defaultText;
    option0.style = "display: none";
    select3.appendChild(option0);
    var option1 = document.createElement("option");
    option1.value = 1;
    option1.text = option1.value;
    select3.appendChild(option1);
    var option2 = document.createElement("option");
    option2.value = 2;
    option2.text = option2.value;
    select3.appendChild(option2);

    if (select1.value == "Licenta") { // Licenta
        var option3 = document.createElement("option");
        option3.value = 3;
        option3.text = option3.value;
        select3.appendChild(option3);

        if (select2.value == "CTI") {
            var option4 = document.createElement("option");
            option4.value = 4;
            option4.text = option4.value;
            select3.appendChild(option4);
        }

    }

}
