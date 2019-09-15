
/**
 * Display the users and Calculates popular hashtags (topics) over sliding 10 and 60 second windows from a Twitter
 * stream and save the last 60 sec hashtag tweets into textfile of given argument 
 
 *The stream is instantiated with pathtosave,credentials and optionally filters supplied by the explained in the readme.txt file

 */


import org.apache.spark.streaming.{Seconds, StreamingContext}
import StreamingContext._
import org.apache.spark.SparkContext._
import org.apache.spark.streaming.twitter._
import org.apache.spark._

/**
 * Calculates popular hashtags (topics) over sliding 10 and 60 second windows from a Twitter
 * stream. 
 
 *The stream is instantiated with credentials and optionally filters supplied by the
 * command line arguments.
 *
 */

object TwitterPopularTags {

  def main(args: Array[String]) {
    if (args.length < 5) {
      System.err.println("Usage: pathtosave" + "TwitterPopularTags <consumer key> <consumer secret> " +
        "<access token> <access token secret> [<filters>]")
      System.exit(1)
    }

    // Set logging level if log4j not configured (override by adding log4j.properties to classpath)

    val Array(pathtosave,consumerKey, consumerSecret, accessToken, accessTokenSecret) = args.take(5)
    val filters = args.takeRight(args.length - 5)

    // Twitter Authentication credentials should pass with arguments
    System.setProperty("twitter4j.oauth.consumerKey", consumerKey)
    System.setProperty("twitter4j.oauth.consumerSecret", consumerSecret)
    System.setProperty("twitter4j.oauth.accessToken", accessToken)
    System.setProperty("twitter4j.oauth.accessTokenSecret", accessTokenSecret)


    val ssc = new StreamingContext("local[2]", "TwitterPopularTags", Seconds(2),
      System.getenv("SPARK_HOME"), StreamingContext.jarOfClass(this.getClass))
    val stream = TwitterUtils.createStream(ssc, None, filters)

    // process initiated
      
   println("===Direct Stream Print===")
      
      
      
    // user details printing user details on console
    
      
    val users = stream.map(userJson => userJson.getUser())
    val usersCol = stream.map(user => user.getUser().getName()+ "@" + user.getUser().getFollowersCount())
   
    usersCol.foreachRDD(rdd=>rdd.foreach(println))

    
      
    // getting the hashtags
    val hashTags = stream.flatMap(status => status.getText.split(" ").filter(_.startsWith("#")))


    val topCounts60 = hashTags.map((_, 1)).reduceByKeyAndWindow(_ + _, Seconds(60))
      .map{case (topic, count) => (count, topic)}
      .transform(_.sortByKey(false))

    val topCounts10 = hashTags.map((_, 1)).reduceByKeyAndWindow(_ + _, Seconds(10))
      .map{case (topic, count) => (count, topic)}
      .transform(_.sortByKey(false))

      
    // Print popular hashtags
      
      
    topCounts60.foreachRDD(rdd => {
      val topList = rdd.take(10)
      println("\nPopular topics in last 10 seconds (%s total):".format(rdd.count()))
      topList.foreach{case (count, tag) => println("%s (%s tweets)".format(tag, count))}
      // we can add arguments or path 
      //topCounts60.saveAsTextFiles("file:///home/shashireddy408417/sparkstream/streaming/for60count","txt")
      //topCounts60.saveAsHadoopFiles("hdfs:///user/shashireddy408417/outfit","txt")
        
        topCounts60.saveAsTextFiles(pathtosave)
    })

      topCounts10.foreachRDD(rdd => {
        val topList = rdd.take(5)
        println("\nPopular topics in last 10 seconds (%s total):".format(rdd.count()))
        topList.foreach{case (count, tag) => println("%s (%s tweets)".format(tag, count))}
        
        // if we want to save topcounts10 we can save by giving path 
        //topCounts10.saveAsTextFiles("file:///home/shashireddy408417/sparkstream/streaming/for10count","txt")
      
      })
      
      
      ssc.start()
      ssc.awaitTermination()
    }
  }
