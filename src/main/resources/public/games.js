console.log("Games js loaded");


const gamePromise = axios.get("/api/games/" + 1);

gamePromise.then(function(response) {

    const games = response.data;
    displayGame(game);
    })
    .catch(function(error) {
        console.log("There waas an error!", error);
           dispalyError(error)

    });

function displayGame(game); )

        let gamesContainer = document.getElementById("games-container");


        const p = document.createElement("p");
        p.textContent = `Game ${game.id} is ${game.state}`;
        gamesContainer.appendChild(p);
        }
     );


function displayError(error); )

let gamesContainer = document.getElementById("games-container");


const p = document.createElement("p");
p.textContent = `Game ${game.id} is ${game.state}`;
gamesContainer.appendChild(p);

);
