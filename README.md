impact Assignnment Twitter Analysis


Please Note: For Complete Project with all dependencies Download  only impact_final.Zip file and extract
and follow the below steps




Objective:
Design and build an application that connects to and ingests data from twitter using any of the publicly available twitter APIs. The application should perform multiple aggregations and transformations on the data set and publish the results to the console and to file.

Popular hashtags will save in given argument file and it will print in console as well, please follow the below steps. 




spark-streaming-twitter-hashtag Calculates popular hashtags (topics) over sliding 10 and 60 second windows from a Twitter  stream.
=====================================================================================================================================


Project Structure
==================
impact_final.Zip>>extract

>>>>>>  bin------------------- Run ./bin/sbt.sh
	build.sbt
	log.err
	project
	README.md 
	output_sample.txt
	src---------------------------------------Source Code is available in src/main/Scala/TwitterPopularTags.scala 
	target


How to Run
==========

1. Open Terminal

2. Run ./bin/sbt.sh from project root directory

3. you will be in the scala command shell

4. run following commands
    > clean

    > package
    
    > run pathtosave consumerKey consumerSecret accessToken accessTokenSecret [filter1,filter2] 
	
	
	(filters are optional,pathtosave change as per your directory,below are the examples, API Keys should replace with your authorization keys)
	
	Below are the sample Argument 

run file:///home/ssr/Documents/outputfile/for60count O9OCPLFT5IDMqdqKFZKGcYpJn Rvil5TfqAp4ufMVr0vpOkKoLVUxUnJjN1Nsqzilnfe3p3wLgVW 1919544354-J5HXw3P4AGdqQaJit5whdeVd1aZCeCRPwIjuDH8 Y724hhIf0R3dqxuFMYB7gJheGQaX8EVtZQPbQ15no3Z18 football cricket


Please note that, we can open same project in InteliJ Idea (my laptop is dead slow just 4GB ram, So i have used SBT console),for more info another textfile added sample_oupput.txt
