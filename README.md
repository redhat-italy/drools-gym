Drools-gym
==========

An empty drools project using CDI, Arquillian and following the correct modularizations patterns as in [Drools workshop](https://github.com/salaboy/drools-workshop)
Useful if you want to start using drools in 5 minutes AND in the correct way. 

* drools-gym-example
* drools-gym-kjar
* drools-gym-model
* drools-gym-tests

drools-gym-example
------------------
Contains just a simple CDI bootstrapped App executing the rules. Depends only by the model, expects to find the drools-gym-kjar.jar with the rules in local Maven repo

drools-gym-kjar
---------------
Contains only the rules, no dependencies from other modules.

drools-gym-model
----------------
A simple POJO used as a model.

drools-gym-tests
----------------
Runs the tests with Arquillian, depends only by the model and expects to find the drools-gym-kjar.jar with the rules in local Maven repo

Build instructions
==================

```shell
mvn clean install
```
