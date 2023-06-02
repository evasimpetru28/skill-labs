function getOnclick(skillName, criteria, starsNumber, studentId) {
    return function update(event) {
        $.ajax({
            type : "POST",
            url : "/reevaluate/" + skillName + "/" + criteria + "/" + starsNumber + "/" + studentId,
            dataType: "html",
            contentType: 'application/json',
            mimeType: 'application/json',
            timeout : 100000,
            success : function() {
                location.reload();
            },
            error : function(e) {
                console.log("ERROR: ", e);
            }
        });
    };
}

function getStars(callingElementId, studentId) {
    let idLength = callingElementId.length;
    let starsNumber = parseInt(callingElementId.substring(idLength - 1, idLength));
    let nameAndCriteria;
    let nameLength;

    if (callingElementId.substring(idLength - 2, idLength) === '10') {
        starsNumber = parseInt(callingElementId.substring(idLength - 2, idLength));
        nameLength = idLength - 3;
        nameAndCriteria = callingElementId.substring(0, idLength - 2);
    } else {
        nameLength = idLength - 2;
        nameAndCriteria = callingElementId.substring(0, idLength - 1);
    }
    if (isNaN(starsNumber)) {
        starsNumber = 0;
        nameLength = idLength;
        nameAndCriteria = callingElementId + "-";
    }

    let skillName = callingElementId.substring(0, nameLength - 4);
    let criteria = callingElementId.substring(nameLength - 3, nameLength);
    let starsDiv = document.getElementById(callingElementId);

    for (let i = 1; i <= starsNumber; i++) {
        let iTag = document.createElement("i");
        iTag.className = "fa-solid fa-star fa-fw icon-pointer";
        iTag.id = nameAndCriteria + i;
        iTag.style = "color: #f5d836;";
        iTag.onclick = getOnclick(skillName, criteria, i, studentId);
        starsDiv.appendChild(iTag);
        starsDiv.append('\n');
    }

    for (let i = starsNumber + 1; i <= 10; i++) {
        let iTag = document.createElement("i");
        iTag.className = "fa-regular fa-star fa-fw icon-hover icon-pointer";
        iTag.id = nameAndCriteria + i;
        iTag.onclick = getOnclick(skillName, criteria, i, studentId);
        starsDiv.appendChild(iTag);
        starsDiv.append('\n');
    }

    let trashCanDiv = document.getElementById(callingElementId.substring(0, nameLength) + "-trash-can");
    let trashIcon = document.createElement("i");
    trashIcon.className = "fa-regular fa-trash-can icon-pointer";
    trashIcon.style = "color: #b42727;";
    trashCanDiv.appendChild(trashIcon);
}

function loadStars(studentId) {
    let starsDivs = document.getElementsByClassName('stars-div');
    for (let i = 0; i < starsDivs.length; i++) {
        let itemId = starsDivs.item(i).id;
        getStars(itemId, studentId);
    }
}

function deleteEvaluation(callingElement, studentId){
    let id = callingElement.id;
    let skillName = id.substring(0, id.length - 14);
    let criteria = id.substring(id.length - 13, id.length - 10);

    $.ajax({
        type : "POST",
        url : "/delete-evaluation/" + skillName + "/" + criteria + "/" + studentId,
        dataType: "html",
        contentType: 'application/json',
        mimeType: 'application/json',
        timeout : 100000,
        success : function() {

            location.reload();
        },
        error : function(e) {
            console.log("ERROR: ", e);
        }
    });
}
