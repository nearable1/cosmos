import urllib.request

#website
url = 'http://www.douban.com'

#request
request = urllib.request.Request(url)

#result
response = urllib.request.urlopen(url)

data = response.read()

#coding
data = data.decode('utf-8')

#print
print(data)

#print types info
print(type(response))
print(response.geturl())
print(response.info())
print(response.getcode())