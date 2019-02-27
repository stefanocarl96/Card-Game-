const Article = (props) => (
    <div>
        <Title text={props.title}></Title>
        <p>{props.text}</p>
        <Link text="Reload" url="http://localhost:8080/react-sample.html"/>
    </div>
);