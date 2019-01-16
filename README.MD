
# stream-contract-tools

Welcome to the stream-contract-maven-plugin for Apache Maven 3.

This plugin provides a means to perform HTTP GET operation on a URL to retrieve response and store the results into files.

**Background**

During implement contract test with spring-cloud-starter-contract-verifier, by default spring verifier plugin support to get contract from resource/contracts folder, or remote repository, or Jar. Basically we could using git submodule way to structure the contracts.

However, sometimes we hope to limit the user only able to view related contracts instead of all of them. While at the same time, we also need a central view of all contracts. So, store the contract into said mongo DB may easier.

As a result, we could achieve it in this way:
1. All contracts stored in mongo DB with a service provided CRUD of contract
2. Client make use of below plugin, HTTP GET above mongo service to collect associated contracts and generated into files
3. Using spring verifier to continue generate contract Test and Stub.jar




## common-info
It contains a contract object which included fields:

* String fileName [mandatory]
* String filePath
* String fileContent
* String fileExtension

We expect the REST response json should be a List of JSON object which included above fields.

## stream-contract-maven-plugin

### Goal:
* cleanContract - This is to cleanup the targetDirectory as specified
* streamConvert - This is to trigger HTTP GET base on common.RestEndPoint config and extract into files

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
						<common.RestEndPoint>
							<baseUrl>http://127.0.0.1:8080/contracts/ContractProvider</baseUrl>
						</common.RestEndPoint>
					</restEndPoints>
				</configuration>
			</plugin>
		</plugins>
		
		<!-- To attach the goal with maven phase -->
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
