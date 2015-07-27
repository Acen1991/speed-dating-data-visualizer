Speed Dating Data Visualizer
============

====BEFORE====
=

This one week project has been implemented by a group 4 students during a datavis course in 2013. 
Orginally, we wrote the whole code in Java (sorry for the uglyness of the code, we had to produce something fast) by taking advantage of Processing library. The goal was to provide a visualization tool for a specific dataset (http://www.stat.columbia.edu/~gelman/arm/examples/speed.dating/ and http://flowingdata.com/2008/02/06/speed-dating-data-attractiveness-sincerity-intelligence-hobbies/).

Briefly, hundreds of men and women in Columbia University have been invited to participate to a speed dating experience where one would ask them questions after each date. So the dataset is substantial with over 8,000 observations for answers to twenty something survey questions. Here is a sample of questions:

"How do you measure up?"
"What do you look for in the opposite sex?".
"On a scale of 1 – 10, how much do you rate the previous partner ?"

====TODAY====
=
I am currently porting the UI to a NodeJs-Angular-D3 webapp for ease of use and for sharing the stuff we made with the world ;) here

Simply put, after the whole refactoring stuff, this java app processes data from a csv file to produce a set of a JSON files (see example here) so that the Webapp could use it easily.
