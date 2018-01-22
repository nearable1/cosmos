import mysql.connector

name='root'
password='root'
host='111.231.59.209'
database='myProject'

conn = mysql.connector.connect(user=name, password=password,host=host,database=database)
print("connect succ")

cu = conn.cursor()
sql = 'select * from user'
cu.execute(sql)
data = cu.fetchall()
if len(data) > 0:
    for x in range(len(data)):
        print(data[x])

cu.close()
conn.close()


#db = connect("111.231.59.209","root","root",myProject)

