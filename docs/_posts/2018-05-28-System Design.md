---
layout: post
title: "System  Design"
date: 2018-05-28
---
Hello Everyone! Welcome to our third blog on System Design. After analyzing the requirements and formulating the corresponding user stories, we are now in the design phase. This process involves defining the architecture, modules, interfaces, and data for our system to satisfy specified requirements.

We have used interaction diagrams to represent the behavior of the system and class diagrams for the classes and methods that will be used. We will introduce the design pattern which we will be adapting in our development process. We will also see the development strategy of our team and some changes that occurred in the past two weeks. 

## Behavioral Diagram 

#### Interaction diagram for use case "Logging an activity"
<img src="{{site.baseurl}}/images/Sequencediagram.png" alt="UCD Sequencediagram" width="1526" height="350">

#### Activity diagram for use case "review activity"
<img src="{{site.baseurl}}/images/Review.PNG" alt="UCD Review" width="1526" height="350">


## Class Diagrams
<img src="{{site.baseurl}}/images/Classdiagram.jpg" alt="UCD Classdiagram" width="1526" height="350">

#### Login
<img src="{{site.baseurl}}/images/Classlogin.PNG" alt="UCD Classlogin" width="1526" height="350">

#### HomeScreen
<img src="{{site.baseurl}}/images/Classhome.PNG" alt="UCD Classhome" width="1526" height="180">

#### Settings
<img src="{{site.baseurl}}/images/Classsettings.PNG" alt="UCD Classsettings" width="1526" height="350">

#### Review
<img src="{{site.baseurl}}/images/Classreview.PNG" alt="UCD Classreview" width="1526" height="350">

#### NavigationPane
<img src="{{site.baseurl}}/images/Classnavigation.PNG" alt="UCD Classnavigation" width="1526" height="200">

#### Logactivity
<img src="{{site.baseurl}}/images/Classlog.PNG" alt="UCD Classlog" width="1526" height="350">

#### RoomDatabase
<img src="{{site.baseurl}}/images/Classdatabase.PNG" alt="UCD Classdatabase" width="1526" height="350">

#### Category
<img src="{{site.baseurl}}/images/Classcategory.PNG" alt="UCD Classcategory" width="1526" height="350">

#### ActivityClass
<img src="{{site.baseurl}}/images/Classactivity.PNG" alt="UCD Classactivity" width="1526" height="350">


## Design Pattern 

## Development Strategy
To manage and monitor our work and especially our progress we stay in close contact with each other. Most importantly to mention are our regular meetings. Apart from meeting directly after the customer meeting, we also started to meet at least one more time during the week. Meetings after the appointment with the customer are used to discuss the outcome of that meeting, new or changed information and to plan the next sprint. That includes adding user stories to the dashboard or modify existing ones. That is followed by estimating the user stories included in the next sprint and split work accordingly.

Additional meetings are set when there is a specific topic to be discussed like, e.g., how our system design should look like. Those meetings are precisely documented and to keep a general view over our progress.When everyone has gotten his or her responsibilities for the next week we start working on the respective topic, update the other team members or ask for support via slack and move the user stories on the dashboard according to our progress.Our experience over the last weeks is, that this approach works out pretty good for our team. Everybody gets kept in the loop, knows where possible risks are and where additional effort is necessary. 
That helps us to reach optimal success in our project.

<img src="{{site.baseurl}}/images/DevelopStrat.PNG" alt="UCD DevelopStrat" width="1526" height="350">


## Summary of changes  
During our team meetings, we came across few requirements which we were uncertain about. For instance, we were not sure if we need to include a calendar view in the home screen to review the activities. Likewise, what happens when a user adds more than one activity for the same time stamp. We discussed these questions with the customer and decided to go with below requirements.
> * Home screen will not have a calendar view but it should display last 10 activities. 

> * User can add multiple activities with overlapping time period.  

#### Thank you for visiting our blog!! See you next time with more interesting details on Implementation.  


