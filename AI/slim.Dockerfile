# Use an official Python runtime as a parent image
FROM python:3.11-slim

# Copy the AI directory contents
COPY AI/server.py .

# Make port 65432 available to the world outside this container
EXPOSE 65432

# Run app.py when the container launches
CMD ["python", "server.py"]
