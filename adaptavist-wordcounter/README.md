## Word Frequency Counter

### Description
This program takes an input file containing words and prints the frequency of each word in the file to the console; or an output file if specified.

### Assumptions
- Words are only made up of alphabetical characters
- Words are separated by space
- Words are all considered lowercase
- Punctuation marks are ignored
- The input file can be empty

### Running the program

#### Build Jar
```java
mvn clean install
```

#### Usage
```java
java -jar target/adaptavist-wordcounter-1.0-SNAPSHOT.jar [input file] [optional: output file]
```

#### Example
```java
java -jar target/adaptavist-wordcounter-1.0-SNAPSHOT.jar src/test/resources/data/input/text.txt
```

#### Logging
Logging is done using logback. The log files are located at `logs/wordcounter.log`
The log level is set to `INFO` by default. To change the log level, modify the `logback.xml` file.

