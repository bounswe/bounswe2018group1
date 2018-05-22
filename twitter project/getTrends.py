from pymongo import MongoClient
import datetime
import twitter
import time
import requests
import json

oembed_base_url = "https://publish.twitter.com/oembed?url=https%3A%2F%2Ftwitter.com%2FInterior%2Fstatus%2F"

api = twitter.Api(consumer_key='', consumer_secret='', access_token_key='', access_token_secret='')

client = MongoClient('')
db = client['cmpe352twitter-proj-dev']
tweet = db['tweetEntity']
topics = db['trendingTopicEntity']

year = datetime.datetime.today().year
month = datetime.datetime.today().month
day = datetime.datetime.today().day
date = str(year) + "-" +  str(month) + "-" + str(day)

trend = api.GetTrendsWoeid(23424969)
trend = trend[0:10]
datetr = date + "-tr"
for topic in trend:
    tweets = api.GetSearch(term=topic.name , count = 100 , result_type = 'popular')
    trend_id = topics.insert_one({
        "date" : date ,
        "name" : topic.name , 
        "regionId" : 23424969
    }).inserted_id
    for tw in tweets:
        print tw.id
        #html = api.GetStatusOembed(status_id = tw.id , omit_script=False)
        while True:
            try:
                s = requests.get(oembed_base_url + str(tw.id)).text
                html = json.loads(s)
                break
            except:
                time.sleep(5)
                print "trying again"
                continue
        print html['html'] 
        tweet.insert_one({
            "trendingTopicId" : trend_id ,
            "twitterId" : html['html']
        })

trend = api.GetTrendsCurrent()
trend = trend[0:10]
dateww = date  + "-ww"
for topic in trend:
    tweets = api.GetSearch(term=topic.name, count=100 , result_type = 'popular')
    trend_id = topics.insert_one({
        "date": date,
        "name": topic.name , 
        "regionId" : 1
    }).inserted_id
    for tw in tweets:
        print tw.id
        #html = api.GetStatusOembed(status_id = tw.id , omit_script=False)
        while True:
            try:
                s = requests.get(oembed_base_url + str(tw.id)).text
                html = json.loads(s)
                break
            except:
                time.sleep(5)
                print "trying again"
                continue
        print html['html']
        tweet.insert_one({
            "trendingTopicId": trend_id,
            "twitterId": html['html']
        })
print "Today's work has finished!"
