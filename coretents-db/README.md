Coretents db
============================

Databases for Coretents project.

## Launch with docker

Build and launch the mongodb image:

```bash
docker build -f Dockerfile-mongodb -t coretents-db:latest .
docker run -d -p 27017:27017 coretents-db:latest
```

