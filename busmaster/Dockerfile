FROM ubuntu:20.04

LABEL maintainer="Philipp Arndt <2f.mail@gmx.de>"
LABEL version="1.0"
LABEL description="1wire docker"

ENV LANG en_US.UTF-8
ENV TERM xterm

#RUN printf "blacklist ds2490\nblacklist ds9490r\nblacklist wire\n" >> /etc/modprobe.d/raspi-blacklist.conf

RUN apt-get update && \
apt-get install -y \
owfs \
owserver \
ow-shell \
owhttpd \
owftpd

RUN apt-get install -y locales locales-all
ENV LC_ALL de_DE.UTF-8
ENV LANG de_DE.UTF-8
ENV LANGUAGE de_DE.UTF-8

# Create mount point
RUN mkdir /mnt/1wire && chmod 744 /mnt/1wire

# Add missing init script
COPY ./config/owfs.init /etc/init.d/owfs

# OWFS config
COPY ./config/owfs.conf /etc/owfs.conf

# Update run level
RUN update-rc.d owftpd remove && update-rc.d owfs defaults

# App
COPY ./config/app.sh /app.sh

CMD /app.sh