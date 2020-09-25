# maven:3.6.3-jdk-8 with postgresql-client, bash, curl, docker-cli
FROM maven:3.6.3-jdk-8
ENV DOCKERVERSION=19.03.12
RUN apt-get update && apt-get install -y \
    postgresql-client \
    bash && \
    apt-get -y --no-install-recommends install apt-transport-https \
      ca-certificates \
      curl \
      gnupg2 \
      software-properties-common && \
    curl -fsSL https://download.docker.com/linux/$(. /etc/os-release; echo "$ID")/gpg > /tmp/dkey; apt-key add /tmp/dkey && \
    add-apt-repository \
      "deb [arch=amd64] https://download.docker.com/linux/$(. /etc/os-release; echo "$ID") \
      $(lsb_release -cs) \
      stable" && \
   apt-get update && \
#   apt-get -y --no-install-recommends install docker-ce && \
   curl -fsSLO https://download.docker.com/linux/static/stable/x86_64/docker-${DOCKERVERSION}.tgz && \
   tar xzvf docker-${DOCKERVERSION}.tgz --strip 1 -C /usr/local/bin docker/docker && \
   rm docker-${DOCKERVERSION}.tgz && \
   apt-get clean
   
