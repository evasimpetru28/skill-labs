function getStars(callingElementId) {
    let idLength = callingElementId.length;
    let starsNumber = parseInt(callingElementId.substring(idLength - 1, idLength));
    let nameLength;

    if (callingElementId.substring(idLength - 2, idLength) === '10') {
        starsNumber = parseInt(callingElementId.substring(idLength - 2, idLength));
        nameLength = idLength - 3;
    } else {
        nameLength = idLength - 2;
    }

    let starsDiv = document.getElementById(callingElementId);
    for (let i = 1; i <= starsNumber; i++) {
        let iTag = document.createElement("i");
        iTag.className = "fa-solid fa-star fa-fw";
        iTag.style = "color: #f5d836;";
        starsDiv.appendChild(iTag);
        starsDiv.append('\n');
    }

    for (let i = starsNumber + 1; i <= 10; i++) {
        let iTag = document.createElement("i");
        iTag.className = "fa-regular fa-star fa-fw icon-hover";
        starsDiv.appendChild(iTag);
        starsDiv.append('\n');
    }

    let trashCanDiv = document.getElementById(callingElementId.substring(0, nameLength) + "-trash-can");
    let trashIcon = document.createElement("i");
    trashIcon.className = "fa-regular fa-trash-can";
    trashIcon.style = "color: #b42727;";
    trashCanDiv.appendChild(trashIcon);
}

function loadStars() {
    let starsDivs = document.getElementsByClassName('stars-div');
    for (let i = 0; i < starsDivs.length; i++) {
        let itemId = starsDivs.item(i).id;
        getStars(itemId);
    }
}

function deleteKnowledge(callingElement) {
    var tempId = callingElement.id;
    console.log(tempId);

}
