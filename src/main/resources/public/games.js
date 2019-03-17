// ?gameId=1
const urlParams = new URLSearchParams(window.location.search);
const gameId = urlParams.get('gameId');

axios.get("/api/games/" + gameId)
    .then(function (response) {
        const game = response.data;
        displayGame(game);
    })
    .catch(function (error) {
        displayError(error);
    });



function displayGame(game) {
    const container = document.getElementById("container");
    console.log(game);
    let gameTitle = document.createElement("h1");
    gameTitle.textContent = `Game ID: ${game.id}`;
    container.appendChild(gameTitle);
    let gameState = document.createElement("h2");
    gameState.textContent = game.state;
    container.appendChild(gameState);


    let gamePlayers = document.createElement("p");
    gamePlayers.textContent = "Players: " + game.playersName;
    container.appendChild(gamePlayers);

    if (game.state == "OPEN") {

        let input = document.createElement("input");
        input.type = "text";
        input.name = "username";
        container.appendChild(input);

        let button = document.createElement("button");
        button.type = "button";
        button.value = "Join this game";
        button.name = "Join game";
        button.onclick = function (){
            updateGame(input.value);
            //console.log(input.value);
        };
        container.appendChild(button);
    }
}

function displayError(error) {
    let container = document.getElementById("container");
    const p = document.createElement("p");
    p.textContent = "The game could not be loaded: " + error;
    container.appendChild(p);
}

function updateGame(player) {
    axios.put("/api/games/" + gameId + "/join", {gameUser : player})
        .then(function (response) {
            window.location.reload();
            console.log(response);
        })
        .catch(function (error) {
            displayError(error);
        });
}