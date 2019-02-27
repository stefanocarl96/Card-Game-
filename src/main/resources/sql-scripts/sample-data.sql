CREATE TABLE games (
    id INT AUTO_INCREMENT PRIMARY KEY,
    state VARCHAR(50) NOT NULL,
    players VARCHAR(100),
);

insert into games (state, players) values
('PLAYING', 'homer,bart'),
    ('OPEN', 'lisa'),
    ('OPEN', NULL);
