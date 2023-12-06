# Use an official Python runtime as a parent image
FROM ghcr.io/khoanguyenducanh/python:library

# Copy the AI directory contents
COPY AI .

# Install any needed packages specified in requirements.txt
RUN pip install --no-cache-dir -r requirements.txt

# Make port 65432 available to the world outside this container
EXPOSE 65432

# Run app.py when the container launches
CMD ["python", "server.py"]
