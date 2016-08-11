Scala BCrypt
============

[![Build Status](https://travis-ci.org/rmleon/scalaBCrypt.svg?branch=master)](https://travis-ci.org/rmleon/scalaBCrypt)

Scala port of a Java BCrypt library.

Goals:
------
1. Have a version of BCrypt in Scala
2. Learn how it works

TODO:
-----
1. Implement Version 2b
2. Better testing

How to Use:
-----------
```scala

import ft.crypt.bcrypt.BCrypt
val plain = "YourPlainPassword"
// Generate the salt.  Can take log rounds (between 2 and 30, where 30 will be the hardest and will take a long time)
val salt = BCrypt.generateSalt
val hashed = BCrypt.hashPassword(plain, salt)
val passwordsMatch = BCrypt.checkPassword(plain, hashed)
if (passwordsMatch) {
  println("Congrats!  You can go in")
} else {
  println("Call the police!")
}
```
