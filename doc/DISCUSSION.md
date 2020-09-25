## Lab Discussion
### Team: 12
### Names: Wyatt Focht (wgf6), Hosam Tageldin (ht120)


### Issues in Current Code

#### Method or Class
 * Design issues
 
Level: Currently there is no inheritance for implementation for different levels.  The current level class should be made abstract with several level sublcasses extending it.  This might also involve implementing a levelHandler that controls which level the game is currently displaying/playing.
 * Design issue

Cheat key switch tree: Currently, this is the longest method in the program.  As of right now, we see no easy way of slimming it down, but regardless, a method of this length is always a code smell.
#### Method or Class
 * Design issues

Currently, the game class is responsible for slightly more functionality than just itself.  For instance, after being created, the blocks for a certain level are added into the root of the game within the game class.  While design like this is acceptable, it would be better to extract such functionality into a different class.

 * Design issue
There are a few magic values throughout the program. This is a bad design practice and is something we intend to fix in this lab.

### Refactoring Plan

 * What are the code's biggest issues?

The code's biggest issues are long methods (due to lack of inheritance, where necessary).  Additionally, many of our methods are lacking in JavaDoc comments.
 * Which issues are easy to fix and which are hard?

It will be significantly easier to add JavaDoc comments to our existing methods than it will be to refactor our code to support a greater use of polymorphism/inheritance.   
It will also be a quick fix to fix the magic numbers that occur in a few places within our program.
 * What are good ways to implement the changes "in place"?


### Refactoring Work

 * Issue chosen: Fix and Alternatives
 
Fixing the magic numbers by moving them into constant names and then calling the constant inside the function. This is the most obvious fix with the type of
magic numbers that we currently have but there are other fixes to magic numbers depending on the type of problem.

 * Issue chosen: Fix and Alternatives
 
 Fixing missing JavaDoc comments by going through the program, class by class, and adding new comments.  There are not any alternatives to this, as JavaDocs must be done in exactly a certain way.