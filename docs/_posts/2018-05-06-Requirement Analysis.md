---
layout: post
title: "Requirement Analysis & Use case Diagram"
date: 2018-05-06
---


In an attempt to create a clear definition of all the functions of the application we are ordered to program, we conduct several steps of requirement analysis:


## Studying User's needs

#### First Customer Meeting
In the run-up to the meeting, we thought about aspects which could be important for the activity monitoring app. After that, we short-listed some questions to get as much important information as possible from the customer. Furthermore, those questions could point out aspects which might have not been considered by the customer.

The following paragraph is and exemplary selection of questions asked during the meeting:
>  * Should the app work on-/ offline?
>  * With which Android versions should the app be compatible?
>  * Should the app be useable from lock screen?
>  * Should we provide the user to possibility to enable e-mail reports about his activities?
>  * What is the preferable interface for displaying logged activities?
>  * Are there any specific requirement for the app?

#### Group Observation
After the customer has presented a rough concept of the app, we asked both prepared and un-prepared questions in order to gain as much detail as possible. That lead to many material for conducting a requirement analysis. This has been done during an internal team meeting right after the customer meeting.

In a first step, we put the user stories into natural language requirements. Secondly, we extracted user stories out of those and added them to our ZenHub Scrum Board. Since some user stories are related to others or belong to a common functional area, we decided to create epics and assign user stories to them where appropriate.

After this process has been finished, we started reading out every single user story and each team member was asked for his or her understanding of it. This approach lead to a more precise phrasing of some user stories, and most importantly, to a lot of questions, which should be brought up during the second customer meeting.

Some unclear points that were founded are:
>  * Requirement for the name of the app.
>  * Screen size optimization.
>  * The ability of assigning color to categories and activities.
>  * Requirement of the security system

[comment]: <> (* Review the meeting in timeline manner)
[comment]: <> (* Dig more detail)
[comment]: <> (* Prepare for the next meeting)

#### Second Customer Meeting
As mentioned above, we have gathered a lot of questions to clarify some uncertainties about the requirements collected so far. Since we could not be sure that the time of the meeting would be sufficient to cover all our questions, we prioritized them. Questions which seemed to be very important in this early stage got top priority, while others, like the name of the app, have been considered as less important.

During the meeting, the customer recalled the requirement that it should be possible to delete categories and we have also been able to clarify all our questions. This helped to clear up the key functions of the application.
[comment]: <> (Our next team meeting was used to specify and complement the existing user stories as well as to validate some usecase diagrams. )

[comment]: <> (* Clear up the key function of the application)
[comment]: <> (* More questionaries)

#### Prototyping
* Draft Use case Diagram
* User story without point calculated

## Requirement Validation

#### Third Meeting
The third meeting with the customer should be used to present our use case diagrams and user stories. 
In contrast to the first two customer meetings, our team decided to set up an additional meeting right before the third one. This happened due to open discussions about the use case diagrams and to prepare the presentation of our user stories for the customer. This helped to enter the meeting with a common sense of what we want to present and achieve there.

The presentation of our user stories lead to two positive outcomes. Mainly the customer confirmed that we covered all currently known requirements with our user stories. Secondly, we have been able to clarify the detailed functionality to be provided by our app and clear up some more wrong assumptions that we have made. One of those was that we should not provide predefined activities to the user. The second one was related to the actual logging of the activity.
While we assumed the user can just press “start activity” or “stop activity” and the current system time will then be assigned to the chosen activity, the customer wants the manual specification of a start and end time of an activity to be mandatory for the user. That produced the content for our next team meeting. In the first few minutes we further specified our user stories. This has been followed by the creation of a rudimental draw, which shows the various areas to be created for the app. We used this to allocate work to groups consisting of two team members each, respecting our earlier agreement that at least two members should have knowledge about a specific topic. 

[comment]: <> (* we present the requirement in the form of user story and use case diagram.)
[comment]: <> (* Clear up the mis-understanding/mis-assumption)

#### Reflection:
The repeated customer meetings helped to get a common understanding of what the customer actually wants and what we are able to implement. This is meant to avoid incomplete requirements or wrong assumptions about requirements, which can lead to problems at later stages of our project work.

* The assumption about the length of any activity
* The misunderstanding about...

## Results

#### Descriptions of the functionality of the application in UML Use case diagrams
<img src="{{site.baseurl}}/images/LogActivity.JPG" alt="UCD LogActivity" width="1526" height="350">
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;*Use Case Diagram - Log Activity*

<img src="{{site.baseurl}}/images/Review.JPG" alt="UCD Review" width="1357" height="350">
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;*Use Case Diagram - Review Activities*

#### Descriptions of the understanding of the user perspective in (Epic) user stories
*Insert picture of Zenhub's backlog here*

#### Thank You for visiting our blog!! That’s all for now. Stay tuned for more updates on our project!!
