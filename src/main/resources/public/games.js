console.log("Games js loaded");

const gamesPromise = fetch("http://localhost:8080/api/games");

gamesPromise
    .then(x => x.json()) // converts the response to JSON
    .then(function(games) {

        // This function will be called when the data comes
        // At this point, games contains the data that the end-point sends (the list of games)

        let gamesContainer = document.getElementById("games-container");

        for (let game of games) {

            const p = document.createElement("p");
            p.textContent = `Game ${game.id} is ${game.state}`;
            gamesContainer.appendChild(p);
        }
    });



