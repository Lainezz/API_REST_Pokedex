INSERT INTO tipos(`tipo`) VALUES ('electrico');

INSERT INTO pokemon(`is_shiny`, `vida`, `id`, `id_tipo`, `nombre`) VALUES (1,50,1,'electrico','pikachu');

INSERT INTO ataques(`danio_base`, `is_especial`, `id_pokemon`, `id_tipo`, `nombre`) VALUES (25,1,1,'electrico','rayo');