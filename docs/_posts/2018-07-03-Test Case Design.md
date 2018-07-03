---
layout: post
title: "Test Case Design"
date: 2018-07-03
---

## Testing Process

Testing is an integral part of any Software Development process. Testing helps the developers to identify bugs/defects and fix them.
The different types of test methods are:
* Unit Testing
* Integration Testing
* Functional Testing
* System Testing
* Acceptance Testing

Test methods can be further classified into 
* White-Box Testing: Testing performed with the knowledge of internal code.
* Black-Box Testing: Testing performed without the knowledge of internal code.

**Unit Testing:** Each team member has tested and fixed all defects arising from their individual unit component. White-Box testing method was used for this testing. `Example: Login Screen`

**Integration Testing:** Each individual unit component was integrated with other unit components and tested. White-Box and Black-Box testing methods were used for this testing. `Example: Enabling the PIN from Settings Screen and validating the user defined PIN in Login Screen.`

**Functional Testing:** All the functional requirements provided by the user were tested. Black-Box testing method was used for this testing. `Example: As a user, I want to log activities.`

**System Testing:** The code was tested in different environments/systems. Black-Box testing method was used for this testing. `Example: App tested on devices running Android Oreo and Android Nougat.`

**Acceptance Testing:** The App was shown to the customer and tested for every requirement provided by him. Black-Box testing method was used for this testing. 

All the bugs are logged into Zenhub for tracking the efforts involved in fixing them.  

## White-Box Testing

White-Box Testing is a type of testing which involves test case design by knowing the internal code.

The following classes were tested using White-Box Testing:

#### LoginActivity: Validate user credentials

<img src="{{site.baseurl}}/images/LoginActivity.png" alt="LoginActivity"><img src="{{site.baseurl}}/images/LoginActivityFlow.png" alt="LoginActivityFlow">

* Scenario 1:

| set_pin  | entered_pin  | path(line numbers) |
|:--------:|:------------:|:------------------:|
| 1234     | 1234         | 37,40,41,42,43     |

* Scenario 2:

| set_pin  | entered_pin  | path(line numbers) |
|:--------:|:------------:|:------------------:|
| 1234     | 1111         | 37,40,44,45,46     |

#### LogActivity: Validate EndDateTime is not before StartDateTime

<img src="{{site.baseurl}}/images/LogActivity.png" alt="LogActivity"><img src="{{site.baseurl}}/images/LogActivityFlow.png" alt="LogActivityFlow">

* Scenario 1:

| convertedStartDate  | convertedEndDate  | path(line numbers) |
|:-------------------:|:-----------------:|:------------------:|
| 2018-07-02 08:00    | 2018-07-02 08:30  | 233,234,235        |

* Scenario 2:

| convertedStartDate  | convertedEndDate  | path(line numbers) |
|:-------------------:|:-----------------:|:------------------:|
| 2018-07-02 08:00    | 2018-07-02 07:30  | 233,236,237,238    |

#### ReviewGraph: Load data into the Pie Chart

<img src="{{site.baseurl}}/images/ReviewGraph.png" alt="ReviewGraph"><img src="{{site.baseurl}}/images/ReviewGraphFlow.png" alt="ReviewGraphFlow">

* Scenario 1:

| category_names.size()  | path(line numbers)                     |
|:----------------------:|:--------------------------------------:|
| 2                      | 353,356,357,353,356,357,353,358        |

* Scenario 2:

| category_names.size()  | path(line numbers)|
|:----------------------:|:--------------:|
| 0                      | 353,358        |

#### DisplayActivity: Load the categories into category spinner

<img src="{{site.baseurl}}/images/DisplayActivity.png" alt="DisplayActivity"><img src="{{site.baseurl}}/images/DisplayActivity.png" alt="DisplayActivityFlow">

* Scenario 1:

| called method  | path(line numbers) |
|:-----------------:|:-------------------:|
| loadSpinnerData() | 166,179,181-200,202 |

#### MainActivity: Navigate using Menu Drawer

<img src="{{site.baseurl}}/images/MainActivity.png" alt="MainActivity"><img src="{{site.baseurl}}/images/MainActivityFlow.png" alt="MainActivityFlow">

* Scenario 1:

| menuItem(clicked) | path(line numbers) |
|:-----------------:|:-------------------:|
| Log Activity | 118,120,121-122 |

* Scenario 2:

| menuItem(clicked) | path(line numbers) |
|:-----------------:|:-------------------:|
| Review | 118,120,123,124-125 |

## Black-Box Testing

## Summary of Changes

The following changes were made to the application:
* Subcategories are added to the Categories page.
* 
