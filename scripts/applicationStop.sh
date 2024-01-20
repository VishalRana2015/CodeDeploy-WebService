#!/bin/bash

# Specify the port number
PORT_NUMBER=8080

# Check if the port is in use
PID=$(lsof -t -i :$PORT_NUMBER)

if [ -z "$PID" ]; then
    echo "No process found on port $PORT_NUMBER."
else
    echo "Stopping process with PID $PID on port $PORT_NUMBER..."
    kill -15 $PID  # Send a termination signal
    sleep 2  # Wait for the process to gracefully terminate
    echo "Process stopped."
fi

