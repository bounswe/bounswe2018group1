import tweepy as tp
import time
   
# get credentials from the credentials.text file
file = open("credentials.text", "r") 
f = file.readlines();

# credentials to login to twitter api
consumer_key = f[0][:-1]
consumer_secret = f[1][:-1]
access_token = f[2][:-1]
access_token_secret = f[3][:-1]

while True:
	auth = tweepy.OAuthHandler(consumer_key, consumer_secret)
	auth.set_access_token(access_token, access_token_secret)
	api = tweepy.API(auth,wait_on_rate_limit=True)

	i = 1

	for tweet in tweepy.Cursor(api.search,q="#followfriday",count=100,
		                   lang="en",
		                   since="2018-04-12").items():
	    api.update_status("#ff Its Friday! Here is a retweet from that tt: "+tweet.text)
	    if i == 1:
		break
	time.sleep(604800)

