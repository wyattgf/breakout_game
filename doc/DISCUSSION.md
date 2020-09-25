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


### Refactoring Plan

 * What are the code's biggest issues?

The code's biggest issues are long methods (due to lack of inheritance, where necessary).  Additionally, many of our methods are lacking in JavaDoc comments.
 * Which issues are easy to fix and which are hard?

It will be significantly easier to add JavaDoc comments to our existing methods than it will be to refactor our code to support a greater use of polymorphism/inheritance.   
 * What are good ways to implement the changes "in place"?


### Refactoring Work

 * Issue chosen: Fix and Alternatives


 * Issue chosen: Fix and Alternatives