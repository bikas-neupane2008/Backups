Introduction:
This repository contains a simple client-server messaging system implemented in Python. The system allows users to log in with a username, compose messages to other users, read their messages, and exit the system.

File Structure:

server.py: Python script for the server side of the messaging system.
client.py: Python script for the client side of the messaging system.
startServer.sh: Bash script to start the server with a specified port.
startClient.sh: Bash script to start the client with specified host and port.

Usage:
Note: Please provide the required permissions to both startServer.sh and startClient.sh files.
Example: chmod +rwx startServer.sh startClient.sh

Starting the Server:
To start the server, run startServer.sh with the desired port number as an argument.
Example: ./startServer.sh 1234

Starting the Client:
To start the client, run startClient.sh with the server's hostname and port number as arguments.
Example: ./startClient.sh localhost 1234

Server:

The server.py script implements the server side of the messaging system.
It listens for incoming connections from clients and handles client requests.
The server maintains a dictionary to store messages (message_storage) and a set to store client connections (clients).
Clients can send commands to the server, including LOGIN, COMPOSE, READ, and EXIT.
The server handles each command appropriately, such as logging in users, sending messages, reading messages, and handling client exits.

Client:

The client.py script implements the client side of the messaging system.
It connects to the server using the specified hostname and port.
Clients can log in with a username, compose messages, read messages, and exit the system.
User inputs are validated to ensure proper functioning of the system.

Additional Notes:

Usernames cannot contain spaces. If a username with spaces is provided, the system will prompt the user to enter a valid username.
Each user can have up to 100 unread messages. If the limit is exceeded, the system notifies the sender about the message limit.
The system handles client disconnections and server shutdowns gracefully.

Functionality:

Once the client is started, it will prompt user to enter a username. User must provide a username without spaces.

Logging In:After entering a valid username, the client will attempt to log in to the server with the provided username.
If successful, and if the username is new to the server, it displays the message "Login successful with new username. Welcome {username}"
If username is already in the server, it displays the message "Login successful. Welcome {username}. You have {unread_count} unread messages."

Composing Messages:To compose a message, enter COMPOSE when prompted for a command. Then, provide the recipient's username without spaces 
Here, if recipient's username is not in the server also, it will sent the message and then create that username in the server and display "MESSAGE SENT SUCCESSFULLY TO NEW USER."
But if there is already recipient's username in the server, it will sent the message and display "MESSAGE SENT SUCCESSFULLY."
Enter recipient's username.
Enter message content.

Reading Messages:To read messages, enter READ when prompted for a command.
If there is unread messages in the server, the system will display one earliest unread messages for that username with sender and then display the remaining number of unread messages count.
If there is no unread messages in the server, the system will display "No More Unread Messages."

Exiting the System:To exit the system, enter EXIT when prompted for a command.

Validation:

Username Validation:Usernames cannot contain spaces. If you provide a username with spaces, the system will prompt you to enter a valid username.
Error Handling:

Connection Error:If a connection error occurs, the client will display a message indicating the error.

Socket Timeout:If a socket timeout occurs, the client will display a message indicating the error.

Other Errors:For any other errors, the client will display a generic error message.

Signals:

The client script registers a signal handler for the SIGINT signal (Ctrl+C). When you press Ctrl+C, the client will gracefully shut down.

Requirements:

Python 3.x
Bash (for running the start scripts)

Author:
Bikash Neupane(bneupan2@myune.edu.au)
