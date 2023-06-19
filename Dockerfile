FROM fedora:38

RUN dnf install -y java-17-openjdk-headless
RUN dnf install -y texlive-scheme-minimal

RUN dnf install -y R
RUN dnf install -y 'dnf-command(copr)'
RUN dnf copr enable -y iucar/cran
RUN dnf install -y R-CoprManager

WORKDIR /opt
RUN curl -O https://archive.apache.org/dist/tomcat/tomcat-11/v11.0.0-M5/bin/apache-tomcat-11.0.0-M5.tar.gz
RUN tar xzf apache-tomcat-11.0.0-M5.tar.gz
RUN mv apache-tomcat-11.0.0-M5 tomcat
RUN rm -rf apache-tomcat-11.0.0-M5.tar.gz

RUN dnf install -y "naver-nanum*"
RUN dnf install -y "texlive-kotex*"

RUN dnf install -y R-CRAN-rJava
RUN dnf install -y R-CRAN-xtable

ENV R_HOME /usr/lib64/R
ENV CATALINA_HOME /opt/tomcat
ENV JRE_HOME /usr/lib/jvm/jre-17-openjdk

WORKDIR /
EXPOSE 8080

# 차후에 개발 환경이 바뀌었을 떄를 대비하여 Dockerfile을 작성하는 중...
