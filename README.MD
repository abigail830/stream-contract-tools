
# stream-contract-tools

Welcome to the stream-contract-maven-plugin for Apache Maven 3.

This plugin provides a means to perform HTTP GET operation on a URL to retrieve response and store the results into files.

## common-info
It contains a contract object which included fields:

* String fileName [mandatory]
* String filePath
* String fileContent
* String fileExtension

We expect the REST response json should be a List of object which included above fields.

## stream-contract-maven-plugin

### Goal:
* cleanContract
* streamConvert

### How to config in client side?
In pom.xml


```xml
	<build>
		<plugins>
			<plugin>
				<groupId>com.github.abigail830</groupId>
				<artifactId>stream-contract-maven-plugin</artifactId>
				<configuration>
					<targetContractDirectory>contracts</targetContractDirectory>
					<restEndPoints>
						<RestEndPoint>
							<baseUrl>http://127.0.0.1:8080/contracts/ContractProvider</baseUrl>
						</RestEndPoint>
					</restEndPoints>
				</configuration>
			</plugin>
		</plugins>
		
		<!-- To attach the goal with maven phase ->
		<pluginManagement>
			<plugins>
				<plugin>
					<groupId>com.github.abigail830</groupId>
					<artifactId>stream-contract-maven-plugin</artifactId>
					<version>1.0-SNAPSHOT</version>
					<executions>
						<execution>
							<id>cleanContract</id>
							<phase>clean</phase>
							<goals>
								<goal>cleanContract</goal>
							</goals>
						</execution>
						<execution>
							<id>streamConvert</id>
							<phase>compile</phase>
							<goals>
								<goal>streamConvert</goal>
							</goals>
						</execution>
					</executions>
				</plugin>
			</plugins>
		</pluginManagement>
	</build>
```


## TODO
* Develop stream-contract-gradle-plugin
* Support http header config
* Review if need to support http POST


# Enjoy :-)
