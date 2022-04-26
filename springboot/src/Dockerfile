# Docker image for springboot file run
# VERSION 0.0.1
# Author: eangulee
# 基础镜像使用java
FROM java:8
#作者
MAINTAINER keasonyan <keasonyan@163.com>
# 修改时区
RUN rm -f /etc/localtime \
&& ln -sv /usr/share/zoneinfo/Asia/Shanghai /etc/localtime \
&& echo "Asia/Shanghai" > /etc/timezone
ARG env_from
# VOLUME 指定了临时文件目录为/tmp。
# 其效果是在主机 /var/lib/docker 目录下创建了一个临时文件，并链接到容器的/tmp
VOLUME /tmp
# 将jar拷贝到容器，【注意写ADD时：Dockerfile文件和打包的jar的目录关系】
# 参考 https://blog.csdn.net/byamao1/article/details/77389057
ADD target/test-SNAPSHOT.jar test-SNAPSHOT.jar
RUN bash -c 'touch /test-SNAPSHOT.jar'

# 运行jar包
RUN bash -c 'echo java -jar -Djava.security.egd=file:/dev/./urandom -Dspring.profiles.active=$env_from /test-SNAPSHOT.jar'

# 运行jar包
#ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-Dspring.profiles.active=$env_from","-jar","/test-SNAPSHOT.jar"]
#run bash -c  'java -jar -Djava.security.egd=file:/dev/./urandom -Dspring.profiles.active=$env_from /test-SNAPSHOT.jar'
#ENTRYPOINT ["sh","-c","exec java -Djava.security.egd=file:/dev/./urandom -Dspring.profiles.active=$env_from -jar /test-SNAPSHOT.jar"] \
ENTRYPOINT ["bash","-c","java -XX:-OmitStackTraceInFastThrow -jar -Djava.security.egd=file:/dev/./urandom -Dspring.profiles.active=$env_from -Xms1024m -Xmx3072m -XX:+UseParNewGC -XX:+UseConcMarkSweepGC -verbose:gc -XX:+PrintGCDateStamps -XX:+PrintGCDetails -Xloggc:/gc.log /test-SNAPSHOT.jar"]