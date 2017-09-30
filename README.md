# Wallethub Log Parser
Project created for the WalletHub team to get a better idea of my technical and problem solving skills

##Instructions
The goal is to write a parser in Java that parses web server access log file, loads the log to MySQL and checks if a 
given IP makes more than a certain number of requests for the given duration. 

###Java


1. Create a java tool that can parse and load the given log file to MySQL. The delimiter of the log file is pipe (|)
1. The tool takes "startDate", "duration" and "threshold" as command line arguments. "startDate" is of 
`yyyy-MM-dd.HH:mm:ss` format, "duration" can take only "hourly", "daily" as inputs and "threshold" can be an integer.
1. This is how the tool works:

> java -cp "parser.jar" com.ef.Parser --startDate=2017-01-01.13:00:00 --duration=hourly --threshold=100

The tool will find any IPs that made more than 100 requests starting from 2017-01-01.13:00:00 to 2017-01-01.14:00:00 
(one hour) and print them to console AND also load them to another MySQL table with comments on why it's blocked.

> java -cp "parser.jar" com.ef.Parser --startDate=2017-01-01.13:00:00 --duration=daily --threshold=250

The tool will find any IPs that made more than 250 requests starting from 2017-01-01.13:00:00 to 2017-01-02.13:00:00 
(24 hours) and print them to console AND also load them to another MySQL table with comments on why it's blocked.

###SQL

1. Write MySQL query to find IPs that mode more than a certain number of requests for a given time period.

> E.g.: Write SQL to find IPs that made more than 100 requests starting from 2017-01-01.13:00:00 to 2017-01-01.14:00:00.

1. Write MySQL query to find requests made by a given IP.

## Built With

* [Maven](https://maven.apache.org/) - Dependency Management

## Author

* **Eliecer Hernandez** - [eliecerhdz@gmail.com](mailto:eliecerhdz@gmail.com)

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details