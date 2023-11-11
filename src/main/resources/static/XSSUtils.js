function setVulnerabilityXSS() {
    let checkBox = document.getElementById("vulnerabilityCheckboxXSS");
    vulnerabilityEnabledXSS = checkBox.checked ? true : false;
    fetchComments(vulnerabilityEnabledXSS);
}

function fetchComments(vulnerabilityEnabledXSS) {
    let commentContainer = document.getElementById("commentContainer")
    if(commentContainer) {
        while (commentContainer.firstChild) {
            commentContainer.removeChild(commentContainer.firstChild);
        }
    }
    fetch('http://localhost:8080/api/comments').then(data => data.json())
        .then(data => {
            console.log(data);
            for(let i = 0; i < data.length; i++) {
                let commentDiv = document.createElement("div");
                commentDiv.className = "commentDiv";

                // SANITIZE INPUT
                let userP = document.createElement("p");
                let contentP = document.createElement("p");
                if(!vulnerabilityEnabledXSS) {
                    userP.innerText = "User: " + sanitizeHTML(data[i]['username']);
                    contentP.innerHTML = "Comment: " + sanitizeHTML(data[i]['content']);
                } else {
                    userP.innerText = "User: " + data[i]['username'];
                    contentP.innerHTML = "Comment: " + data[i]['content'];
                }

                commentDiv.appendChild(userP);
                commentDiv.appendChild(contentP);
                commentContainer.appendChild(commentDiv);
            }
        }).catch();
}

let sanitizeHTML = function (str) {
    return str.replace(/[^\w. ]/gi, function (c) {
        return '&#' + c.charCodeAt(0) + ';';
    });
};