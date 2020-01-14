
let apiURL = "localhost";


function getRapportages(URL) {
    // Create a request variable and assign a new XMLHttpRequest object to it.
    let request = new XMLHttpRequest()

    // Open a new connection, using the GET request on the URL endpoint
    request.open('GET', 'http://'+URL+':8080/filmcheques_api/rapportage', true)
    request.onload = function () {
        // Begin accessing JSON data here
        if (this.readyState == 4 && this.status == 200) {
            const data = JSON.parse(this.response)
            populateDropdown(data);
        } else {
            document.getElementById("response").innerHTML = 'Kon API niet vinden!';
        }
    }
    // Send request
    request.send()
}

function getRapportage(URL) {
    const e = document.getElementById("rapportages");
    let request = new XMLHttpRequest();
    request.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200) {
            var data = JSON.parse(this.responseText);
            document.getElementById("response").innerHTML = '';
            CreateTableFromJSON(JSON.parse(data["rapportage"]));
        } else {
            document.getElementById("response").innerHTML = 'Kon API niet vinden!';
        }
    };
    request.open("GET", "http://"+URL+":8080/filmcheques_api/rapportage/" + e.options[e.selectedIndex].value, true);
    request.send();
}

//Functie die de huidige dropdownlist verwijdert en nieuwe toevoegt
function updateRapportages(URL) {
    let request = new XMLHttpRequest();
    request.onreadystatechange = function() {
        if (this.readyState == 4 && this.status == 200) {
            document.getElementById("response").innerHTML = 'Rapportages geupdated!';
            // Begin accessing JSON data here
            const data = JSON.parse(this.response);
            let select = document.getElementById("rapportages");
            for (let i = 0; i <= select.options.length; i++) {
                select.options[i] = null;
                select.options.remove(i);
            }
            populateDropdown(data);
        } else {
            document.getElementById("response").innerHTML = 'Kon API niet vinden!';
        }
    };
    request.open("GET", "http://"+URL+":8080/filmcheques_api/rapportage/update", true);
    request.send();
}


function populateDropdown(data){
    let select = document.getElementById("rapportages");
    for(let i = 0; i < data['rapportageLijst'].length; i++){
        let el = document.createElement("option");
        el.textContent = data['rapportageLijst'][i];
        el.value = data['rapportageLijst'][i];
        select.appendChild(el);
    }
}

//Deze functie maakt van een JSON rapportage een tabel.
function CreateTableFromJSON(json) {

    //Haalt alvast data eruit om de koppen van tabel te maken.
    let col = [];
    for (let i = 0; i < json.length; i++) {
        for (let key in json[i]) {
            if (col.indexOf(key) === -1) {
                col.push(key);
            }
        }
    }

    //Dit maakt een tabel aan
    let table = document.createElement("table");

    // Maakt koppen van Tabel met data van hierboven

    let tr = table.insertRow(-1);                   // TABLE ROW.

    for (let i = 0; i < col.length; i++) {
        let th = document.createElement("th");      // TABLE HEADER.
        th.innerHTML = col[i];
        tr.appendChild(th);
    }

    // Voegt JSON data toe als rijen bij de tabel.
    for (let i = 0; i < json.length; i++) {
        tr = table.insertRow(-1);
        for (let j = 0; j < col.length; j++) {
            var tabCell = tr.insertCell(-1);
            tabCell.innerHTML = json[i][col[j]];
        }
    }

    //Voeg de nieuwe toegevoegde tabel met JSON data erin toe aan een element/container
    var divContainer = document.getElementById("response");
    divContainer.innerHTML = "";
    divContainer.appendChild(table);
}






