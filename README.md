# SmartTV - Group 15
_Contributing members: Matti Kjellstadli, Håkon Svensen Karlsen, Di Xie, Adrian Faustino Johansen_

## About This Program
This program was created as an assignment for a networking class at NTNU (IDATA2304).
Written below are the requirements of the assignment and a short user manual for the program itself.

This program sets up a TVserver with a given number of channels and a remote controller 
to control the server from a remote client. Only one controlller is allowed per TV, but there can
be several TVservers open at once.

## User Manual
To run the application from the IDE you first need to run the SmartTV class and then you need to run the 
RemoteControll class. This sets up both a server and a client connection. You are then able to either interact directly 
with the GUI as you normally would do when operating a TV or you can type directly into the client console.

To operate the client console you can type any of these commands:
```
1 - Turn the TV on
0 - Turn the TV off
n - See the available channels
c - change channel. "c2" changes it to channel 2, | ex C3,C4 etc...
+ - goes up one channel
- - goes down one channel
```
## The Assignment
This socket programming exercise consists of three parts. Your group needs to finish all three parts until the deadline. 
Also, you will need to discuss your solution with teachers during checkpoint C2 (tentative date: November 3rd).

## Part 1: Creating a Simple Smart TV Solution

n this part the point is to “get started” and create a solution according to the specification.
You are allowed to be as sloppy as you want with the code structure. But you will need to fix
it later ☺.

### Smart TV Specification

- The Smart TV has the following features:
    - By default, it's turned off and accepts only the "Turn ON" command.
    - It can be turned on and off.
    - It can report its power status (on or off).
    - When turned on, it provides:
        - Retrieval of the number of available channels (C) and channel switching within the range [1, C].
        - It remembers the last active channel after turning off, unless the server is restarted.
        - Setting the channel to a desired number (c) within the allowed range.

### Remote Control Features

- The Smart remote control is a TCP client with the following functions:
    - Turning the TV on/off.
    - Switching to the next or previous channel.
    - Displaying the current channel of the connected TV.


### Design and Implementation

- Both applications use TCP communication for interaction.
- You can extend the applications to include command-line or graphical interfaces for a more user-friendly experience.



## Part 2: Refactoring and Testing

In this part your task is to improve the structure of the source code according to good
practices. As a result of that, the code will become easier to test. You also need to write unit
tests.

## Part 3: Adding Multi-Client Support

Use multi-threading (and other techniques, if necessary) to extend the functionality of your
solution:
 - Allow multiple simultaneous client-connections on the server. I.e., one TV supports
   multiple remotes connected at the same time. If one remote “keeps the line busy”
   for a long time, this must not affect other connection of other clients.
 - Whenever one remote control switches a channel, other remotes should get a 
   notification about it right away. Implement this in an asynchronous way, without the
   clients polling the server all the time

## Deadline

You need to finish all three parts of this exercise before checkpoint C2 (tentative date: Friday
on Week 44, November 3rd)