import socket
import sys
import signal

# Function to send a command to the server and receive response
def send_command(server_socket, command):
    # Send the command to the server
    server_socket.send(command.encode() + b'\n')
    # Receive the response from the server
    response = server_socket.recv(1024).decode().strip()
    return response

# Function to validate username
def validate_username(username):
    # Check if username contains spaces
    if ' ' in username:
        print("Error: Username cannot contain spaces")
        return False
    return True

# Function to customise response
def process_response(response):
    # Check if there are any unread messages response
    if response == "No More Unread Messages.":
        print(response)
    else:
        parts = response.split(' ')
        remaining_unread_count = int(parts[0])
        sender = parts[1]
        message = ' '.join(parts[2:])
        print(f"UNREAD MESSAGE:\nSent By: {sender}\nMessage: {message}\nNow there are {remaining_unread_count} unread messages.")


# Function to start the client
def start_client(host, port):
    server_socket = None
    try:
        # Create a socket object
        server_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        # Connect to the server
        server_socket.connect((host, port))

        # Get username from the user and validate
        username = input("Enter your username: ")
        while not validate_username(username):
            username = input("Enter your username: ")

        # Send LOGIN command to the server
        response = send_command(server_socket, f'LOGIN {username}')
        print(response)

        # Main loop for user interaction
        while True:
            # Get user command
            command = input("Enter command (COMPOSE, READ, EXIT): ").strip().upper()
            # Handle different commands
            if command == 'COMPOSE':
                while True:
                    # Get recipient's username and validate
                    recipient = input("Enter recipient's username: ")
                    if validate_username(recipient):
                        break
                # Get message from the user
                message = input("Enter message: ")
                # Send COMPOSE command with username, recipient, and message to the server
                response = send_command(server_socket, f'{command} {username} {recipient} {message}')
                print(f"{response}")
            elif command == 'READ':
                # Send READ command with username to the server
                response = send_command(server_socket, f'READ {username}')
                process_response(response)
            elif command == 'EXIT':
                # Send EXIT command to the server
                response = send_command(server_socket, command)
                print("Sorry to see you go")
                break
            else:
                print("Invalid command.")
                continue
    except ConnectionError as ce:
        print("Connection error:", ce)
    except socket.timeout as to:
        print("Socket timeout:", to)
    except Exception as e:
        print(f"Error: {e}")
    finally:
        # Close the server socket
        if server_socket:
            server_socket.close()

if __name__ == "__main__":
    # Check command line arguments
    if len(sys.argv) != 3:
        print("Usage: python3 client.py <hostname> <port>")
        sys.exit(1)
    # Get hostname and port from command line arguments
    host = sys.argv[1]
    port = int(sys.argv[2])

    # Define signal handler for SIGINT (Ctrl+C)
    def signal_handler(sig, frame):
        print("\nClient is shutting down...")
        sys.exit(0)

    # Register signal handler
    signal.signal(signal.SIGINT, signal_handler)

    # Start the client
    start_client(host, port)
