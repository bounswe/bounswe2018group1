from pymongo import MongoClient
import datetime
import twitter
import time

while True:
    api = twitter.Api(consumer_key='', consumer_secret='', access_token_key='', access_token_secret='')

    client = MongoClient('mongodb://backend:123123@ds241869.mlab.com:41869/cmpe352twitter-proj-dev')
    db = client['cmpe352twitter-proj-dev']
    tweet = db['tweetEntity']
    topics = db['trendingTopicEntity']

    year = datetime.datetime.today().year
    month = datetime.datetime.today().month
    day = datetime.datetime.today().day
    date = str(day) + "-" +  str(month) + "-" + str(year)

    trend = api.GetTrendsWoeid(23424969)
    trend = trend[0:10]
    datetr = date + "-tr"
    for topic in trend:
        tweets = api.GetSearch(term=topic.name , count = 100)
        trend_id = topics.insert_one({
            "date" : datetr ,
            "name" : topic.name
        }).inserted_id
        for tw in tweets:
            html = api.GetStatusOembed(status_id = tw.id , omit_script=False)
            #print(html['html'])
            tweet.insert_one({
                "trendingTopicId" : trend_id ,
                "twitterId" : html['html']
            })

    trend = api.GetTrendsCurrent()
    trend = trend[0:10]
    dateww = date  + "-ww"
    for topic in trend:
        tweets = api.GetSearch(term=topic.name, count=100)
        trend_id = topics.insert_one({
            "date": dateww,
            "name": topic.name
        }).inserted_id
        for tw in tweets:
            html = api.GetStatusOembed(status_id=tw.id, omit_script=False)
            #print(html['html'])
            tweet.insert_one({
                "trendingTopicId": trend_id,
                "twitterId": html['html']
            })
    print("Today's work has finished!")
    time.sleep(86400)

