# AFAGOAL——SALEMAN_CLIENT 

## <a name="注册业务员"></a>注册业务员
* URL ： https://www.hamster.afagoal.top/salemans
* method : post
* 参数

```
{
	"product_name":"房产",
	"product_id":10,
	"saleman_intro":"房产经历"
}
```

* 返回

```
{
    "msg": null,
    "rc": 0,
    "data": "提交成功！"
}
```

## <a name="添加客户"></a>添加客户

* URL ： https://www.hamster.afagoal.top/clients
* method ： POST
* 参数
```
{
	"product_name":"房地产",
	"product_id":1,
	"client_name":"新疆哥",
	"client_state":"0",
	"client_mobile":"18267898765",
	"client_source":1,
	"notice_date_str":"2018-07-18",
	"client_address":"杭州",
	"client_level":1,
	"client_industry":1
}
```

* 参数说明

| 参数  | 参数名称  | 参数类型 | 是否必传 |  说明  |
|:------------- |:---------------:| -------------:| ------:|------:|
|产品名称   |product_name         |  String   |   是  |   |
|产品ID   |product_id            |     String     |  是  | 暂时不传，产品管理TODO|
|客户名称   | client_name            |  String       |   是 | |
|客户状态   | client_state          |   byte      |  是   | 客户状态 0-初步接触，1-意向客户，2-报价客户，3-成交客户，4-搁置客户 |
|客户电话   | client_mobile        |      String   |  是   | |
|客户来源   | client_source  |   Byte     |   否  | 客户来源 0-广告，1-社交推广，2-研讨会，3-门户网站，4-客户介绍 5-其他 |
|提醒日期   | notice_date_str |  String       |   否  | 2018-07-17 |
|客户地址    | client_address     |   String      |  否   | |
|客户等级| client_level       |   Byte     |  否   | 客户等级 0-低价值客户，1-一般客户 2-重要客户 |
|所属行业| client_industry   |    Byte     |    否 |客户行业 0-服务，1-金融，2-医疗，3-政府，4-教育 5-旅游 6-媒体 7-重工业 8-机械 9-其他 |

* 返回
```
{
    "msg": null,
    "rc": 0,
    "data": "添加成功！"
}
```