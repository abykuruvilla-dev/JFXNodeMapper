# JFXNodeMapper

[![Build Status](https://camo.githubusercontent.com/cfcaf3a99103d61f387761e5fc445d9ba0203b01/68747470733a2f2f7472617669732d63692e6f72672f6477796c2f657374612e7376673f6272616e63683d6d6173746572)](https://github.com/CaptainParanoid/JFXNodeMapper)

JFXNodeMapper is a simple library that focuses on mapping data from common data represntation formats to JavaFx Nodes. Our main focus is to build a library that,

  - Requires minimal configuration.
  - Easy to understand.
  - Small Size.

# Features

  - Automatic Node traversal to find children nodes and assign data to them based on their ids.
  - Accept JSON and ResultSet as data source.
  - Support custom mappings for nodes that has custom datatypes using event listners
  - Supports customised datatype conversions.
  
#   Upcoming Features
- Support for csv and xml.
- Reverse mapping, i.e convert Nodes to JSON ,CSV and XML,


# How to use JFXNodeMapper in your project
  - Add JFXNodeMapper to your project
  - Assign id to nodes same as the Key/Column name
  - (Optional) Assign custom mapping to node
  - Pass the root node or parent node that contains the required fields to be mapped.
  - Pass data source.
  - See the Magic
  
# Example

/** Example 1 : Mapping from a JSON string*/

Scene scene = parent.getScene();
Node root=   scene.getRoot();
DataMapper mapper =new DataMapper();
mapper.setRoot(root);
String json=getJsonFromServer();
mapper.setDataFromJSON(json);// json keys and root ids shoudl match;


/** Example 1 : Mapping from a ResultSet object*/

Scene scene2 = parent2.getScene();
Node root2=   scene2.getRoot();
DataMapper mapper2 =new DataMapper();
mapper2.setRoot(root2);
Resulset resultset=getAllStudentDetails();
mapper2.setDataFromJSON(resultSet);//column name and root ids should match;



** Example 1 : Mapping from json String with Custom mapping*

Scene scene3 = parent3.getScene();
Node root3=   scene.getRoot();
DataMapper mapper3 =new DataMapper();
mapper3.setRoot(root3);
String json2=getJsonFromServer();
//this listner will be called whenever the specified id is encountered.
//this will override all other mappings for the specified id
mapper3.mapToCustomDataType("subject-combo", (data, id, node) ->
            ComboBox<String> subs=(ComboBox<String>) node;
            String subject=(String) data;
            subs.getItems.add(subject);
        });
mapper3.setDataFromJSON(json);// json keys and root ids shoudl match;

