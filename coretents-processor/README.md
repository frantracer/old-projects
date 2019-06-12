Coretents processor
============================

Application that processes contents from feeds, generates metadata and stores it in the database.

## Launch with docker

Build the image:

```bash
docker build -t coretents-processor:latest .
```

If you want to launch the container for developing purposes use:

```bash
docker run -it -v $(pwd):/shared/ coretents-processor:latest /bin/bash
sbt run
```

Or if you want to launch it in production use:

```bash
docker run -d coretents-processor:latest
```
