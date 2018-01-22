import mysql.connector

name='root'
password='root'
host='111.231.59.209'
database='myProject'

#连接mysql，相当于jdbc中的connection
conn = mysql.connector.connect(user=name, password=password,host=host,database=database)

#获取游标
cu = conn.cursor()
#sql文
sql = 'select name,password from user'
#运行
cu.execute(sql)
#接收全部的返回结果行
data = cu.fetchall()
#遍历
if len(data) > 0:
    for x in range(len(data)):
        print(data[x])

cu.close()
#提交
conn.commit()
conn.close()