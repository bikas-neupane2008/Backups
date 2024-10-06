import socket
import threading

# Dictionary to store messages
message_storage = {}

# Set to store client connections
clients = set()

# Function to handle each client connection
def handle_client(client_socket):
    global clients

    # Function to send response to the client
    def send_response(response):
        client_socket.send(response.encode() + b'\n')

    # Function to handle the LOGIN command
    def handle_login(username):
        # Check if username contains whitespace
        if ' ' in username:
            send_response('LOGIN ERROR')
            print("Error: Username contains whitespace.")
            return False
        # If user is new, create an entry in message_storage
        if username not in message_storage:
            message_storage[username] = []
            send_response(f'Login successful with new username. Welcome {username}')
            print("User logged in with new username.")
        else:  # If user is existing
            # Count unread messages
            unread_count = sum(1 for msg in message_storage[username] if not msg[2])
            send_response(f'Login successful. Welcome {username}. You have {unread_count} unread messages.')
            print("User logged in again")
        return True

    # Function to handle the COMPOSE command
    def handle_compose(sender, recipient, message):
        if recipient not in message_storage:  # If recipient is new
            # Create message list for recipient
            message_storage.setdefault(recipient, []).append((sender, message, False))
            send_response('MESSAGE SENT SUCCESSFULLY TO NEW USER.')
            print("Message sent to new username.")
        elif len(message_storage[recipient]) >= 100:  # Limiting messages per recipient
            # Inform sender about message limit
            send_response(f'MESSAGE FAILED. UNREAD MESSAGE IS MORE THAN 100 FOR THE USERNAME <{recipient}>.')
            print("The message box capacity of each username is 100 maximum. And so it exceeds.")
        else:  # Add message to recipient's message list
            message_storage.setdefault(recipient, []).append((sender, message, False))
            send_response('MESSAGE SENT SUCCESSFULLY.')
            print("Message sent.")

    # Function to handle the READ command
    def handle_read(username):
        # Retrieve unread messages for the given username
        unread_messages = [msg for msg in message_storage.get(username, []) if not msg[2]]
        if not unread_messages:  # No unread messages
            send_response('No More Unread Messages.')
            print("Unread messages finished.")
        else:  # Send unread messages to the client
                sender, message, _ = unread_messages[0]  # Extract sender and message
                unread_count = sum(1 for msg in message_storage[username] if not msg[2])  # Count unread messages
                # Send message to the client
                send_response(f'{unread_count -1} {sender} {message}')
                # Mark the message as read
                message_storage[username][message_storage[username].index((sender, message, False))] = (sender, message, True)

    try:
        while True:
            # Receive data from the client
            data = client_socket.recv(1024).decode().strip()
            if not data:  # If no data received, exit loop
                break

            # Split command from arguments
            command_parts = data.split(' ', 1)
            command = command_parts[0]  # Extract command
            if command == 'LOGIN':
                handle_login(command_parts[1])  # Handle LOGIN command
            elif command == 'COMPOSE':
                # Extract sender, recipient, and message
                sender, rest = command_parts[1].split(' ', 1)
                recipient, message = rest.split(' ', 1)
                handle_compose(sender, recipient, message)  # Handle COMPOSE command
            elif command == 'READ':
                handle_read(command_parts[1])  # Handle READ command
            elif command == 'EXIT':
                # Inform about client exit
                print(f"Client {client_socket.getpeername()} has exited.")
                print("Client is now closed.")
                print("Waiting for new client to join.")
                break  # Exit loop
            else:
                send_response('UNKNOWN COMMAND')  # Send unknown command response
    except Exception as e:
        print(f"Error: {e}")
    finally:
        # Close client socket after loop ends
        client_socket.close()
        # Remove client from set
        clients.remove(client_socket)

# Function to start the server
def start_server(port):
    global clients
    try:
        # Create socket
        server_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        # Bind socket to address
        server_socket.bind(('0.0.0.0', port))
        # Listen for connections
        server_socket.listen(5)
        print(f"Server started, listening on port {port}")

        while True:  # Main server loop
            # Accept client connection
            client_socket, client_address = server_socket.accept()
            # Add client to set
            clients.add(client_socket)
            print(f"Accepted connection from {client_address[0]}:{client_address[1]}")
            # Create a thread to handle the client
            client_handler = threading.Thread(target=handle_client, args=(client_socket,))
            # Start the client handling thread
            client_handler.start()
    except Exception as e:
        print(f"Error starting server: {e}")
    finally:
        # Close server socket when done
        server_socket.close()
        # Close all client sockets
        for client in clients:
            client.close()

# Entry point of the program
if __name__ == "__main__":
    import sys
    import signal

    # Check command line arguments
    if len(sys.argv) != 2:
        print("Usage: python3 server.py <port>")
        sys.exit(1)
    port = int(sys.argv[1])  # Get port number from command line argument

    # Set up a signal handler for SIGINT (Ctrl+C)
    def signal_handler(sig, frame):
        print("\nServer is shutting down...")
        # Close all client sockets
        for client in clients:
            client.close()
        sys.exit(0)

    signal.signal(signal.SIGINT, signal_handler)

    start_server(port)  # Start the server
