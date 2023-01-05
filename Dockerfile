FROM ubuntu:focal

###------------------------------------------------------------------------------JAVA------------------------------------------------------------

ENV LANG='en_US.UTF-8' LANGUAGE='en_US:en' LC_ALL='en_US.UTF-8'

RUN apt-get update \
    && apt-get install -y --no-install-recommends tzdata curl ca-certificates fontconfig locales \
    && echo "en_US.UTF-8 UTF-8" >> /etc/locale.gen \
    && locale-gen en_US.UTF-8 \
    && rm -rf /var/lib/apt/lists/*

ENV JAVA_VERSION jdk-16.0.1+9_openj9-0.26.0

RUN set -eux; \
    ARCH="$(dpkg --print-architecture)"; \
    case "${ARCH}" in \
       ppc64el|ppc64le) \
         ESUM='9200acc9ddb6b0d4facf3ea44b17d3a10035316a379b4b148382b25cacf2bb83'; \
         BINARY_URL='https://github.com/AdoptOpenJDK/openjdk16-binaries/releases/download/jdk-16.0.1%2B9_openj9-0.26.0/OpenJDK16U-jdk_ppc64le_linux_openj9_16.0.1_9_openj9-0.26.0.tar.gz'; \
         ;; \
       s390x) \
         ESUM='6659d96dff1fff2731cd3977a6aec69a1313700c32c56216e3f0ee9c6114cfde'; \
         BINARY_URL='https://github.com/AdoptOpenJDK/openjdk16-binaries/releases/download/jdk-16.0.1%2B9_openj9-0.26.0/OpenJDK16U-jdk_s390x_linux_openj9_16.0.1_9_openj9-0.26.0.tar.gz'; \
         ;; \
       amd64|x86_64) \
         ESUM='7395aaa479a7410bbe5bd5efc43d2669718c61ba146b06657315dbd467b98bf1'; \
         BINARY_URL='https://github.com/AdoptOpenJDK/openjdk16-binaries/releases/download/jdk-16.0.1%2B9_openj9-0.26.0/OpenJDK16U-jdk_x64_linux_openj9_16.0.1_9_openj9-0.26.0.tar.gz'; \
         ;; \
       *) \
         echo "Unsupported arch: ${ARCH}"; \
         exit 1; \
         ;; \
    esac; \
    curl -LfsSo /tmp/openjdk.tar.gz ${BINARY_URL}; \
    echo "${ESUM} */tmp/openjdk.tar.gz" | sha256sum -c -; \
    mkdir -p /opt/java/openjdk; \
    cd /opt/java/openjdk; \
    tar -xf /tmp/openjdk.tar.gz --strip-components=1; \
    rm -rf /tmp/openjdk.tar.gz;

ENV JAVA_HOME=/opt/java/openjdk \
    PATH="/opt/java/openjdk/bin:$PATH"
ENV JAVA_TOOL_OPTIONS="-XX:+IgnoreUnrecognizedVMOptions -XX:+IdleTuningGcOnIdle -Xshareclasses:name=openj9_system_scc,cacheDir=/opt/java/.scc,readonly,nonFatal"

# Create OpenJ9 SharedClassCache (SCC) for bootclasses to improve the java startup.
# Downloads and runs tomcat to generate SCC for bootclasses at /opt/java/.scc/openj9_system_scc
# Does a dry-run and calculates the optimal cache size and recreates the cache with the appropriate size.
# With SCC, OpenJ9 startup is improved ~50% with an increase in image size of ~14MB
# Application classes can be create a separate cache layer with this as the base for further startup improvement


###------------------------------------------------------------------------------MYSQL-----------------------------------------------------------

# add our user and group first to make sure their IDs get assigned consistently, regardless of whatever dependencies get added
RUN apt-get install mariadb-server


COPY ctrlventas .
COPY ctrlventasapi .
COPY ventas.sql .

EXPOSE 8085 

RUN mysql -u root -p"root" ventas < ventas.sql

CMD ["./ctrlventas/mvnw", "spring-boot:run";"./ctrlventasapi/mvnw", "spring-boot:run"]
