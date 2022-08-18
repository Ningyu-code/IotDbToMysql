#### 将IotDB中数据存到Mysql中思路

```
1.根据查询条件将IOTDB数据库中数据取出,存为List<JSONObject>形式。
2.根据查到的IOTDB数据定制生成Mysql数据表信息。
3.根据上一步生成的表名，字段名，数据类型新建表。
4.将List<JSONObject>形式数据存到第三步所建的表中。
```



1.根据查询条件将IOTDB数据库中数据取出,存为List<JSONObject>形式。

![image-20220810172221615](https://raw.githubusercontent.com/Ningyu-code/IotDbToMysql/new/readme-pic/firstStep.jpg)

2.根据查到的IOTDB数据定制生成Mysql数据表信息。

![image-20220810172221615](https://raw.githubusercontent.com/Ningyu-code/IotDbToMysql/new/readme-pic/secondStep.png)

3.根据上一步生成的表名，字段名，数据类型新建表。

![image-20220810172221615](https://raw.githubusercontent.com/Ningyu-code/IotDbToMysql/new/readme-pic/thirdStep.png)

4.将List<JSONObject>形式数据存到第三步所建的表中。

![image-20220810172221615](https://raw.githubusercontent.com/Ningyu-code/IotDbToMysql/new/readme-pic/fourthStep.png)



连接数据库

![image-20220810172350681](https://raw.githubusercontent.com/Ningyu-code/IotDbToMysql/main/readme-pic/image-20220810172350681.png)

![image-20220810172453154](https://raw.githubusercontent.com/Ningyu-code/IotDbToMysql/main/readme-pic/image-20220810172453154.png)

只需要在yml文件中新增数据库信息，通过新建配置类，配置类利用@Value()标签选定新增数据库信息

即可连接不同主机IOTDB数据库。

