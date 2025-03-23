INSERT INTO themes (alias_code, name, description, alternative_description, icon_url, is_final, parent_id, content)
VALUES ('theme1', 'Example Theme', 'This is an example theme.', 'An alternative description',
        'http://example.com/icon.png', FALSE, NULL, NULL),
       ('theme2', 'Example Theme', 'This is an example theme.', 'An alternative description',
        'http://example.com/icon.png', TRUE, NULL, NULL),
       ('theme3', 'Example Theme', 'This is an example theme.', 'An alternative description',
        'http://example.com/icon.png', TRUE, 1, 'Content');
