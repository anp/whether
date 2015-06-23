FROM ubuntu:14.10
MAINTAINER Adam Perry (adam.n.perry@gmail.com)
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

RUN echo "deb http://apt.postgresql.org/pub/repos/apt/ utopic-pgdg main" >> /etc/apt/sources.list.d/pgdg.list
RUN apt-get update && \
	apt-get install -y wget && \
		wget --quiet -O - https://www.postgresql.org/media/keys/ACCC4CF8.asc | \
      		sudo apt-key add -
RUN sudo apt-get update


RUN apt-get install -y \
	 postgresql-$PG_MAJOR \
	 postgresql-contrib-$PG_MAJOR \
	 libpq-dev

ENV PATH /usr/lib/postgresql/$PG_MAJOR/bin:$PATH
ENV PGDATA /var/lib/postgresql/data
ENV PGPASS "whether_password"
ENV PGHOST localhost
ENV PGPORT 5432

RUN mkdir -p /var/run/postgresql && chown -R postgres /var/run/postgresql

VOLUME /var/lib/postgresql

RUN chmod g+s /run/postgresql
RUN chown -R postgres:postgres /run/postgresql

EXPOSE 5432

### END POSTGRES ###

### PIP ###

RUN apt-get install -y python3-pip
RUN pip3 install py-postgresql

### END PIP ###

### R ###

RUN apt-get -y install r-base
RUN apt-get -y install libcurl4-gnutls-dev libxml2 libxml2-dev libssl-dev
ADD r_whether/dependencies.R /
RUN Rscript dependencies.R

### END R ###

### WHETHER ITSELF ###

# VOLUME /whether

RUN mkdir /py_whether
ADD config/whether.ini /
ADD py_whether/ /py_whether

ADD config/config.R /
ADD r_whether/main.R /
ADD r_whether/plotMeanTemp.R /
ADD r_whether/plotTempStdDev.R /
ADD r_whether/plotTornadoLocations.R /

ADD bash/whether.sh /
ADD bash/download_and_unpack.sh /
RUN chmod +x whether.sh \
	&& chmod +x download_and_unpack.sh
CMD ["/whether.sh"]
