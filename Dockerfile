MAINTAINER Adam Perry (adam.n.perry@gmail.com)

FROM ubuntu

### POSTGRES###

# postgres section adapted from https://github.com/docker-library/postgres/blob/bfca9b8a92a99ccfc8f04933b7ecc29a108c7f49/9.4/Dockerfile
# add our user and group first to make sure their IDs get assigned consistently, regardless of whatever dependencies get added
RUN groupadd -r postgres && useradd -r -g postgres postgres

# grab gosu for easy step-down from root
RUN gpg --keyserver pool.sks-keyservers.net --recv-keys B42F6819007F00F88E364FD4036A9C25BF357DD4
RUN apt-get update && apt-get install -y curl && rm -rf /var/lib/apt/lists/* \
	&& curl -o /usr/local/bin/gosu -SL "https://github.com/tianon/gosu/releases/download/1.2/gosu-$(dpkg --print-architecture)" \
	&& curl -o /usr/local/bin/gosu.asc -SL "https://github.com/tianon/gosu/releases/download/1.2/gosu-$(dpkg --print-architecture).asc" \
	&& gpg --verify /usr/local/bin/gosu.asc \
	&& rm /usr/local/bin/gosu.asc \
	&& chmod +x /usr/local/bin/gosu \
	&& apt-get purge -y --auto-remove curl

# make the "en_US.UTF-8" locale so postgres will be utf-8 enabled by default
RUN apt-get update && apt-get install -y locales && rm -rf /var/lib/apt/lists/* \
	&& localedef -i en_US -c -f UTF-8 -A /usr/share/locale/locale.alias en_US.UTF-8
ENV LANG en_US.utf8

ENV PG_MAJOR 9.4
ENV PG_VERSION 9.4.4-1.pgdg70+1

RUN apt-get install -y \
	 postgresql-$PG_MAJOR=$PG_VERSION \
	 postgresql-contrib-$PG_MAJOR=$PG_VERSION

RUN mkdir -p /var/run/postgresql && chown -R postgres /var/run/postgresql

ENV PATH /usr/lib/postgresql/$PG_MAJOR/bin:$PATH
ENV PGDATA /var/lib/postgresql/data
ENV PGPORT 5432
ENV PGPASS "whether_password"

VOLUME /var/lib/postgresql/data

RUN gosu echo "alter user  postgres with password $PGPASS;" | psql postgres
RUN gosu postgres initdb

### END POSTGRES ###

### PIP ###

RUN apt-get install -y python3-pip
RUN pip install py-postgresql

### END PIP ###

### R ###

RUN apt-get install r-base
COPY r_whether/dependencies.R /
RUN Rscript dependencies.R

### END R ###

### WHETHER ITSELF ###

RUN mkdir /py_whether
COPY config/whether.ini /
COPY py_whether/ /py_whether

COPY config/config.R /
COPY r_whether/main.R /
COPY r_whether/plotMeanTemp.R /
COPY r_whether/plotTempStdDev.R /
COPY r_whether/plotTornadoLocations.R /

COPY bash/whether.sh /
COPY bash/download_and_unpack.sh /
CMD ["whether.sh"]
