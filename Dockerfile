MAINTAINER Adam Perry (adam.n.perry@gmail.com)

FROM ubuntu

#ENV #variable value


RUN mkdir -p /opt/whether/src
RUN mkdir /opt/whether/data
RUN mkdir /out
VOLUME ~/whether_out:/out

ADD src/ /opt/whether/src/
ADD build.gradle /opt/whether/

ADD ftp://ftp.ncdc.noaa.gov/pub/data/ghcn/daily/ghcnd_all.tar.gz /opt/whether/data/

RUN apt-get update

EXPOSE #port port... ports that the host needs


CMD #["exec", "param1", "param2"]