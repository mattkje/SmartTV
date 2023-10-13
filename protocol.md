# Protocol

Message are separated by newLine.

The client always sends a request first, the server responds.
There can be several requests/responses during one connection.

The following messages could be sent from point to server:
* "1" turn on the TV
* "0" turn off the TV
* "n" get the number of channels
* "c###" set current channel, where ### is the desired channel number ("c1", "c2", "c3"... "c255")

Server can respond with:
* "o" - performed the necessary action
* "c###" - report the number of channels, where ### is the number of channels, as a string
* "eM" - if the operation failed, where M is an error message. If the error message is 
  "Invalid channel number", the response will be "eInvalid channel number"