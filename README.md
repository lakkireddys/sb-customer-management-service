Distributed Tracing - Splunk

https://www.splunk.com/en_us/blog/learn/distributed-tracing.html

docker run -d -p 8000:8000 -e "SPLUNK_START_ARGS=--accept-license" -e "SPLUNK_PASSWORD=splunk123" --name splunk splunk/splunk:latest


Configure HEC (httpEventCollector)
https://docs.splunk.com/Documentation/Splunk/9.4.0/Data/UsetheHTTPEventCollector

```
  <repositories>
    <repository>
        <id>splunk-artifactory</id>
        <name>Splunk Releases</name>
        <url>https://splunk.jfrog.io/splunk/ext-releases-local</url>
    </repository>
  </repositories>
```

Documentation:-
https://github.com/splunk/splunk-library-javalogging?tab=readme-ov-file
```
<dependency>
    <groupId>com.splunk.logging</groupId>
    <artifactId>splunk-library-javalogging</artifactId>
    <version>1.11.8</version>
    <scope>runtime</scope>
</dependency>
```
configure the logback-spring.xml----
https://dev.splunk.com/enterprise/docs/devtools/java/logging-java/howtouseloggingjava/enableloghttpjava/


```
Input Type: Token: 2dd33590-7401-42b7-a784-5bdd0e62ccec 
Name: my-demo-logging 
Source name: training-logging 
Default index: demo_index 
Source Type: log4j 
App Context: launcher 
url: localhost:8088
```
