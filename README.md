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
* DB: Exposed, PostgreSQL
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

### Step 1: Install Ubuntu with Docker and JDK on your server.

### Step 2: Build docker image.

- `./gradlew buildImage` - build docker image.

### Step 3: Connect to your server via SSH.

- `ssh user@your-server-ip`

### Step 4: Upload the docker image to your server using SCP.

- `scp /home/user/jib-image.tar user@your-server-ip:/home/user` - upload the image.

### Step 5: Load the docker image on your server.

- `docker load < /home/user/jib-image.tar` - load the image.
- `docker images` - check the image.

### Step 6: Run the docker image on your server.

- `docker run -e DB_JDBC_URL="REPLACE" -e DB_JDBC_USER="REPLACE" -e DB_JDBC_PASSWORD="REPLACE" -e THEME_PROMPT_FILE="REPLACE" -e TASK_PROMPT_FILE="REPLACE" -p 8080:8080 -t quiz-platform:your_semiversion` -
run the container. Use `-d` instead of `t` to run in detached mode.
- `docker ps` - check the container.

### Step 7: Configure Nginx as a Reverse Proxy

#### Step 7.1: Install Nginx

- `sudo apt update` - update apt.
- `sudo apt install nginx -y` - install Nginx.

#### Step 7.2: Configure Nginx

[Read Nginx documentation](https://nginx.org/en/docs/beginners_guide.html)

Example commands:

- `sudo nano /etc/nginx/sites-available/yourdomain.com`

#### Step 7.3: Install and configure firewall on Ubuntu using UFW

[Read UFW documentation](https://help.ubuntu.com/community/UFW).

#### Step 7.4: Start and enable Nginx on Ubuntu

[Read Nginx documentation](https://nginx.org/en/docs/beginners_guide.html)

Example commands:

- `systemctl start nginx`
- `systemctl enable nginx`
- `systemctl status nginx`
- `ufw allow 'Nginx HTTP'` - if you haven't done it before
- `ufw status` - if you haven't done it before
- `systemctl reload nginx`

### Step 8: Start and fill the database based on PostgreSQL

[Read special section](#database-setup-for-development-macos).

# Update docker image

### Step 1: Connect to your server via SSH.

- `ssh user@your-server-ip`

### Step 2: Upload the docker image to your server using SCP.

- `scp /home/user/jib-image.tar user@your-server-ip:/home/user` - upload the image.

### Step 3: Load the docker image on your server.

- `docker load < /home/user/jib-image.tar` - load the image.
- `docker images` - check the image.

### Step 4: Stop and Remove the Old Container (if running)

- `docker ps`
- `docker stop <container_name_or_id>` - `container_name_or_id` is the name or ID of the running container.
- `docker rm <container_name_or_id>` - `container_name_or_id` is the name or ID of the stopped container.

### Step 6: Run the docker image on your server.

- `docker run -e DB_JDBC_URL="REPLACE" -e DB_JDBC_USER="REPLACE" -e DB_JDBC_PASSWORD="REPLACE" -e THEME_PROMPT_FILE="REPLACE" -e TASK_PROMPT_FILE="REPLACE" -p 8080:8080 -t quiz-platform:your_semiversion` -
run the container. Use `-d` instead of `t` to run in detached mode.
- `docker ps` - check the container.

### Step 7: Clean up old images (Optional)

- `docker images` - check the images.
- `docker rmi <image_name_or_id>` - `image_name_or_id` is the name or ID of the old image you want to remove.

# Database setup for development (macOS)

### Step 1: Install and start PostgreSQL

- `brew install postgresql`
- `brew services start postgresql`

### Step 2: Set up PostgreSQL

- `psql postgres` - Switch to the PostgreSQL user
- `CREATE DATABASE themes;` - Create a new database
- `CREATE USER postgres WITH PASSWORD 'password';` - Create a new user
- `GRANT ALL PRIVILEGES ON DATABASE themes TO postgres;` - Grant privileges to the user
- `\q` - Exit the PostgreSQL terminal

### Step 3: Configure PostgreSQL in IntelliJ IDEA Ultimate

- Open the Database window.
- Click the + sign and choose Data Source → PostgreSQL.
- In the Data Source Properties dialog, specify the data source name and the database connection settings.

### Step 4: Create tables

[Run Create the themes Table](sample-data/create_themes_table.sql)

[Insert Sample Data](sample-data/generate_sample_themes.sql)

### Step 5: Stop PostgreSQL after use

- `brew services stop postgresql`

# Database setup for production (Ubuntu)

### Step 1: Install and configure PostgreSQL on Ubuntu

[Read PostgreSQL documentation](https://documentation.ubuntu.com/server/how-to/databases/install-postgresql/index.html)

[Read Database setup for development](#database-setup-for-development-macos)

### Step 2: Create tables

[Run Create the themes Table](sample-data/create_themes_table.sql)

### Step 3: Fill database with real data

#### For example, you can run predefined SQL scripts to fill the database:

[Insert Sample Data](sample-data/generate_sample_themes.sql)

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
