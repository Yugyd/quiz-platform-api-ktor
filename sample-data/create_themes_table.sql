CREATE TABLE themes
(
    id                      SERIAL PRIMARY KEY,
    alias_code              TEXT    NOT NULL,
    name                    TEXT    NOT NULL,
    description             TEXT    NOT NULL,
    alternative_description TEXT,
    icon_url                TEXT,
    is_final                BOOLEAN NOT NULL DEFAULT FALSE,
    parent_id               INTEGER,
    content                 TEXT,
    FOREIGN KEY (parent_id) REFERENCES themes (id) ON DELETE CASCADE
);
