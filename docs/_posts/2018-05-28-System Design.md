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
<img src="{{site.baseurl}}/images/Classhome.PNG" alt="UCD Classhome" width="1526" height="150">

#### Settings
<img src="{{site.baseurl}}/images/Classsettings.PNG" alt="UCD Classsettings" width="1526" height="350">

#### Review
<img src="{{site.baseurl}}/images/Classreview.PNG" alt="UCD Classreview" width="1526" height="300">

#### NavigationPane
<img src="{{site.baseurl}}/images/Classnavigation.PNG" alt="UCD Classnavigation" width="1526" height="200">

#### LogActivity
<img src="{{site.baseurl}}/images/Classlog.PNG" alt="UCD Classlog" width="1526" height="150">

#### RoomDatabase
<img src="{{site.baseurl}}/images/Classdatabase.PNG" alt="UCD Classdatabase" width="1526" height="250">

#### Category
<img src="{{site.baseurl}}/images/Classcategory.PNG" alt="UCD Classcategory" width="1526" height="120">

#### ActivityClass
<img src="{{site.baseurl}}/images/Classactivity.PNG" alt="UCD Classactivity" width="1526" height="180">


## Design Pattern 
<!--
One of the principles of object-oriented software is Encapsulation. It ensures that the different objects of our software can be varied and reused independently from each other. That means, relating to our app, that we need to separate the objects Review and LogActivity, where Review is dependet from the latter. Whenever the user logs a new activity, Review needs to be notified that there is something new that needs to be displayed. Hence, we need to maintain consistency between all dependent objects. We do not want to do this by coupling the classes very tightly, since that would reduce their reusability. 
According to those circumstances we decided to apply the Observer pattern to our app. It defines a one-to-many dependency between objects. That means, when one object changes its state, all objects dependent from it, are notified and updated automatically. <sup>[1]</sup>

#### The Observer Design Pattern
<img src="{{site.baseurl}}/images/Observer.JPG" alt="Observer Pattern" width="1526" height="180">
-->

The main object of our application is to manage logs of activity.  We have to create many objects of the same class with different variations like the start time, end time, duration, category, status, alert ... Normally, creating and assembling all parts of a complex object directly within a class itseft is stiff and not flexible; therefore we chose to use a creational pattern for our application for easier constructing and directing our objects.

Among many creational pattern, the *builder pattern* introduced by the *Gang of Four* stands out as flexible solution to various object creation problems. It helps solving the problem of creating different representations of a complex object and simplifying that process into customizing a normal object. The clear advantages <sup>[2]</sup> of the Builder pattern are:

* Allows you to vary a product’s internal representation.
* Encapsulates code for construction and representation.
* Provides control over steps of construction process.

The constructing can then be delegate to the builder class to acchieve the seperation of the construction and representation of every complex objects.  Below is a sample class diagram of a Builder pattern <sup>[3]</sup>

![Builder Pattern]({{site.baseurl}}/images/Builder_Pattern.jpg "Builder Pattern")

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

#### Resources
[1] -  Gama et al., Design Patterns - Elements of Reusable Object-Oriented Software, 2009, p. 293
[2] -  [www.classes.cs.uchicago.edu. Retrieved 2016-03-03.](https://www.classes.cs.uchicago.edu/archive/2010/winter/51023-1/presentations/ricetj_builder.pdf "Builder advantage")
[3] -  [The Builder design pattern - Structure and Collaboration. w3sDesign.com. Retrieved 2017-08-12.](http://w3sdesign.com/?gr=c02&ugr=struct "Builder link")
