FROM ubuntu:focal
###------------------------------------------------------------------------------JAVA------------------------------------------------------------

ENV LANG='en_US.UTF-8' LANGUAGE='en_US:en' LC_ALL='en_US.UTF-8'

RUN apt-get update \
    && apt-get install -y --no-install-recommends gzip tar tzdata curl ca-certificates fontconfig locales \
    && echo "en_US.UTF-8 UTF-8" >> /etc/locale.gen \
    && locale-gen en_US.UTF-8 \
    && rm -rf /var/lib/apt/lists/*

ENV JAVA_HOME /usr/java/openjdk-18
ENV PATH $JAVA_HOME/bin:$PATH
ENV JAVA_VERSION 18.0.2.1

RUN set -eux; \
  downloadUrl='https://download.java.net/java/GA/jdk18.0.2.1/db379da656dc47308e138f21b33976fa/1/GPL/openjdk-18.0.2.1_linux-x64_bin.tar.gz'; \
	downloadSha256='3bfdb59fc38884672677cebca9a216902d87fe867563182ae8bc3373a65a2ebd'; \
	curl -fL -o openjdk.tgz "$downloadUrl"; \
	echo "$downloadSha256 *openjdk.tgz" | sha256sum --strict --check -; \
	mkdir -p "$JAVA_HOME"; \
	tar --extract \
		--file openjdk.tgz \
		--directory "$JAVA_HOME" \
		--strip-components 1 \
		--no-same-owner; \
	rm openjdk.tgz*; \
	rm -rf "$JAVA_HOME/lib/security/cacerts"; \
# see "update-ca-trust" script which creates/maintains this cacerts bundle
	ln -sT /etc/pki/ca-trust/extracted/java/cacerts "$JAVA_HOME/lib/security/cacerts"; \
# https://github.com/oracle/docker-images/blob/a56e0d1ed968ff669d2e2ba8a1483d0f3acc80c0/OracleJava/java-8/Dockerfile#L17-L19
	ln -sfT "$JAVA_HOME" /usr/java/default; \
	ln -sfT "$JAVA_HOME" /usr/java/latest; \
	for bin in "$JAVA_HOME/bin/"*; do \
		base="$(basename "$bin")"; \
		[ ! -e "/usr/bin/$base" ]; \
		update-alternatives --install "/usr/bin/$base" "$base" "$bin" 20000; \
	done; \
	java -Xshare:dump; \
	javac --version; \
	java --version

###------------------------------------------------------------------------------MYSQL-----------------------------------------------------------

RUN groupadd -r mysql && useradd -r -g mysql mysql

# add gosu for easy step-down from root
# https://github.com/tianon/gosu/releases
# gosu key is B42F6819007F00F88E364FD4036A9C25BF357DD4
ENV GOSU_VERSION 1.14

ARG GPG_KEYS=177F4010FE56CA3336300305F1656F24C74CD1D8
# pub   rsa4096 2016-03-30 [SC]
#         177F 4010 FE56 CA33 3630  0305 F165 6F24 C74C D1D8
# uid           [ unknown] MariaDB Signing Key <signing-key@mariadb.org>
# sub   rsa4096 2016-03-30 [E]
# install "libjemalloc2" as it offers better performance in some cases. Use with LD_PRELOAD
# install "pwgen" for randomizing passwords
# install "tzdata" for /usr/share/zoneinfo/
# install "xz-utils" for .sql.xz docker-entrypoint-initdb.d files
# install "zstd" for .sql.zst docker-entrypoint-initdb.d files
# hadolint ignore=SC2086
RUN set -eux; \
	apt-get update; \
	DEBIAN_FRONTEND=noninteractive apt-get install -y --no-install-recommends \
		ca-certificates \
		gpg \
		gpgv \
		libjemalloc2 \
		pwgen \
		tzdata \
		xz-utils \
		zstd ; \
	savedAptMark="$(apt-mark showmanual)"; \
	apt-get install -y --no-install-recommends \
		dirmngr \
		gpg-agent \
		wget; \
	rm -rf /var/lib/apt/lists/*; \
	dpkgArch="$(dpkg --print-architecture | awk -F- '{ print $NF }')"; \
	wget -q -O /usr/local/bin/gosu "https://github.com/tianon/gosu/releases/download/$GOSU_VERSION/gosu-$dpkgArch"; \
	wget -q -O /usr/local/bin/gosu.asc "https://github.com/tianon/gosu/releases/download/$GOSU_VERSION/gosu-$dpkgArch.asc"; \
	GNUPGHOME="$(mktemp -d)"; \
	export GNUPGHOME; \
	gpg --batch --keyserver hkps://keys.openpgp.org --recv-keys B42F6819007F00F88E364FD4036A9C25BF357DD4; \
	for key in $GPG_KEYS; do \
		gpg --batch --keyserver keyserver.ubuntu.com --recv-keys "$key"; \
	done; \
	gpg --batch --export "$GPG_KEYS" > /etc/apt/trusted.gpg.d/mariadb.gpg; \
	if command -v gpgconf >/dev/null; then \
		gpgconf --kill all; \
	fi; \
	gpg --batch --verify /usr/local/bin/gosu.asc /usr/local/bin/gosu; \
	gpgconf --kill all; \
	rm -rf "$GNUPGHOME" /usr/local/bin/gosu.asc; \
	apt-mark auto '.*' > /dev/null; \
	[ -z "$savedAptMark" ] ||	apt-mark manual $savedAptMark >/dev/null; \
	apt-get purge -y --auto-remove -o APT::AutoRemove::RecommendsImportant=false; \
	chmod +x /usr/local/bin/gosu; \
	gosu --version; \
	gosu nobody true

RUN mkdir /docker-entrypoint-initdb.d

# Ensure the container exec commands handle range of utf8 characters based of
# default locales in base image (https://github.com/docker-library/docs/blob/135b79cc8093ab02e55debb61fdb079ab2dbce87/ubuntu/README.md#locales)
ENV LANG C.UTF-8

# OCI annotations to image
LABEL org.opencontainers.image.authors="MariaDB Community" \
      org.opencontainers.image.title="MariaDB Database" \
      org.opencontainers.image.description="MariaDB Database for relational SQL" \
      org.opencontainers.image.documentation="https://hub.docker.com/_/mariadb/" \
      org.opencontainers.image.base.name="docker.io/library/ubuntu:focal" \
      org.opencontainers.image.licenses="GPL-2.0" \
      org.opencontainers.image.source="https://github.com/MariaDB/mariadb-docker" \
      org.opencontainers.image.vendor="MariaDB Community" \
      org.opencontainers.image.version="10.6.11" \
      org.opencontainers.image.url="https://github.com/MariaDB/mariadb-docker"

# bashbrew-architectures: amd64 arm64v8 ppc64le s390x
ARG MARIADB_MAJOR=10.6
ENV MARIADB_MAJOR $MARIADB_MAJOR
ARG MARIADB_VERSION=1:10.6.11+maria~ubu2004
ENV MARIADB_VERSION $MARIADB_VERSION
ENV MARIADB_DATABASE=ventas
ENV MARIADB_ROOT_PASSWORD=root
# release-status:Stable
# (https://downloads.mariadb.org/rest-api/mariadb/)

# Allowing overriding of REPOSITORY, a URL that includes suite and component for testing and Enterprise Versions
ARG REPOSITORY="http://archive.mariadb.org/mariadb-10.6.11/repo/ubuntu/ focal main"

RUN set -e;\
	echo "deb ${REPOSITORY}" > /etc/apt/sources.list.d/mariadb.list; \
	{ \
		echo 'Package: *'; \
		echo 'Pin: release o=MariaDB'; \
		echo 'Pin-Priority: 999'; \
	} > /etc/apt/preferences.d/mariadb
# add repository pinning to make sure dependencies from this MariaDB repo are preferred over Debian dependencies
#  libmariadbclient18 : Depends: libmysqlclient18 (= 5.5.42+maria-1~wheezy) but 5.5.43-0+deb7u1 is to be installed

# the "/var/lib/mysql" stuff here is because the mysql-server postinst doesn't have an explicit way to disable the mysql_install_db codepath besides having a database already "configured" (ie, stuff in /var/lib/mysql/mysql)
# also, we set debconf keys to make APT a little quieter
# hadolint ignore=DL3015
RUN set -ex; \
	{ \
		echo "mariadb-server-$MARIADB_MAJOR" mysql-server/root_password password 'unused'; \
		echo "mariadb-server-$MARIADB_MAJOR" mysql-server/root_password_again password 'unused'; \
	} | debconf-set-selections; \
	apt-get update; \
# mariadb-backup is installed at the same time so that `mysql-common` is only installed once from just mariadb repos
	apt-get install -y \
		"mariadb-server=$MARIADB_VERSION" mariadb-backup socat \
	; \
	rm -rf /var/lib/apt/lists/*; \
# purge and re-create /var/lib/mysql with appropriate ownership
	rm -rf /var/lib/mysql; \
	mkdir -p /var/lib/mysql /var/run/mysqld; \
	chown -R mysql:mysql /var/lib/mysql /var/run/mysqld; \
# ensure that /var/run/mysqld (used for socket and lock files) is writable regardless of the UID our mysqld instance ends up having at runtime
	chmod 777 /var/run/mysqld; \
# comment out a few problematic configuration values
	find /etc/mysql/ -name '*.cnf' -print0 \
		| xargs -0 grep -lZE '^(bind-address|log|user\s)' \
		| xargs -rt -0 sed -Ei 's/^(bind-address|log|user\s)/#&/'; \
# don't reverse lookup hostnames, they are usually another container
	printf "[mariadb]\nhost-cache-size=0\nskip-name-resolve\n" > /etc/mysql/mariadb.conf.d/05-skipcache.cnf; \
# Issue #327 Correct order of reading directories /etc/mysql/mariadb.conf.d before /etc/mysql/conf.d (mount-point per documentation)
	if [ -L /etc/mysql/my.cnf ]; then \
# 10.5+
		sed -i -e '/includedir/ {N;s/\(.*\)\n\(.*\)/\n\2\n\1/}' /etc/mysql/mariadb.cnf; \
	fi


VOLUME /var/lib/mysql

COPY healthcheck.sh /usr/local/bin/healthcheck.sh
COPY docker-entrypoint.sh /usr/local/bin/
RUN chmod +x /usr/local/bin/docker-entrypoint.sh

COPY ctrlventas/* /ctrlventas/
COPY ctrlventasapi/* /ctrlventasapi/
COPY ventas.sql .
COPY run.sh .

ENTRYPOINT ["docker-entrypoint.sh"]

RUN chmod +x run.sh

EXPOSE 8085

CMD ["./run.sh"]
