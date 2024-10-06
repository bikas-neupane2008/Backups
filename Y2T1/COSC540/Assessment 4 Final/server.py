import socket
import ssl
import threading
import hashlib
import sqlite3
import datetime
import json

# Function to create the database and tables
def create_db():
    conn = sqlite3.connect('user_credentials.db')
    c = conn.cursor()
    # Create users table to store user credentials
    c.execute('''
        CREATE TABLE IF NOT EXISTS users (
            username TEXT PRIMARY KEY,
            password_hash TEXT NOT NULL,
            is_online BOOLEAN NOT NULL DEFAULT FALSE,
            last_online TIMESTAMP
        )
    ''')
    # Create messages table to store messages between users
    c.execute('''
            CREATE TABLE IF NOT EXISTS messages (
                sender TEXT NOT NULL,
                receiver TEXT NOT NULL,
                send_time TEXT NOT NULL,
                read_time TEXT,
                message TEXT NOT NULL,
                notification_sent BOOLEAN,
                PRIMARY KEY (sender, receiver, send_time, message)
            )
        ''')
    conn.commit()
    conn.close()

# Function to add a new user to the database
def add_user(username, password_hash):
    conn = sqlite3.connect('user_credentials.db')
    c = conn.cursor()
    c.execute('INSERT INTO users (username, password_hash) VALUES (?, ?)', (username, password_hash))
    conn.commit()
    conn.close()
    print(f"User '{username}' added successfully.")

# Function to add a new message to the database
def add_message(sender, receiver, message):
    send_time = datetime.datetime.now().isoformat()
    conn = sqlite3.connect('user_credentials.db')
    c = conn.cursor()
    try:
        c.execute('SELECT 1 FROM messages WHERE sender = ? AND receiver = ? AND message = ? AND send_time = ?',
                  (sender, receiver, message, send_time))
        if c.fetchone() is not None:
            print(f"Message from '{sender}' to '{receiver}' at '{send_time}' already exists.")
            return False
        c.execute('INSERT INTO messages (sender, receiver, send_time, message) VALUES (?, ?, ?, ?)',
                  (sender, receiver, send_time, message))
        conn.commit()
        saved = c.rowcount == 1
        if saved:
            print(f"Message from '{sender}' to '{receiver}' saved successfully.")
        else:
            print(f"Failed to save message from '{sender}' to '{receiver}'.")
    except sqlite3.Error as e:
        saved = False
        print(f"Error saving message from '{sender}' to '{receiver}': {e}")
    finally:
        conn.close()
    return saved

# Function to fetch unread messages for a user
def unread_message(username):
    conn = sqlite3.connect('user_credentials.db')
    c = conn.cursor()
    try:
        c.execute('''
            SELECT sender, receiver, send_time, message FROM messages
            WHERE receiver = ? AND read_time IS NULL
        ''', (username,))
        messages = c.fetchall()
        if messages:
            current_time = datetime.datetime.now().isoformat()
            c.execute('''
                UPDATE messages SET read_time = ?, notification_sent = ?
                WHERE receiver = ? AND read_time IS NULL
            ''', (current_time, False, username))
            conn.commit()
            print(f"Marked messages as read for '{username}'.")
        return messages
    except sqlite3.Error as e:
        print(f"Error fetching unread messages for '{username}': {e}")
        return None
    finally:
        conn.close()

# Function to fetch all messages for a user
def read_all_message(username):
    conn = sqlite3.connect('user_credentials.db')
    c = conn.cursor()
    try:
        c.execute('''
            SELECT sender, receiver, send_time, read_time, message FROM messages
            WHERE receiver = ?
        ''', (username,))
        messages = c.fetchall()
        if messages:
            current_time = datetime.datetime.now().isoformat()
            c.execute('''
                UPDATE messages SET read_time = ?, notification_sent = ?
                WHERE receiver = ? AND read_time IS NULL
            ''', (current_time, False, username))
            c.execute('''
                SELECT sender, receiver, send_time, read_time, message FROM messages
                WHERE receiver = ?
            ''', (username,))
            messages = c.fetchall()
            conn.commit()
            print(f"Retrieved all messages for '{username}'.")
        return messages
    except sqlite3.Error as e:
        print(f"Error fetching all messages for '{username}': {e}")
        return None
    finally:
        conn.close()

# Function to check if a user exists in the database
def user_exists(username):
    conn = sqlite3.connect('user_credentials.db')
    c = conn.cursor()
    c.execute('SELECT 1 FROM users WHERE username = ?', (username,))
    exists = c.fetchone() is not None
    conn.close()
    print(f"User '{username}' exists: {exists}")
    return exists

# Function to get the password hash of a user
def get_user_password_hash(username):
    conn = sqlite3.connect('user_credentials.db')
    c = conn.cursor()
    c.execute('SELECT password_hash FROM users WHERE username = ?', (username,))
    result = c.fetchone()
    conn.close()
    if result:
        print(f"Retrieved password hash for '{username}'.")
    else:
        print(f"No password hash found for '{username}'.")
    return result[0] if result else None

# Function to set a user's online status
def set_user_online(username, online):
    conn = sqlite3.connect('user_credentials.db')
    c = conn.cursor()
    current_time = datetime.datetime.now().isoformat()
    if online:
        c.execute('''
            UPDATE users SET is_online = ?, last_online = ?
            WHERE username = ?
        ''', (True, current_time, username))
        print(f"Set user '{username}' online.")
    else:
        c.execute('''
            UPDATE users SET is_online = ?
            WHERE username = ?
        ''', (False, username))
        print(f"Set user '{username}' offline.")
    conn.commit()
    conn.close()

# Function to send notifications for unread messages
def send_notifications(username):
    conn = sqlite3.connect('user_credentials.db')
    c = conn.cursor()
    try:
        c.execute('''
            SELECT receiver, read_time FROM messages
            WHERE notification_sent = ? AND sender = ?
        ''', (False, username))
        notifications = c.fetchall()
        if notifications:
            c.execute('''
                UPDATE messages SET notification_sent = ?
                WHERE sender = ? AND notification_sent = ?
            ''', (True, username, False))
            conn.commit()
            print(f"Sent notifications for '{username}'.")
        return notifications
    except sqlite3.Error as e:
        print(f"Error sending notifications for '{username}': {e}")
        return None
    finally:
        conn.close()

# Function to count unread messages for the username
def count_unread_messages(username):
    conn = sqlite3.connect('user_credentials.db')
    c = conn.cursor()
    try:
        c.execute('''
            SELECT COUNT(*) FROM messages
            WHERE receiver = ? AND read_time IS NULL
        ''', (username,))
        count = c.fetchone()[0]
        print(f"User '{username}' has {count} unread messages.")
        return count
    except sqlite3.Error as e:
        print(f"Error counting unread messages for '{username}': {e}")
        return 0
    finally:
        conn.close()

# Function to count new notifications for the username
def count_notifications(username):
    conn = sqlite3.connect('user_credentials.db')
    c = conn.cursor()
    try:
        c.execute('''
            SELECT COUNT(*) FROM messages
            WHERE notification_sent = ? AND sender = ?
        ''', (False, username))
        count = c.fetchone()[0]
        print(f"User '{username}' has {count} unread notifications.")
        return count
    except sqlite3.Error as e:
        print(f"Error counting notifications for '{username}': {e}")
        return 0
    finally:
        conn.close()


# Initialize the database
create_db()

clients = set()

# Function to handle each client connection
def handle_client(client_socket):
    current_user = None

    def send_response(response):
        client_socket.sendall(response.encode() + b'\n')

    def handle_username(username):
        if user_exists(username):
            send_response('USERNAME ALREADY EXISTS')
        else:
            send_response('USERNAME NOT FOUND')

    def handle_login(username, hashed_password):
        nonlocal current_user
        stored_hash = get_user_password_hash(username)
        if stored_hash == hashed_password:
            current_user = username
            set_user_online(username, True)
            unread_message_count = count_unread_messages(username)
            notification_count = count_notifications(username)
            response = {
                'status': 'LOGIN SUCCESSFUL',
                'unread_messages': unread_message_count,
                'notifications': notification_count
            }
            send_response(json.dumps(response))
        else:
            send_response('LOGIN FAILED: INCORRECT PASSWORD')

    def create_account(username, hashed_password):
        add_user(username, hashed_password)
        send_response('ACCOUNT CREATED')

    def handle_unread(username):
        messages = unread_message(username)
        if not messages:
            send_response('No Messages Yet')
        else:
            response = []
            for message in messages:
                response.append({
                    'sender': message[0],
                    'receiver': message[1],
                    'send_time': message[2],
                    'message': message[3]
                })
            json_response = json.dumps(response)
            send_response(json_response)

    def handle_all_messages(username):
        messages = read_all_message(username)
        if not messages:
            send_response('No Messages Yet')
        else:
            response = []
            for message in messages:
                response.append({
                    'sender': message[0],
                    'receiver': message[1],
                    'send_time': message[2],
                    'read_time': message[3],
                    'message': message[4]
                })
            json_response = json.dumps(response)
            send_response(json_response)

    def handle_notifications(username):
        notifications = send_notifications(username)
        if not notifications:
            send_response('No New Notifications')
        else:
            response = []
            for notification in notifications:
                response.append({
                    'receiver': notification[0],
                    'read_time': notification[1],
                })
            json_response = json.dumps(response)
            send_response(json_response)

    def handle_compose(sender, receiver, message):
        if add_message(sender, receiver, message):
            send_response('MESSAGE SENT SUCCESSFULLY.')
        else:
            send_response('There is some error in sending the message')

    try:
        while True:
            data = client_socket.recv(1024).decode().strip()
            if not data:
                break
            command, *params = data.split()
            if command == 'CHECK':
                handle_username(params[0])
            elif command == 'LOGIN':
                handle_login(params[0], params[1])
            elif command == 'CREATE_ACCOUNT':
                create_account(params[0], params[1])
            elif command == 'COMPOSE':
                composed_message = " ".join(params[2:])
                handle_compose(params[0], params[1], composed_message)
            elif command == 'READ_UNREAD':
                handle_unread(params[0])
            elif command == 'READ_ALL':
                handle_all_messages(params[0])
            elif command == 'VIEW':
                handle_notifications(params[0])
            elif command == 'EXIT':
                set_user_online(current_user, False)
                break
            else:
                send_response('UNKNOWN COMMAND')
    except Exception as e:
        print(f"Error handling client: {e}")
    finally:
        if current_user:
            set_user_online(current_user, False)
        client_socket.close()
        clients.discard(client_socket)
        print(f"Client disconnected: {current_user if current_user else 'Unknown user'}")

# Function to start the secure server
def start_server(port):
    context = ssl.create_default_context(ssl.Purpose.CLIENT_AUTH)
    context.load_cert_chain(certfile="server.crt", keyfile="server.key")

    server_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    server_socket.bind(('0.0.0.0', port))
    server_socket.listen(5)
    secure_socket = context.wrap_socket(server_socket, server_side=True)

    print(f"Secure server started, listening on port {port}")
    try:
        while True:
            client_socket, addr = secure_socket.accept()
            print(f"Accepted connection from {addr}")
            clients.add(client_socket)
            threading.Thread(target=handle_client, args=(client_socket,)).start()
    except Exception as e:
        print(f"Error starting server: {e}")
    finally:
        secure_socket.close()
        for client in clients:
            client.close()
        print("Server shut down.")

# Entry point for the server script
if __name__ == "__main__":
    import sys
    import signal

    if len(sys.argv) != 2:
        print("Usage: python3 server.py <port>")
        sys.exit(1)
    port = int(sys.argv[1])

    signal.signal(signal.SIGINT, lambda sig, frame: sys.exit(0))

    start_server(port)
