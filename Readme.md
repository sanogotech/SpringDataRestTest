POST /houses

{
"name" : "ma maison",
"inhabitants":[
{
"name":"Baptiste",
"age":45
},{
"name":"Joe",
"age":18
},{
"name":"Sophie",
"age":200
}
],
"furnitures":[
{
"name":"bed",
"price":5
},{
"name":"tv",
"age":10
},
{
"name":"sofa",
"age":200
}
]
}

GET /houses => toute la liste

GET /houses/1 => la première liste

GET /houses/1/furnitures => 404

