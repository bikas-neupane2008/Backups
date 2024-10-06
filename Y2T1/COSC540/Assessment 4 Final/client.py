import socket
import ssl
import sys
import hashlib
import json
import getpass
from datetime import datetime

# Function to send a command to the server
def send_command(server_socket, command):
    server_socket.send((command + '\n').encode())

# Function to receive a response from the server
def receive_response(server_socket):
    response = b""
    while True:
        part = server_socket.recv(1024)
        response += part
        if b'\n' in part:
            break
    decoded_response = response.decode().strip()
    return decoded_response

# Function to validate the username
def validate_username(username):
    return ' ' not in username and username.isalnum()

# Function to handle user login
def handle_login(server_socket):
    while True:
        username = input("Enter your username: ")
        if not validate_username(username):
            print("Invalid username. Usernames should be alphanumeric and contain no spaces.")
            continue
        send_command(server_socket, f'CHECK {username}')
        response = receive_response(server_socket)
        if response == 'USERNAME ALREADY EXISTS':
            password = getpass.getpass("Enter your password: ")
            hashed_password = hashlib.sha256(password.encode()).hexdigest()
            send_command(server_socket, f'LOGIN {username} {hashed_password}')
            login_response = receive_response(server_socket)
            try:
                response_data = json.loads(login_response)
                if response_data['status'] == 'LOGIN SUCCESSFUL':
                    print("Login successful.")
                    print(f"Your Unread messages: {response_data['unread_messages']}")
                    print(f"Your new Notifications: {response_data['notifications']}")
                    return username
                else:
                    print("Login failed. Incorrect password.")
            except json.JSONDecodeError:
                print("Failed to parse server response.")
        elif response == 'USERNAME NOT FOUND':
            if input("Create new account? (yes/no): ").lower() == 'yes':
                password = getpass.getpass("Enter a password for the new account: ")
                hashed_password = hashlib.sha256(password.encode()).hexdigest()
                send_command(server_socket, f'CREATE_ACCOUNT {username} {hashed_password}')
                account_response = receive_response(server_socket)
                if account_response == 'ACCOUNT CREATED':
                    print("Account created successfully. Please log in.")
                else:
                    print("Account creation failed.")

# Function to display messages
def display_messages(response_json):
    messages = json.loads(response_json)
    print(f"Total Messages: {len(messages)}\n")
    for idx, message in enumerate(messages, start=1):
        send_time = datetime.fromisoformat(message["send_time"])
        formatted_sent_date = send_time.strftime("%A, %d %b, %Y")
        formatted_sent_time = send_time.strftime("%I:%M:%S %p")
        read_time = message.get("read_time")
        if read_time:
            read_time = datetime.fromisoformat(read_time)
        else:
            read_time = datetime.now()
        formatted_read_date = read_time.strftime("%A, %d %b, %Y")
        formatted_read_time = read_time.strftime("%I:%M:%S %p")
        print("***********************************************************")
        print(f"Message {idx}:")
        print("***********************************************************")
        print(f"Sender: {message['sender']}")
        print(f"Sent Date: {formatted_sent_date}")
        print(f"Sent Time: {formatted_sent_time}")
        print(f"Receiver: {message['receiver']}")
        print(f"Read Date: {formatted_read_date}")
        print(f"Read Time: {formatted_read_time}")
        print(f"Message: {message['message']}")
        print("***********************************************************\n")

# Function to display notifications
def display_notification(response_json):
    notifications = json.loads(response_json)
    print(f"Total New Notifications: {len(notifications)}\n")
    for idx, notification in enumerate(notifications, start=1):
        read_time = notification.get("read_time")
        read_time = datetime.fromisoformat(read_time)
        formatted_read_date = read_time.strftime("%A, %d %b, %Y")
        formatted_read_time = read_time.strftime("%I:%M:%S %p")
        print("***********************************************************")
        print(f"Notification {idx}:")
        print("***********************************************************")
        print(
            f"The message you sent to {notification['receiver']} has been read on {formatted_read_date} at {formatted_read_time}.")
        print("***********************************************************\n")

# Function to compose a message
def compose_message(server_socket, username):
    while True:
        recipient = input("Enter recipient's username: ")
        if not validate_username(recipient):
            print("Invalid username. Usernames should be alphanumeric and contain no spaces.")
            continue
        send_command(server_socket, f'CHECK {recipient}')
        response = receive_response(server_socket)
        if response == 'USERNAME NOT FOUND':
            print("Recipient does not exist. Please enter a valid username.")
        else:
            message = input("Enter your message: ")
            send_command(server_socket, f'COMPOSE {username} {recipient} {message}')
            response = receive_response(server_socket)
            print(response)
            break

# Function to start the client
def start_client(host, port):
    context = ssl.create_default_context()
    context.load_verify_locations('server.crt')
    with context.wrap_socket(socket.socket(socket.AF_INET), server_hostname=host) as server_socket:
        print(f"Connecting to {host}:{port}...")
        server_socket.connect((host, port))
        print("Connected to the server.")
        username = handle_login(server_socket)
        while True:
            print("\n***********************************************************\n")
            print("Enter COMPOSE to send new message to other users.")
            print("Enter READ to read messages sent by others to you.")
            print("Enter VIEW to view all the new notifications.")
            print("Enter EXIT to Logout and exit the system")
            print("\n***********************************************************\n")
            command = input("Enter command (COMPOSE, READ, VIEW, EXIT): ").strip().upper()
            if command == 'COMPOSE':
                compose_message(server_socket, username)
            elif command == 'READ':
                while True:
                    print("\n***********************************************************\n")
                    print("Enter ALL to read all the messages.")
                    print("Enter UNREAD to read only the unread messages.")
                    print("\n***********************************************************\n")
                    read_unread = input("Enter command (ALL, UNREAD): ").strip().upper()
                    if read_unread == 'ALL':
                        send_command(server_socket, f'READ_ALL {username}')
                    elif read_unread == 'UNREAD':
                        send_command(server_socket, f'READ_UNREAD {username}')
                    else:
                        print("Invalid command. Please enter 'ALL' or 'UNREAD'.")
                        continue
                    response = receive_response(server_socket)
                    if response == 'No Messages Yet':
                        print(response)
                        break
                    else:
                        display_messages(response)
                        break
            elif command == 'VIEW':
                send_command(server_socket, f'VIEW {username}')
                response = receive_response(server_socket)
                if response == 'No New Notifications':
                    print(response)
                else:
                    display_notification(response)
            elif command == 'EXIT':
                send_command(server_socket, f'EXIT')
                print("Exiting the client. Goodbye!")
                break
            else:
                print("Unknown command. Please enter COMPOSE, READ, VIEW, or EXIT.")

# Entry point for the client script
if __name__ == "__main__":
    if len(sys.argv) != 3:
        print("Usage: python3 client.py <hostname> <port>")
        sys.exit(1)
    host = sys.argv[1]
    port = int(sys.argv[2])
    start_client(host, port)
