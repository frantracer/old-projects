Coretents processor
============================

Application that processes contents from feeds, generates metadata and stores it in the database.

## Launch with docker

Build the image:

```bash
docker build -t coretents-api:latest .
```

If you want to launch the container for developing purposes use:

```bash
docker run -it -v $(pwd):/shared/ -p 51415:51415 coretents-api:latest /bin/bash
python3.5 /shared/src/app.py
```

Or if you want to launch it in production use:

```bash
docker run -d -p 51415:51415 coretents-api:latest
```
