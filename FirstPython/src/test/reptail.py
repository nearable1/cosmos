import re  
import urllib.request   
import os  
  
def getHtml(url):  
    page = urllib.request.urlopen(url)  
    html = page.read()  
      
    return html.decode('UTF-8')  
  
def getImg(html):  
    reg = r'src="(.+?\.jpg)" pic_ext' #Ҫ�����ţ���ΪԪ�鷵�أ�ץȡ�Ա���ͼƬpng(�ȿ�Դ����ͼƬ�ĵ�ַ·��)reg = r'data-lazy="(.+?\.png)" '  
    imgre = re.compile(reg)  
    imglist = imgre.findall(html)  
    x = 0  
    path = 'D:\\test'  
    if not os.path.isdir(path):  
        os.makedirs(path)  
    paths = path+'\\'      #������test·����  
    for imgurl in imglist:  
        urllib.request.urlretrieve(imgurl,'{}{}.jpg'.format(paths,x))  
        x = x + 1          
     
html = getHtml("http://tieba.baidu.com/p/2460150866") #�Ա��ģ�html = getHtml(r"http://www.taobao.com/")  
getImg(html)  