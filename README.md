This is project which tackles the security concern about multiple request from clinet in given time window.
To avoid malicious attack to the system by making too many request in small window of time, rate limiter is implemented. 
Rate limiter allows only configured number of request per unit time.
Jar file is created for logic of rate limiting and AOP is used. 
Same jar file is used in targeted application for security purposes.
