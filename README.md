# spring-boot-with-mongodb
## Create buildings collection and add this data to it
`
{
    "_id" : "1",
    "name" : "OSI Consulting Pvt Ltd",
    "address" : "Madhapur",
    "block_name" : " ",
    "parking_level" : "G",
    "total_car_slots" : 10,
    "remaining_car_slots" : 3,
    "total_bike_slots" : 5,
    "remaining_bike_slots" : 0,
    "_class" : "com.osi.vehicle_access.model.Building"
},
{
    "_id" : "2",
    "name" : "OSI Consulting Pvt Ltd",
    "address" : "Madhapur",
    "block_name" : "",
    "parking_level" : "B1",
    "total_car_slots" : 50,
    "remaining_car_slots" : 40,
    "total_bike_slots" : 70,
    "remaining_bike_slots" : 20,
    "_class" : "com.osi.vehicle_access.model.Building"
},
{
    "_id" : "3",
    "name" : "OSI Consulting Pvt Ltd",
    "address" : "Madhapur",
    "block_name" : "",
    "parking_level" : "B2",
    "total_car_slots" : 30.0,
    "remaining_car_slots" : 20.0,
    "total_bike_slots" : 50.0,
    "remaining_bike_slots" : 40.0
}
`

## Create persons collection and add this data to it
`
{
    "_id" : "NS1075",
    "name" : "abc",
    "designation" : "Delivery Manager",
    "supervisor_id" : "NS0034",
    "supervisor_name" : "def",
    "email" : "def@def.com",
    "mobile" : "+91 343434",
    "vehicle_numbers" : [ 
        {
            "number" : "AP13L7277",
            "type" : "CAR"
        }, 
        {
            "number" : "AP29D5136",
            "type" : "BIKE"
        }
    ],
    "photo" : "",
    "type" : "EMPLOYEE",
    "address" : "HYDERABAD",
    "valid_from" : ISODate("2015-08-19T00:00:00.000Z"),
    "valid_to" : ISODate("2100-12-31T00:00:00.000Z"),
    "remarks" : ""
},
{
    "_id" : "NS1247",
    "name" : "ttt",
    "designation" : "Associate Software Engineer",
    "supervisor_id" : "NS0034",
    "supervisor_name" : "yyy",
    "email" : "yyy@yyy.com",
    "mobile" : "+91 454544",
    "photo" : "",
    "type" : "EMPLOYEE",
    "address" : "HYDERABAD",
    "valid_from" : ISODate("2016-07-11T00:00:00.000Z"),
    "valid_to" : ISODate("2100-12-31T00:00:00.000Z"),
    "remarks" : "",
    "vehicle_numbers" : [ 
        {
            "number" : "TS09EM4567",
            "type" : "CAR"
        }, 
        {
            "number" : "JH22P4321",
            "type" : "BIKE"
        }
    ]
}
`
## Url to hit
### http://localhost:8080/getPlateDetails?checkType=lookup&vehicleNumber=TS09EM4567 creates log entry into vehicleLog collection
### http://localhost:8080/getPlateDetails?checkType=check&vehicleNumber=TS09EM4567 only checks presence no log created
### Most of the aggregation are on vehicleLog collection. So create the logs.
