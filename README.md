#### 将IotDB中数据存到Mysql中思路

```
将IOTDB数据库中数据取出，用实体类接收，实体类属性与Mysqls数据表属性一致。
```



插入IOTDB数据

![image-20220810172221615](C:\Users\ningyu\AppData\Roaming\Typora\typora-user-images\image-20220810172221615.png)

MySQL存储数据

![image-20220810172251663](C:\Users\ningyu\AppData\Roaming\Typora\typora-user-images\image-20220810172251663.png)





动态连接数据库

![image-20220810172350681](C:\Users\ningyu\AppData\Roaming\Typora\typora-user-images\image-20220810172350681.png)

![image-20220810172453154](C:\Users\ningyu\AppData\Roaming\Typora\typora-user-images\image-20220810172453154.png)

只需要在yml文件中修改IP地址即可连接不同主机IOTDB数据库。


