FROM gradle:7.1.0-jdk11
WORKDIR /app
COPY . /app

RUN gradle --no-daemon --warning-mode all

# fetch dependencies
RUN chmod +x start.sh 

# script which watches source file changes in background and executes bootRun
CMD ["sh", "start.sh"]

