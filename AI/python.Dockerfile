# Use an official Python runtime as a parent image
FROM ghcr.io/khoanguyenducanh/python:library

# Copy files from the AI directory
COPY AI/main.py .
COPY AI/server.py .

# Make port 65432 available to the world outside this container
EXPOSE 65432

# Run app.py when the container launches
CMD ["python", "server.py"]
