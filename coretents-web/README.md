If you want to launch the container for developing purposes use:

# docker run -d -v $(pwd)/src:/shared/src -p 80:80 -p 443:443 coretents-web:latest development

Or if you want to launch it in production use:

# docker run -d -p 80:80 -p 443:443 coretents-web:latest
