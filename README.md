Quiz-Platform API
=================

Quiz Platform is a Open Source knowledge testing platform for mobile apps.

# Mobile app projects

[Android](https://github.com/Yugyd/quiz-platform/)

[iOS](https://github.com/Yugyd/quiz-platform-ios/)

# Stack

* Framework: Ktor
* Language: Kotlin
* Architecture: Clean
* Threading: Coroutines + Flow
* DI: Koin
* DB: Exposed
* Logging: Logback
* Testing: JUnit, Mockk

# Contributions

[Guide](docs/CONTRIBUTION.md)

# Build

- `./gradlew clean build` - build project.
- `./gradlew run` - run project.

# Run

- `./gradlew buildFatJar` - build fat jar.
- `./gradlew buildImage` - build docker image.

# Deploy docker image

## Step 1: Install Ubuntu with Docker and JDK on your server.

## Step 2: Build docker image.

- `./gradlew buildImage` - build docker image.

## Step 3: Connect to your server via SSH.

- `ssh user@your-server-ip`

## Step 4: Upload the docker image to your server using SCP.

- `scp /home/user/jib-image.tar user@your-server-ip:/home/user` - upload the image.

## Step 5: Load the docker image on your server.

- `docker load < /home/user/jib-image.tar` - load the image.
- `docker images` - check the image.

## Step 6: Run the docker image on your server.

- `docker run -p 8080:8080 -t quiz-platform:your_semiversion` - run the container.
- `docker ps` - check the container.

## Step 7: Configure Nginx as a Reverse Proxy

Read special documentation.

### Install Nginx

- `sudo apt update` - update apt.
- `sudo apt install nginx -y` - install Nginx.

### Configure Nginx

- `sudo nano /etc/nginx/sites-available/yourdomain.com`

### Update apt

- `apt update`

### Install and configure firewall on Ubuntu using UFW

### Install Nginx on Ubuntu

- `apt install nginx`
- `systemctl start nginx`
- `systemctl enable nginx`
- `systemctl status nginx`
- `ufw allow 'Nginx HTTP'` - if you haven't done it before
- `ufw status` - if you haven't done it before
- `systemctl reload nginx`
- `nano /etc/nginx/sites-available/yourdomain.com`

Read special documentation.

## Step 8: Start and fill the database based on PostgreSQL

Read special documentation.

# Database setup for development

## Install and start PostgreSQL on macOS

- `brew install postgresql`
- `brew services start postgresql`

## Set up PostgreSQL Database

- `psql postgres` - Switch to the PostgreSQL user
- `CREATE DATABASE themes;` - Create a new database
- `CREATE USER postgres WITH PASSWORD 'password';` - Create a new user
- `GRANT ALL PRIVILEGES ON DATABASE themes TO postgres;` - Grant privileges to the user
- `\q` - Exit the PostgreSQL terminal

## Configure PostgreSQL in IntelliJ IDEA Ultimate

- Open the Database window.
- Click the + sign and choose Data Source → PostgreSQL.
- In the Data Source Properties dialog, specify the data source name and the database connection settings.

## Create tables

### Create the themes Table

```sql
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
    FOREIGN KEY (parent_id) REFERENCES themes (id) ON DELETE CASCADE
);
```

### Insert Sample Data

```sql
INSERT INTO themes (alias_code, name, description, alternative_description, icon_url, is_final, parent_id)
VALUES ('theme1', 'Example Theme', 'This is an example theme.', 'An alternative description',
        'http://example.com/icon.png', FALSE, NULL),
       ('theme2', 'Example Theme', 'This is an example theme.', 'An alternative description',
        'http://example.com/icon.png', TRUE, 1),
       ('theme3', 'Example Theme', 'This is an example theme.', 'An alternative description',
        'http://example.com/icon.png', TRUE, 2);
```

## Stop PostgreSQL

- `brew services stop postgresql`

# Pre-filled Data

## Step 1: Create a SQL script with your initial data, for example, data.sql

```sql
-- data.sql
INSERT INTO themes (alias_code, name, description, alternative_description, icon_url, is_final, parent_id)
VALUES ('theme1', 'Nature', 'A theme about nature', 'Green and serene', 'http://example.com/nature-icon.png', TRUE,
        NULL),
       ('theme2', 'Technology', 'A theme about technology', 'Innovative and cutting-edge',
        'http://example.com/tech-icon.png', FALSE, NULL),
       ('theme3', 'Space', 'A theme about outer space', 'Mysterious and vast', 'http://example.com/space-icon.png',
        TRUE, 2);
```

## Step 2: Place the SQL Script in the Resources Directory

Place the data.sql script inside your resources folder in your Ktor project.

# Use release configuration file for production

https://ktor.io/docs/server-configuration-file.html#command-line

# License

Copyright 2025 Roman Likhachev

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
