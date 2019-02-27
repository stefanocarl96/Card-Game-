
class GameList extends React.Component {

    constructor(props) {
        super(props);

        this.state = {
            games: [],
            loadingText: "Loading games"
        };
    }

    componentDidMount() {

        axios.get("/api/games/");
         .then(response => {

        const games = response.data;
        console.log(games);

        this.setState({
            games: games,
            loadingText: ""
        });

         });

         addgame() {
             console.log("Adding a game");

             axios.post("/api/games").then(response => {
                 //Do something after the game is created
                 console.log("Game added");
             })
        }

    }

    render() {
        return (
            <p>
                <Title text={this.props.title} />
                <p>{this.state.loadingText}</p>
                <p><button onClick={() }>Add game</button></p>
                <ul>
                {this.state.games.map(x => (
                        <li> key={game<a href={"/games/" + game.id}>Game {game.id}}</a> is {game.state}</li>
                    ))}
                </ul>
            </div>
        );
    }
}