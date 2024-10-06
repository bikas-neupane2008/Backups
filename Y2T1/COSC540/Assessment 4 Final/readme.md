Secure Communication System

This secure communication system consists of a server and client implementation using Python. It provides user authentication, message encryption, and read receipts. Below are the steps to set up and run the server and client, along with a detailed description of the files and their functionalities.

Prerequisites

Python 3.x
sqlite3 module
SSL certificate (server.crt) and key (server.key)
(Here, server.crt is already generated for testing purpose based on the common name as "localhost")

Files

server.py: The server-side script that handles user authentication, message storage, and communication.

client.py: The client-side script that allows users to interact with the server, send messages, and read notifications.

startServer.sh: A shell script to start the server.

startClient.sh: A shell script to start the client.

(Note : Please provide the read, write and execute permission for the startServer.sh and startClient.sh files if not provided.)
In bash, you may do so by entering the code
'''
chmod +rwx startServer.sh startClient.sh
'''

Setup

Generate SSL Certificates
Before running the server and client, you need to generate SSL certificates. You can create a self-signed certificate using OpenSSL with the following commands:
'''
openssl req -newkey rsa:2048 -nodes -keyout server.key -x509 -days 365 -out server.crt
'''
And use your own "Common Name".

Database Initialization

The server.py script automatically initializes an SQLite database (user_credentials.db) with the necessary tables when run for the first time.

Running the Server

To start the server, use the provided startServer.sh script:
'''
./startServer.sh <port>
'''
Replace <port> with the port number you want the server to listen on.

Example:
'''
./startServer.sh 12345
'''
Running the Client

To start the client, use the provided startClient.sh script:
'''
./startClient.sh <host> <port>
'''
Replace <host> with the server's hostname or IP address and <port> with the port number the server is listening on.

Example:
'''
./startClient.sh localhost 12345
'''

Server (server.py) Functionalities

Database Functions

create_db(): Creates the users and messages tables if they do not exist.

add_user(username, password_hash): Adds a new user to the users table.

add_message(sender, receiver, message): Adds a new message to the messages table.

unread_message(username): Fetches unread messages for a user and marks them as read.

read_all_message(username): Fetches all messages for a user.

user_exists(username): Checks if a user exists in the users table.

get_user_password_hash(username): Retrieves the password hash for a user.

set_user_online(username, online): Sets a user's online status.

send_notifications(username): Sends notifications for unread messages.

count_unread_messages(username): Counts unread messages for a user.

count_notifications(username): Counts new notifications for a user.

Client Handling Functions

handle_client(client_socket): Handles communication with a connected client.

handle_username(username): Checks if a username already exists.

handle_login(username, hashed_password): Handles user login.

create_account(username, hashed_password): Creates a new user account.

handle_unread(username): Fetches and sends unread messages to the client.

handle_all_messages(username): Fetches and sends all messages to the client.

handle_notifications(username): Fetches and sends notifications to the client.

handle_compose(sender, receiver, message): Handles message composition and storage.

Server Startup

start_server(port): Starts the secure server on the specified port.

Client (client.py) Functionalities

Command Functions

send_command(server_socket, command): Sends a command to the server.

receive_response(server_socket): Receives a response from the server.

validate_username(username): Validates the format of a username.

handle_login(server_socket): Handles user login and account creation.

display_messages(response_json): Displays messages received from the server.

display_notification(response_json): Displays notifications received from the server.

compose_message(server_socket, username): Composes and sends a message to another user.

Client Startup

start_client(host, port): Connects to the server and starts the client interface.

Usage

Starting the Server
Run the startServer.sh script with the desired port number:
'''
./startServer.sh 12345
'''

Starting the Client
Run the startClient.sh script with the server's hostname and port number:
'''
./startClient.sh localhost 12345
'''

Then, follow the on-screen instructions to log in, create an account, send messages, read messages, and view notifications.

License
This is licensed under © bneupan2@myune.edu.au