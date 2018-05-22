import tweepy as tp
import time
   
# get credentials from the credentials.text file
file = open("credentials.text", "r") 
f = file.readlines();

# credentials to login to twitter api
consumerKey = f[0][:-1]
consumerSecret = f[1][:-1]
accessToken = f[2][:-1]
accessTokenSecret = f[3][:-1]

while True:
	# login to twitter account api
	auth = tp.OAuthHandler(consumerKey, consumerSecret)
	auth.set_access_token(accessToken, accessTokenSecret)
	api = tp.API(auth)
	   
	res = api.trends_place(1)[0]
	trends = res['trends']

	myList = []

	for i in range(10):
		myList.append(trends[i]['name'])


	api.update_status("Heyy, I will post the today's first ten trending topics. Stay Tuned...")
	tweet = ''
	i=1
	for name in myList:
	    tweet+=str(i)+". "+name+"\n"
	    i = i+1

	#print(tweet)

	time.sleep(60)
	api.update_status(status=tweet)
	time.sleep(60)
	api.update_status("Also check out our site: http://cildir.gq/cmpe352/#")
	time.sleep(84960) #wait one day
	

