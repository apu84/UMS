############################################################
# Dockerfile to run Memcached Containers
# Based on Ubuntu Image
############################################################

# Set the base image to use to Ubuntu
FROM ubuntu:16.04

# Set the file maintainer (your name - the file's author)
MAINTAINER Moshlehuddin Mazumder

# Update the default application repository sources list
RUN apt-get update

# Install Memcached
RUN apt-get install -y memcached

# Port to expose (default: 11211)
EXPOSE 11211

# Default Memcached run command arguments
CMD ["-m", "2048"]

# Set the user to run Memcached daemon
USER daemon

# Set the entrypoint to memcached binary
ENTRYPOINT memcached
